package com.cronutils.dataset;

public interface HumanCronDescriptionSelectionStrategy {
    String getHumanCronDescription(String humanDescription, String humanDescriptionTransformed);

    static HumanCronDescriptionSelectionStrategy getHumanDescription(){
        return (humanDescription, humanDescriptionTransformed) -> humanDescription;
    }

    static HumanCronDescriptionSelectionStrategy getHumanDescriptionTransformed(){
        return (humanDescription, humanDescriptionTransformed) -> humanDescriptionTransformed;
    }
}
