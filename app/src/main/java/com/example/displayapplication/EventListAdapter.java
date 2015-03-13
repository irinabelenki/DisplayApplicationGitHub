package com.example.displayapplication;

import java.util.List;
import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class EventListAdapter extends ArrayAdapter<EventItem> {
    Context context;

    public EventListAdapter(Context context, int resourceId, List<EventItem> items) {
        super(context, resourceId, items);
        this.context = context;
    }

    private class ViewHolder {
        ImageView eventImage;
        TextView eventName;
    }

    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder = null;
        EventItem rowItem = getItem(position);

        LayoutInflater mInflater = (LayoutInflater) context.getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
        if (convertView == null) {
            convertView = mInflater.inflate(R.layout.event_item, null);
            holder = new ViewHolder();
            holder.eventName = (TextView) convertView.findViewById(R.id.event_name);
            holder.eventImage = (ImageView) convertView.findViewById(R.id.event_image);
            convertView.setTag(holder);
        }
        else {
            holder = (ViewHolder) convertView.getTag();
        }

        holder.eventName.setText(rowItem.getName());
        holder.eventImage.setImageResource(android.R.drawable.ic_menu_save);

        return convertView;
    }
}
