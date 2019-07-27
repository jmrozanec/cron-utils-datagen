package com.cronutils.dataset;

import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.Map;

public class Dataset {
    private Map<String, String> sources;
    private Map<String, String> targets;

    public Dataset(Map<String, String> sources, Map<String, String> targets){
        this.sources = Collections.unmodifiableMap(new LinkedHashMap<>(sources));
        this.targets = Collections.unmodifiableMap(new LinkedHashMap<>(targets));;
    }

    public final Map<String, String> getSources(){
        return sources;
    }

    public final Map<String, String> getTargets(){
        return targets;
    }

    public final void visit(DatasetVisitor visitor){
        visitor.visit(this);
    }
}