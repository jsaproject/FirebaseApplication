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

import com.example.juan_.practicafirebase.models.Group;
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
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.firestore.SetOptions;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

public class LoginActivity extends AppCompatActivity implements  View.OnClickListener{


    private Button buttonRegister;
    private EditText editTextEmail;
    private EditText editTextPassword;
    private Button   buttonLogin;


    private FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();
    private DatabaseReference firebase;
    private FirebaseFirestore db = FirebaseFirestore.getInstance();
    private ProfileActivity profileActivity;
    private ArrayList<String> listItems;
    private String email;
    private UserList userList1;
    private User user;



    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        if(firebaseAuth.getCurrentUser() != null){
            finish();
            db.collection("users").get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                @Override
                public void onComplete(@NonNull Task<QuerySnapshot> task) {
                    Iterator<QueryDocumentSnapshot> iterator = task.getResult().iterator();
                    Boolean encontrado = false;
                    QueryDocumentSnapshot next = null;
                    while(!encontrado && iterator.hasNext()){
                        next = iterator.next();
                        encontrado =next.getId().equalsIgnoreCase(firebaseAuth.getCurrentUser().getEmail());

                    }
                    if (encontrado){
                        finish();
                        user = next.toObject(User.class);
                        Intent intent = new Intent(LoginActivity.this, ProfileActivity.class);
                        Bundle bundle = new Bundle();
                        bundle.putSerializable("User", user);
                        intent.putExtras(bundle);
                        startActivity(intent);

                    }
                }
            });
        }
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);



        buttonRegister = (Button)findViewById(R.id.phone_register_button);
        buttonLogin = (Button)findViewById(R.id.phone_login_button);
        editTextEmail = (EditText)findViewById(R.id.login_email);
        editTextPassword = (EditText)findViewById(R.id.login_password);



        buttonRegister.setOnClickListener(this);
        buttonLogin.setOnClickListener(this);


    }




    private void loginUser() {
        final String email = editTextEmail.getText().toString().trim();
        String password  = editTextPassword.getText().toString().trim();

        firebaseAuth.signInWithEmailAndPassword(email,password).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()){
                    db.collection("users").get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                        @Override
                        public void onComplete(@NonNull Task<QuerySnapshot> task) {
                            Iterator<QueryDocumentSnapshot> iterator = task.getResult().iterator();
                            Boolean encontrado = false;
                            QueryDocumentSnapshot next = null;
                            while(!encontrado && iterator.hasNext()){
                                next = iterator.next();
                                encontrado =next.getId().equalsIgnoreCase(firebaseAuth.getCurrentUser().getEmail());

                            }
                            if (encontrado){
                                finish();
                                user = next.toObject(User.class);
                                Intent intent = new Intent(LoginActivity.this, ProfileActivity.class);
                                Bundle bundle = new Bundle();
                                bundle.putSerializable("User", user);
                                intent.putExtras(bundle);
                                startActivity(intent);
                            }
                        }
                    });
                }else{
                    Toast.makeText(LoginActivity.this,"User or password incorrect",Toast.LENGTH_LONG).show();
                }
            }
        });

    }


    @Override
    public void onClick(View view) {
       if (view == buttonRegister){
            finish();
            startActivity(new Intent(LoginActivity.this,RegisterActivity.class));
        }
        if (view == buttonLogin){
            loginUser();
        }
    }
}
