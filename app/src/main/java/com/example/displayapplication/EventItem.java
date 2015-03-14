package com.example.displayapplication;

public class EventItem {
    private String name;
    private String color;
    private String date;
    private String eventEnd;
    private String image;
    private long id = -1;

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

    public EventItem() {
        this.name = "";
        this.color = "";
        this.date = "";
        this.eventEnd = "";
        this.image = "";
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getEventEnd() {
        return eventEnd;
    }

    public void setEventEnd(String eventEnd) {
        this.eventEnd = eventEnd;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    @Override
    public String toString() {
        return name + ": " + color + ", " + date + ", " +  eventEnd;
    }
}