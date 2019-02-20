package com.example.juan_.practicafirebase;



import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.firebase.client.Firebase;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import com.google.firebase.auth.*;
import com.google.firebase.database.*;
import com.google.firebase.firestore.FirebaseFirestore;

public class MainActivity extends AppCompatActivity{

/*private Button buttonRegister;
private EditText editTextEmail;
private EditText editTextPassword;
private TextView textViewSignin;
private Button   buttonLogin;*/


private FirebaseAuth firebaseAuth;
private DatabaseReference firebase;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        firebaseAuth = FirebaseAuth.getInstance();


         firebase = FirebaseDatabase.getInstance().getReference();






    }

    @Override
    public void onStart() {
        super.onStart();


        if (firebaseAuth.getCurrentUser() != null) {
            finish();
            startActivity(new Intent(getApplicationContext(), ProfileActivity.class));
        }else{
            finish();
            startActivity(new Intent(getApplicationContext(), LoginActivity.class));
        }
    }





}
