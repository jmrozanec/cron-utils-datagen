package com.cronutils.dataset;

import com.cronutils.model.CronType;
import com.cronutils.model.definition.CronDefinition;
import com.cronutils.model.definition.CronDefinitionBuilder;

public class DatasetOptionsBuilder {
    private ISO639 targetLanguage = ISO639.EN;
    private CronDescriptionTemplate template = CronDescriptionTemplate.TEMPLATE_01;
    private CronDefinition cronDefinition = CronDefinitionBuilder.instanceDefinitionFor(CronType.QUARTZ);
    private CronKeySelectionStrategy cronKeySelectionStrategy = CronKeySelectionStrategy.getCronTemplate();
    private HeuristicCronDescriptionSelectionStrategy heuristicCronDescriptionSelectionStrategy = HeuristicCronDescriptionSelectionStrategy.getHeuristicDescription();
    private HumanCronDescriptionSelectionStrategy humanCronDescriptionSelectionStrategy = HumanCronDescriptionSelectionStrategy.getHumanDescription();
    private CronTemplateProcessor cronTemplateProcessor = CronTemplateProcessor.getNullCronTemplateProcessor();
    private HeuristicCronDescriptionProcessor heuristicCronDescriptionProcessor = HeuristicCronDescriptionProcessor.getNullHeuristicCronDescriptionProcessor();

    private DatasetOptionsBuilder(){}

    public DatasetOptionsBuilder withTargetLanguage(ISO639 lang){
        this.targetLanguage = lang;
        return this;
    }

    public DatasetOptionsBuilder withTemplate(CronDescriptionTemplate template){
        this.template = template;
        return this;
    }

    public DatasetOptionsBuilder forCronDefinition(CronDefinition cronDefinition){
        this.cronDefinition = cronDefinition;
        return this;
    }

    public DatasetOptionsBuilder withCronKey(CronKeySelectionStrategy cronKeySelectionStrategy){
        this.cronKeySelectionStrategy = cronKeySelectionStrategy;
        return this;
    }

    public DatasetOptionsBuilder withHeuristicDescription(HeuristicCronDescriptionSelectionStrategy heuristicCronDescriptionSelectionStrategy){
        this.heuristicCronDescriptionSelectionStrategy = heuristicCronDescriptionSelectionStrategy;
        return this;
    }

    public DatasetOptionsBuilder withHumanDescription(HumanCronDescriptionSelectionStrategy humanCronDescriptionSelectionStrategy){
        this.humanCronDescriptionSelectionStrategy = humanCronDescriptionSelectionStrategy;
        return this;
    }

    public DatasetOptionsBuilder withCronTemplateProcessor(CronTemplateProcessor cronTemplateProcessor){
        this.cronTemplateProcessor = cronTemplateProcessor;
        return this;
    }

    public DatasetOptionsBuilder withHeuristicCronDescriptionProcessor(HeuristicCronDescriptionProcessor heuristicCronDescriptionProcessor){
        this.heuristicCronDescriptionProcessor = heuristicCronDescriptionProcessor;
        return this;
    }

    public static DatasetOptionsBuilder getInstance(){
        return new DatasetOptionsBuilder();
    }

    public DatasetOptions build(){
        return new DatasetOptions(targetLanguage, template, cronDefinition, cronKeySelectionStrategy,
                heuristicCronDescriptionSelectionStrategy, humanCronDescriptionSelectionStrategy,
                cronTemplateProcessor, heuristicCronDescriptionProcessor);
    }
}
