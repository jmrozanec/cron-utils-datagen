package com.cronutils.dataset;
import com.cronutils.descriptor.CronDescriptor;
import com.cronutils.model.definition.CronDefinition;
import com.cronutils.model.definition.CronDefinitionBuilder;
import com.cronutils.parser.CronParser;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.*;

import static com.cronutils.model.CronType.QUARTZ;

public class TranslateEnToEn {

    public static void main(String[] args) {
        CronDefinition definition = CronDefinitionBuilder.instanceDefinitionFor(QUARTZ);
        CronParser parser = new CronParser(definition);
        CronDescriptor descriptor = CronDescriptor.instance(Locale.UK);

        int entries = 250000;
        Map<String, String> templates = templates();
        validate(templates);
        List<String> templatekeys = new ArrayList<>(templates.keySet());

        Set<String> seconds = generateSet(0, 59);
        Set<String> minutes = generateSet(0, 59);
        Set<String> hours = generateSet(0, 23);
        Set<String> months = generateSet(1, 12);
        Set<String> dow = generateSet(1, 7);
        Set<String> dom = generateSet(1, 31);
        Set<String> years = generateSet(1970, 2099);
        Set<String> weekcounts = generateSet(1, 5);
        Set<String> domcounts = generateSet(1, 31);

        Set<String> expressions = new HashSet<>();
        Map<String, String> sources = new HashMap<>();
        Map<String, String> targets = new HashMap<>();

        while(expressions.size()<entries){
            String choice = templatekeys.get((int)(templatekeys.size()*Math.random()));
            String target = templates.get(choice);
            Map<String, String> valueMappings = new HashMap<>();
            populate(valueMappings, "SEC", chooseRandom(seconds, 4));
            populate(valueMappings, "MIN", chooseRandom(minutes, 4));
            populate(valueMappings, "HOUR", chooseRandom(hours, 4));
            populate(valueMappings, "DOM", chooseRandom(dom, 4));
            populate(valueMappings, "MONTH", chooseRandom(months, 4));
            populate(valueMappings, "DOW", chooseRandom(dow, 4));
            populate(valueMappings, "YEAR", chooseRandom(years, 4));

            valueMappings.put("DOWCOUNT", chooseRandom(dow, 1).get(0));
            valueMappings.put("DOMCOUNT", chooseRandom(domcounts, 1).get(0));
            valueMappings.put("WEEK_ORDINAL", chooseRandom(weekcounts, 1).get(0));

            String instance = generateInstance(choice, valueMappings);
            if(!expressions.contains(instance)){
                try {
                    String description = descriptor.describe(parser.parse(instance));
                    expressions.add(instance);
                    sources.put(instance, description);
                    targets.put(instance, target);
                }catch (RuntimeException ex){
                    System.out.println(String.format("'%s' produces an exception while described: %s", instance, ex.getMessage()));
                }
            }
        }

        BufferedWriter writer = null;
        try {
            writer = new BufferedWriter(new FileWriter("dataset-cronutils_en2en.psv"));
            for(String key : expressions){
                writer.write(String.format("%s | %s\n", sources.get(key), targets.get(key)));
            }
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void validate(Map<String, String> expressions){
        CronDefinition definition = CronDefinitionBuilder.instanceDefinitionFor(QUARTZ);
        CronParser parser = new CronParser(definition);
        Set<String> seconds = generateSet(0, 59);
        Set<String> minutes = generateSet(0, 59);
        Set<String> hours = generateSet(0, 23);
        Set<String> months = generateSet(1, 12);
        Set<String> dow = generateSet(1, 7);
        Set<String> dom = generateSet(1, 31);
        Set<String> years = generateSet(1970, 2099);
        Set<String> weekcounts = generateSet(1, 5);
        Set<String> domcounts = generateSet(1, 31);

        expressions.entrySet().forEach(e -> {
            Map<String, String> valueMappings = new HashMap<>();
            populate(valueMappings, "SEC", chooseRandom(seconds, 4));
            populate(valueMappings, "MIN", chooseRandom(minutes, 4));
            populate(valueMappings, "HOUR", chooseRandom(hours, 4));
            populate(valueMappings, "DOM", chooseRandom(dom, 4));
            populate(valueMappings, "MONTH", chooseRandom(months, 4));
            populate(valueMappings, "DOW", chooseRandom(dow, 4));
            populate(valueMappings, "YEAR", chooseRandom(years, 4));

            valueMappings.put("DOWCOUNT", chooseRandom(dow, 1).get(0));
            valueMappings.put("DOMCOUNT", chooseRandom(domcounts, 1).get(0));
            valueMappings.put("WEEK_ORDINAL", chooseRandom(weekcounts, 1).get(0));

            String instance = generateInstance(e.getKey(), valueMappings);
            try{
                parser.parse(instance);
            }catch (RuntimeException ex){
                System.out.println(String.format("Not valid: %s with message: %s", e.getKey(), ex.getMessage()));
            }
        });
    }

    private static Set<String> generateSet(int low, int high){
        Set<String> values = new HashSet<>();
        for(int j=low; j<=high; j++){
            values.add(String.format("%s", j));
        }
        return values;
    }

    private static List<String> chooseRandom(Set<String>alternatives, int howmany){
        Set<String> choices = new HashSet<>();
        List<String> alts = new ArrayList<>(alternatives);
        while(choices.size()<howmany){
            int ch = (int)((alternatives.size()-1)*Math.random());
            choices.add(alts.get(ch));
        }
        List<String>choiceList = new ArrayList<>(choices);
        Collections.sort(choiceList);
        return choiceList;
    }

    private static void populate(Map<String, String> mappings, String prefix, List<String> values){
        for(int j=0;j<values.size();j++){
            mappings.put(prefix+(j+1), values.get(j));
        }
    }

    public static String generateInstance(String crontemplate, Map<String, String>mappings){
        String[]template = new String[]{crontemplate};
        mappings.entrySet().forEach(e->{
            template[0] = template[0].replace(e.getKey(), e.getValue());
        });
        return template[0];
    }

    private static Map<String, String> templates(){
        //seconds, minutes, hours, DoM, M, DoW, Y
        Map<String, String> templates = new HashMap<>();
        templates.put("* MIN1 HOUR1-HOUR2 ? * DOW1-DOW2", "at minute MIN_VAL every day between DOW_VAL and DOW_VAL between HOUR_VAL and HOUR_VAL");
        templates.put("* * HOUR1 ? MONTH1 DOW1#WEEK_ORDINAL *", "Every WEEK_ORDINAL_VAL DOW_VAL in MONTH_VAL at HOUR_VAL");
        templates.put("0 * HOUR1-HOUR2 DOW1-DOW2 * ?", "every minute between HOUR_VAL and HOUR_VAL between DOW_VAL and DOW_VAL");
        templates.put("* * * ? * *", "Every second");
        templates.put("* * * ? * YEAR1", "Every second in YEAR_VAL");
        templates.put("0 * * ? * *", "Every minute");
        templates.put("0 * * ? * YEAR1", "Every minute in YEAR_VAL");
        templates.put("0 0 * ? * *", "Every hour");
        templates.put("0 */2 * ? * *", "Every even minute");
        templates.put("0 1/2 * ? * *", "Every uneven minute");
        templates.put("0 */MIN1 * ? * *", "Every MIN1 minutes");
        templates.put("0 MIN1,MIN2,MIN3 * ? * *", "Every hour at minutes MIN_VAL, MIN_VAL and MIN_VAL");
        templates.put("0/2 * * ? * *", "Every even second");
        templates.put("0 0/2 * ? * *", "Every even minute");
        templates.put("SEC1 0/2 * ? * *", "Every even minute on SEC_VAL");
        templates.put("0 0 0/2 ? * *", "Every even hour");
        templates.put("0 MIN1 0/2 ? * *", "Every even hour on minute MIN_VAL");
        templates.put("SEC1 MIN1 0/2 ? * *", "Every even hour on minute MIN_VAL and second SEC_VAL");
        templates.put("0 0 * ? 2/2 *", "Every hour on even months");
        templates.put("0 0 * ? * 2/2", "Every hour on even days of week");
        templates.put("SEC1 0 * ? 2/2 *", "Every hour at second SEC_VAL on even months");
        templates.put("SEC1 MIN1 * ? 2/2 *", "Every hour at minute MIN_VAL and second SEC_VAL on even months");
        templates.put("0 MIN1 * ? 2/2 *", "Every hour at minute MIN_VAL on even months");
        templates.put("0 0 * ? * 2/2", "Every hour on even days of week");
        templates.put("SEC1 0 * ? * 2/2", "Every hour at second SEC_VAL on even days of week");
        templates.put("SEC1 MIN1 * ? * 2/2", "Every hour at minute MIN_VAL and second SEC_VAL on even days of week");
        templates.put("0 MIN1 * ? * 2/2", "Every hour at minute MIN_VAL on even days of week");

        templates.put("1/2 * * ? * *", "Every uneven second");
        templates.put("0 1/2 * ? * *", "Every uneven minute");
        templates.put("SEC1 1/2 * ? * *", "Every uneven minute on SEC_VAL");
        templates.put("0 0 1/2 ? * *", "Every uneven hour");
        templates.put("0 MIN1 1/2 ? * *", "Every uneven hour on minute MIN_VAL");
        templates.put("SEC1 MIN1 1/2 ? * *", "Every uneven hour on minute MIN_VAL and second SEC_VAL");
        templates.put("0 0 * ? 1/2 *", "Every hour on uneven months");
        templates.put("0 0 * ? * 1/2", "Every hour on uneven days of week");
        templates.put("0 0 * ? 1/2 *", "Every hour on uneven months");
        templates.put("0 0 * ? * 1/2", "Every hour on uneven days of week");
        templates.put("SEC1 0 * ? 1/2 *", "Every hour at second SEC_VAL on uneven months");
        templates.put("SEC1 MIN1 * ? 1/2 *", "Every hour at minute MIN_VAL and second SEC_VAL on uneven months");
        templates.put("0 MIN1 * ? 1/2 *", "Every hour at minute MIN_VAL on uneven months");
        templates.put("0 0 * ? * 1/2", "Every hour on uneven days of week");
        templates.put("SEC1 0 * ? * 1/2", "Every hour at second SEC_VAL on uneven days of week");
        templates.put("SEC1 MIN1 * ? * 1/2", "Every hour at minute MIN_VAL and second MIN_VAL on uneven days of week");
        templates.put("0 MIN1 * ? * 1/2", "Every hour at minute MIN_VAL on uneven days of week");


        templates.put("0 0 */HOUR1 ? * *", "Every HOUR_VAL hours");
        templates.put("0 0 0 * * ?", "Every day at midnight");

        templates.put("0 0 HOUR1 * * ?", "Every day at HOUR_VAL");//we can then transform HOUR1 to AM/PM format!

        templates.put("0 0 12 * * ?", "Every day at noon");
        templates.put("0 0 12 ? * DOW1", "Every DOW_VAL at noon");
        templates.put("0 0 12 ? * MON-FRI", "Every weekday at noon");
        templates.put("0 0 12 * * ?", "Every day at noon");
        templates.put("0 0 12 ? * DOW1,DOW2", "Every DOW_VAL and DOW_VAL at noon");
        templates.put("0 0 12 */DOMCOUNT * ?", "Every DOMCOUNT_VAL days at noon");
        templates.put("0 0 12 DOM1 * ?", "Every month on the DOM_VAL, at noon");
        templates.put("0 0 12 1/MONTH1 * ?", "Every MONTH_VAL months on the DOM_VAL, at noon");
        templates.put("0 0 12 L * ?", "Every month on the last day of the month, at noon");
        templates.put("0 0 12 L-DOM1 * ?", "Every month on the DOM_VAL to last day of the month, at noon");
        templates.put("0 0 12 LW * ?", "Every month on the last weekday, at noon");
        templates.put("0 0 12 ? * DOW1L", "Every month on the last DOW_VAL, at noon");
        templates.put("0 0 12 DOM1W * ?", "Every month on the nearest weekday to the DOM_ORDINAL_VAL of the month, at noon");
        templates.put("0 0 12 ? * DOW1#WEEK_ORDINAL", "Every month on the WEEK_ORDINAL_VAL DOW_VAL of the month, at noon");
        templates.put("0 0 12 ? MONTH1 *", "Every day at noon in MONTH_VAL only");

        templates.put("0 0 0 * * ?", "Every day at midnight");
        templates.put("0 0 0 ? * DOW1", "Every DOW_VAL at midnight");
        templates.put("0 0 0 ? * MON-FRI", "Every weekday at midnight");
        templates.put("0 0 0 * * ?", "Every day at midnight");
        templates.put("0 0 0 ? * DOW1,DOW2", "Every DOW_VAL and DOW_VAL at midnight");
        templates.put("0 0 0 */DOWCOUNT * ?", "Every DOWCOUNT_VAL days at midnight");
        templates.put("0 0 0 DOM1 * ?", "Every month on the DOM_VAL, at midnight");
        templates.put("0 0 0 1/MONTH1 * ?", "Every MONTH_VAL months on the DOM_VAL, at midnight");
        templates.put("0 0 0 L * ?", "Every month on the last day of the month, at midnight");
        templates.put("0 0 0 L-DOM1 * ?", "Every month on the DOM_VAL to last day of the month, at midnight");
        templates.put("0 0 0 LW * ?", "Every month on the last weekday, at midnight");
        templates.put("0 0 0 ? * DOW1L", "Every month on the last DOW_VAL, at midnight");
        templates.put("0 0 0 DOM1W * ?", "Every month on the nearest weekday to the DOM_ORDINAL_VAL of the month, at midnight");
        templates.put("0 0 0 ? * DOW1#WEEK_ORDINAL", "Every month on the WEEK_ORDINAL_VAL DOW_VAL of the month, at midnight");
        templates.put("0 0 0 ? MONTH1 *", "Every day at midnight in MONTH_VAL only");

        templates.put("0 MIN1 HOUR1 * * ?", "Every day at HOUR_VAL:MIN_VAL");
        templates.put("0 MIN1 HOUR1 ? * DOW1", "Every DOW_VAL at HOUR_VAL:MIN_VAL");
        templates.put("0 MIN1 HOUR1 ? * MON-FRI", "Every Weekday at HOUR_VAL:MIN_VAL");
        templates.put("0 MIN1 HOUR1 * * ?", "Every day at HOUR_VAL:MIN_VAL");
        templates.put("0 MIN1 HOUR1 ? * DOW1,DOW2", "Every DOW_VAL and DOW_VAL at HOUR_VAL:MIN_VAL");
        templates.put("0 MIN1 HOUR1 */DOM1 * ?", "Every DOM_VAL days at HOUR_VAL:MIN_VAL");
        templates.put("0 MIN1 HOUR1 DOM1 * ?", "Every month on the DOM_VAL, at HOUR_VAL:MIN_VAL");
        templates.put("0 MIN1 HOUR1 1/MONTH1 * ?", "Every MONTH_VAL months on the DOM_VAL, at HOUR_VAL:MIN_VAL");
        templates.put("0 MIN1 HOUR1 L * ?", "Every month on the last day of the month, at HOUR_VAL:MIN_VAL");
        templates.put("0 MIN1 HOUR1 L-DOM1 * ?", "Every month on the DOM_VAL to last day of the month, at HOUR_VAL:MIN_VAL");
        templates.put("0 MIN1 HOUR1 LW * ?", "Every month on the last weekday, at HOUR_VAL:MIN_VAL");
        templates.put("0 MIN1 HOUR1 ? * DOW1L", "Every month on the last DOW_VAL, at HOUR_VAL:MIN_VAL");
        templates.put("0 MIN1 HOUR1 DOM1W * ?", "Every month on the nearest weekday to the DOM_ORDINAL_VAL of the month, at HOUR_VAL:MIN_VAL");
        templates.put("0 MIN1 HOUR1 ? * DOW1#WEEK_ORDINAL", "Every month on the WEEK_ORDINAL_VAL DOW_VAL of the month, at HOUR_VAL:MIN_VAL");
        templates.put("0 MIN1 HOUR1 ? MONTH1 *", "Every day at HOUR_VAL:MIN_VAL in MONTH_VAL only");

        templates.put("0 MIN1 0-11 * * ?", "Every day at minute MIN_VAL every hour before midday");
        templates.put("0 MIN1 0-11 ? * DOW1", "Every DOW_VAL at minute MIN_VAL every hour before midday");
        templates.put("0 MIN1 0-11 ? * MON-FRI", "Every Weekday at minute MIN_VAL every hour before midday");
        templates.put("0 MIN1 0-11 * * ?", "Every day at minute MIN_VAL every hour before midday");
        templates.put("0 MIN1 0-11 ? * DOW1,DOW2", "Every DOW_VAL and DOW_VAL at minute MIN_VAL every hour before midday");
        templates.put("0 MIN1 0-11 */DOMCOUNT * ?", "Every DOMCOUNT_VAL days at minute MIN_VAL every hour before midday");
        templates.put("0 MIN1 0-11 DOM1 * ?", "Every month on the DOM1, at minute MIN1 every hour before midday");
        templates.put("0 MIN1 0-11 1/MONTH1 * ?", "Every MONTH_VAL months on the DOM_VAL, at minute MIN_VAL every hour before midday");
        templates.put("0 MIN1 0-11 L * ?", "Every month on the last day of the month, at minute MIN_VAL every hour before midday");
        templates.put("0 MIN1 0-11 L-DOM1 * ?", "Every month on the DOM_VAL to last day of the month, at minute MIN_VAL every hour before midday");
        templates.put("0 MIN1 0-11 LW * ?", "Every month on the last weekday, at minute MIN_VAL every hour before midday");
        templates.put("0 MIN1 0-11 ? * DOW1L", "Every month on the last DOW_VAL, at minute MIN_VAL every hour before midday");
        templates.put("0 MIN1 0-11 DOM1W * ?", "Every month on the nearest weekday to the DOM_ORDINAL_VAL of the month, at minute MIN_VAL every hour before midday");
        templates.put("0 MIN1 0-11 ? * DOW1#WEEK_ORDINAL", "Every month on the WEEK_ORDINAL_VAL DOW_VAL of the month, at minute MIN_VAL every hour before midday");
        templates.put("0 MIN1 0-11 ? MONTH1 *", "Every day at minute MIN_VAL every hour before midday in MONTH_VAL only");

        templates.put("0 MIN1 HOUR1 DOM1 MONTH1 ? YEAR1,YEAR2", "Once a year, on MONTH_VAL DOM_VAL at HOUR_VAL:MIN_VAL, if the year is YEAR_VAL or YEAR_VAL");
        templates.put("0 MIN1,MIN2,MIN3,MIN4 HOUR1,HOUR2,HOUR3,HOUR4 ? * 1-5 *", "At MIN_VAL, MIN_VAL, MIN_VAL, MIN_VAL for HOUR_VALhs, HOUR_VALhs, HOUR_VALhs, HOUR_VALhs, on weekdays");
        templates.put("* * 0-11 ? * *", "Each second before midday");
        templates.put("0 * 0-11 ? * *", "Each minute before midday");
        templates.put("0 0 0 ? * DOW1 *", "Each DOW_VALUE at midnight");
        return templates;
    }
}

