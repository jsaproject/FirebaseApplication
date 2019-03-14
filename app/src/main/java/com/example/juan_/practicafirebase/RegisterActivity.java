package com.example.juan_.practicafirebase;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.juan_.practicafirebase.models.User;
import com.example.juan_.practicafirebase.models.UserList;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;

public class RegisterActivity extends AppCompatActivity implements  View.OnClickListener{

    private Button buttonRegister;
    private EditText editTextEmail;
    private EditText editTextPassword;
    private EditText editTextNombre;
    private EditText editTextApellidos;
    private EditText editTextNumero;


    private FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();
    private DatabaseReference firebase;
    private FirebaseFirestore db = FirebaseFirestore.getInstance();
    private String email;
    private User user;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);


        buttonRegister = (Button)findViewById(R.id.register_button);

        editTextEmail = (EditText)findViewById(R.id.register_email);
        editTextPassword = (EditText)findViewById(R.id.register_password);
        editTextNombre = (EditText) findViewById(R.id.register_nombre);
        editTextApellidos = (EditText) findViewById(R.id.register_apellidos);
        editTextNumero = (EditText) findViewById(R.id.register_telefono);

        db = FirebaseFirestore.getInstance();

        buttonRegister.setOnClickListener(this);





    }

    private void registerUser(){

        String email = editTextEmail.getText().toString().trim();
        String password  = editTextPassword.getText().toString().trim();
        String nombre = editTextNombre.getText().toString().trim();
        String apellidos = editTextApellidos.getText().toString().trim();
        String telefono = editTextNumero.getText().toString().trim();

        //creating a new user
        firebaseAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                //checking if success
                if(task.isSuccessful()){
                    onAuthSuccess(task.getResult().getUser());
                    Toast.makeText(RegisterActivity.this,"Registration correct",Toast.LENGTH_LONG).show();
                    finish();
                    startActivity(new Intent(RegisterActivity.this,LoginActivity.class));
                }else{
                    //display some message here
                    Toast.makeText(RegisterActivity.this,"Registration Error",Toast.LENGTH_LONG).show();
                }

            }
        });


    }

    private void onAuthSuccess(FirebaseUser user) {
        String username = usernameFromEmail(user.getEmail());



        // Write new user
        writeNewUser(username, user.getEmail());

    }

    private String usernameFromEmail(String email) {
        if (email.contains("@")) {
            return email.split("@")[0];
        } else {
            return email;
        }
    }

    private void writeNewUser(String name, String email) {
        String nombre = editTextNombre.getText().toString().trim();
        String apellidos = editTextApellidos.getText().toString().trim();
        String telefono = editTextNumero.getText().toString().trim();

        user = new User(email, nombre, apellidos, telefono);

        db.collection("users").document(user.getEmail()).set(user);



    }

    @Override
    public void onClick(View v) {
        if (v == buttonRegister){
            registerUser();

        }

    }
}
