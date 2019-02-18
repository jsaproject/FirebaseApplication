package com.example.juan_.practicafirebase;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import com.example.juan_.practicafirebase.models.User;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;


public class ProfileActivity extends AppCompatActivity implements View.OnClickListener {

    private TextView textViewUserEmail;
    private Button buttonLogout;
    private ListView ListaUsuarios;
    private FirebaseAuth firebaseAuth;
    private FirebaseFirestore db;





    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


        firebaseAuth = FirebaseAuth.getInstance();

        textViewUserEmail =(TextView) findViewById(R.id.textViewUserEmail);
        buttonLogout = (Button) findViewById(R.id.buttonLogout);
        ListaUsuarios = (ListView) findViewById(R.id.ListaUsuarios);

        db = FirebaseFirestore.getInstance();



        numeroUsuariosGrupo();





        //textViewUserEmail.setText(firebaseAuth.getCurrentUser().getEmail());
        buttonLogout.setOnClickListener(this);
    }


    @Override
    public void onClick(View view) {
        if (view == buttonLogout){
            logout();
        }
    }

    private void logout() {
        finish();
        firebaseAuth.signOut();
        startActivity(new Intent(getApplicationContext(), LoginActivity.class));
    }


    private void numeroUsuariosGrupo() {



        /*DatabaseReference dinosaursRef = FirebaseDatabase.getInstance().getReference("users");
        dinosaursRef.orderByKey().addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                User value = dataSnapshot.getValue(User.class);
                String email = value.getEmail();
                String password = value.getPassword();
                listItems.add(email);
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
        });*/



        db.collection("users").get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        List<String> listItems = new ArrayList<>();
                        ArrayAdapter<String> arrayAdapter;

                        if (task.isSuccessful()) {

                            for (QueryDocumentSnapshot document : task.getResult()) {
                                   User user = document.toObject(User.class);
                                   listItems.add(user.getEmail());
                            }
                        }
//                        if (task.isSuccessful()){
//
//                            DocumentSnapshot documentSnapshot = task.getResult();
//                            Map<String, Object> data = documentSnapshot.getData();
//                            Set<Map.Entry<String, Object>> entries = data.entrySet();
//                            Iterator<Map.Entry<String, Object>> iterator = entries.iterator();
//
//                            while(iterator.hasNext()){
//                                Map.Entry<String, Object> next = iterator.next();
//                                HashMap<String, Object> a = (HashMap) next.getValue();
//                                listItems.add(a.getEmail());
//                            }


                            textViewUserEmail.setText("prueba");


                         arrayAdapter = new ArrayAdapter<String>
                                    (getApplicationContext(), android.R.layout.simple_list_item_1, listItems);


                            ListaUsuarios.setAdapter(arrayAdapter);
                        }
                    });




    }
}
