package com.example.juan_.practicafirebase;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.example.juan_.practicafirebase.models.Group;
import com.example.juan_.practicafirebase.models.Message;
import com.example.juan_.practicafirebase.models.MessageList;
import com.example.juan_.practicafirebase.models.User;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QueryDocumentSnapshot;

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

public class ChatActivity extends AppCompatActivity implements  View.OnClickListener{

    private TextView nameGroup;
    private User user;
    private FirebaseFirestore db;
    private FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();
    private String nombre_grupo_string;
    private ListView listamessages;
    private Button sendMessage;
    private EditText editText;
    private String fecha;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);
        Toolbar toolbar = findViewById(R.id.main_page_toolbar);
        setSupportActionBar(toolbar);
        db = FirebaseFirestore.getInstance();

        nameGroup = (TextView) findViewById(R.id.nameGroup);
        listamessages = (ListView)findViewById(R.id.listMessage);
        sendMessage = (Button) findViewById(R.id.sendbutton);
        editText = (EditText) findViewById(R.id.editText);

        nombre_grupo_string= getIntent().getStringExtra("Grupo");
        nameGroup.setText(nombre_grupo_string);

        Intent intent = this.getIntent();
        Bundle extras = intent.getExtras();
        user = (User) extras.getSerializable("User");

        initializeMessages();


        sendMessage.setOnClickListener(this);



    }

    private void initializeMessages() {

        db.collection("Listagrupos").document(nombre_grupo_string).addSnapshotListener(new EventListener<DocumentSnapshot>() {
            @Override
            public void onEvent(@Nullable DocumentSnapshot documentSnapshot, @Nullable FirebaseFirestoreException e) {
                ArrayAdapter<String> arrayAdapter;
                int size;
                List<Map<String, String>> data = new ArrayList<Map<String, String>>();
                HashMap<String, Object> hash_usuarios = (HashMap<String, Object>) documentSnapshot.get("usuarios");
                HashMap<String, Object> lista_usuarios = (HashMap<String, Object>) hash_usuarios.get("users");
                HashMap<String, Object> hash_mensajes = (HashMap<String, Object>) documentSnapshot.get("listaMensajes");
                HashMap<Integer, Object> lista_mensajes = (HashMap<Integer, Object>) hash_mensajes.get("messageList");
                ArrayList<Message> dataset = new ArrayList<>();
                if (lista_mensajes != null){
                     size = lista_mensajes.size();
                }else{
                    size = -1;
                }

                int i = 0;
                while(i<size){
                    String s = Integer.toString(i);
                    HashMap<String,String> o= (HashMap<String,String>)lista_mensajes.get(s);
                    Map<String, String> datum = new HashMap<String, String>(2);
                    String mensaje = o.get("mensaje");
                     String autor = o.get("autor");
                    String fecha = o.get("fecha");
                    //HashMap<String,String> nombre = (HashMap<String,String>) o.get("autor");
                    //String nombre1 = nombre.get("nombre");
                    Message message = new Message(mensaje, autor, fecha, false);
                    dataset.add(message);
                    datum.put("First Line",autor);

                    datum.put("Second Line",mensaje);
                    datum.put("Third Line", "Fecha");
                    data.add(datum);
                    i++;
                }

                CustomAdapter customAdapter = new CustomAdapter(dataset, getApplicationContext());
                listamessages.setAdapter(customAdapter);
/*                SimpleAdapter adapter = new SimpleAdapter(getApplicationContext(), data,
                        android.R.layout.simple_list_item_2,
                        new String[] {"First Line", "Second Line"},
                        new int[] {android.R.id.text1, android.R.id.text2});

                adapter.notifyDataSetChanged();
                listamessages.setAdapter(adapter);*/

            }
        });

    }



    @Override
    public void onClick(View view) {
        if (view == sendMessage){
            mandarMensaje();
            editText.setText("");
        }

    }

    private void mandarMensaje() {
        String s = editText.getText().toString();
        if (!s.equalsIgnoreCase("")){
            String pattern = "MM/dd/yyyy HH:mm:ss";


            DateFormat df = new SimpleDateFormat(pattern);


            Date today = Calendar.getInstance().getTime();

            String todayAsString = df.format(today);
            final Message message = new Message(s, user.getNombre(), todayAsString, false);
            db.collection("Listagrupos").document(nombre_grupo_string).get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                @Override
                public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                    Map<String, Object> data = task.getResult().getData();

                    HashMap<String, Object> hash_mensajes = (HashMap<String, Object>) data.get("listaMensajes");
                    HashMap<String, Object> lista_mensajes = (HashMap<String, Object>) hash_mensajes.get("messageList");
                    int size = 0;
                    if(lista_mensajes!= null){
                        size = lista_mensajes.size();
                    }
                    String s1 = Integer.toString(size);
                    lista_mensajes.put(s1,message);



                    db.collection("Listagrupos").document(nombre_grupo_string).update(data);
                }
            });
        }


    }

}
