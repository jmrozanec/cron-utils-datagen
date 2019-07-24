package com.cronutils.dataset;

import java.util.HashMap;
import java.util.Map;

public class DatasetUtils {

    private static Map<String, String> dow(){
        Map<String, String> values = new HashMap<>();
        values.put("Monday", "DOW_VAL");
        values.put("Tuesday", "DOW_VAL");
        values.put("Wednesday", "DOW_VAL");
        values.put("Thursday", "DOW_VAL");
        values.put("Friday", "DOW_VAL");
        values.put("Saturday", "DOW_VAL");
        values.put("Sunday", "DOW_VAL");
        return values;
    }

    private static Map<String, String> month(){
        Map<String, String> values = new HashMap<>();
        values.put("January", "MONTH_VAL");
        values.put("February", "MONTH_VAL");
        values.put("March", "MONTH_VAL");
        values.put("April", "MONTH_VAL");
        values.put("June", "MONTH_VAL");
        values.put("July", "MONTH_VAL");
        values.put("August", "MONTH_VAL");
        values.put("September", "MONTH_VAL");
        values.put("October", "MONTH_VAL");
        values.put("November", "MONTH_VAL");
        values.put("December", "MONTH_VAL");
        return values;
    }

    private static Map<String, String> weekcount(){
        Map<String, String> values = new HashMap<>();
        values.put("1", "WEEKCOUNT_VAL");
        values.put("2", "WEEKCOUNT_VAL");
        values.put("3", "WEEKCOUNT_VAL");
        values.put("4", "WEEKCOUNT_VAL");
        values.put("5", "WEEKCOUNT_VAL");
        return values;
    }

    private static String ordinals(int number){
        String numberString= String.format("%s", number);
        int lastDigit = Integer.parseInt(""+numberString.charAt(numberString.lastIndexOf(numberString)));
        String ending = "th";
        switch (lastDigit){
            case 1:
                ending = "st";
            case 2:
                ending = "nd";
            case 3:
                ending = "rd";
        }
        return String.format("%s%s", number, ending);
    }

    private static Map<String, String> year(){
        Map<String, String> values = new HashMap<>();
        for(int j=1900;j<2100;j++){
            values.put(""+j, "YEAR_VAL");
        }
        return values;
    }
}
