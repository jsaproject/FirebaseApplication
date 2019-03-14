package com.example.juan_.practicafirebase;

import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
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
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.annotation.Nullable;


public class ProfileActivity extends AppCompatActivity implements View.OnClickListener {

    private TextView textViewUserEmail;
    private ListView ListaUsuarios;
    private FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();
    private EditText groupName;
    private FirebaseFirestore db;
    public User user;
    private Dialog a;
    private ListView listAvailable;
    private final String email = firebaseAuth.getCurrentUser().getEmail();
    private final String nombre = usernameFromEmail(email);
    private ViewPager myViewPager;
    private TabLayout myTabLayout;
    private TabsAcessorAdapter myTabsAcessorAdapter;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        Toolbar toolbar = findViewById(R.id.main_page_toolbar);
        setSupportActionBar(toolbar);

        Intent intent = this.getIntent();
        Bundle extras = intent.getExtras();
        user = (User) extras.getSerializable("User");
        firebaseAuth = FirebaseAuth.getInstance();

        textViewUserEmail = (TextView) findViewById(R.id.textViewUserEmail);
//        buttonLogout = (Button) findViewById(R.id.buttonLogout);
        ListaUsuarios = (ListView) findViewById(R.id.ListaUsuarios);
        groupName = (EditText) findViewById(R.id.groupname);
        listAvailable = (ListView) findViewById(R.id.listviewavailable);
        db = FirebaseFirestore.getInstance();

        /*myViewPager = (ViewPager) findViewById(R.id.main_tabs_pager);
        myTabsAcessorAdapter = new TabsAcessorAdapter(getSupportFragmentManager());
        myViewPager.setAdapter(myTabsAcessorAdapter);

        myTabLayout = (TabLayout) findViewById(R.id.main_tabs);
        myTabLayout.setupWithViewPager(myViewPager);*/

        initialiceUser();

        gruposUsuarioRegistrado();
        gruposUsuarioDisponibles();




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

    private String usernameFromEmail(String email) {
        if (email.contains("@")) {
            return email.split("@")[0];
        } else {
            return email;
        }
    }

    private void initialiceUser() {
        textViewUserEmail.setText("Bienvenido a la sesion " + user.getNombre());
    }


