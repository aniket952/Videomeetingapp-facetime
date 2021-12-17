package com.example.facetime;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.api.Backend;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class loginactivity extends AppCompatActivity {

    EditText emaibox, passwordbox;
    Button loginbutton, creatbutton;
    FirebaseAuth auth;
    ProgressDialog dialog;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_loginactivity);
        dialog  = new ProgressDialog(this);
        dialog.setMessage("Please wait.......");

        emaibox = findViewById(R.id.emailbox);
        passwordbox = findViewById(R.id.passwordbox);
        auth = FirebaseAuth.getInstance();

        loginbutton = findViewById(R.id.loginbutton);
        creatbutton = findViewById(R.id.creatbutton);

        loginbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.show();
                String email, password;
                email = emaibox.getText().toString();
                password = passwordbox.getText().toString();
                auth.signInWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        dialog.dismiss();
                        if(task.isSuccessful()){
                            startActivity(new Intent(loginactivity.this, dashboardactivity.class));
                            Toast.makeText(loginactivity.this, "logged in", Toast.LENGTH_SHORT).show();

                        }else {
                            Toast.makeText(loginactivity.this, task.getException().getLocalizedMessage(), Toast.LENGTH_SHORT).show();
                        }
                    }

                });
            }
        });
        creatbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(loginactivity.this, signupactivity.class));
            }
        });
    }
}