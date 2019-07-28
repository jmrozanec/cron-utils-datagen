package com.cronutils.dataset;

import com.cronutils.descriptor.CronDescriptor;
import com.cronutils.model.definition.CronDefinition;
import com.cronutils.model.field.CronFieldName;
import com.cronutils.model.field.constraint.FieldConstraints;
import com.cronutils.parser.CronParser;

import java.io.InputStream;
import java.util.*;

public class DatasetGenerator {
    private long seed = 12345;
    private Random random;
    private DatasetOptions datasetOptions;
    private CronParser parser;
    private CronDescriptor descriptor;

    private Set<String> seconds = generateSet(0, 59);
    private Set<String> minutes = generateSet(0, 59);
    private Set<String> hours = generateSet(0, 23);
    private Set<String> months = generateSet(1, 12);
    private Set<String> dow = generateSet(1, 7);
    private Set<String> dom = generateSet(1, 31);
    private Set<String> weekcounts = generateSet(1, 5);
    private Set<String> domcounts = generateSet(1, 31);
    private Set<String> years;


    //Configure object restricting templates to given cron definition, etc. Do so through a builder pattern
    public DatasetGenerator(DatasetOptions datasetOptions){
        this.datasetOptions = datasetOptions;
        this.parser = new CronParser(datasetOptions.getCronDefinition());
        if(datasetOptions.getCronDefinition().containsFieldDefinition(CronFieldName.YEAR)){
            FieldConstraints constraints = datasetOptions.getCronDefinition().getFieldDefinition(CronFieldName.YEAR).getConstraints();
            this.years = generateSet(constraints.getStartRange(), constraints.getEndRange());
        }else{
            this.years = generateSet(1970, 2099);
        }
        this.descriptor = CronDescriptor.instance(Locale.UK);
    }



    public Dataset build(int entries){
        this.random = new Random(seed); //we reset it every time, to ensure same datasets are generated regardless how many times invoked

        Map<String, String> templates = templates();
        validate(templates, parser);

        List<String> templatekeys = new ArrayList<>(templates.keySet());

        Set<String> expressions = new HashSet<>();
        Map<String, String> sources = new HashMap<>();
        Map<String, String> targets = new HashMap<>();

        while(expressions.size()<entries){
            Map<String, String> valueMappings = createRandomValueMappings(seconds, minutes, hours, months, dow, dom, years, weekcounts, domcounts);
            String cronTemplate = templatekeys.get(getNextRandom(templatekeys.size()));
            String cronTemplateTransformed = datasetOptions.getCronTemplateProcessor().process(valueMappings, cronTemplate);
            String cronTemplateInstance = generateCronExpressionInstance(cronTemplate, valueMappings);
            try {
                String heuristicDescription = descriptor.describe(parser.parse(cronTemplateInstance));
                String heuristicDescriptionTransformed = datasetOptions.getHeuristicCronDescriptionProcessor().process(heuristicDescription);
                String humanDescription = templates.get(cronTemplate);
                String humanDescriptionTransformed = humanDescription; //TODO we are not performing transformations by now

                String tripletKey = datasetOptions.getCronKeySelectionStrategy().getValue(cronTemplate, cronTemplateTransformed, cronTemplateInstance, heuristicDescription, heuristicDescriptionTransformed, humanDescription, humanDescriptionTransformed);
                String tripletHeuristicDesc = datasetOptions.getHeuristicCronDescriptionSelectionStrategy().getValue(cronTemplate, cronTemplateTransformed, cronTemplateInstance, heuristicDescription, heuristicDescriptionTransformed, humanDescription, humanDescriptionTransformed);
                String tripletHumanDesc = datasetOptions.getHumanCronDescriptionSelectionStrategy().getValue(cronTemplate, cronTemplateTransformed, cronTemplateInstance, heuristicDescription, heuristicDescriptionTransformed, humanDescription, humanDescriptionTransformed);

                if(!expressions.contains(tripletKey)){
                    expressions.add(tripletKey);
                    sources.put(tripletKey, tripletHeuristicDesc);
                    targets.put(tripletKey, tripletHumanDesc);
                }
                if(expressions.size()%100==0){
                    System.out.println("==================================================");
                    System.out.println(String.format("We got %s items", expressions.size()));
                    System.out.println("==================================================");
                }
            }catch (RuntimeException ex){
                System.err.println(String.format("'%s' from template '%s' produces an exception while described: %s ", cronTemplateInstance, cronTemplate, ex.getMessage()));
            }
        }
        return new Dataset(sources, targets);
    }




