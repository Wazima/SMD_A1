package com.wazimatariq.i190439;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserProfileChangeRequest;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class signup extends AppCompatActivity {

    EditText su_name,su_email,su_pass;
    ImageView su_male,su_female,su_other;
    CheckBox su_checkBox;
    Button signup1,signin1;
    FirebaseAuth mAuth;
    DatabaseReference databaseReference;
    String gender;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

        databaseReference=FirebaseDatabase.getInstance().getReference("users");

        su_name=findViewById(R.id.su_name);
        su_email=findViewById(R.id.su_email);
        su_pass=findViewById(R.id.su_pass);
        su_male=findViewById(R.id.su_male);
        su_female=findViewById(R.id.su_female);
        su_other=findViewById(R.id.su_other);
        su_checkBox=(CheckBox) findViewById(R.id.su_checkBox);
        signup1=findViewById(R.id.signup1);
        signin1=findViewById(R.id.signin1);

        mAuth= FirebaseAuth.getInstance();



        signin1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(),signin.class));
                finish();
            }
        });

        signup1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String name=su_name.getText().toString();
                String email=su_email.getText().toString();
                String pass=su_pass.getText().toString();


                if(name.isEmpty()){
                    su_name.setError("Enter Name");
                    su_name.requestFocus();
                    return;
                }if(email.isEmpty()){
                    su_email.setError("Enter email");
                    su_email.requestFocus();
                    return;
                }if(pass.isEmpty()){
                    su_pass.setError("Enter password");
                    su_pass.requestFocus();
                    return;
                }
                if(!Patterns.EMAIL_ADDRESS.matcher(email).matches()){
                    su_email.setError("Please provide valid email");
                    su_email.requestFocus();
                    return;
                }
                if(pass.length()<6){
                    su_pass.setError("Min password length is 6 characters");
                    su_pass.requestFocus();
                    return;

                }
                if(!su_checkBox.isChecked()){
                    su_checkBox.setError("Please Accept");
                    su_checkBox.requestFocus();
                    return;
                }





                mAuth.createUserWithEmailAndPassword(email,pass)
                        .addOnSuccessListener(new OnSuccessListener<AuthResult>() {
                            @Override
                            public void onSuccess(AuthResult authResult) {
                                Toast.makeText(signup.this,"Success",Toast.LENGTH_LONG).show();
                                /*UserProfileChangeRequest userProfileChangeRequest=new UserProfileChangeRequest.Builder().setDisplayName(name).build();
                                FirebaseUser firebaseUser=FirebaseAuth.getInstance().getCurrentUser();
                                firebaseUser.updateProfile(userProfileChangeRequest);
                                User user=new User(FirebaseAuth.getInstance().getUid(),name,email,pass);
                                databaseReference.child(FirebaseAuth.getInstance().getUid()).setValue(user);*/

                                startActivity(new Intent(getApplicationContext(),credentials.class));

                                finish();
                            }
                        }).addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                Toast.makeText(signup.this,"Failed",Toast.LENGTH_LONG).show();
                            }
                        });


            }




        });



    }
}