package com.gamaliel.surya.merame;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.Date;

public class ViewHolderTrip extends RecyclerView.ViewHolder {
    View mView;

    public ViewHolderTrip(View itemView) {
        super(itemView);
        mView = itemView;
    }

    public void setUsername(String username) {
        TextView sIdUser = (TextView) mView.findViewById(R.id.singleTrip_user);
        sIdUser.setText(username);
    }

    public void setFollower(int follower) {
        TextView sFollower = (TextView) mView.findViewById(R.id.singleTrip_follower);
        sFollower.setText(String.valueOf(follower));
    }

    public void setCreated(long created) {
        TextView sCreated = (TextView) mView.findViewById(R.id.singleTrip_created);
        String tCreated = new SimpleDateFormat("dd.MM.yy").format(new Date(created));
        sCreated.setText(tCreated);
    }
}