    private void gruposUsuarioRegistrado() {
        db.collection("Listagrupos").addSnapshotListener(new EventListener<QuerySnapshot>() {
            @Override
            public void onEvent(@Nullable QuerySnapshot queryDocumentSnapshots, @Nullable FirebaseFirestoreException e) {


                ArrayAdapter<String> arrayAdapter;
                Iterator<QueryDocumentSnapshot> iterator = queryDocumentSnapshots.iterator();
                final List<String> nombregrupos = new ArrayList<>();

                while (iterator.hasNext()) {
                    QueryDocumentSnapshot next = iterator.next();
                   HashMap<String, Object> lista_usuarios = (HashMap<String, Object>) next.getData().get("usuarios");
                    HashMap<String, Object> usuariosimple = (HashMap<String, Object>) lista_usuarios.get("users");

                    if (usuariosimple.containsKey(email)) {
                        nombregrupos.add(next.getId());

                    }
                }
                arrayAdapter = new ArrayAdapter<String>
                        (getApplicationContext(), android.R.layout.simple_list_item_1, nombregrupos);
                arrayAdapter.notifyDataSetChanged();
                ListaUsuarios.setAdapter(arrayAdapter);
                ListaUsuarios.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        Intent intent = new Intent(ProfileActivity.this, ChatActivity.class);
                        String s = nombregrupos.get(position);
                        intent.putExtra("Grupo", s);
                        Bundle bundle = new Bundle();
                        bundle.putSerializable("User", user);
                        intent.putExtras(bundle);
                        startActivity(intent);
                    }
                });
            }
        });
    }


    private void gruposUsuarioDisponibles() {
        db.collection("Listagrupos").addSnapshotListener(new EventListener<QuerySnapshot>() {
            @Override
            public void onEvent(@Nullable QuerySnapshot queryDocumentSnapshots, @Nullable FirebaseFirestoreException e) {
                final List<String> nombregrupos = new ArrayList<>();
                ArrayAdapter<String> arrayAdapter2;
                Iterator<QueryDocumentSnapshot> iterator = queryDocumentSnapshots.iterator();

                while (iterator.hasNext()) {
                    QueryDocumentSnapshot next = iterator.next();
                    HashMap<String, Object> lista_usuarios = (HashMap<String, Object>) next.getData().get("usuarios");
                    HashMap<String, Object> usuariosimple = (HashMap<String, Object>) lista_usuarios.get("users");
                    boolean b = usuariosimple.containsKey(email);
                    if (!usuariosimple.containsKey(email) && usuariosimple.size() < 7) {
                        nombregrupos.add(next.getId());
                    }
                }

                arrayAdapter2 = new ArrayAdapter<String>
                        (getApplicationContext(), android.R.layout.simple_list_item_1, nombregrupos);
                listAvailable.setAdapter(arrayAdapter2);
                listAvailable.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, final int position, long id) {

                        Toast.makeText(ProfileActivity.this, nombregrupos.get(position),Toast.LENGTH_SHORT).show();
                        Dialog addinGroup = dialogAddInGroup(nombregrupos.get(position));
                        addinGroup.show();
                    }
                });
            }
        });
    }

    private Dialog dialogAddInGroup(final String nombre_grupo) {
        AlertDialog.Builder builder = new AlertDialog.Builder(ProfileActivity.this);


        builder.setMessage("Desea ser incluido en el grupo " + nombre_grupo)
                .setTitle("Incluir grupo")
                .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        registerGroupAvailable(nombre_grupo);
                        dialog.dismiss();
                    }
                })
                .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });


        return builder.create();
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_profile, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
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
        AlertDialog.Builder builder = new AlertDialog.Builder(ProfileActivity.this);
        LayoutInflater inflater = this.getLayoutInflater();
        builder.setView(inflater.inflate(R.layout.dialog_createnewgroup, null))
                .setPositiveButton("Añadir", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.dismiss();
                        groupName = a.findViewById(R.id.groupname);
                        final String nombreGrupo = groupName.getText().toString();
                        Boolean encontrado = false;

                            db.collection("Listagrupos").get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                                @Override
                                public void onComplete(@NonNull Task<QuerySnapshot> task) {
                                    Boolean encontrado = false;
                                    Iterator<QueryDocumentSnapshot> iterator = task.getResult().iterator();
                                    while(iterator.hasNext() && !encontrado){
                                        String id1 = iterator.next().getId();
                                        if (id1.equalsIgnoreCase(nombreGrupo)){
                                            encontrado=true;
                                        }
                                    }
                                    if (encontrado) {
                                        Toast.makeText(ProfileActivity.this, "Este grupo ya habia sido creado", Toast.LENGTH_LONG).show();
                                    } else {
                                        addgroup(nombreGrupo);
                                        gruposUsuarioDisponibles();
                                    }

                                }
                            });


                    }
                })
                .setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.dismiss();
                    }
                })

                .setTitle("Introduzca un nuevo grupo");


        return builder.create();
    }




    private void addgroup(String nombreGrupo) {
        UserList userList = new UserList();
        userList.addUser(user);
        Group group = new Group(nombreGrupo, userList);
        db.collection("Listagrupos").document(nombreGrupo).set(group);

    }

    private void registerGroupAvailable(final String nombre_grupo){
        db.collection("Listagrupos").document(nombre_grupo).get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                Map<String, Object> data = task.getResult().getData();
                HashMap<String, Object> lista_usuarios = (HashMap<String, Object>) data.get("usuarios");
                HashMap<String, Object> usuariosimple = (HashMap<String, Object>) lista_usuarios.get("users");
                usuariosimple.put(email,user);
                db.collection("Listagrupos").document(nombre_grupo).update(data);
                //db.collection("Listagrupos").document(nombre_grupo).set(data);
            }
        });
    }
}
