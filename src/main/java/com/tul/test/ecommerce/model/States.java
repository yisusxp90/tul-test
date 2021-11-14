package com.tul.test.ecommerce.model;

public enum States {

    PENDING("PENDING"),
    COMPLETE("COMPLETE");

    private String value;

    States(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}
