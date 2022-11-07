package com.androidtopup.tripapptcd0502.View;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class Data {
    @SerializedName("jsonpayload")
    private String jsonpayload;
    @SerializedName("uploadResponseCode")
    private String uploadResponseCode;
    @SerializedName("userId")
    private String userId;
    @SerializedName("detailList")
    private List<DetailList> detailList;
    @SerializedName("number")
    private int number;

    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
    }

    @SerializedName("names")
    private String names;
    @SerializedName("message")
    private String message;

    public String getUploadResponseCode() {
        return uploadResponseCode;
    }

    public void setUploadResponseCode(String uploadResponseCode) {
        this.uploadResponseCode = uploadResponseCode;
    }

    public String getNames() {
        return names;
    }

    public void setNames(String names) {
        this.names = names;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Data(String userId, List<DetailList> detailList) {
        this.userId = userId;
        this.detailList = detailList;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public List<DetailList> getDetailList() {
        return detailList;
    }

    public void setDetailList(List<DetailList> detailList) {
        this.detailList = detailList;
    }
}
