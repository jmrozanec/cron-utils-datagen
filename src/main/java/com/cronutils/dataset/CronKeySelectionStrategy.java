package com.cronutils.dataset;

public interface CronKeySelectionStrategy {

    String getCronKey(String cronTemplate, String cronTemplateTransformed, String cronTemplateInstance);

    static CronKeySelectionStrategy getCronTemplate(){
        return (cronTemplate, cronTemplateTransformed, cronTemplateInstance) -> cronTemplate;
    }

    static CronKeySelectionStrategy getCronTemplateTransformed(){
        return (cronTemplate, cronTemplateTransformed, cronTemplateInstance) -> cronTemplateTransformed;
    }

    static CronKeySelectionStrategy getCronTemplateInstance(){
        return (cronTemplate, cronTemplateTransformed, cronTemplateInstance) -> cronTemplateInstance;
    }
}
