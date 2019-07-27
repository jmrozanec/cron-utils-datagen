package com.cronutils.dataset;

import java.util.Map;

public interface TemplateMappingStrategy {
    void add(Map<String, String> source, Map<String, String> destination, Map<String, String> valueMappings, String cronexpression, String basicDescription, String targetDescription);

    static TemplateMappingStrategy getCronExpressionToTarget(){
        return (source, destination, valueMappings, cronexpression, utilsDescription, targetDescription) -> {

        };
    }

    static TemplateMappingStrategy getUtilsDescriptionToTargetDescriptionWithTargetReplacement(){
        return (source, destination, valueMappings, cronexpression, basicDescription, targetDescription) -> {
            for(String key: valueMappings.keySet()){
                targetDescription = targetDescription.replaceAll(key, valueMappings.get(key));
            }
            source.put(cronexpression, basicDescription);
            destination.put(cronexpression, targetDescription);
        };
    }

    static TemplateMappingStrategy getUtilsDescriptionToTargetDescription(){
        return (source, destination, valueMappings, cronexpression, basicDescription, targetDescription) -> {
            source.put(cronexpression, basicDescription);
            destination.put(cronexpression, targetDescription);
        };
    }
}