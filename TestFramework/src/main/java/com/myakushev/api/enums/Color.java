package com.myakushev.api.enums;

public enum Color {
    BLUE("BLUE"),
    PINK("PINK"),
    YELLOW("YELLOW");

    private String text;

    Color(String text) {
      this.text = text;
    }

    public String getColorText() {
        return text;
    }
}
