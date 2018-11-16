package com.soxfmr.jsloader.model;

public class PayloadInfo {

    private String name;
    private String expression;

    public PayloadInfo(String name, String expression) {
        this.name = name;
        this.expression = expression;
    }

    public String getExpression() {
        return expression;
    }

    public void setExpression(String expression) {
        this.expression = expression;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
