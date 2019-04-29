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
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Nullable;

public class ChatActivityContacts extends AppCompatActivity implements  View.OnClickListener{

    private TextView nameGroup;
    private RecyclerView rvMensajes;
    private User user;
    private FirebaseFirestore db;
    private FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();
    private String nombre_grupo_string;
    private ListView listamessages;
    private Button sendMessage;
    private EditText editText;
    private String fecha;
    private FloatingActionButton addPhoto;
    private Uri filePath;
    private String sfoto;
    private AdapterMensajes adapter;
    int PICK_IMAGE_REQUEST=1;
    ArrayList<String> mediaUriList = new ArrayList<>();
    private ImageButton btnEnviarFoto;
    private FirebaseStorage storage;
    private StorageReference storageReference;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);
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
        sendMessage = (Button) findViewById(R.id.btnEnviar);
        editText = (EditText) findViewById(R.id.txtMensaje);
        btnEnviarFoto = (ImageButton) findViewById(R.id.btnEnviarFoto);
        addPhoto = (FloatingActionButton) findViewById(R.id.addPhoto);
        nombre_grupo_string= getIntent().getStringExtra("Grupo");
        String nombre_grupo= getIntent().getStringExtra("Nombre");
        nameGroup.setText("Nombre del grupo es: " + nombre_grupo);

        Intent intent = this.getIntent();
        Bundle extras = intent.getExtras();
        user = (User) extras.getSerializable("User");

        initializeMessages();

        btnEnviarFoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                chooseImage();
                mandarFoto();
            }
        });

        sendMessage.setOnClickListener(this);




    }

    private void initializeMessages() {

        db.collection("Conversaciones").document(nombre_grupo_string).addSnapshotListener(new EventListener<DocumentSnapshot>() {
            @Override
            public void onEvent(@Nullable DocumentSnapshot documentSnapshot, @Nullable FirebaseFirestoreException e) {
                ArrayAdapter<String> arrayAdapter;
                int size;
                List<Map<String, String>> data = new ArrayList<Map<String, String>>();
                HashMap<String, Object> hash_mensajes = (HashMap<String, Object>) documentSnapshot.get("listaMensajes");
                HashMap<Integer, Object> lista_mensajes = (HashMap<Integer, Object>) hash_mensajes.get("messageList");
                ArrayList<Message> dataset = new ArrayList<>();
                if (lista_mensajes != null){
                    size = lista_mensajes.size();
                }else{
                    size = -1;
                }

                int i = 0;
                adapter = new AdapterMensajes(getApplicationContext());
                while(i<size){
                    String s = Integer.toString(i);
                    HashMap<String,String> o= (HashMap<String,String>)lista_mensajes.get(s);
                    Map<String, String> datum = new HashMap<String, String>(2);
                    String mensaje = o.get("mensaje");
                    String autor = o.get("autor");
                    String fecha = o.get("fecha");
                    String foto = o.get("foto");
                    String fotoautor = o.get("fotoPerfil");
                    //HashMap<String,String> nombre = (HashMap<String,String>) o.get("autor");
                    //String nombre1 = nombre.get("nombre");
                    Message message = new Message(mensaje, autor, fecha, foto, fotoautor);

                    i++;
                    adapter.addMensaje(message);
                }

                rvMensajes.setAdapter(adapter);
                //listamessages.setAdapter(adapterMensajes);
/*                SimpleAdapter adapter = new SimpleAdapter(getApplicationContext(), data,
                        android.R.layout.simple_list_item_2,
                        new String[] {"First Line", "Second Line"},
                        new int[] {android.R.id.text1, android.R.id.text2});

                adapter.notifyDataSetChanged();
                listamessages.setAdapter(adapter);*/

            }
        });

                //listamessages.setAdapter(adapterMensajes);
/*                SimpleAdapter adapter = new SimpleAdapter(getApplicationContext(), data,
                        android.R.layout.simple_list_item_2,
                        new String[] {"First Line", "Second Line"},
                        new int[] {android.R.id.text1, android.R.id.text2});

                adapter.notifyDataSetChanged();
                listamessages.setAdapter(adapter);*/




    }



    @Override
    public void onClick(View view) {
        if (view == sendMessage){
            mandarMensaje();
            editText.setText("");
        }else if(view==addPhoto){
            chooseImage();
            mandarFoto();
        }

    }

    private void mandarMensaje() {
        String s = editText.getText().toString();
        if (!s.equalsIgnoreCase("")){
            String pattern = "MM/dd/yyyy HH:mm:ss";


            DateFormat df = new SimpleDateFormat(pattern);


            Date today = Calendar.getInstance().getTime();

            String todayAsString = df.format(today);
            final Message message = new Message(s, user.getNombre(), todayAsString, "2", user.getUriphoto());
            db.collection("Conversaciones").document(nombre_grupo_string).get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
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



                    db.collection("Conversaciones").document(nombre_grupo_string).update(data);
                }
            });
        }


    }

    private void mandarFoto() {

            String pattern = "MM/dd/yyyy HH:mm:ss";


            DateFormat df = new SimpleDateFormat(pattern);


            Date today = Calendar.getInstance().getTime();

            String todayAsString = df.format(today);
            final Message message = new Message(sfoto, user.getNombre(), todayAsString, "1", user.getUriphoto());
            db.collection("Conversaciones").document(nombre_grupo_string).get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
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



                    db.collection("Conversaciones").document(nombre_grupo_string).update(data);
                }
            });



    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @android.support.annotation.Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(resultCode==RESULT_OK){
            if(requestCode==PICK_IMAGE_REQUEST){
                if(data.getClipData()==null){
                    mediaUriList.add(data.getData().toString());
                    filePath = data.getData();
                    sfoto = data.getData().toString();
                    mandarFoto();
                }else{
                    for(int i = 0; i<data.getClipData().getItemCount();i++){
                        mediaUriList.add(data.getClipData().getItemAt(i).getUri().toString());
                    }
                }

            }
        }
    }

    private void chooseImage() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.putExtra(Intent.EXTRA_ALLOW_MULTIPLE, false);
        intent.setAction(intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent,"Select picture"), PICK_IMAGE_REQUEST);
    }

}
