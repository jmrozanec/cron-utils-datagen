package com.cronutils.dataset;

public enum TemplateEnum {
    TEMPLATE_01("%s-01.template");

    private String templateString;

    TemplateEnum(String templateString){
        this.templateString = templateString;
    }

    public String getTemplateString(){
        return this.templateString;
    }
}
