package com.androidtopup.tripapptcd0502.View;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class Data {
    @SerializedName("uploadResponseCode")
    private String uploadResponseCode;
    @SerializedName("userId")
    private String userId;
    @SerializedName("detailList")
    private List<DetailList> detailList;
    @SerializedName("number")
    private int number;
    @SerializedName("names")
    private String names;
    @SerializedName("message")
    private String message;


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
