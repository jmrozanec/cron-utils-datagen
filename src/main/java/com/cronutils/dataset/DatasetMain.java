package com.cronutils.dataset;

import com.cronutils.model.CronType;
import com.cronutils.model.definition.CronDefinitionBuilder;

public class DatasetMain {
    public static void main(String[] args) {
        int instancesCount = 250000;
        options01(ISO639.EN, instancesCount, CronDescriptionTemplate.TEMPLATE_01, "set01-temp01");
        options02(ISO639.EN, instancesCount, CronDescriptionTemplate.TEMPLATE_01, "set02-temp01");
        options03(ISO639.EN, instancesCount, CronDescriptionTemplate.TEMPLATE_01, "set03-temp01");

        options01(ISO639.EN, instancesCount, CronDescriptionTemplate.TEMPLATE_02, "set01-temp02");
        options02(ISO639.EN, instancesCount, CronDescriptionTemplate.TEMPLATE_02, "set02-temp02");
        options03(ISO639.EN, instancesCount, CronDescriptionTemplate.TEMPLATE_02, "set03-temp02");
    }


    /**
     * @param lang - target language
     * @param instancesCount - how many instances we aim for at the dataset
     */
    private static void options01(ISO639 lang, int instancesCount, CronDescriptionTemplate template, String dataset_id){
        DatasetOptions options = DatasetOptionsBuilder.getInstance()
                .forCronDefinition(CronDefinitionBuilder.instanceDefinitionFor(CronType.QUARTZ))
                .withTargetLanguage(lang)

                .withCronKey(ValueSelectionStrategy.getCronTemplateInstance())
                .withHeuristicDescription(ValueSelectionStrategy.getCronTemplateInstance())
                .withHumanDescription(ValueSelectionStrategy.getHumanDescription())

                .withCronTemplateProcessor(CronTemplateProcessor.getNullCronTemplateProcessor())
                .withHeuristicCronDescriptionProcessor(HeuristicCronDescriptionProcessor.getNullHeuristicCronDescriptionProcessor())

                .withTemplate(template)
                .build();
        new DatasetGenerator(options).build(instancesCount).visit(DatasetVisitor.exportPipedFileDatasetVisitor(String.format("dataset-cronutils_en2%s-%s-%s.psv", lang.toString().toLowerCase(), instancesCount, dataset_id)));
    }

    /**
     * @param lang - target language
     * @param instancesCount - how many instances we aim for at the dataset
     */
    private static void options02(ISO639 lang, int instancesCount, CronDescriptionTemplate template, String dataset_id){
        DatasetOptions options = DatasetOptionsBuilder.getInstance()
                .forCronDefinition(CronDefinitionBuilder.instanceDefinitionFor(CronType.QUARTZ))
                .withTargetLanguage(lang)

                .withCronKey(ValueSelectionStrategy.getCronTemplateInstance())
                .withHeuristicDescription(ValueSelectionStrategy.getHeuristicDescription())
                .withHumanDescription(ValueSelectionStrategy.getHumanDescription())

                .withCronTemplateProcessor(CronTemplateProcessor.getNullCronTemplateProcessor())
                .withHeuristicCronDescriptionProcessor(HeuristicCronDescriptionProcessor.getNullHeuristicCronDescriptionProcessor())

                .withTemplate(template)
                .build();
        new DatasetGenerator(options).build(instancesCount).visit(DatasetVisitor.exportPipedFileDatasetVisitor(String.format("dataset-cronutils_en2%s-%s-%s.psv", lang.toString().toLowerCase(), instancesCount, dataset_id)));
    }

    /**
     * @param lang - target language
     * @param instancesCount - how many instances we aim for at the dataset
     */
    private static void options03(ISO639 lang, int instancesCount, CronDescriptionTemplate template, String dataset_id){
        DatasetOptions options = DatasetOptionsBuilder.getInstance()
                .forCronDefinition(CronDefinitionBuilder.instanceDefinitionFor(CronType.QUARTZ))
                .withTargetLanguage(lang)

                .withCronKey(ValueSelectionStrategy.getCronTemplateTransformed())
                .withHeuristicDescription(ValueSelectionStrategy.getCronTemplateTransformed())
                .withHumanDescription(ValueSelectionStrategy.getHumanDescription())

                .withCronTemplateProcessor(CronTemplateProcessor.replaceAllButListsAndRanges())
                .withHeuristicCronDescriptionProcessor(HeuristicCronDescriptionProcessor.getNullHeuristicCronDescriptionProcessor())

                .withTemplate(template)
                .build();
        new DatasetGenerator(options).build(instancesCount).visit(DatasetVisitor.exportPipedFileDatasetVisitor(String.format("dataset-cronutils_en2%s-%s-%s.psv", lang.toString().toLowerCase(), instancesCount, dataset_id)));
    }
}