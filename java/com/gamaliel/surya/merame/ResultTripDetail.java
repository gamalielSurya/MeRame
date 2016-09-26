package com.gamaliel.surya.merame;

import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.MediaController;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.VideoView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class ResultTripDetail extends AppCompatActivity {

    DatabaseReference mTripRef, nameRef;
    TextView deskripsi, video, idTrip, follower;
    String sentKeyTrip, sentKeyUserName;
    String tempName;

    String videoURL = "https://firebasestorage.googleapis.com/v0/b/merame-6cb99.appspot.com/o/sample.3gp?alt=media&token=e7a4db51-5f0b-4b50-ad05-f59a6411decf";
    VideoView videoView;
    private MediaController mediaController;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result_trip_detail);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


        videoView = (VideoView) findViewById(R.id.video_teaser);

        try {
            mediaController = new MediaController(ResultTripDetail.this);
            mediaController.setAnchorView(videoView);
            Uri video = Uri.parse(videoURL);
            videoView.setMediaController(mediaController);
            videoView.setVideoURI(video);
        } catch (Exception e) {
            Log.e("Error video streaming", e.getMessage());
            e.printStackTrace();
        }

        videoView.requestFocus();
        videoView.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
            @Override
            public void onPrepared(MediaPlayer mediaPlayer) {
         /*       mediaPlayer.setOnVideoSizeChangedListener(new MediaPlayer.OnVideoSizeChangedListener() {
                    @Override
                    public void onVideoSizeChanged(MediaPlayer mediaPlayer, int i, int i1) {
                        try {
                            mediaController = new MediaController(TripDetail.this);
                            mediaController.setAnchorView(videoView);
                            Uri video = Uri.parse(videoURL);
                            videoView.setMediaController(mediaController);
                            videoView.setVideoURI(video);
                        } catch (Exception e) {
                            Log.e("Error video streaming", e.getMessage());
                            e.printStackTrace();
                        }
                    }
                });*/
                videoView.start();
                mediaController.show();
            }
        });

        if (savedInstanceState == null) {
            Bundle extras = getIntent().getExtras();
            if (extras == null) {
                sentKeyTrip = "NULL_KEY";
                sentKeyUserName = "NULL_USERNAME";
            } else {
                sentKeyTrip = extras.getString("KEY_TRIP");
                sentKeyUserName = extras.getString("KEY_USERNAME");
            }
        } else {
            sentKeyTrip = (String) savedInstanceState.getSerializable("KEY_TRIP");
            sentKeyUserName = (String) savedInstanceState.getSerializable("KEY_USERNAME");
        }

        tempName = "";

        mTripRef = FirebaseDatabase.getInstance().getReference()
                .child("trip-detail").child(sentKeyTrip);

        deskripsi = (TextView) findViewById(R.id.det_deskripsi);
        video = (TextView) findViewById(R.id.det_video);
        idTrip = (TextView) findViewById(R.id.det_id);

        mTripRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                TripDetail detTrip = dataSnapshot.getValue(TripDetail.class);

                deskripsi.setText("DESKRIPSI__" + detTrip.getDeskripsi());
                video.setText("VIDEO__" + detTrip.getVideo());
                idTrip.setText("IDTRIP__" + sentKeyTrip);

                Map<String, Boolean> mapFollower = detTrip.getFollowerID();
                final List<String> listFollower = new ArrayList<>(mapFollower.keySet());
                tempName = "FOLLOWER___ : ";

                final ArrayList<String> userName = new ArrayList<>();
                final ArrayList<Integer> imageID = new ArrayList<>();
                final CustomListFollower listFollowerAdapter = new CustomListFollower(ResultTripDetail.this, userName, imageID);

                for(int i=0; i < listFollower.size(); i++) {
                    //tempName = tempName + listFollower.get(i).toString();

                    nameRef = FirebaseDatabase.getInstance().getReference()
                            .child("user").child(listFollower.get(i).toString()).child("name");

                    nameRef.addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(DataSnapshot dataSnapshot) {
                            userName.add(dataSnapshot.getValue().toString());
                            imageID.add(R.drawable.guest);

                            ListView listViewFollower = (ListView) findViewById(R.id.det_list_follower);
                            listViewFollower.setAdapter(listFollowerAdapter);
                            listViewFollower.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                                @Override
                                public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                                    Toast.makeText(ResultTripDetail.this, userName.get(i), Toast.LENGTH_SHORT).show();
                                }
                            });

                            /*tempName = tempName + "[" + dataSnapshot.getKey() + "]" + dataSnapshot.getValue() + ", ";
                            follower.setText(tempName);*/
                        }

                        @Override
                        public void onCancelled(DatabaseError databaseError) {

                        }
                    });

                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });




    }

}
