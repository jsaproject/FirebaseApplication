package com.example.juan_.practicafirebase;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.TextView;

public class ChatActivity extends AppCompatActivity {

    private TextView nameGroup;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        nameGroup = (TextView) findViewById(R.id.nameGroup);
        String sessionId= getIntent().getStringExtra("Grupo");
        nameGroup.setText(sessionId);



    }

}
