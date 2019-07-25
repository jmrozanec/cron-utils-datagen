package com.cronutils.dataset;

import java.util.Map;

public interface TemplateMappingStrategy {
    void add(Map<String, String> source, Map<String, String> destination, String cronexpression, String basicDescription, String targetDescription);

    static TemplateMappingStrategy getCronExpressionToTarget(){
        return (source, destination, cronexpression, utilsDescription, targetDescription) -> {
            source.put(cronexpression, cronexpression);
            destination.put(cronexpression, targetDescription);
        };
    }

    static TemplateMappingStrategy getUtilsDescriptionToTargetDescription(){
        return (source, destination, cronexpression, basicDescription, targetDescription) -> {
            source.put(cronexpression, basicDescription);
            destination.put(cronexpression, targetDescription);
        };
    }
}
