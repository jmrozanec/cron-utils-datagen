package com.cronutils.dataset;

public class DatasetMain {
    public static void main(String[] args) {
        int instances = 250000;
        new DatasetBuilder(ISO639.EN).build(instances).export("dataset-cronutils_en2en.psv");
        new DatasetBuilder(ISO639.ES).build(instances).export("dataset-cronutils_en2es.psv");
        new DatasetBuilder(ISO639.SI).build(instances).export("dataset-cronutils_en2si.psv");
    }
}
