package com.cronutils.dataset;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;

public interface DatasetVisitor {

    void visit(Dataset dataset);

    static DatasetVisitor exportPipedFileDatasetVisitor(String targetFilename){
        return dataset -> {
            BufferedWriter writer = null;
            try {
                writer = new BufferedWriter(new FileWriter(targetFilename));
                for(String key : dataset.getSources().keySet()){
                    String entry = String.format("%s\n", String.format("%s | %s", dataset.getSources().get(key), dataset.getTargets().get(key)).trim().replaceAll(" +", " "));
                    writer.write(entry);
                }
                writer.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        };
    }
}