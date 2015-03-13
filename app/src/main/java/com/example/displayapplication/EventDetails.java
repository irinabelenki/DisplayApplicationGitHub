package com.example.displayapplication;

public class EventDetails {
    private String color;
    private String date;
    private String eventEnd;

    EventDetails(String color, String date, String eventEnd) {
        this.color = color;
        this.date = date;
        this.eventEnd = eventEnd;
    }

    public String getColor() {
        return color;
    }

    public String getDate() {
        return date;
    }

    public String getEventEnd() {
        return eventEnd;
    }
}
