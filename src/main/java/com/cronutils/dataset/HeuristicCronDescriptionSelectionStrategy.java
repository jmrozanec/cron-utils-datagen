package com.cronutils.dataset;

public interface HeuristicCronDescriptionSelectionStrategy {

    String getHeuristicCronDescription(String heuristicDescription, String heuristicDescriptionTransformed);

    static HeuristicCronDescriptionSelectionStrategy getHeuristicDescription(){
        return (heuristicDescription, heuristicDescriptionTransformed) -> heuristicDescription;
    }

    static HeuristicCronDescriptionSelectionStrategy getHeuristicDescriptionTransformed(){
        return (heuristicDescription, heuristicDescriptionTransformed) -> heuristicDescriptionTransformed;
    }
}