    private Map<String, String> createRandomValueMappings(Set<String> seconds, Set<String> minutes, Set<String> hours,
                                                          Set<String> months, Set<String> dow, Set<String> dom, Set<String> years,
                                                          Set<String> weekcounts, Set<String> domcounts){

        Map<String, String> valueMappings = new HashMap<>();
        populate(valueMappings, "SEC", chooseRandom(seconds, 5));
        populate(valueMappings, "MIN", chooseRandom(minutes, 5));
        populate(valueMappings, "HOUR", chooseRandom(hours, 5));
        populate(valueMappings, "DOM", chooseRandom(dom, 5));
        populate(valueMappings, "MONTH", chooseRandom(months, 5));
        populate(valueMappings, "DOW", chooseRandom(dow, 5));
        populate(valueMappings, "YEAR", chooseRandom(years, 5));

        valueMappings.put("DOWCOUNT", chooseRandom(dow, 1).get(0));
        valueMappings.put("DOMCOUNT", chooseRandom(domcounts, 1).get(0));
        valueMappings.put("WEEK_ORDINAL", chooseRandom(weekcounts, 1).get(0));

        valueMappings.put("SEC_LIST", generateList("SEC", valueMappings));
        valueMappings.put("SEC_RANGE", generateRange("SEC", valueMappings));
        valueMappings.put("MIN_LIST", generateList("MIN", valueMappings));
        valueMappings.put("MIN_RANGE", generateRange("MIN", valueMappings));
        valueMappings.put("HOUR_LIST", generateList("HOUR", valueMappings));
        valueMappings.put("HOUR_RANGE", generateRange("HOUR", valueMappings));
        valueMappings.put("DOM_LIST", generateList("DOM", valueMappings));
        valueMappings.put("DOM_RANGE", generateRange("DOM", valueMappings));
        valueMappings.put("MONTH_LIST", generateList("MONTH", valueMappings));
        valueMappings.put("MONTH_RANGE", generateRange("MONTH", valueMappings));
        valueMappings.put("DOW_LIST", generateList("DOW", valueMappings));
        valueMappings.put("DOW_RANGE", generateRange("DOW", valueMappings));
        valueMappings.put("YEAR_LIST", generateList("YEAR", valueMappings));
        valueMappings.put("YEAR_RANGE", generateRange("YEAR", valueMappings));

        return valueMappings;
    }

    private void validate(Map<String, String> expressions, CronParser parser){
        expressions.forEach((key, value) -> {
            Map<String, String> valueMappings = createRandomValueMappings(seconds, minutes, hours, months, dow, dom, years, weekcounts, domcounts);
            String instance = generateCronExpressionInstance(key, valueMappings);
            try {
                parser.parse(instance);
            } catch (RuntimeException ex) {
                System.out.println(String.format("Not valid: %s with message: %s", key, ex.getMessage()));
            }
        });
    }

    private String generateList(String prefix, Map<String, String> valueMappings){
        int size = getNextRandom(5);
        StringBuilder builder = new StringBuilder();
        for (int j=1; j<=size; j++) {
            builder.append(String.format(",%s", valueMappings.get(String.format("%s%s", prefix, j))));
        }
        return builder.toString().replaceFirst(",", "");
    }

    private String generateRange(String prefix, Map<String, String> valueMappings){
        return String.format("%s-%s", valueMappings.get(String.format("%s1", prefix)), valueMappings.get(String.format("%s2", prefix)));
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
            int ch = getNextRandom(alternatives.size());
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

    private String generateCronExpressionInstance(String crontemplate, Map<String, String> mappings){
        String[]template = new String[]{crontemplate};
        mappings.forEach((key, value) -> template[0] = template[0].replace(key, value));
        return template[0];
    }

    private Map<String, String> templates(){
        Map<String, String> templates = new HashMap<>();
        InputStream inputStream =
                getClass().getClassLoader()
                        .getResourceAsStream(
                                String.format(
                                        this.datasetOptions.getTemplate().getTemplateString(),
                                        this.datasetOptions.getTargetLanguage().toString().toLowerCase()
                                )
                        );
        try (Scanner s = new Scanner(inputStream)) {
            while (s.hasNext()) {
                String line = s.nextLine();
                templates.put(line.split("\\|")[0], line.split("\\|")[1]);
            }
        }
        return templates;
    }

    private int getNextRandom(int boundSize){
        return this.random.nextInt(boundSize);
    }
}
