# cron-utils-datagen
Provides means to create datasets for cron descriptions in various languages. We provide a Java application, that generates dataset always following the same random pattern and based on a given CRON expressions template. Currently only QUARTZ expressions and translations to English language are given.

The application allows for different configurations, so that we gain flexibility on dataset output: if we are willing to identify CRON patterns to description patterns, CRON instances to description patterns, CRON instances to full descriptions or the other way around.

This repository also provides scripts to train, evaluate and debug the models. Reference architecture is a basic Encoder-Decoder LSTM. 
