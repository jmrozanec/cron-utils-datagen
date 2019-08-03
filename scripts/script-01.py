import click
from keras.callbacks import ModelCheckpoint, TensorBoard, EarlyStopping
from keras.layers import Input, LSTM, Dense
from keras.models import load_model
from keras.models import Model
import random
from random import shuffle
import numpy as np
import datetime as dt
import os


def create_source_target_lists(lines, separator):
    expr_source = []
    expr_target = []
    for line in range(len(lines)):
        if len(lines[line])!=0:
            source = str(lines[line]).split(separator)[0]
            # We use tab separator to indicate start of sentence, and carriage return ('\n') to indicate end of it
            # This two characters do not appear in cron expressions and descriptions.
            target = '\t' + str(lines[line]).split(separator)[1] + '\n'
            expr_source.append(source)
            expr_target.append(target)
    return expr_source, expr_target

def create_dictionary(lines):
    charsset = set()
    for line in lines:
        for ch in line:
            if(ch not in charsset):
                charsset.add(ch)
    return charsset

def index_characters(characters_list):
    index2char = {}
    char2index = {}
    for k, v in enumerate(characters_list):
        index2char[k] = v
        char2index[v] = k
    return index2char, char2index

def create_tokenized_expression(samples_count, max_len_expr, characters_list):
    return np.zeros(shape = (samples_count, max_len_expr, len(characters_list)), dtype='float32')

def vectorize_expressions(expressions_source, expressions_target, char2index_source, char2index_target, tokenized_expressions_source, tokenized_expressions_target, target_data):
    for i in range(len(expressions_source)):
        for k,ch in enumerate(expressions_source[i]):
            tokenized_expressions_source[i, k, char2index_source[ch]] = 1
        for k,ch in enumerate(expressions_target[i]):
            tokenized_expressions_target[i, k, char2index_target[ch]] = 1
            if k > 0:
                target_data[i, k-1, char2index_target[ch]] = 1
    return tokenized_expressions_source, tokenized_expressions_target, target_data


def decode_seq(encoder, decoder, index2char_source, index2char_target, charsset_target, input_sequence):
    states_val = encoder.predict(input_sequence)

    target_seq = np.zeros((1, 1, len(charsset_target)))
    target_seq[0, 0, char2index_target['\t']] = 1

    translation = ''
    stop_condition = False

    while not stop_condition:
        decoder_out, decoder_h, decoder_c = decoder.predict(x=[target_seq] + states_val)

        max_val_index = np.argmax(decoder_out[0,-1,:])
        sampled_target_char = index2char_target[max_val_index]
        translation += sampled_target_char

        if ( (sampled_target_char == '\n') or (len(translation) > max_len_expr_target)) :
            stop_condition = True

        target_seq = np.zeros((1, 1, len(charsset_target)))
        target_seq[0, 0, max_val_index] = 1

        states_val = [decoder_h, decoder_c]
    return translation


