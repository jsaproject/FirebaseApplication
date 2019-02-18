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

  /*      if(firebaseAuth.getCurrentUser() != null){
            finish();
            startActivity(new Intent(getApplicationContext(), ProfileActivity.class));
        }else{
            finish();
            startActivity(new Intent(getApplicationContext(), LoginActivity.class));
        }*/

         firebase = FirebaseDatabase.getInstance().getReference();






/*        buttonRegister = (Button)findViewById(R.id.buttonRegister);
       buttonLogin = (Button)findViewById(R.id.buttonLogin);
        editTextEmail = (EditText)findViewById(R.id.editTextEmail);
       editTextPassword = (EditText)findViewById(R.id.editTextPassword);*/



/*        buttonRegister.setOnClickListener(this);
       buttonLogin.setOnClickListener(this);*/

    }

    @Override
    public void onStart() {
        super.onStart();

        // Check auth on Activity start
        if (firebaseAuth.getCurrentUser() != null) {
            finish();
            startActivity(new Intent(getApplicationContext(), ProfileActivity.class));
        }else{
            finish();
            startActivity(new Intent(getApplicationContext(), LoginActivity.class));
        }
    }




/*    private void registerUser(){

        String email = editTextEmail.getText().toString().trim();
        String password  = editTextPassword.getText().toString().trim();

        //creating a new user
        firebaseAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                //checking if success
                if(task.isSuccessful()){
                    //display some message here
                    Toast.makeText(MainActivity.this,"Registration correct",Toast.LENGTH_LONG).show();
                }else{
                    //display some message here
                    Toast.makeText(MainActivity.this,"Registration Error",Toast.LENGTH_LONG).show();
                }

            }
        });



    }

    private void loginUser() {
        String email = editTextEmail.getText().toString().trim();
        String password  = editTextPassword.getText().toString().trim();

        firebaseAuth.signInWithEmailAndPassword(email,password).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()){
                    finish();
                    startActivity(new Intent(getApplicationContext(), ProfileActivity.class));
                }else{
                    Toast.makeText(MainActivity.this,"User or password incorrect",Toast.LENGTH_LONG).show();
                }
            }
        });



    }*/

   /* @Override
    public void onClick(View view) {
        if (view == buttonRegister){
            registerUser();
        }
        if (view == buttonLogin){
            loginUser();
        }
        if (view == textViewSignin){
            startActivity(new Intent());
        }
    }*/
}
