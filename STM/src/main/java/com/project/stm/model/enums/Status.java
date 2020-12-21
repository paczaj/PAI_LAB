package com.project.stm.model.enums;

public enum Status {
    NEW("NEW"),
    INPROGRESS("INPROGRESS"),
    DONE("DONE");

    private final String statusName;

    Status(String statusName){
        this.statusName = statusName;
    }

    public String getStatusName() {
        return statusName;
    }
}
