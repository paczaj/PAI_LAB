package com.project.stm.model.enums;

public enum Types {
    TASK("TASK"),
    BUG("BUG"),
    FEATURES("FEATURES");

    private final String typesName;

    Types(String typesName){
        this.typesName = typesName;
    }

    public String getTypesName() {
        return typesName;
    }
}
