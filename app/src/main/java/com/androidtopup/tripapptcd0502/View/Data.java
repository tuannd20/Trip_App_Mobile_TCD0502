package com.androidtopup.tripapptcd0502.View;

import java.util.List;

public class Data {
    private String userId;
    private List<DetailList> detailList;

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

    @Override
    public String toString() {
        return "Data{" +
                "userId='" + userId + '\'' +
                ", detailList=" + detailList +
                '}';
    }
}
