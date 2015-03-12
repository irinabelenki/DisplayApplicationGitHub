package com.example.displayapplication;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import android.os.AsyncTask;
import android.util.JsonReader;
import android.util.Log;

class LoadDataTask extends AsyncTask<String, String, String> {
    public static final String TAG = "LoadDataTask";

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        //TODO show progressBar
    }

    @Override
    protected String doInBackground(String... f_url) {

        try {
            URL url = new URL(f_url[0]);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            List events = readJsonStream(connection.getInputStream());
            Log.i(TAG, "events number: " + events.size());
        } catch (Exception e) {
            Log.e(TAG, e.getMessage());
        }
        return null;
    }

    protected void onProgressUpdate(String... progress) {
        //TODO
    }

    @Override
    protected void onPostExecute(String file_url) {
        //TODO fill list
    }

    public List readJsonStream(InputStream in) throws IOException {
        JsonReader reader = new JsonReader(new InputStreamReader(in, "UTF-8"));
        String image = null;
        List events = null;
        try {
            reader.beginObject();
            while (reader.hasNext()) {
                String name = reader.nextName();
                if (name.equals("image")) {
                    image = reader.nextString();
                }
                else if(name.equals("events")) {
                    events = readEventsArray(reader);
                }
                else {
                    reader.skipValue();
                }
            }
            reader.endObject();
        }
        catch (Exception e) {
            Log.e(TAG, e.getMessage());
        }
        finally {
            reader.close();
            return events;
        }
    }


    public List readEventsArray(JsonReader reader) throws IOException {
        List events = new ArrayList();

        reader.beginArray();
        while (reader.hasNext()) {
            events.add(readEvent(reader));
        }
        reader.endArray();
        return events;
    }

    public Event readEvent(JsonReader reader) throws IOException {
        String eventName = null;
        Details eventDetails = null;
        String eventImage = null;

        reader.beginObject();
        while (reader.hasNext()) {
            String name = reader.nextName();
            if (name.equals("name")) {
                eventName = reader.nextString();
                Log.i(TAG, "event name: " + eventName);
            }
            else if (name.equals("details")) {
                eventDetails = readEventDetails(reader);
            }
            else if (name.equals("image")) {
                eventImage = reader.nextString();
            }
            else {
                reader.skipValue();
            }
        }
        reader.endObject();
        return new Event(eventName, eventDetails, eventImage);
    }

    public Details readEventDetails(JsonReader reader) throws IOException {
        String color = null;
        String date = null;
        String event_end = null;

        reader.beginObject();
        while (reader.hasNext()) {
            String name = reader.nextName();
            if (name.equals("color")) {
                color = reader.nextString();
            }
            else if (name.equals("date")) {
                date = reader.nextString();
            }
            else if (name.equals("event_end")) {
                event_end = reader.nextString();
            }else {
                reader.skipValue();
            }
        }
        reader.endObject();
        return new Details(color, date, event_end);
    }

    private class Details {
        private String color;
        private String date;
        private String event_end;

        Details(String color, String date, String event_end) {
            this.color = color;
            this.date = date;
            this.event_end = event_end;
        }
    }

    private class Event {
        private String name;
        private Details details;
        private String image;

        Event( String name, Details details, String image) {
            this.name = name;
            this.details = details;
            this.image = image;
        }
    }

}
