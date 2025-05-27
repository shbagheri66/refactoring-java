package com.movierental.model;

public enum MovieCode {
    REGULAR("regular"),
    NEW_MOVIE("new"),
    CHILDRENS("childrens");

    private final String value;

    MovieCode(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}