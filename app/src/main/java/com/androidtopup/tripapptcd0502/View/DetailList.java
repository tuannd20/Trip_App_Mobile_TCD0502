package com.androidtopup.tripapptcd0502.View;

import java.util.List;

public class DetailList {
    private String name;
    private List<OtherDetails> otherDetails;

    public DetailList(String name, List<OtherDetails> otherDetails) {
        this.name = name;
        this.otherDetails = otherDetails;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    static class OtherDetails {
        private String destination;
        private String date;

        public OtherDetails(String destination, String date) {
            this.destination = destination;
            this.date = date;
        }

        public String getDestination() {
            return destination;
        }

        public void setDestination(String destination) {
            this.destination = destination;
        }

        public String getDate() {
            return date;
        }

        public void setDate(String date) {
            this.date = date;
        }
    }
}
