package com.example.displayapplication;

import java.util.ArrayList;
import java.util.List;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

public class EventDataSource {

    // Database fields
    private SQLiteDatabase database;
    private EventDBHelper dbHelper;
    private String[] allColumns = { EventDBHelper.COLUMN_ID,
                                    EventDBHelper.COLUMN_NAME,
                                    EventDBHelper.COLUMN_COLOR,
                                    EventDBHelper.COLUMN_DATE,
                                    EventDBHelper.COLUMN_EVENT_END,
                                    EventDBHelper.COLUMN_IMAGE_URL,
                                  };

    public EventDataSource(Context context) {
        dbHelper = new EventDBHelper(context);
    }

    public void open() throws SQLException {
        database = dbHelper.getWritableDatabase();
    }

    public void close() {
        dbHelper.close();
    }

    public EventItem createEvent(String name, String color, String date, String eventEnd, String imageUrl) {
        ContentValues values = new ContentValues();
        values.put(EventDBHelper.COLUMN_NAME, name);
        values.put(EventDBHelper.COLUMN_COLOR, color);
        values.put(EventDBHelper.COLUMN_DATE, date);
        values.put(EventDBHelper.COLUMN_EVENT_END, eventEnd);
        values.put(EventDBHelper.COLUMN_IMAGE_URL, imageUrl);
        long insertId = database.insert(EventDBHelper.TABLE_EVENTS, null, values);
        Cursor cursor = database.query(EventDBHelper.TABLE_EVENTS,
                allColumns, EventDBHelper.COLUMN_ID + " = " + insertId, null,
                null, null, null);
        cursor.moveToFirst();
        EventItem newEvent = cursorToEvent(cursor);
        cursor.close();
        return newEvent;
    }

    public void deleteEvent(EventItem event) {
        long id = event.getId();
        System.out.println("Event deleted with id: " + id);
        database.delete(EventDBHelper.TABLE_EVENTS, EventDBHelper.COLUMN_ID
                + " = " + id, null);
    }

    public List<EventItem> getAllEvents() {
        List<EventItem> events = new ArrayList<EventItem>();

        Cursor cursor = database.query(EventDBHelper.TABLE_EVENTS,
                allColumns, null, null, null, null, null);

        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            EventItem event = cursorToEvent(cursor);
            events.add(event);
            cursor.moveToNext();
        }
        cursor.close();
        return events;
    }

    private EventItem cursorToEvent(Cursor cursor) {
        EventItem event = new EventItem();
        event.setId(cursor.getLong(0));
        event.setName(cursor.getString(1));
        return event;
    }
}

