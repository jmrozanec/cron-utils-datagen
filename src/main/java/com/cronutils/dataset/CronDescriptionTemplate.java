package com.cronutils.dataset;

public enum CronDescriptionTemplate {
    TEMPLATE_01("%s-01.template"), TEMPLATE_02("%s-02.template");

    private String templateString;

    CronDescriptionTemplate(String templateString){
        this.templateString = templateString;
    }

    public String getTemplateString(){
        return this.templateString;
    }
}
