package com.example.facetime;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.analytics.FirebaseAnalytics;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;

public class signupactivity extends AppCompatActivity {
    FirebaseAuth auth;
    EditText emaibox, passwordbox, namebox;
    Button loginbutton, creatbutton;
    FirebaseFirestore database;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signupactivity);

        database = FirebaseFirestore.getInstance();
        auth = FirebaseAuth.getInstance();


        emaibox = findViewById(R.id.emailbox);
        namebox = findViewById(R.id.namebox);
        passwordbox = findViewById(R.id.passwordbox);

        loginbutton = findViewById(R.id.loginbutton);
        creatbutton = findViewById(R.id.creatbutton);

        creatbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String email, pass, name;
                email = emaibox.getText().toString();
                pass = passwordbox.getText().toString();
                name = namebox.getText().toString();
                User user   = new User();
                user.setEmail(email);
                user.setPass(pass);
                user.setName(name);
                auth.createUserWithEmailAndPassword(email,pass).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(task.isSuccessful()){
                            database.collection("Users")
                                    .document().set(user).addOnSuccessListener(new OnSuccessListener<Void>() {
                                @Override
                                public void onSuccess(Void aVoid) {
                                   startActivity(new Intent(signupactivity.this, loginactivity.class));
                                }
                            });
                            Toast.makeText(signupactivity.this, "Account is created", Toast.LENGTH_SHORT).show();

                        }else {
                            Toast.makeText(signupactivity.this, task.getException().getLocalizedMessage(), Toast.LENGTH_SHORT).show();
                        }
                    }
                });
             }
        });
        loginbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(signupactivity.this, loginactivity.class ));
            }
        });
    }
}