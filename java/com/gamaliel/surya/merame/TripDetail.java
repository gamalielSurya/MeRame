package com.gamaliel.surya.merame;

import com.google.firebase.database.IgnoreExtraProperties;
import java.util.Map;

public class TripDetail {
    public String deskripsi;
    public Map<String, Boolean> followerID;
    public String video;

    //required default contructor for Firebase object mapping
    @SuppressWarnings("unused")
    public TripDetail() {}

    TripDetail(
            String deskripsi,
            Map<String, Boolean> followerID,
            String video) {

        this.deskripsi = deskripsi;
        this.followerID = followerID;
        this.video = video;
    }

    public Map<String, Boolean> getFollowerID() { return followerID; }

    public String getDeskripsi() {
        return deskripsi;
    }

    public String getVideo() {
        return video;
    }
}
