package com.cronutils.dataset;

public interface HeuristicCronDescriptionProcessor {
    String process(String heuristicCronDescription);

    static HeuristicCronDescriptionProcessor getNullHeuristicCronDescriptionProcessor(){
        return (heuristicCronDescription) -> heuristicCronDescription;
    }
}
