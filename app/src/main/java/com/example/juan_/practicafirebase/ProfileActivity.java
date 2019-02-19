package com.example.juan_.practicafirebase;

import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import com.example.juan_.practicafirebase.models.Group;
import com.example.juan_.practicafirebase.models.ListGroups;
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
import java.util.Collection;
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



        numeroGruposUsuario();





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


    private void numeroGruposUsuario() {

        String email = firebaseAuth.getCurrentUser().getEmail();
        db.collection("users").document(email).get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                List<String> nombregrupos = new ArrayList<>();
                User user = null;
                ArrayAdapter<String> arrayAdapter;
                if(task.isSuccessful()){
                    DocumentSnapshot result = task.getResult();
                    user = result.toObject(User.class);
                }
                if (user!=null) {
                    ListGroups groups = user.getGroups();
                    Collection<Group> groups1 = groups.allValue();
                    Iterator<Group> iterator = groups1.iterator();
                    while(iterator.hasNext()){
                        Group next = iterator.next();
                        nombregrupos.add(next.getNombreGrupo());
                    }
                }
                textViewUserEmail.setText("prueba");
                arrayAdapter = new ArrayAdapter<String>
                        (getApplicationContext(), android.R.layout.simple_list_item_1, nombregrupos);
                ListaUsuarios.setAdapter(arrayAdapter);
            }
        });

    }

    private User returnUser() {
        User a = new User();
        String email = firebaseAuth.getCurrentUser().getEmail();
        db.collection("users").document(email).get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                User u = new User();
                    if (task.isSuccessful()){
                        DocumentSnapshot result = task.getResult();
                        u = result.toObject(User.class);
                    }
            }
        });
        return a;
    }
/*    private void numeroUsuariosGrupo() {

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


                            textViewUserEmail.setText("prueba");


                         arrayAdapter = new ArrayAdapter<String>
                                    (getApplicationContext(), android.R.layout.simple_list_item_1, listItems);


                            ListaUsuarios.setAdapter(arrayAdapter);
                        }
                    });

    }*/

    @Override
    public boolean onCreateOptionsMenu(Menu menu){
        getMenuInflater().inflate(R.menu.main_profile, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item){
        switch (item.getItemId()){
            case R.id.action_settings:
                onCreateDialog().show();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }


    public Dialog onCreateDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        // Get the layout inflater
        LayoutInflater inflater = this.getLayoutInflater();

        // Inflate and set the layout for the dialog
        // Pass null as the parent view because its going in the dialog layout
        builder.setView(inflater.inflate(R.layout.dialog_createnewgroup, null))
                // Add action buttons
                .setPositiveButton("Añadir", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int id) {
                        TextView namegroup =(TextView) findViewById(R.id.username);
                        String nombreGrupo = namegroup.getText().toString();
                        User u = returnUser();
                        //addgroup(nombreGrupo,);
                        dialog.dismiss();
                    }
                })
                .setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.dismiss();
                    }
                });
        return builder.create();
    }

    private void addgroup() {
        
        
    }
}
