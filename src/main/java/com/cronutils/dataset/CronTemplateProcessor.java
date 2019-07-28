package com.cronutils.dataset;

import java.util.Map;

public interface CronTemplateProcessor {

    String process(Map<String, String> valueMappings, String cronTemplate);

    static CronTemplateProcessor getNullCronTemplateProcessor(){
        return ((valueMappings, cronTemplate) -> cronTemplate);
    }

    static CronTemplateProcessor replaceAllButListsAndRanges(){
        return ((valueMappings, cronTemplate) -> {
            String[]template = new String[]{cronTemplate};
            valueMappings.forEach((key, value) -> {
                if(!((key.endsWith("_LIST") || (key.endsWith("_LIST"))))){
                    template[0] = template[0].replace(key, value);
                }
            });
            return template[0];
        });
    }
}
