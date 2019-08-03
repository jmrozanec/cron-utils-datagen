package com.cronutils.dataset;

import com.cronutils.model.CronType;
import com.cronutils.model.definition.CronDefinitionBuilder;

public class DatasetMain {
    public static void main(String[] args) {
        int instancesCount = 100;//250000;
        //cron instance -> human template
        options01(ISO639.EN, instancesCount, CronDescriptionTemplate.TEMPLATE_01, "set01");
        //cron instance -> human template with KEYWORDS replaced
        options02(ISO639.EN, instancesCount, CronDescriptionTemplate.TEMPLATE_01, "set02");
        //cron instance with LIST or RANGE keywords -> human template
        options03(ISO639.EN, instancesCount, CronDescriptionTemplate.TEMPLATE_01, "set03");
        //cron instance with LIST or RANGE keywords -> human template with KEYWORDS replaced
        options04(ISO639.EN, instancesCount, CronDescriptionTemplate.TEMPLATE_01, "set04");
    }

    /**
     * cron instance -> human template
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
     * cron instance -> human template with KEYWORDS replaced
     * @param lang - target language
     * @param instancesCount - how many instances we aim for at the dataset
     */
    private static void options02(ISO639 lang, int instancesCount, CronDescriptionTemplate template, String dataset_id){
        DatasetOptions options = DatasetOptionsBuilder.getInstance()
                .forCronDefinition(CronDefinitionBuilder.instanceDefinitionFor(CronType.QUARTZ))
                .withTargetLanguage(lang)

                .withCronKey(ValueSelectionStrategy.getCronTemplateInstance())
                .withHeuristicDescription(ValueSelectionStrategy.getCronTemplateInstance())
                .withHumanDescription(ValueSelectionStrategy.getHumanDescriptionTransformed())

                .withCronTemplateProcessor(CronTemplateProcessor.getNullCronTemplateProcessor())
                .withHeuristicCronDescriptionProcessor(HeuristicCronDescriptionProcessor.getNullHeuristicCronDescriptionProcessor())
                .withHumanCronDescriptionProcessor(HumanCronDescriptionProcessorFactory.getHumanCronDescriptionProcessor())

                .withTemplate(template)
                .build();
        new DatasetGenerator(options).build(instancesCount).visit(DatasetVisitor.exportPipedFileDatasetVisitor(String.format("dataset-cronutils_en2%s-%s-%s.psv", lang.toString().toLowerCase(), instancesCount, dataset_id)));
    }

    /**
     * cron instance with LIST and RANGE keywords -> human template
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

    /**
     * cron instance with LIST and RANGE keywords -> human template
     * @param lang - target language
     * @param instancesCount - how many instances we aim for at the dataset
     */
    private static void options04(ISO639 lang, int instancesCount, CronDescriptionTemplate template, String dataset_id){
        DatasetOptions options = DatasetOptionsBuilder.getInstance()
                .forCronDefinition(CronDefinitionBuilder.instanceDefinitionFor(CronType.QUARTZ))
                .withTargetLanguage(lang)

                .withCronKey(ValueSelectionStrategy.getCronTemplateTransformed())
                .withHeuristicDescription(ValueSelectionStrategy.getCronTemplateTransformed())
                .withHumanDescription(ValueSelectionStrategy.getHumanDescriptionTransformed())

                .withCronTemplateProcessor(CronTemplateProcessor.replaceAllButListsAndRanges())
                .withHeuristicCronDescriptionProcessor(HeuristicCronDescriptionProcessor.getNullHeuristicCronDescriptionProcessor())
                .withHumanCronDescriptionProcessor(HumanCronDescriptionProcessorFactory.getHumanCronDescriptionProcessor())

                .withTemplate(template)
                .build();
        new DatasetGenerator(options).build(instancesCount).visit(DatasetVisitor.exportPipedFileDatasetVisitor(String.format("dataset-cronutils_en2%s-%s-%s.psv", lang.toString().toLowerCase(), instancesCount, dataset_id)));
    }
}