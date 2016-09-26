package com.gamaliel.surya.merame;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.Transaction;
import com.google.firebase.database.ValueEventListener;

public class ResultTrip extends AppCompatActivity {

    public ProgressBar progressBar;
    public String keyPlace, keyMonth, keyYear, keyBudget, keyAge, keyGender;
    public RecyclerView mTripList;
    public LinearLayoutManager mManager;
    public DatabaseReference mTripRef;
    public FirebaseRecyclerAdapter<Trip, ViewHolderTrip> mAdapter;
    public Query thisQuery;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result_trip);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        if (savedInstanceState == null) {
            Bundle extras = getIntent().getExtras();
            if (extras == null) {
                Log.e("Error_Extra_NULL", "NULL EXTRAS BUNDLE");
            } else
            {
                keyPlace = extras.getString("KEY_PLACE");
                keyMonth = extras.getString("KEY_MONTH");
                keyYear = extras.getString("KEY_YEAR");
                keyBudget = extras.getString("KEY_BUDGET");
                keyAge = extras.getString("KEY_AGE");
                keyGender = extras.getString("KEY_GENDER");
            }
        } else {
            keyPlace = (String) savedInstanceState.getSerializable("KEY_PLACE");
            keyMonth = (String) savedInstanceState.getSerializable("KEY_MONTH");
            keyYear = (String) savedInstanceState.getSerializable("KEY_YEAR");
            keyBudget = (String) savedInstanceState.getSerializable("KEY_BUDGET");
            keyAge = (String) savedInstanceState.getSerializable("KEY_AGE");
            keyGender = (String) savedInstanceState.getSerializable("KEY_GENDER");
        }

        mTripRef = FirebaseDatabase.getInstance().getReference()
                .child("trip")
                .child(keyYear).child(keyPlace).child(keyMonth)
                .child(keyBudget).child(keyGender).child(keyAge);

        TextView tv = (TextView) findViewById(R.id.test_gama);
        tv.setText(
                keyAge + "\n" +
                        keyBudget + "\n" +
                        keyGender + "\n" +
                        keyMonth + "\n" +
                        keyYear + "\n" +
                        keyPlace
        );
        Toast.makeText(ResultTrip.this, mTripRef.getKey(), Toast.LENGTH_SHORT).show();

        mTripRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    Trip trip = dataSnapshot.getValue(Trip.class);
                    Toast.makeText(ResultTrip.this, trip.getUsername(), Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(ResultTrip.this, "Data Tidak Ada", Toast.LENGTH_SHORT).show();
                    progressBar.setVisibility(View.GONE);
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        mTripList = (RecyclerView) findViewById(R.id.recycler_trip_list);
        mManager = new LinearLayoutManager(this);
        mManager.setReverseLayout(false);
        mManager.setOrientation(LinearLayoutManager.VERTICAL);
        mTripList.setHasFixedSize(true);
        mTripList.setLayoutManager(mManager);
    }

    @Override
    public void onStart() {
        super.onStart();
        thisQuery  = mTripRef;

        mAdapter = new FirebaseRecyclerAdapter<Trip, ViewHolderTrip>
                (Trip.class,
                        R.layout.part_single_trip,
                        ViewHolderTrip.class, thisQuery) {

            @Override
            public void populateViewHolder(ViewHolderTrip tvh, Trip trip, final int pos) {
                progressBar = (ProgressBar) findViewById(R.id.progbar_resultTrip);
                progressBar.setVisibility(View.GONE);
                Toast.makeText(ResultTrip.this, "populateView masuk", Toast.LENGTH_LONG).show();
                tvh.setUsername(trip.getUsername());
                tvh.setCreated(trip.getCreated());
                tvh.setFollower(trip.getFollower());

                final String tempUser = trip.getUsername();

                tvh.mView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(ResultTrip.this, ResultTripDetail.class);
                        intent.setFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
                        intent.putExtra("KEY_TRIP", mAdapter.getRef(pos).getKey());
                        intent.putExtra("KEY_USERNAME", tempUser);
                        startActivity(intent);
                    }
                });
            }
        };
        mTripList.setAdapter(mAdapter);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_search_trip, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
