package com.cronutils.dataset;

public class DatasetMain {
    public static void main(String[] args) {
        int instancesCount = 250000;
        buildDataset01(ISO639.EN, instancesCount);
        buildDataset02(ISO639.EN, instancesCount);
        buildDataset03(ISO639.EN, instancesCount);
    }

    private static void buildDataset01(ISO639 lang, int instancesCount){
        new DatasetBuilder(ISO639.EN, TemplateMappingStrategy.getCronExpressionToTarget(), TemplateEnum.TEMPLATE_01).build(instancesCount).export(String.format("dataset-01-cronutils_en2%s-%s.psv", lang.toString().toLowerCase(), instancesCount));
    }

    private static void buildDataset02(ISO639 lang, int instancesCount){
        new DatasetBuilder(ISO639.EN, TemplateMappingStrategy.getUtilsDescriptionToTargetDescriptionWithTargetReplacement(), TemplateEnum.TEMPLATE_01).build(instancesCount).export(String.format("dataset-02-cronutils_en2%s-%s.psv", lang.toString().toLowerCase(), instancesCount));
    }

    private static void buildDataset03(ISO639 lang, int instancesCount){
        new DatasetBuilder(ISO639.EN, TemplateMappingStrategy.getUtilsDescriptionToTargetDescription(), TemplateEnum.TEMPLATE_01).build(instancesCount).export(String.format("dataset-03-cronutils_en2%s-%s.psv", lang.toString().toLowerCase(), instancesCount));
    }
}