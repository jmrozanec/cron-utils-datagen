package com.cronutils.dataset;

public enum CronDescriptionTemplate {
    TEMPLATE_01("%s-traintest.template");

    private String templateString;

    CronDescriptionTemplate(String templateString){
        this.templateString = templateString;
    }

    public String getTemplateString(){
        return this.templateString;
    }
}
