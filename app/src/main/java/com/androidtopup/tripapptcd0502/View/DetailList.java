package com.androidtopup.tripapptcd0502.View;

public class DetailList {
    private String name;
    private String otherDetails;

    public DetailList(String name, String otherDetails) {
        this.name = name;
        this.otherDetails = otherDetails;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getOtherDetails() {
        return otherDetails;
    }

    public void setOtherDetails(String otherDetails) {
        this.otherDetails = otherDetails;
    }
}
