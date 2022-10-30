package com.wazimatariq.i190439;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.squareup.picasso.Picasso;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.Calendar;

import de.hdodenhof.circleimageview.CircleImageView;

public class credentials extends AppCompatActivity {
    CircleImageView dp;
    MainActivity ma;
    EditText firstname,lastname,gender,bio;
    Button enter,back14;
    Bitmap bitmap;
    byte[] arrbyte;
    FirebaseDatabase rootNode;
    DatabaseReference reference;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_credentials);



            dp=findViewById(R.id.dp);
            firstname=findViewById(R.id.firstname);
            lastname=findViewById(R.id.lastname);
            gender=findViewById(R.id.gender);
            bio=findViewById(R.id.bio);
            enter=findViewById(R.id.enter);
            back14=findViewById(R.id.back14);

            back14.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    startActivity(new Intent(getApplicationContext(),signup.class));
                }
            });


            dp.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent i= new Intent();
                    i.setType("image/*");
                    i.setAction(Intent.ACTION_GET_CONTENT);
                    startActivityForResult(
                            Intent.createChooser(i,"Choose your Dp"),
                            100
                    );
                }
            });

            enter.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    rootNode=FirebaseDatabase.getInstance();
                    reference = rootNode.getReference("users");

                        //getting values
                    String fname=firstname.getText().toString();
                    String lname=lastname.getText().toString();
                    String gen=gender.getText().toString();
                    String bi=bio.getText().toString();
                    User user=new User(fname,lname,gen,bi);

                    reference=reference.push();
                    reference.getKey();
                    reference.setValue(user);   //is a dynamically created key
                    finish();

                    startActivity(new Intent(getApplicationContext(),playlists1.class));






                }
            });

    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode==100 & resultCode==RESULT_OK){
            Uri image=data.getData();
            Calendar c= Calendar.getInstance();
            //dp.setImageURI(image);
            FirebaseStorage storage=FirebaseStorage.getInstance();
            StorageReference ref=storage.getReference().child("dp/mydp"+c.getTimeInMillis()+".jpeg");
            ref.putFile(image)
                    .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                            Task<Uri> task=taskSnapshot.getStorage().getDownloadUrl();
                            task.addOnSuccessListener(new OnSuccessListener<Uri>() {
                                @Override
                                public void onSuccess(Uri uri) {

                                    Picasso.get().load(uri.toString()).into(dp);
                                    startActivity(new Intent(getApplicationContext(),playlists1.class));
                                    finish();
                                }
                            });
                            Toast.makeText(
                                    credentials.this,
                                    "Success",Toast.LENGTH_LONG
                            ).show();
                        }
                    })
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Toast.makeText(
                                    credentials.this,
                                    "Failed",Toast.LENGTH_LONG
                            ).show();
                        }
                    })
            ;
        }
    }

}