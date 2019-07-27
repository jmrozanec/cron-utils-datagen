package com.cronutils.dataset;

import com.cronutils.model.definition.CronDefinition;

class DatasetOptions {
    private ISO639 targetLanguage;
    private CronDescriptionTemplate template;
    private CronDefinition cronDefinition;
    private CronKeySelectionStrategy cronKeySelectionStrategy;
    private HeuristicCronDescriptionSelectionStrategy heuristicCronDescriptionSelectionStrategy;
    private HumanCronDescriptionSelectionStrategy humanCronDescriptionSelectionStrategy;
    private CronTemplateProcessor cronTemplateProcessor;
    private HeuristicCronDescriptionProcessor heuristicCronDescriptionProcessor;

    DatasetOptions(ISO639 targetLanguage, CronDescriptionTemplate template, CronDefinition cronDefinition,
                   CronKeySelectionStrategy cronKeySelectionStrategy,
                   HeuristicCronDescriptionSelectionStrategy heuristicCronDescriptionSelectionStrategy,
                   HumanCronDescriptionSelectionStrategy humanCronDescriptionSelectionStrategy,
                   CronTemplateProcessor cronTemplateProcessor, HeuristicCronDescriptionProcessor heuristicCronDescriptionProcessor) {
        this.targetLanguage = targetLanguage;
        this.template = template;
        this.cronDefinition = cronDefinition;
        this.cronKeySelectionStrategy = cronKeySelectionStrategy;
        this.heuristicCronDescriptionSelectionStrategy = heuristicCronDescriptionSelectionStrategy;
        this.humanCronDescriptionSelectionStrategy = humanCronDescriptionSelectionStrategy;
        this.cronTemplateProcessor = cronTemplateProcessor;
        this.heuristicCronDescriptionProcessor = heuristicCronDescriptionProcessor;
    }

    public ISO639 getTargetLanguage() {
        return targetLanguage;
    }

    public CronDescriptionTemplate getTemplate() {
        return template;
    }

    public CronDefinition getCronDefinition() {
        return cronDefinition;
    }

    public CronKeySelectionStrategy getCronKeySelectionStrategy() {
        return cronKeySelectionStrategy;
    }

    public HeuristicCronDescriptionSelectionStrategy getHeuristicCronDescriptionSelectionStrategy() {
        return heuristicCronDescriptionSelectionStrategy;
    }

    public HumanCronDescriptionSelectionStrategy getHumanCronDescriptionSelectionStrategy() {
        return humanCronDescriptionSelectionStrategy;
    }

    public CronTemplateProcessor getCronTemplateProcessor() {
        return cronTemplateProcessor;
    }

    public HeuristicCronDescriptionProcessor getHeuristicCronDescriptionProcessor() {
        return heuristicCronDescriptionProcessor;
    }
}
