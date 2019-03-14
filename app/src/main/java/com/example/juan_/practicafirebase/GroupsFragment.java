package com.example.juan_.practicafirebase;


import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.example.juan_.practicafirebase.models.User;
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
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.annotation.Nullable;


/**
 * A simple {@link Fragment} subclass.
 */
public class GroupsFragment extends Fragment {

    private Context applicationContext;
    private FirebaseFirestore db;
    private ListView listAvailable;
    private final String email;
    private FirebaseAuth firebaseAuth;
    private User user;

    public GroupsFragment() {
        ProfileActivity activity = (ProfileActivity) getActivity();
        applicationContext = null;
        db = FirebaseFirestore.getInstance();
        gruposUsuarioDisponibles();

        firebaseAuth= FirebaseAuth.getInstance();
        listAvailable = (ListView) getView().findViewById(R.id.listviewavailable);
        email = firebaseAuth.getCurrentUser().getEmail();
        user = null;


    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_groups, container, false);





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
                        (applicationContext, android.R.layout.simple_list_item_1, nombregrupos);
                listAvailable.setAdapter(arrayAdapter2);
                listAvailable.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, final int position, long id) {

                        Toast.makeText(applicationContext, nombregrupos.get(position),Toast.LENGTH_SHORT).show();
                        Dialog addinGroup = dialogAddInGroup(nombregrupos.get(position));
                        addinGroup.show();
                    }
                });
            }
        });
    }

    private Dialog dialogAddInGroup(final String nombre_grupo) {
        AlertDialog.Builder builder = new AlertDialog.Builder(applicationContext);


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
