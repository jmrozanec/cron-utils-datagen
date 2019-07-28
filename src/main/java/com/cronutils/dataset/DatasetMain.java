package com.cronutils.dataset;

import com.cronutils.model.CronType;
import com.cronutils.model.definition.CronDefinitionBuilder;

public class DatasetMain {
    public static void main(String[] args) {
        int instancesCount = 250000;
        options01(ISO639.EN, instancesCount, CronDescriptionTemplate.TEMPLATE_01);
        options02(ISO639.EN, instancesCount, CronDescriptionTemplate.TEMPLATE_01);
        options03(ISO639.EN, instancesCount, CronDescriptionTemplate.TEMPLATE_01);

        options01(ISO639.EN, instancesCount, CronDescriptionTemplate.TEMPLATE_02);
        options02(ISO639.EN, instancesCount, CronDescriptionTemplate.TEMPLATE_02);
        options03(ISO639.EN, instancesCount, CronDescriptionTemplate.TEMPLATE_02);
    }


    /**
     * @param lang - target language
     * @param instancesCount - how many instances we aim for at the dataset
     */
    private static void options01(ISO639 lang, int instancesCount, CronDescriptionTemplate template){
        DatasetOptions options = DatasetOptionsBuilder.getInstance()
                .forCronDefinition(CronDefinitionBuilder.instanceDefinitionFor(CronType.QUARTZ))
                .withTargetLanguage(lang)
                .withCronTemplateProcessor(CronTemplateProcessor.getNullCronTemplateProcessor())
                .withCronKey(ValueSelectionStrategy.getCronTemplateInstance())
                .withHeuristicCronDescriptionProcessor(HeuristicCronDescriptionProcessor.getNullHeuristicCronDescriptionProcessor())
                .withHeuristicDescription(ValueSelectionStrategy.getCronTemplateInstance())
                .withHumanDescription(ValueSelectionStrategy.getHumanDescription())
                .withTemplate(template)
                .build();
        new DatasetGenerator(options).build(instancesCount).visit(DatasetVisitor.exportPipedFileDatasetVisitor(String.format("dataset-01-cronutils_en2%s-%s.psv", lang.toString().toLowerCase(), instancesCount)));
    }

    /**
     * @param lang - target language
     * @param instancesCount - how many instances we aim for at the dataset
     */
    private static void options02(ISO639 lang, int instancesCount, CronDescriptionTemplate template){
        DatasetOptions options = DatasetOptionsBuilder.getInstance()
                .forCronDefinition(CronDefinitionBuilder.instanceDefinitionFor(CronType.QUARTZ))
                .withTargetLanguage(lang)
                .withCronTemplateProcessor(CronTemplateProcessor.getNullCronTemplateProcessor())
                .withCronKey(ValueSelectionStrategy.getCronTemplateInstance())
                .withHeuristicCronDescriptionProcessor(HeuristicCronDescriptionProcessor.getNullHeuristicCronDescriptionProcessor())
                .withHeuristicDescription(ValueSelectionStrategy.getHeuristicDescription())
                .withHumanDescription(ValueSelectionStrategy.getHumanDescription())
                .withTemplate(template)
                .build();
        new DatasetGenerator(options).build(instancesCount).visit(DatasetVisitor.exportPipedFileDatasetVisitor(String.format("dataset-01-cronutils_en2%s-%s.psv", lang.toString().toLowerCase(), instancesCount)));
    }

    /**
     * @param lang - target language
     * @param instancesCount - how many instances we aim for at the dataset
     */
    private static void options03(ISO639 lang, int instancesCount, CronDescriptionTemplate template){
        DatasetOptions options = DatasetOptionsBuilder.getInstance()
                .forCronDefinition(CronDefinitionBuilder.instanceDefinitionFor(CronType.QUARTZ))
                .withTargetLanguage(lang)
                .withCronTemplateProcessor(CronTemplateProcessor.getNullCronTemplateProcessor())
                .withCronKey(ValueSelectionStrategy.getCronTemplate())
                .withHeuristicCronDescriptionProcessor(HeuristicCronDescriptionProcessor.getNullHeuristicCronDescriptionProcessor())
                .withHeuristicDescription(ValueSelectionStrategy.getCronTemplate())
                .withHumanDescription(ValueSelectionStrategy.getHumanDescription())
                .withTemplate(template)
                .build();
        new DatasetGenerator(options).build(instancesCount).visit(DatasetVisitor.exportPipedFileDatasetVisitor(String.format("dataset-01-cronutils_en2%s-%s.psv", lang.toString().toLowerCase(), instancesCount)));
    }
}