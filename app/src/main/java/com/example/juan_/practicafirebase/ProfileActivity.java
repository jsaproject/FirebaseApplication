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
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.juan_.practicafirebase.models.Group;
import com.example.juan_.practicafirebase.models.ListGroups;
import com.example.juan_.practicafirebase.models.User;
import com.example.juan_.practicafirebase.models.UserList;
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
    private EditText groupName;
    private FirebaseFirestore db;
    private User user;
    private Boolean groupExists;
    private Dialog a;
    private Boolean decision;
    private ListView listAvailable;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


        firebaseAuth = FirebaseAuth.getInstance();

        textViewUserEmail =(TextView) findViewById(R.id.textViewUserEmail);
//        buttonLogout = (Button) findViewById(R.id.buttonLogout);
        ListaUsuarios = (ListView) findViewById(R.id.ListaUsuarios);
        groupName = (EditText) findViewById(R.id.groupname);
        listAvailable = (ListView) findViewById(R.id.listviewavailable);
        db = FirebaseFirestore.getInstance();

        initialiceUser();

        numeroGruposUsuario();
        numeroGruposUsuario2();





        //textViewUserEmail.setText(firebaseAuth.getCurrentUser().getEmail());
/*        buttonLogout.setOnClickListener(this);*/
    }


    @Override
    public void onClick(View view) {

    }

    private void logout() {
        finish();
        firebaseAuth.signOut();
        firebaseAuth = null;
        db = null;


        startActivity(new Intent(getApplicationContext(), LoginActivity.class));
    }


    private void numeroGruposUsuario() {

        final String email = firebaseAuth.getCurrentUser().getEmail();
        db.collection("Listagrupos").get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                final List<String> nombregrupos = new ArrayList<>();

                ArrayAdapter<String> arrayAdapter;
                HashMap<String,Object> a = new HashMap<>();
                if(task.isSuccessful()){
                    for (QueryDocumentSnapshot document : task.getResult()){
                        String id = document.getId();
                        Map<String,Object> map = (HashMap<String,Object>)document.getData().get("usuarios");
                        for (Map.Entry<String, Object> entry : map.entrySet()) {
                            if (entry.getKey().equals("users")) {
                                a= (HashMap<String,Object>)entry.getValue();
                            }
                        }
                        Set<String> strings = a.keySet();
                        Iterator<String> iterator = strings.iterator();
                        boolean not_encontrado = false;
                        while(iterator.hasNext() && !not_encontrado){
                            String next = iterator.next();
                            if(next.equalsIgnoreCase(email)){
                                not_encontrado = true;
                            }
                        }
                        if(not_encontrado){
                            nombregrupos.add(document.getId());
                        }


                    }

                }


                arrayAdapter = new ArrayAdapter<String>
                        (getApplicationContext(), android.R.layout.simple_list_item_1, nombregrupos);
                arrayAdapter.notifyDataSetChanged();
                ListaUsuarios.setAdapter(arrayAdapter);
                ListaUsuarios.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        String s = nombregrupos.get(position);

                    }
                });

            }
        });

    }


   private void numeroGruposUsuario2() {

        final String email = firebaseAuth.getCurrentUser().getEmail();
        db.collection("Listagrupos").get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                List<String> nombregrupos = new ArrayList<>();

                ArrayAdapter<String> arrayAdapter2;
                HashMap<String,Object> a = new HashMap<>();
                if(task.isSuccessful()){
                    for (QueryDocumentSnapshot document : task.getResult()){
                        String id = document.getId();
                        Map<String,Object> map = (HashMap<String,Object>)document.getData().get("usuarios");
                        for (Map.Entry<String, Object> entry : map.entrySet()) {
                            if (entry.getKey().equals("users")) {
                                a= (HashMap<String,Object>)entry.getValue();
                            }
                        }
                        Set<String> strings = a.keySet();
                        Iterator<String> iterator = strings.iterator();
                        boolean not_encontrado = false;
                        while(iterator.hasNext() && !not_encontrado){
                            String next = iterator.next();
                            if(next.equalsIgnoreCase(email)){
                                not_encontrado = true;
                            }
                        }
                        if(!not_encontrado){
                            nombregrupos.add(document.getId());
                        }


                    }

                }


                arrayAdapter2 = new ArrayAdapter<String>
                        (getApplicationContext(), android.R.layout.simple_list_item_1, nombregrupos);
                arrayAdapter2.notifyDataSetChanged();
                listAvailable.setAdapter(arrayAdapter2);
            }
        });

    }

    private void initialiceUser() {
        final String email = firebaseAuth.getCurrentUser().getEmail();
        db.collection("users").get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if(task.isSuccessful()){
                    for (QueryDocumentSnapshot document : task.getResult()){
                        String id = document.getId();
                        if (id.equalsIgnoreCase(email)){
                            user = document.toObject(User.class);
                        }
                    }
                }
                textViewUserEmail.setText("Bienvenido a la sesion " + user.getNombre());
            }
        });



    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu){
        getMenuInflater().inflate(R.menu.main_profile, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item){
        switch (item.getItemId()){
            case R.id.action_addGroup:
                a = onCreateDialog();
                a.show();
                return true;
            case R.id.action_logout:
                logout();
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
                        dialog.dismiss();
                        addgroup();
                    }
                })
                .setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.dismiss();
                        decision = false;
                    }
                })

                .setTitle("Introduzca un nuevo grupo");




        return builder.create();
    }

    private void grupoExistente(final String nombreGrupo){
        groupExists = false;
        db.collection("groups").get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if (task.isSuccessful()){
                    for (QueryDocumentSnapshot document : task.getResult()) {
                        String id = document.getId();
                        if(id == nombreGrupo){
                            groupExists = true;
                        }
                    }
                }
            }

        });

    }

    private boolean addgroup() {

        groupName= a.findViewById(R.id.groupname);
        String nombreGrupo = groupName.getText().toString();
        grupoExistente(nombreGrupo);
        if (groupExists){
            return false;
        }else {
            UserList userList = new UserList();
            userList.addUser(user.getEmail(),user);
            Group group = new Group(nombreGrupo, userList);
/*            ListGroups groups = user.getGroups();
            groups.addGroup(nombreGrupo, group);*/
            db.collection("Listagrupos").document(nombreGrupo).set(group);
            numeroGruposUsuario();
        }
        return true;
    }
}
