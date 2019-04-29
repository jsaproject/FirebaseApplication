package com.example.juan_.practicafirebase;

import android.content.Intent;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.example.juan_.practicafirebase.models.User;
import com.firebase.ui.storage.images.FirebaseImageLoader;
import com.google.android.gms.tasks.Continuation;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.Set;

import de.hdodenhof.circleimageview.CircleImageView;


public class SettingsActivity extends AppCompatActivity{

    private Button UpdateAccountSettings;
    private EditText userName, userStatus;
    private ImageView userProfileImage;
    private User user;
    private FirebaseFirestore db;
    private Uri filePath;
    private String s;
    private String uri;
    int PICK_IMAGE_REQUEST=1;
    ArrayList<String> mediaUriList = new ArrayList<>();

    private FirebaseStorage storage;
    private StorageReference storageReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        db = FirebaseFirestore.getInstance();

        storage = FirebaseStorage.getInstance();
        storageReference = storage.getReference();
        final Intent intent = this.getIntent();
        Bundle extras = intent.getExtras();
        user = (User) extras.getSerializable("User");
        InitializeFields();
        userName.setText(user.getUsername());
        userStatus.setText(user.getStatus());
        if (!user.getUriphoto().isEmpty()){
            Glide.with(getApplicationContext() )
                    .load(user.getUriphoto())
                    .into(userProfileImage);
           // Picasso.get().load(user.getUriphoto()).into(userProfileImage);
        }
        UpdateAccountSettings.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                updateUser();
            }
        });

        userProfileImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                chooseImage();
            }
        });

    }

    private void InitializeFields(){
        UpdateAccountSettings = (Button) findViewById(R.id.update_settings_button);
        userName = (EditText) findViewById(R.id.set_user_name);
        userStatus = (EditText) findViewById(R.id.set_profile_status);
        userProfileImage = (ImageView) findViewById(R.id.set_profile_image);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(resultCode==RESULT_OK){
            if(requestCode==PICK_IMAGE_REQUEST){
                if(data.getClipData()==null){
                    mediaUriList.add(data.getData().toString());
                    filePath = data.getData();
                    s = data.getData().toString();
                    Glide.with(getApplicationContext())
                            .load(s)
                            .into(userProfileImage);
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

    private void updateUser() {
        user.setStatus(userStatus.getText().toString());
        user.setUsername(userName.getText().toString());

        if (filePath!=null){
            final StorageReference riversRef = storageReference.child("images/"+ filePath.getLastPathSegment());

            UploadTask uploadTask = riversRef.putFile(filePath);

            Task<Uri> urlTask = uploadTask.continueWithTask(new Continuation<UploadTask.TaskSnapshot, Task<Uri>>() {
                @Override
                public Task<Uri> then(@NonNull Task<UploadTask.TaskSnapshot> task) throws Exception {
                    if (!task.isSuccessful()) {
                        throw task.getException();
                    }

                    // Continue with the task to get the download URL
                    return riversRef.getDownloadUrl();
                }
            }).addOnCompleteListener(new OnCompleteListener<Uri>() {
                @Override
                public void onComplete(@NonNull Task<Uri> task) {
                    if (task.isSuccessful()) {
                        Uri downloadUri = task.getResult();

                        user.setUriphoto(downloadUri.toString());
                        db.collection("users").document(user.getEmail()).set(user);
                    } else {
                        // Handle failures
                        // ...
                    }
                }
            });

            //user.setUriphoto(riversRef.toString());
        }



        finish();
        Intent intent = new Intent(SettingsActivity.this, ProfileActivity.class);
        Bundle bundle = new Bundle();
        bundle.putSerializable("User", user);
        intent.putExtras(bundle);
        startActivity(intent);
    }
}
