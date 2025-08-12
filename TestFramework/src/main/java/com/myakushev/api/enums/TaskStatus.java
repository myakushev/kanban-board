package com.myakushev.api.enums;

public enum TaskStatus {
    TODO("To do"),
    IN_PROGRESS("In Progress"),
    DONE("Done");

    private String text;

    TaskStatus(String text) {
        this.text = text;
    }

    public String getText() {
      return text;
    }
}
