package com.github.gridswipe_launcher;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

public class AppAdapter extends ArrayAdapter<AppDetail> {
    private final LayoutInflater inflater;

    AppAdapter(Context context) {
        super(context, android.R.layout.simple_list_item_2);

        inflater = LayoutInflater.from(context);
    }

    // Populate new items in the list.
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view;

        if (convertView == null)
            view = inflater.inflate(R.layout.icon_text, parent, false);
        else // convertView != null
            view = convertView;

        AppDetail app = getItem(position);
        ((ImageView)view.findViewById(R.id.icon)).setImageDrawable(app.getIcon());
        ((TextView)view.findViewById(R.id.text)).setText(app.getLabel());

        return view;
    }

    public void setData(ArrayList<AppDetail> data, int position) {
        clear();

        int i = 24 * position;
        int end = i + 23;

        if (data != null)
            while (i <= end && i < data.size()) {
                add(data.get(i));
                i++;
            }
    }
}
