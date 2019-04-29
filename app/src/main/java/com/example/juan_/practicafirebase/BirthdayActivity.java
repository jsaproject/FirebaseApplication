package com.example.juan_.practicafirebase;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;

import com.example.juan_.practicafirebase.models.Message;
import com.example.juan_.practicafirebase.models.User;
import com.getbase.floatingactionbutton.FloatingActionButton;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.lang.reflect.Array;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.annotation.Nullable;

public class BirthdayActivity extends AppCompatActivity{

    private TextView nameGroup;
    private RecyclerView rvMensajes;
    private User user;
    private FirebaseFirestore db;
    private FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();
    private ListView listamessages;


    private AdapterMensajes adapter;


    private FirebaseStorage storage;
    private StorageReference storageReference;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_birthday);
        Toolbar toolbar = findViewById(R.id.main_page_toolbar);
        setSupportActionBar(toolbar);
        db = FirebaseFirestore.getInstance();

        storage = FirebaseStorage.getInstance();
        storageReference = storage.getReference();
        rvMensajes = (RecyclerView) findViewById(R.id.rvMensajes);


        LinearLayoutManager l = new LinearLayoutManager(this);
        rvMensajes.setLayoutManager(l);


        nameGroup = (TextView) findViewById(R.id.nombre);
        listamessages = (ListView)findViewById(R.id.listMessage);



        Intent intent = this.getIntent();
        Bundle extras = intent.getExtras();
        user = (User) extras.getSerializable("User");
        nameGroup.setText("Cumpleaños del usuario: " + user.getUsername());

        initializeMessages();






    }

    private void initializeMessages() {

        db.collection("users").document(user.getEmail()).addSnapshotListener(new EventListener<DocumentSnapshot>() {
            @Override
            public void onEvent(@Nullable DocumentSnapshot documentSnapshot, @Nullable FirebaseFirestoreException e) {
                ArrayAdapter<String> arrayAdapter;
                int size;
                List<Map<String, String>> data = new ArrayList<Map<String, String>>();
                ArrayList<String> cumpleaños_usuarios = (ArrayList<String>) documentSnapshot.get("contactos");

                ArrayList<Message> dataset = new ArrayList<>();
                Iterator<String> iterator = cumpleaños_usuarios.iterator();
                adapter = new AdapterMensajes(getApplicationContext());
                while(iterator.hasNext()){
                    final String usuario = iterator.next();
                    final ArrayList<String> usuarios = new ArrayList<>();
                    db.collection("users").addSnapshotListener(new EventListener<QuerySnapshot>() {
                        @Override
                        public void onEvent(@Nullable QuerySnapshot queryDocumentSnapshots, @Nullable FirebaseFirestoreException e) {
                            Iterator<QueryDocumentSnapshot> iterator = queryDocumentSnapshots.iterator();
                            while(iterator.hasNext()){
                                QueryDocumentSnapshot next = iterator.next();
                                String username = (String) next.getData().get("username");
                                if (username.equalsIgnoreCase(usuario)){
                                    usuarios.add(0, (String) next.getData().get("nombre"));
                                    usuarios.add(1, (String) next.getData().get("uriphoto"));
                                    usuarios.add(2, (String) next.getData().get("fechaNacimiento"));
                                    usuarios.add(3, (String) next.getData().get("status"));
                                    String mensaje = usuarios.get(3);
                                    String autor = "Cumpleaños de: " + usuarios.get(0);
                                    String fecha = usuarios.get(2);
                                    String foto = "2";
                                    String fotoautor = usuarios.get(1);
                                    //HashMap<String,String> nombre = (HashMap<String,String>) o.get("autor");
                                    //String nombre1 = nombre.get("nombre");
                                    Message message = new Message(mensaje, autor, fecha, foto, fotoautor);

                                    adapter.addMensaje(message);

                                }
                            }
                            rvMensajes.setAdapter(adapter);
                        }
                    });

                }


                //listamessages.setAdapter(adapterMensajes);
/*                SimpleAdapter adapter = new SimpleAdapter(getApplicationContext(), data,
                        android.R.layout.simple_list_item_2,
                        new String[] {"First Line", "Second Line"},
                        new int[] {android.R.id.text1, android.R.id.text2});

                adapter.notifyDataSetChanged();
                listamessages.setAdapter(adapter);*/

            }
        });

    }







}
