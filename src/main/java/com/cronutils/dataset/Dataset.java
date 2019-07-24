package com.cronutils.dataset;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Map;

public class Dataset {
    private Map<String, String> sources;
    private Map<String, String> targets;

    public Dataset(Map<String, String> sources, Map<String, String> targets){
        this.sources = sources;
        this.targets = targets;
    }

    public void export(String filename){
        BufferedWriter writer = null;
        try {
            writer = new BufferedWriter(new FileWriter(filename));
            for(String key : sources.keySet()){
                writer.write(String.format("%s | %s\n", sources.get(key), targets.get(key)));
            }
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}