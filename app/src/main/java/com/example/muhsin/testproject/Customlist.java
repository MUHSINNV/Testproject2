package com.example.muhsin.testproject;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;

/**
 * Created by MUHSIN on 2/16/2017.
 */

public class Customlist extends ArrayAdapter<String> {
    private List<String> id;
    private List<String> name;
    //private Integer[] imageid;
    private Activity context;

    public Customlist(Activity context, List<String> id, List<String> name) {
        super(context, R.layout.list_layout, id);
        this.context = context;
        this.id = id;
        this.name = name;
        //this.imageid = imageid;

    }
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = context.getLayoutInflater();
        View listViewItem = inflater.inflate(R.layout.list_layout, null, true);
        TextView textViewName = (TextView) listViewItem.findViewById(R.id.textViewName);
        TextView textViewDesc = (TextView) listViewItem.findViewById(R.id.textViewDesc);
        //ImageView image = (ImageView) listViewItem.findViewById(R.id.imageView);

        textViewName.setText(id.get(position));
        textViewDesc.setText(name.get(position));
        //image.setImageResource(imageid[position]);
        return  listViewItem;
    }
}
