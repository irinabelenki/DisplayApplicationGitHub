package com.example.displayapplication;

public class EventItem {
    private String name;
    private String color;
    private String date;
    private String eventEnd;
    private String image;

    public EventItem(String name, String color, String date, String eventEnd, String image) {
        this.name = name;
        this.color = color;
        this.date = date;
        this.eventEnd = eventEnd;
        this.image = image;
    }

    public EventItem(String name, EventDetails details, String image) {
        this.name = name;
        this.color = details.getColor();
        this.date = details.getDate();
        this.eventEnd = details.getEventEnd();
        this.image = image;
    }

    public String getName() {
        return name;
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

    public String getImage() {
        return image;
    }

    @Override
    public String toString() {
        return name + ": " + color + ", " + date + ", " +  eventEnd;
    }
}