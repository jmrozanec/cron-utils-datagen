package com.cronutils.dataset;

public interface CronTemplateProcessor {

    String process(String cronTemplate);

    static CronTemplateProcessor getNullCronTemplateProcessor(){
        return (cronTemplate) -> cronTemplate;
    }
}
