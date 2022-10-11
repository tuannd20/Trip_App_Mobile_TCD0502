package com.androidtopup.tripapptcd0502.Adapter;

import java.util.ArrayList;

public class TripModel {
    private final String id;
    private final ArrayList<String> name;
    private final ArrayList<String> destination;
    private final ArrayList<String> dateOfTrip;
    private final ArrayList<String> assessment;
    private String description;

    public TripModel(String id, ArrayList<String> name, ArrayList<String> destination, ArrayList<String> dateOfTrip, ArrayList<String> assessment) {
        this.id = id;
        this.name = name;
        this.destination = destination;
        this.dateOfTrip = dateOfTrip;
        this.assessment = assessment;
//        this.description = description;
    };

    public String getIdTrip() {
//        for (int i = 0; i < id.toString().length(); i++) {
//            return id.get(i);
//        }
//        return null;
        return id;
    }

    public ArrayList<String> getNameTrip() {
        return name;
    }

    public ArrayList<String> getDateOfTrip() {
        return dateOfTrip;
    }

    public ArrayList<String> getDestination() {
        return destination;
    }

    public ArrayList<String> getAssessment() {
        return assessment;
    }

    public String getDescription    () {
        return description;
    }


}
