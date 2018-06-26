package com.example.hp.e_commerce;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.io.Serializable;

public class LoginActivity extends AppCompatActivity {

    private DatabaseReference myRef;
    private FirebaseDatabase userDatabase;
    EditText username , password;
    Button loginbutton,signinbutton;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        username = (EditText) findViewById(R.id.Loginemail);
        password = (EditText) findViewById(R.id.Loginpassword);

        loginbutton = (Button) findViewById(R.id.Loginbutton);
        signinbutton = (Button) findViewById(R.id.Loginbutton2);


        myRef = FirebaseDatabase.getInstance().getReference();
        myRef = myRef.child("users");

        loginbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String email1 = username.getText().toString();
                final String pass1 = password.getText().toString();
                myRef.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                            User us = snapshot.getValue(User.class);
                            if (us.email.equals(email1)) {
                                if (us.password.equals(pass1)) {
                                    Intent j = new Intent(LoginActivity.this, Homepage.class);
                                    j.putExtra("user" , (Serializable) us);
                                    startActivity(j);
                                    break;
                                } else {
                                    Toast.makeText(LoginActivity.this, "Email and password doesn't match", Toast.LENGTH_SHORT).show();
                                }
                            }
                        }
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }
                });
            }
        });
    }

        public void onButtonClick(View v){
            if(v.getId() == R.id.Loginbutton2){
                Intent i = new Intent(LoginActivity.this , SignUp.class);
                startActivity(i);
            }
        }
    @Override
    public void onBackPressed() {
        Intent intent = new Intent(Intent.ACTION_MAIN);
        intent.addCategory(Intent.CATEGORY_HOME);
        startActivity(intent);
    }
}


