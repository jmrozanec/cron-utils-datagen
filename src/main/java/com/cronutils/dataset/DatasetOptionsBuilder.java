package com.cronutils.dataset;

import com.cronutils.model.CronType;
import com.cronutils.model.definition.CronDefinition;
import com.cronutils.model.definition.CronDefinitionBuilder;

public class DatasetOptionsBuilder {
    private ISO639 targetLanguage = ISO639.EN;
    private CronDescriptionTemplate template = CronDescriptionTemplate.TEMPLATE_01;
    private CronDefinition cronDefinition = CronDefinitionBuilder.instanceDefinitionFor(CronType.QUARTZ);
    private ValueSelectionStrategy cronKeySelectionStrategy = ValueSelectionStrategy.getCronTemplate();
    private ValueSelectionStrategy heuristicCronDescriptionSelectionStrategy = ValueSelectionStrategy.getHeuristicDescription();
    private ValueSelectionStrategy humanCronDescriptionSelectionStrategy = ValueSelectionStrategy.getHumanDescription();
    private CronTemplateProcessor cronTemplateProcessor = CronTemplateProcessor.getNullCronTemplateProcessor();
    private HeuristicCronDescriptionProcessor heuristicCronDescriptionProcessor = HeuristicCronDescriptionProcessor.getNullHeuristicCronDescriptionProcessor();
    private HumanCronDescriptionProcessor humanCronDescriptionProcessor = HumanCronDescriptionProcessorFactory.getNullHumanCronDescriptionProcessor();

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

    public DatasetOptionsBuilder withCronKey(ValueSelectionStrategy cronKeySelectionStrategy){
        this.cronKeySelectionStrategy = cronKeySelectionStrategy;
        return this;
    }

    public DatasetOptionsBuilder withHeuristicDescription(ValueSelectionStrategy heuristicCronDescriptionSelectionStrategy){
        this.heuristicCronDescriptionSelectionStrategy = heuristicCronDescriptionSelectionStrategy;
        return this;
    }

    public DatasetOptionsBuilder withHumanDescription(ValueSelectionStrategy humanCronDescriptionSelectionStrategy){
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

    public DatasetOptionsBuilder withHumanCronDescriptionProcessor(HumanCronDescriptionProcessor humanCronDescriptionProcessor){
        this.humanCronDescriptionProcessor = humanCronDescriptionProcessor;
        return this;
    }

    public static DatasetOptionsBuilder getInstance(){
        return new DatasetOptionsBuilder();
    }

    public DatasetOptions build(){
        return new DatasetOptions(targetLanguage, template, cronDefinition, cronKeySelectionStrategy,
                heuristicCronDescriptionSelectionStrategy, humanCronDescriptionSelectionStrategy,
                cronTemplateProcessor, heuristicCronDescriptionProcessor, humanCronDescriptionProcessor);
    }
}
