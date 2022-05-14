package com.example.demo;

public enum Message {
    S001_SIGNUP_COMPLETE("S001"), //
    S002_UPDATE_COMPLETE("S002"), //
    S003_DELETE_COMPLETE("S003"), //
    S004_UPDATE_PASSWORD_COMPLETE("S004"), //
    E001_TOKEN_EXPIRED("E001"), //
    E002_NOT_EXIST_USER("E002"), //
    E003_IS_ALREADY_EXIST("E003");//

    private Message(String key) {
        this.key = key;
    }

    private String key;

    public String getKey() {
        return this.key;
    }
}