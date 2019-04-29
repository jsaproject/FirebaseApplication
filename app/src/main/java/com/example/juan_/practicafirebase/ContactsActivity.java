package com.example.juan_.practicafirebase;

import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.juan_.practicafirebase.models.Group;
import com.example.juan_.practicafirebase.models.MessageList;
import com.example.juan_.practicafirebase.models.User;
import com.example.juan_.practicafirebase.models.UserList;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.annotation.Nullable;


public class ContactsActivity extends AppCompatActivity implements View.OnClickListener {

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
    private Set<String> contactos;
    private TextView textViewContactos;
    private TextView textViewContactos2;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contacts);
        Toolbar toolbar = findViewById(R.id.main_page_toolbar);
        setSupportActionBar(toolbar);
        toolbar.setTitle("Contactos");
        toolbar.setSubtitle("Contactos");
        Intent intent = this.getIntent();
        Bundle extras = intent.getExtras();
        user = (User) extras.getSerializable("User");
        firebaseAuth = FirebaseAuth.getInstance();

        textViewUserEmail = (TextView) findViewById(R.id.textViewUserEmail);
        ListaUsuarios = (ListView) findViewById(R.id.ListaUsuarios);
        groupName = (EditText) findViewById(R.id.groupname);
        listAvailable = (ListView) findViewById(R.id.listviewavailable);
        db = FirebaseFirestore.getInstance();
        contactos = new HashSet<>();
        textViewContactos = (TextView) findViewById(R.id.textViewContactos);
        textViewContactos2 = (TextView) findViewById(R.id.textViewContactos2);

        textViewContactos.setText("Contactos Incluidos");
        textViewContactos2.setText("Contactos Disponibles");

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
        db.collection("users").addSnapshotListener(new EventListener<QuerySnapshot>(){
            @Override
            public void onEvent(@Nullable QuerySnapshot queryDocumentSnapshots, @Nullable FirebaseFirestoreException e) {
                Iterator<QueryDocumentSnapshot> iterator = queryDocumentSnapshots.iterator();
                Boolean encontrado = false;
                QueryDocumentSnapshot next = null;
                ArrayAdapter<String> arrayAdapter;
                ArrayList<String> lista = new ArrayList<>();
                while (!encontrado && iterator.hasNext()) {
                    next = iterator.next();
                    encontrado = next.getId().equalsIgnoreCase(firebaseAuth.getCurrentUser().getEmail());

                }
                if (encontrado) {
                    final List<String> nombregrupos = new ArrayList<>();
                    lista = (ArrayList<String>) next.getData().get("contactos");

                }

                Iterator<String> iterator1 = lista.iterator();
                while (iterator1.hasNext()) {
                    String next1 = iterator1.next();
                    if (!user.getUsername().equalsIgnoreCase(next1)) {
                        contactos.add(next1);
                    }

                }

                arrayAdapter = new ArrayAdapter<String>
                        (getApplicationContext(), android.R.layout.simple_list_item_1, lista);
                arrayAdapter.notifyDataSetChanged();
                ListaUsuarios.setAdapter(arrayAdapter);
                final ArrayList<String> finalLista = lista;
                ListaUsuarios.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        final int positioncopy = position;
                        db.collection("Conversaciones").get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                            @Override
                            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                                Boolean encontrado = false;
                                Iterator<QueryDocumentSnapshot> iterator = task.getResult().iterator();
                                while (iterator.hasNext() && !encontrado) {
                                    String id1 = iterator.next().getId();
                                    if (id1.equalsIgnoreCase(finalLista.get(positioncopy) + user.getUsername()) || id1.equalsIgnoreCase(user.getUsername() + finalLista.get(positioncopy))) {
                                        encontrado = true;
                                    }
                                }
                                if (encontrado) {
                                    final Intent intent = new Intent(ContactsActivity.this, ChatActivityContacts.class);

                                    db.collection("Conversaciones").get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                                        @Override
                                        public void onComplete(@NonNull Task<QuerySnapshot> task) {
                                            Iterator<QueryDocumentSnapshot> iterator = task.getResult().iterator();
                                            boolean find = false;
                                            String nombreConversacion = "";
                                            while (iterator.hasNext() && !find){
                                                QueryDocumentSnapshot next1 = iterator.next();
                                                String id1 = next1.getId();
                                                if (id1.equalsIgnoreCase(finalLista.get(positioncopy) + user.getUsername())){
                                                    nombreConversacion = finalLista.get(positioncopy) + user.getUsername();
                                                    find = true;
                                                }else if(id1.equalsIgnoreCase(user.getUsername()+finalLista.get(positioncopy))){
                                                    nombreConversacion = user.getUsername()+finalLista.get(positioncopy);
                                                    find = true;
                                                }
                                            }
                                            intent.putExtra("Grupo", nombreConversacion);
                                            intent.putExtra("Nombre", finalLista.get(positioncopy));
                                            Bundle bundle = new Bundle();
                                            bundle.putSerializable("User", user);
                                            intent.putExtras(bundle);
                                            startActivity(intent);
                                        }
                                    });

                                } else {
                                    addContact(finalLista.get(positioncopy) + user.getUsername());
                                    gruposUsuarioDisponibles();
                                }

                            }
                        });
                    }
                });
            }
        });
    }


    private void gruposUsuarioDisponibles() {
        db.collection("users").addSnapshotListener(new EventListener<QuerySnapshot>() {
            @Override
            public void onEvent(@Nullable QuerySnapshot queryDocumentSnapshots, @Nullable FirebaseFirestoreException e) {

                Iterator<QueryDocumentSnapshot> iterator = queryDocumentSnapshots.iterator();

                ArrayList<String> contactosUsuario = user.getContactos();
                Iterator<String> iterator1 = contactosUsuario.iterator();
                while (iterator1.hasNext()) {
                    String next1 = iterator1.next();
                    contactos.add(next1);
                }
                final ArrayList<String> nombregrupos = new ArrayList<>();
                while (iterator.hasNext()) {
                    QueryDocumentSnapshot next = iterator.next();
                    String usuario = (String) next.getData().get("username");

                    if (!contactos.contains(usuario) && !usuario.equalsIgnoreCase(usernameFromEmail(email))) {
                        nombregrupos.add(usuario);
                    }
                }

                ArrayAdapter<String> arrayAdapter2 = new ArrayAdapter<String>
                        (getApplicationContext(), android.R.layout.simple_list_item_1, nombregrupos);
                listAvailable.setAdapter(arrayAdapter2);
                listAvailable.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, final int position, long id) {

                        Toast.makeText(ContactsActivity.this, nombregrupos.get(position), Toast.LENGTH_SHORT).show();
                        Dialog addinGroup = dialogAddInGroup(nombregrupos.get(position));
                        addinGroup.show();

                    }
                });
            }
        });
    }

    private void addContact(String nombreGrupo) {
        UserList userList = new UserList();
        userList.addUser(user);
        MessageList messageList = new MessageList();
        Group group = new Group(nombreGrupo, userList, messageList, 0);
        db.collection("Conversaciones").document(nombreGrupo).set(group);

    }

    private Dialog dialogAddInGroup(final String nombre_grupo) {
        AlertDialog.Builder builder = new AlertDialog.Builder(ContactsActivity.this);


        builder.setMessage("Desea incluir en tus contactos " + nombre_grupo)
                .setTitle("Incluir contacto")
                .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        registerGroupAvailable(nombre_grupo);
                        user.anadirContacto(nombre_grupo);
                        gruposUsuarioRegistrado();
                        gruposUsuarioDisponibles();
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
                return true;
            case R.id.action_logout:
                logout();
                return true;
            case R.id.app_contacts:
                return true;
            case R.id.action_settings:
                finish();
                Intent intent = new Intent(ContactsActivity.this, SettingsActivity.class);
                Bundle bundle = new Bundle();
                bundle.putSerializable("User", user);
                intent.putExtras(bundle);
                startActivity(intent);
                return true;
            case R.id.app_profile:
                finish();
                Intent intent2 = new Intent(ContactsActivity.this, ProfileActivity.class);
                Bundle bundle2 = new Bundle();
                bundle2.putSerializable("User", user);
                intent2.putExtras(bundle2);
                startActivity(intent2);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }


    private void registerGroupAvailable(final String nombre_grupo) {
        db.collection("users").document(email).get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                Map<String, Object> data = task.getResult().getData();
                ArrayList<String> lista_usuarios = (ArrayList<String>) data.get("contactos");
                lista_usuarios.add(nombre_grupo);
                db.collection("users").document(email).update(data);
                //db.collection("Listagrupos").document(nombre_grupo).set(data);
            }
        });
        db.collection("users").get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                Iterator<QueryDocumentSnapshot> iterator = task.getResult().iterator();
                Boolean encontrado = false;
                QueryDocumentSnapshot next = null;
                ArrayAdapter<String> arrayAdapter;
                ArrayList<String> lista = new ArrayList<>();
                while (!encontrado && iterator.hasNext()) {
                    next = iterator.next();
                    encontrado = usernameFromEmail(next.getId()).equalsIgnoreCase(usernameFromEmail(nombre_grupo));

                }
                if (encontrado) {
                    Map<String, Object> data = next.getData();
                    ArrayList<String> lista_usuarios = (ArrayList<String>) data.get("contactos");
                    lista_usuarios.add(usernameFromEmail(email));
                    db.collection("users").document(next.getId()).update(data);
                }
            }
        });

    }


}
