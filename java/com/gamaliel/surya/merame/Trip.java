package com.gamaliel.surya.merame;

import com.google.firebase.database.IgnoreExtraProperties;

@IgnoreExtraProperties
public class Trip {
    public String username;
    public int follower;
    public long created;

    //required default contructor for Firebase object mapping
    @SuppressWarnings("unused")
    public Trip() {}

    Trip(
            long created,
            int follower,
            String username
    ) {
        this.created = created;
        this.follower = follower;
        this.username = username;
    }
    public long getCreated() {
        return created;
    }

    public int getFollower() {
        return follower;
    }

    public String getUsername() {
        return username;
    }

}