package com.cronutils.dataset;

import java.util.Map;

public interface HumanCronDescriptionProcessor {
    String process(Map<String, String> valueMappings, String humanDescription);
}
