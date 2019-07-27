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
                    writer.write(String.format("%s | %s\n", dataset.getSources().get(key), dataset.getTargets().get(key)));
                }
                writer.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        };
    }
}