import random
from random import shuffle
import datetime as dt
import os

time_start = dt.datetime.now()

dataset_name="dataset-cronutils_en2en-250000-set04.psv"
dataset_path="../datasets/{}".format(dataset_name)
results_path="results-{}".format(dataset_name)
best_model_checkpoint_name="{}-{}-best.h5".format("cron-descriptor", dataset_name)
total_samples = len(list(open(dataset_path)))
print("We have a total of {} samples".format(total_samples))

separator="|"

lines = open(dataset_path).read().split('\n')

train_size = (int)(total_samples*0.8)
test_size = total_samples-train_size

test_lines = [x for x in lines[train_size+1:len(lines)]]
result_lines = [x for x in lines[train_size+1:len(lines)]]

print("We have a total of {} test lines and {} test results.".format(len(test_lines), len(result_lines)))

for idx in range(0, len(test_lines)):
    if(test_lines[idx] != '' and (test_lines[idx].split(separator)[1] != result_lines[idx].split(separator)[1])):
        print("'{}' vs '{}'".format(test_lines[idx].split(separator)[1], result_lines[idx].split(separator)[1]))
