package com.cronutils.dataset;

public class DatasetMain {
    public static void main(String[] args) {
        int instances = 250000;
        new DatasetBuilder(ISO639.EN, TemplateMappingStrategy.getCronExpressionToTarget(), TemplateEnum.TEMPLATE_01).build(instances).export("dataset-cronutils_en2en.psv");
        //new DatasetBuilder(ISO639.ES, TemplateMappingStrategy.getCronExpressionToTarget(), TemplateEnum.TEMPLATE_01).build(instances).export("dataset-cronutils_en2es.psv");
        //new DatasetBuilder(ISO639.SI, TemplateMappingStrategy.getCronExpressionToTarget(), TemplateEnum.TEMPLATE_01).build(instances).export("dataset-cronutils_en2si.psv");
    }
}
