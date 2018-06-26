package com.example.hp.e_commerce;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

//package info.androidhive.firebase;

/**
 * Created by hp on 04-09-2017.
 */

public class SignUp extends Activity {
    private FirebaseAuth mAuth;
    int flag;
    private DatabaseReference myRef,proRef;
    private FirebaseDatabase userDatabase,productDatabase;
    private EditText inputname , inputaddress , inputnumber , inputdob , inputemail , inputpassword , inputcpassword;
    private Button btnSave;
    private String userId,productId;
    private String str = new String("@");
    String email,name,address,number,dob,password,cpassword;
    private static final String tag = "MyActivity";
    Orders orders = new Orders();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.signup);
        mAuth = FirebaseAuth.getInstance();

        inputname = (EditText)findViewById(R.id.Signupname);
        inputaddress = (EditText)findViewById(R.id.signupaddress);
        inputnumber = (EditText)findViewById(R.id.Signupnumber);
        inputdob = (EditText)findViewById(R.id.Signupdateofbirth);
        inputemail = (EditText)findViewById(R.id.Signupemail);
        inputpassword = (EditText)findViewById(R.id.Signuppassword);
        inputcpassword = (EditText)findViewById(R.id.Signupcpassword);
        btnSave = (Button)findViewById(R.id.Signupbutton);

        userDatabase = FirebaseDatabase.getInstance();
        myRef = userDatabase.getReference("users");

        productDatabase = FirebaseDatabase.getInstance();
        proRef = productDatabase.getReference("products");

        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                name = inputname.getText().toString();
                address = inputaddress.getText().toString();
                number  = inputnumber.getText().toString();
                dob = inputdob.getText().toString();
                email = inputemail.getText().toString();
                password = inputpassword.getText().toString();
                cpassword = inputcpassword.getText().toString();


                // Check for already existed userId
                myRef.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                            User us = snapshot.getValue(User.class);
                            if(us.email.equals(email)){
                                Toast.makeText(SignUp.this , "This email id already exists" , Toast.LENGTH_SHORT).show();
                                flag=1;
                                break;
                            }
                        }
                        if(flag == 0) {
                            orders.index = "0";
                            if (TextUtils.isEmpty(userId)) {
                                createUser(name, address, number, dob, email, password, cpassword, orders);
                            }
                        }

                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }
                });

               /* if (TextUtils.isEmpty(productId)) {
                    createProduct("Nike LITEFORCE III MID Sneakers" , "2839 " ,"General\n" +
                            "Model Name\tLITEFORCE III MID\n" +
                            "Ideal For\tMen\n" +
                            "Occasion\tCasual\n" +
                            "Outer Material\tCanvas", "Casual");
                }*/
            }
        });
    }

    public void createUser(String name1 , String address1 , String number1 , String dob1 , String email1 , String password1 , String password2, Orders orders){
        // TODO
        // In real apps this userId should be fetched
        // by implementing firebase auth
        flag=0;
        if (TextUtils.isEmpty(userId)) {
            userId = myRef.push().getKey();
        }
        if (TextUtils.isEmpty(name1)) {
            Toast.makeText(SignUp.this , "Name is empty" , Toast.LENGTH_SHORT).show();
            flag=1;
        }
        if (TextUtils.isEmpty(address1)) {
            Toast.makeText(SignUp.this , "Address is empty" , Toast.LENGTH_SHORT).show();
            flag=1;
        }
        if (TextUtils.isEmpty(number1)) {
            Toast.makeText(SignUp.this , "Number is empty" , Toast.LENGTH_SHORT).show();
            flag=1;
        }
        if (number1.length()>10){
            Toast.makeText(SignUp.this , "Invalid Number" , Toast.LENGTH_SHORT).show();
            flag=1;
        }
        if(number1.length()<10){
            Toast.makeText(SignUp.this , "Invalid Number" , Toast.LENGTH_SHORT).show();
            flag=1;
        }
        if (TextUtils.isEmpty(dob1)) {
            Toast.makeText(SignUp.this , "Date of Birth is empty" , Toast.LENGTH_SHORT).show();
            flag=1;
        }
        if (TextUtils.isEmpty(email1)) {
            Toast.makeText(SignUp.this , "Email is empty" , Toast.LENGTH_SHORT).show();
            flag=1;
        }
        if(!email1.contains(str)){
            Toast.makeText(SignUp.this , "Enter valid email address" , Toast.LENGTH_SHORT).show();
            flag=1;
        }
        if (TextUtils.isEmpty(password1)) {
            Toast.makeText(SignUp.this , "Password is empty" , Toast.LENGTH_SHORT).show();
            flag=1;
        }
        if (TextUtils.isEmpty(password2)) {
            Toast.makeText(SignUp.this , "Confirm Password is empty" , Toast.LENGTH_SHORT).show();
            flag=1;
        }
        if(!password1.equals(password2)) {
            Toast.makeText(SignUp.this , "Passwords don't match" , Toast.LENGTH_SHORT).show();
            flag=1;
        }

        if(flag == 0) {
            User user = new User(name1, address1, number1, dob1, email1, password1, orders );

            myRef.child(userId).setValue(user);

            addUserChangeListener();
            Toast.makeText(SignUp.this, "Signed Up successfully...!!", Toast.LENGTH_SHORT).show();
            Intent i = new Intent(SignUp.this, LoginActivity.class);
            startActivity(i);
        }
    }

    /*public void createProduct(String name , String price , String specs , String cat){

        if (TextUtils.isEmpty(productId)) {
            productId = proRef.push().getKey();
        }
        Products product = new Products(name , specs , price , cat);

        proRef.child(productId).setValue(product);
    }*/

    private void addUserChangeListener() {

    }
}

