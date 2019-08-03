package com.cronutils.dataset;

import org.apache.commons.lang3.StringUtils;

import java.util.*;
import java.util.stream.Collectors;

public class HumanCronDescriptionProcessorFactory {
    private static Map<String, String> weekdaysMap = getWeekdaysMap();
    private static Map<String, String> monthsMap = getMonthsMap();



    public static HumanCronDescriptionProcessor getHumanCronDescriptionProcessor(){
        return ((valueMappings, cronTemplate) -> {
            String[]template = new String[]{cronTemplate};
            List<String> keys = Arrays.asList("WEEK_VALUE_ORDINAL", "DOM_VALUE_ORDINAL", "SEC_VALUE_ORDINAL", "SEC_VAL", "MIN_VAL", "HOUR_VAL", "DOM_VAL", "MONTH_VAL", "DOW_VAL", "YEAR_VAL");
            valueMappings.keySet().stream().filter(s->s.endsWith("_ORDINAL")).forEach(key->{
                template[0] = template[0].replace(key, valueMappings.get(key));
            });
            valueMappings.forEach((key, value) -> {
                if(keys.contains(key)){
                    if(key.equals("DOW_VAL")){
                        template[0] = template[0].replace(key, getWeekdaysMap().get(value));
                    } else {
                        if(key.equals("MONTH_VAL")){
                            template[0] = template[0].replace(key, getMonthsMap().get(value));
                        }else{
                            template[0] = template[0].replace(key, value);
                        }
                    }
                } else {
                    if(key.endsWith("_LIST")){
                        List<String> array = Arrays.stream(value.split(",")).sorted().map(s->{
                            if(s.contains("-")){
                                return getRangeDescription(key.replace("_LIST", ""), s);
                            }
                            return s;
                        }).collect(Collectors.toList());
                        template[0] = template[0].replace(key, String.join(",", array.subList(0, array.size()-1))+" and "+array.get(array.size()-1));
                    }
                    if(key.endsWith("_RANGE")){
                        template[0] = template[0].replace(key, getRangeDescription(key.replace("_RANGE", ""), value));
                    }
                }
            });
            String description = template[0];
            description = everyOnesToEvery(description);
            return StringUtils.capitalize(description.replaceAll("(\\s)+", " ").trim());
        });
    }

    public static HumanCronDescriptionProcessor getNullHumanCronDescriptionProcessor(){
        return (valueMappings, humanCronDescriptionProcessor) -> humanCronDescriptionProcessor;
    }

    private static String getRangeDescription(String field, String range){
        String[]array = range.split("-");
        if(field.equals("HOUR")){
            if(range.equals("0-11")){
                return "before midday";
            }
            return String.format("between %shs and %shs", array[0], array[1]);//TODO we can enhance with AM/PM contextual to locale
        }
        if(field.equals("DOW")){
            if(range.equals("1-5")){
                return "on weekdays";
            }
            if(range.equals("6-7")){
                return "on weekends";
            }
            if(range.equals("1-7")){
                return "every day";
            }
            return String.format("between %s and %s", weekdaysMap.get(array[0]), weekdaysMap.get(array[1]));
        }
        if(field.equals("MONTH")){
            return String.format("between %s and %s", monthsMap.get(array[0]), monthsMap.get(array[1]));
        }
        return String.format("between %s and %s", array[0], array[1]);
    }

    private static String everyOnesToEvery(String description){
        String pattern = "every 1 ";
        if(description.contains(pattern)){
            String next = description.split(pattern)[1].split(" ")[0];
            return description.replaceAll(String.format("%s %s", pattern, next).replaceAll("(\\s)+", " "), String.format("every %s", next.substring(0, next.length()-1)));
        }
        return description;
    }

    private static Map<String, String> getWeekdaysMap(){
        Map<String, String> weekdaysMapping = new HashMap<>();
        weekdaysMapping.put("1", "Monday");
        weekdaysMapping.put("2", "Tuesday");
        weekdaysMapping.put("3", "Wednesday");
        weekdaysMapping.put("4", "Thursday");
        weekdaysMapping.put("5", "Friday");
        weekdaysMapping.put("6", "Saturday");
        weekdaysMapping.put("7", "Sunday");
        return weekdaysMapping;
    }

    private static Map<String, String> getMonthsMap(){
        Map<String, String> monthsMapping = new HashMap<>();
        monthsMapping.put("1", "January");
        monthsMapping.put("2", "February");
        monthsMapping.put("3", "March");
        monthsMapping.put("4", "April");
        monthsMapping.put("5", "May");
        monthsMapping.put("6", "June");
        monthsMapping.put("7", "July");
        monthsMapping.put("8", "August");
        monthsMapping.put("9", "September");
        monthsMapping.put("10", "October");
        monthsMapping.put("11", "November");
        monthsMapping.put("12", "December");
        return monthsMapping;
    }
}
