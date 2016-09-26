package com.gamaliel.surya.merame;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        Button searchButton = (Button) findViewById(R.id.button_main_search);
        searchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, SearchTrip.class);
                startActivity(intent);
            }
        });

        Button createButton = (Button) findViewById(R.id.button_main_create_plan);
        createButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(MainActivity.this, "Create", Toast.LENGTH_SHORT).show();
            }
        });

        Button listButton = (Button) findViewById(R.id.button_main_my_trip_list);
        listButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(MainActivity.this, "List", Toast.LENGTH_SHORT).show();
            }
        });

        Button chatButton = (Button) findViewById(R.id.button_main_chat_room);
        chatButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(MainActivity.this, "Chat", Toast.LENGTH_SHORT).show();
            }
        });

    }

}
