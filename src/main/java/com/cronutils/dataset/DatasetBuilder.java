package com.cronutils.dataset;

import com.cronutils.descriptor.CronDescriptor;
import com.cronutils.model.definition.CronDefinition;
import com.cronutils.model.definition.CronDefinitionBuilder;
import com.cronutils.parser.CronParser;

import java.io.InputStream;
import java.util.*;

import static com.cronutils.model.CronType.QUARTZ;

public class DatasetBuilder {
    private long seed = 12345;
    private ISO639 targetLanguage;
    private Random random;

    public DatasetBuilder(ISO639 targetLanguage){
        this.targetLanguage = targetLanguage;
    }

    public Dataset build(int entries){
        this.random = new Random(seed); //we reset it every time, to ensure same datasets are generated regardless how many times invoked
        CronDefinition definition = CronDefinitionBuilder.instanceDefinitionFor(QUARTZ);
        CronParser parser = new CronParser(definition);
        CronDescriptor descriptor = CronDescriptor.instance(Locale.UK);

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
            String choice = templatekeys.get((int)(templatekeys.size()*this.random.nextInt()));
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
        return new Dataset(sources, targets);
    }

    private void validate(Map<String, String> expressions){
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

        expressions.forEach((key, value) -> {
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

            String instance = generateInstance(key, valueMappings);
            try {
                parser.parse(instance);
            } catch (RuntimeException ex) {
                System.out.println(String.format("Not valid: %s with message: %s", key, ex.getMessage()));
            }
        });
    }

    private Set<String> generateSet(int low, int high){
        Set<String> values = new HashSet<>();
        for(int j=low; j<=high; j++){
            values.add(String.format("%s", j));
        }
        return values;
    }

    private List<String> chooseRandom(Set<String> alternatives, int howmany){
        Set<String> choices = new HashSet<>();
        List<String> alts = new ArrayList<>(alternatives);
        while(choices.size()<howmany){
            int ch = (int)((alternatives.size()-1)*this.random.nextInt());
            choices.add(alts.get(ch));
        }
        List<String>choiceList = new ArrayList<>(choices);
        Collections.sort(choiceList);
        return choiceList;
    }

    private void populate(Map<String, String> mappings, String prefix, List<String> values){
        for(int j=0;j<values.size();j++){
            mappings.put(prefix+(j+1), values.get(j));
        }
    }

    private String generateInstance(String crontemplate, Map<String, String> mappings){
        String[]template = new String[]{crontemplate};
        mappings.forEach((key, value) -> template[0] = template[0].replace(key, value));
        return template[0];
    }

    private Map<String, String> templates(){
        Map<String, String> templates = new HashMap<>();
        InputStream inputStream = getClass().getClassLoader().getResourceAsStream(String.format("%s-01.template", this.targetLanguage.toString().toLowerCase()));
        try (Scanner s = new Scanner(inputStream)) {
            while (s.hasNext()) {
                String line = s.nextLine();
                templates.put(line.split("|")[0], line.split("|")[1]);
            }
        }
        return templates;
    }
}
