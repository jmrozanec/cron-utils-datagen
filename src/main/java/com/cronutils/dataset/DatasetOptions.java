package com.cronutils.dataset;

import com.cronutils.model.definition.CronDefinition;

class DatasetOptions {
    private ISO639 targetLanguage;
    private CronDescriptionTemplate template;
    private CronDefinition cronDefinition;
    private ValueSelectionStrategy cronKeySelectionStrategy;
    private ValueSelectionStrategy heuristicCronDescriptionSelectionStrategy;
    private ValueSelectionStrategy humanCronDescriptionSelectionStrategy;
    private CronTemplateProcessor cronTemplateProcessor;
    private HeuristicCronDescriptionProcessor heuristicCronDescriptionProcessor;
    private HumanCronDescriptionProcessor humanCronDescriptionProcessor;

    DatasetOptions(ISO639 targetLanguage, CronDescriptionTemplate template, CronDefinition cronDefinition,
                   ValueSelectionStrategy cronKeySelectionStrategy,
                   ValueSelectionStrategy heuristicCronDescriptionSelectionStrategy,
                   ValueSelectionStrategy humanCronDescriptionSelectionStrategy,
                   CronTemplateProcessor cronTemplateProcessor, HeuristicCronDescriptionProcessor heuristicCronDescriptionProcessor,
                   HumanCronDescriptionProcessor humanCronDescriptionProcessor) {
        this.targetLanguage = targetLanguage;
        this.template = template;
        this.cronDefinition = cronDefinition;
        this.cronKeySelectionStrategy = cronKeySelectionStrategy;
        this.heuristicCronDescriptionSelectionStrategy = heuristicCronDescriptionSelectionStrategy;
        this.humanCronDescriptionSelectionStrategy = humanCronDescriptionSelectionStrategy;
        this.cronTemplateProcessor = cronTemplateProcessor;
        this.heuristicCronDescriptionProcessor = heuristicCronDescriptionProcessor;
        this.humanCronDescriptionProcessor = humanCronDescriptionProcessor;
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

    public ValueSelectionStrategy getCronKeySelectionStrategy() {
        return cronKeySelectionStrategy;
    }

    public ValueSelectionStrategy getHeuristicCronDescriptionSelectionStrategy() {
        return heuristicCronDescriptionSelectionStrategy;
    }

    public ValueSelectionStrategy getHumanCronDescriptionSelectionStrategy() {
        return humanCronDescriptionSelectionStrategy;
    }

    public CronTemplateProcessor getCronTemplateProcessor() {
        return cronTemplateProcessor;
    }

    public HeuristicCronDescriptionProcessor getHeuristicCronDescriptionProcessor() {
        return heuristicCronDescriptionProcessor;
    }

    public HumanCronDescriptionProcessor getHumanCronDescriptionProcessor() {
        return humanCronDescriptionProcessor;
    }
}
