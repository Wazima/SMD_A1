package com.wazimatariq.i190439;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class signin extends AppCompatActivity {
    Button signup2,signin2,fyp;
    EditText si_mail,si_pass;
    FirebaseAuth fAuth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signin);

        fAuth=FirebaseAuth.getInstance();

        signup2=findViewById(R.id.signup2);
        signin2=findViewById(R.id.signin2);
        fyp=findViewById(R.id.fyp);
        si_mail=findViewById(R.id.si_mail);
        si_pass=findViewById(R.id.si_pass);

        signup2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(),signup.class));
                finish();
            }
        });

        signin2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String mail=si_mail.getText().toString();
                String pass=si_pass.getText().toString();
                //extract data
                if(mail.isEmpty()){
                    si_mail.setError("Email is missing");
                    si_mail.requestFocus();
                    return;
                }if(pass.isEmpty()){
                    si_pass.setError("Password is missing");
                    si_pass.requestFocus();
                    return;
                }

                Toast.makeText(signin.this,"Success",Toast.LENGTH_LONG).show();

                fAuth.signInWithEmailAndPassword(mail,pass)
                        .addOnSuccessListener(new OnSuccessListener<AuthResult>() {
                            @Override
                            public void onSuccess(AuthResult authResult) {
                                Toast.makeText(signin.this,"Success",Toast.LENGTH_LONG).show();
                                startActivity(new Intent(getApplicationContext(),playlists1.class));
                                finish();
                            }
                        })
                        .addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                Toast.makeText(signin.this,"Failed",Toast.LENGTH_LONG).show();
                            }
                        });
            }
        });

    }

    //if already logged in the app
    /*
    @Override
    protected void onStart() {
        super.onStart();
        if(FirebaseAuth.getInstance().getCurrentUser()!=null){
            startActivity(new Intent(getApplicationContext(),playlists1.class));
            finish();
        }
    }*/
}