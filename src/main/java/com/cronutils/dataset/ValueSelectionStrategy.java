package com.cronutils.dataset;

public interface ValueSelectionStrategy {

    String getValue(String cronTemplate, String cronTemplateTransformed, String cronTemplateInstance,
                      String heuristicDescription, String heuristicDescriptionTransformed,
                      String humanDescription, String humanDescriptionTransformed);

    static ValueSelectionStrategy getCronTemplate(){
        return (cronTemplate, cronTemplateTransformed, cronTemplateInstance, heuristicDescription, heuristicDescriptionTransformed, humanDescription, humanDescriptionTransformed) -> cronTemplate;
    }

    static ValueSelectionStrategy getCronTemplateTransformed(){
        return (cronTemplate, cronTemplateTransformed, cronTemplateInstance, heuristicDescription, heuristicDescriptionTransformed, humanDescription, humanDescriptionTransformed) -> cronTemplateTransformed;
    }

    static ValueSelectionStrategy getCronTemplateInstance(){
        return (cronTemplate, cronTemplateTransformed, cronTemplateInstance, heuristicDescription, heuristicDescriptionTransformed, humanDescription, humanDescriptionTransformed) -> cronTemplateInstance;
    }

    static ValueSelectionStrategy getHeuristicDescription(){
        return (cronTemplate, cronTemplateTransformed, cronTemplateInstance, heuristicDescription, heuristicDescriptionTransformed, humanDescription, humanDescriptionTransformed) -> heuristicDescription;
    }

    static ValueSelectionStrategy getHeuristicDescriptionTransformed(){
        return (cronTemplate, cronTemplateTransformed, cronTemplateInstance, heuristicDescription, heuristicDescriptionTransformed, humanDescription, humanDescriptionTransformed) -> heuristicDescriptionTransformed;
    }

    static ValueSelectionStrategy getHumanDescription(){
        return (cronTemplate, cronTemplateTransformed, cronTemplateInstance, heuristicDescription, heuristicDescriptionTransformed, humanDescription, humanDescriptionTransformed) -> humanDescription;
    }

    static ValueSelectionStrategy getHumanDescriptionTransformed(){
        return (cronTemplate, cronTemplateTransformed, cronTemplateInstance, heuristicDescription, heuristicDescriptionTransformed, humanDescription, humanDescriptionTransformed) -> humanDescriptionTransformed;
    }
}