@click.command()
@click.option('--epochs', default=5, help='Epochs')
@click.option('--dataset_path', help='Dataset path')
def train_model(epochs, dataset_path):
    time_start = dt.datetime.now()

    dataset_name=os.path.basename(dataset_path)
    best_model_checkpoint_name="{}-{}-best.h5".format("cron-descriptor", dataset_name)
    total_samples = len(list(open(dataset_path)))
    print("We have a total of {} samples".format(total_samples))

    separator="|"

    lines = open(dataset_path).read().split('\n')

    train_size = (int)(total_samples*0.8)
    test_size = total_samples-train_size

    train_lines = [x for x in lines[0:train_size]]
    test_lines = [x for x in lines[train_size+1:len(lines)]]

    print("We have a total of {} train and {} test cases.".format(len(train_lines), len(test_lines)))

    expr_source, expr_target = create_source_target_lists(train_lines, separator)
    charsset_source = sorted(list(create_dictionary(expr_source)))
    charsset_target = sorted(list(create_dictionary(expr_target)))

    index2char_source, char2index_source = index_characters(charsset_source)
    index2char_target, char2index_target = index_characters(charsset_target)

    max_len_expr_source = max([len(line) for line in expr_source])
    max_len_expr_target = max([len(line) for line in expr_target])

    tokenized_expressions_source = create_tokenized_expression(train_size, max_len_expr_source, charsset_source)
    tokenized_expressions_target = create_tokenized_expression(train_size, max_len_expr_target, charsset_target)
    target_data = np.zeros((train_size, max_len_expr_target, len(charsset_target)), dtype='float32')

    tokenized_expressions_source, tokenized_expressions_target, target_data = vectorize_expressions(expr_source, expr_target, char2index_source, char2index_target, tokenized_expressions_source, tokenized_expressions_target, target_data)


    # Encoder
    encoder_input = Input(shape=(None,len(charsset_source)), name="encoder-input")
    encoder_LSTM = LSTM(256, return_state = True, name="encoder-LSTM")
    encoder_outputs, encoder_h, encoder_c = encoder_LSTM(encoder_input)
    encoder_states = [encoder_h, encoder_c]

    # Decoder
    decoder_input = Input(shape=(None,len(charsset_target)), name="decoder-input")
    decoder_LSTM = LSTM(256,return_sequences=True, return_state = True, name="decoder-LSTM")
    decoder_out, decoder_h, decoder_c = decoder_LSTM(decoder_input, initial_state=encoder_states)
    print("Encoder")
    print(decoder_h)
    print("Decoder")
    print(decoder_c)
    decoder_dense = Dense(len(charsset_target),activation='softmax', name="decoder-dense")
    decoder_out = decoder_dense(decoder_out)

    model = Model(inputs=[encoder_input, decoder_input],outputs=[decoder_out])

    model.compile(optimizer='adam', loss='categorical_crossentropy')
    checkpoint = ModelCheckpoint(best_model_checkpoint_name, monitor='val_loss', verbose=1, save_best_only=True, mode='min')
    callbacks_list = [checkpoint]
    model.fit(x=[tokenized_expressions_source, tokenized_expressions_target], y=target_data, batch_size=64, callbacks=callbacks_list, epochs=epochs, validation_split=0.2)


    time_end = dt.datetime.now()
    print("# We trained the model for {} seconds".format((time_end-time_start).total_seconds()))

    # Load best model
    best_model = load_model(best_model_checkpoint_name)

    # Recreate encoder and decoder based on serialized model
    encoder_inputs = model.input[0]
    encoder_outputs, state_h_enc, state_c_enc = model.layers[2].output
    encoder_states = [state_h_enc, state_c_enc]
    encoder_model = Model(encoder_inputs, encoder_states)

    decoder_inputs = model.input[1]
    decoder_state_input_h = Input(shape=(256,), name='input_3')
    decoder_state_input_c = Input(shape=(256,), name='input_4')
    decoder_states_inputs = [decoder_state_input_h, decoder_state_input_c]
    decoder_lstm = model.layers[3]
    decoder_outputs, state_h_dec, state_c_dec = decoder_lstm(decoder_inputs, initial_state=decoder_states_inputs)
    decoder_states = [state_h_dec, state_c_dec]
    decoder_dense = model.layers[4]
    decoder_outputs = decoder_dense(decoder_outputs)
    decoder_model = Model([decoder_inputs] + decoder_states_inputs, [decoder_outputs] + decoder_states)


    expr_source_test, expr_target_test = create_source_target_lists(test_lines, separator)
    tokenized_expressions_source_test = create_tokenized_expression(test_size, max_len_expr_source, charsset_source)
    tokenized_expressions_target_test = create_tokenized_expression(test_size, max_len_expr_target, charsset_target)
    target_data_test = np.zeros((test_size, max_len_expr_target, len(charsset_target)), dtype='float32')

    tokenized_expressions_source_test, tokenized_expressions_target_test, target_data_test = vectorize_expressions(expr_source_test, expr_target_test, char2index_source, char2index_target, tokenized_expressions_source_test, tokenized_expressions_target_test, target_data_test)

    with open("results-{}".format(dataset_name), 'w') as output_file:
        for seq_index in range(len(expr_source_test)):
            input_sequence = tokenized_expressions_source_test[seq_index:seq_index+1]
            translation = decode_seq(encoder_model, decoder_model, index2char_source, index2char_target, charsset_target, input_sequence)
            output_file.write("'{}'='{}'\n".format(expr_source_test[seq_index], translation.replace("\n", "")))
        output_file.flush()
    output_file.close()

if __name__ == '__main__':
    train_model()
