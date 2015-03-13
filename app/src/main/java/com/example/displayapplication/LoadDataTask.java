package com.example.displayapplication;

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

class LoadDataTask extends AsyncTask<String, String, List<EventItem>> {
    public static final String TAG = "LoadDataTask";
    private EventListAdapter adapter;

    public void setAdapter(EventListAdapter adapter) {
        this.adapter = adapter;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        //TODO show progressBar
    }

    @Override
    protected List<EventItem> doInBackground(String... f_url) {
        List<EventItem> eventList = null;
        try {
            URL url = new URL(f_url[0]);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            eventList = readJsonStream(connection.getInputStream());
            Log.i(TAG, "events number: " + eventList.size());
        } catch (Exception e) {
            Log.e(TAG, e.getMessage());
        }
        return eventList;
    }

    protected void onProgressUpdate(String... progress) {
        //TODO
    }

    @Override
    protected void onPostExecute(List<EventItem> eventList) {
        Log.i(TAG, "events number: " + eventList.size());
        adapter.addAll(eventList);
        adapter.notifyDataSetChanged();
    }

    public List<EventItem> readJsonStream(InputStream in) throws IOException {
        JsonReader reader = new JsonReader(new InputStreamReader(in, "UTF-8"));
        String image = null;
        List<EventItem> eventList = null;
        try {
            reader.beginObject();
            while (reader.hasNext()) {
                String name = reader.nextName();
                if (name.equals("image")) {
                    image = reader.nextString();
                }
                else if(name.equals("events")) {
                    eventList = readEventsArray(reader);
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
            return eventList;
        }
    }


    public List readEventsArray(JsonReader reader) throws IOException {
        List<EventItem> eventList = new ArrayList<EventItem>();

        reader.beginArray();
        while (reader.hasNext()) {
            eventList.add(readEvent(reader));
        }
        reader.endArray();
        return eventList;
    }

    public EventItem readEvent(JsonReader reader) throws IOException {
        String eventName = null;
        EventDetails eventDetails = null;
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
        return new EventItem(eventName, eventDetails, eventImage);
    }

    public EventDetails readEventDetails(JsonReader reader) throws IOException {
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
        return new EventDetails(color, date, event_end);
    }
}
