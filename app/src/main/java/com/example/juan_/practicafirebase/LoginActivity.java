package com.example.juan_.practicafirebase;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.juan_.practicafirebase.models.ListGroups;
import com.example.juan_.practicafirebase.models.User;
import com.example.juan_.practicafirebase.models.UserList;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.GenericTypeIndicator;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.SetOptions;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class LoginActivity extends AppCompatActivity implements  View.OnClickListener{


    private Button buttonRegister;
    private EditText editTextEmail;
    private EditText editTextPassword;
    private TextView textViewSignin;
    private Button   buttonLogin;


    private FirebaseAuth firebaseAuth;
    private DatabaseReference firebase;
    private FirebaseFirestore db;
    private ProfileActivity profileActivity;
    private ArrayList<String> listItems;
    private String email;
    private UserList userList1;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        profileActivity = new ProfileActivity();
        firebaseAuth = FirebaseAuth.getInstance();

        if(firebaseAuth.getCurrentUser() != null){
            finish();
            startActivity(new Intent(getApplicationContext(), ProfileActivity.class));
        }

        firebase = FirebaseDatabase.getInstance().getReference();

        db = FirebaseFirestore.getInstance();

        buttonRegister = (Button)findViewById(R.id.buttonRegister);
        buttonLogin = (Button)findViewById(R.id.buttonLogin);
        editTextEmail = (EditText)findViewById(R.id.editTextEmail);
        editTextPassword = (EditText)findViewById(R.id.editTextPassword);



        buttonRegister.setOnClickListener(this);
        buttonLogin.setOnClickListener(this);


    }


    private void registerUser(){

        String email = editTextEmail.getText().toString().trim();
        String password  = editTextPassword.getText().toString().trim();

        //creating a new user
        firebaseAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                //checking if success
                if(task.isSuccessful()){
                    onAuthSuccess(task.getResult().getUser());
                    Toast.makeText(LoginActivity.this,"Registration correct",Toast.LENGTH_LONG).show();
                }else{
                    //display some message here
                    Toast.makeText(LoginActivity.this,"Registration Error",Toast.LENGTH_LONG).show();
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
                    onAuthSuccess(task.getResult().getUser());
                    Toast.makeText(LoginActivity.this,"Comprueba",Toast.LENGTH_LONG).show();

                   finish();

                    startActivity(new Intent(getApplicationContext(), ProfileActivity.class));
                }else{
                    Toast.makeText(LoginActivity.this,"User or password incorrect",Toast.LENGTH_LONG).show();
                }
            }
        });



    }



    

    private void onAuthSuccess(FirebaseUser user) {
        String username = usernameFromEmail(user.getEmail());

        // Write new user
        writeNewUser(user.getUid(), username, user.getEmail());

    }

    private String usernameFromEmail(String email) {
        if (email.contains("@")) {
            return email.split("@")[0];
        } else {
            return email;
        }
    }

    private void writeNewUser(String userId, String name, String email) {
        ListGroups ls = new ListGroups();
        User user = new User(email, name, null, 0, ls);
/*        User user2 = new User("k", null, name, null, 0);
        User user3 = new User("j", null, name, null, 0);
        final UserList userList = new UserList(user);
        userList.setUsers(user2);
        userList.setUsers(user3);*/

/*        db.collection("users").document("ListUsers").get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                DocumentSnapshot result = task.getResult();
                if (task.isSuccessful()){
                    a = (HashMap<String,Object>) result.get("users");
                }else{
                    a = null;
                }

            }
        });
        if (a != null){
            users =  (HashMap<String, Object>) a.get("users");
            users.put(user.getEmail(),user);
            a.put("users",users);
        }else{
            users = new HashMap<>();
            a = new HashMap<>();
            users.put(user.getEmail(),user);
            a.put("users",users);
        }*/




        db.collection("users").document(user.getEmail()).set(user);

        


    }

/*    private void numeroUsuariosGrupo(){
        DatabaseReference dinosaursRef = FirebaseDatabase.getInstance().getReference("users");
        dinosaursRef.orderByKey().addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                User value = dataSnapshot.getValue(User.class);
                String email = value.getEmail();
                String password = value.getPassword();
            }

            @Override
            public void onChildChanged(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

            }

            @Override
            public void onChildRemoved(@NonNull DataSnapshot dataSnapshot) {

            }

            @Override
            public void onChildMoved(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }*/

    @Override
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

    }
}
