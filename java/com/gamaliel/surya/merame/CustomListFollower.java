package com.gamaliel.surya.merame;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

public class CustomListFollower extends ArrayAdapter<String> {

    private final Activity context;
    private final ArrayList<String> userName;
    private final ArrayList<Integer> imageId;
    public CustomListFollower(Activity context,
                              ArrayList<String> userName, ArrayList<Integer> imageId) {
        super(context, R.layout.part_single_follower, userName);
        this.context = context;
        this.userName = userName;
        this.imageId = imageId;
    }

    @Override
    public View getView(int position, View view, ViewGroup parent) {
        LayoutInflater inflater = context.getLayoutInflater();
        View rowView= inflater.inflate(R.layout.part_single_follower, null, true);
        TextView txtTitle = (TextView) rowView.findViewById(R.id.sFollower_name);

        ImageView imageView = (ImageView) rowView.findViewById(R.id.sFollower_image);
        txtTitle.setText(userName.get(position));

        imageView.setImageResource(imageId.get(position));
        return rowView;
    }
}
