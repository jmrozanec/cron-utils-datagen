package com.cronutils.dataset;

import com.cronutils.model.CronType;
import com.cronutils.model.definition.CronDefinitionBuilder;

public class DatasetMain {
    public static void main(String[] args) {
        int instancesCount = 250000;
        options01(ISO639.EN, instancesCount);
    }


    /**
     * @param lang - target language
     * @param instancesCount - how many instances we aim for at the dataset
     */
    private static void options01(ISO639 lang, int instancesCount){
        DatasetOptions options = DatasetOptionsBuilder.getInstance()
                .forCronDefinition(CronDefinitionBuilder.instanceDefinitionFor(CronType.QUARTZ))
                .withTargetLanguage(lang)
                .withCronTemplateProcessor(CronTemplateProcessor.getNullCronTemplateProcessor())
                .withCronKey(CronKeySelectionStrategy.getCronTemplate())
                .withHeuristicCronDescriptionProcessor(HeuristicCronDescriptionProcessor.getNullHeuristicCronDescriptionProcessor())
                .withHeuristicDescription(HeuristicCronDescriptionSelectionStrategy.getHeuristicDescription())
                .withHumanDescription(HumanCronDescriptionSelectionStrategy.getHumanDescription())
                .withTemplate(CronDescriptionTemplate.TEMPLATE_01)
                .build();
        new DatasetGenerator(options).build(instancesCount).visit(DatasetVisitor.exportPipedFileDatasetVisitor(String.format("dataset-01-cronutils_en2%s-%s.psv", lang.toString().toLowerCase(), instancesCount)));
    }

    /**
     * @param lang - target language
     * @param instancesCount - how many instances we aim for at the dataset
     */
    private static void options02(ISO639 lang, int instancesCount){
        DatasetOptions options = DatasetOptionsBuilder.getInstance()
                .forCronDefinition(CronDefinitionBuilder.instanceDefinitionFor(CronType.QUARTZ))
                .withTargetLanguage(lang)
                .withCronTemplateProcessor(CronTemplateProcessor.getNullCronTemplateProcessor())
                .withCronKey(CronKeySelectionStrategy.getCronTemplate())
                .withHeuristicCronDescriptionProcessor(HeuristicCronDescriptionProcessor.getNullHeuristicCronDescriptionProcessor())
                .withHeuristicDescription(HeuristicCronDescriptionSelectionStrategy.getHeuristicDescription())
                .withHumanDescription(HumanCronDescriptionSelectionStrategy.getHumanDescription())
                .withTemplate(CronDescriptionTemplate.TEMPLATE_01)
                .build();
        new DatasetGenerator(options).build(instancesCount).visit(DatasetVisitor.exportPipedFileDatasetVisitor(String.format("dataset-01-cronutils_en2%s-%s.psv", lang.toString().toLowerCase(), instancesCount)));
    }
}