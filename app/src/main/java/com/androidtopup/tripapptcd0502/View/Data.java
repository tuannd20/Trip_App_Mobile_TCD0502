package com.androidtopup.tripapptcd0502.View;

public class Data {
    private String uploadResponseCode;

    public String getUploadResponseCode() {
        return uploadResponseCode;
    }

    public void setUploadResponseCode(String uploadResponseCode) {
        this.uploadResponseCode = uploadResponseCode;
    }

    private String userId;
    private int number;
    private String name;
    private String message;

    public Data(String uploadResponseCode, String userId, int number, String name, String message) {
        this.uploadResponseCode = uploadResponseCode;
        this.userId = userId;
        this.number = number;
        this.name = name;
        this.message = message;
    }

    public String getUserId() {
        return userId;
    }

    public int getNumber() {
        return number;
    }

    public String getNameUser() {
        return name;
    }

    public String getMessage() {
        return message;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public void setNumber(int number) {
        this.number = number;
    }

    public void setNameUser(String name) {
        this.name = name;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
