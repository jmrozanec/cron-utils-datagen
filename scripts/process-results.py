from nltk.translate.bleu_score import corpus_bleu, SmoothingFunction
from nltk.util import ngrams
from random import shuffle
import datetime as dt
import numpy as np
import random
import os
import re

time_start = dt.datetime.now()

dataset_name="dataset-cronutils_en2en-250000-set01.psv"
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

#for idx in range(0, len(test_lines)):
#    if(test_lines[idx] != '' and (test_lines[idx].split(separator)[1] != result_lines[idx].split(separator)[1])):
#        print("'{}' vs '{}'".format(test_lines[idx].split(separator)[1], result_lines[idx].split(separator)[1]))

def get_tokenized(sentence):
    sentence = sentence.lower().strip()
    sentence = re.sub(r'[^a-zA-Z0-9\s]', ' ', sentence)
    tokens = [token for token in sentence.split(" ") if token != ""]
    return tokens

def get_corpus_bleu(sources, translations):
    references=[]
    candidates=[]
    for idx in range(0, len(sources)):
        if(test_lines[idx]!=''):
            references.append(get_tokenized(sources[idx]))
            candidates.append(get_tokenized(translations[idx]))
    #return corpus_bleu(references, candidates, weights=(0.45, 0.55))
    weighting = SmoothingFunction()
    return corpus_bleu(references, candidates, smoothing_function=weighting.method4)

sources=[x.split(separator)[1].strip().lower() for x in test_lines if x!='']
translations=[x.split(separator)[1].strip().lower() for x in result_lines if x!='']

print(get_corpus_bleu(sources, translations))
