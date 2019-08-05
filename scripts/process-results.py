import click
from nltk.translate.bleu_score import sentence_bleu, corpus_bleu, SmoothingFunction
from nltk.util import ngrams
from random import shuffle
import datetime as dt
import numpy as np
import random
import os
import re

def get_tokenized(sentence):
    sentence = sentence.lower().strip()
    sentence = re.sub(r'[^a-zA-Z0-9\s]', ' ', sentence)
    tokens = [token for token in sentence.split(" ") if token != ""]
    return tokens

def get_sentence_bleu(source, translation):
    #Smoothing methods described here: https://kite.com/python/docs/nltk.translate.bleu_score.SmoothingFunction
    smoothing_method = SmoothingFunction().method3
    if(source==translation):
        return 1.0
    return sentence_bleu(get_tokenized(source), get_tokenized(translation), smoothing_function=smoothing_method)

def get_corpus_bleu(sources, translations):
    references=[]
    candidates=[]
    scores=[]
    for idx in range(0, len(sources)):
        if(sources[idx]!=''):
            #references.append(get_tokenized(sources[idx]))
            #candidates.append(get_tokenized(translations[idx]))
            scores.append(get_sentence_bleu(sources[idx], translations[idx]))
    print("{} {}".format(round(np.mean(scores), 4), round(np.std(scores), 4)))
    return round(np.mean(scores), 4)
    #return round(corpus_bleu(references, candidates, smoothing_function=smoothing_method),4)

@click.command()
@click.option('--dataset_name', help='Dataset name')
def evaluate(dataset_name):
    print("Evaluating dataset {}".format(dataset_name))
    time_start = dt.datetime.now()
    dataset_path="../datasets/{}".format(dataset_name)
    results_path="../results/results-{}".format(dataset_name)
    best_model_checkpoint_name="{}-{}-best.h5".format("cron-descriptor", dataset_name)
    total_samples = len(list(open(dataset_path)))
    print("We have a total of {} samples".format(total_samples))

    separator="|"

    reference_lines = open(dataset_path).read().split('\n')
    translation_lines = open(results_path).read().split('\n')

    train_size = (int)(total_samples*0.8)
    test_size = total_samples-train_size

    test_lines = [x for x in reference_lines[train_size+1:len(reference_lines)]]
    result_lines = [x.replace("'", "").replace("=", separator) for x in translation_lines]

    print("We have a total of {} test lines and {} test results.".format(len(test_lines), len(result_lines)))

    sources=[x.split(separator)[1].strip().lower() for x in test_lines if x!='']
    translations=[x.split(separator)[1].strip().lower() for x in result_lines if x!='']

    sourcekeys=[x.split(separator)[0].strip().lower() for x in test_lines if x!='']
    translkeys=[x.split(separator)[0].strip().lower() for x in result_lines if x!='']
    mismatch_counter=0
    for idx in range(0, len(sourcekeys)):
        if(sources[idx]!=translations[idx]):
            print("'{}': {} -> {}".format(sourcekeys[idx], sources[idx], translations[idx]))
            mismatch_counter=mismatch_counter+1

    print("{}/{}".format(mismatch_counter, len(translations)))
    correct_ratio=round(1-(float(mismatch_counter)/float(len(translations))), 2)
    bleu_score=get_corpus_bleu(sources, translations)
    time_end = dt.datetime.now()
    print("# We invested {} seconds to compute results for single model. BLEU score is: {} We got {} right".format(int((time_end-time_start).total_seconds()), bleu_score, correct_ratio))

if __name__ == '__main__':
    evaluate()
