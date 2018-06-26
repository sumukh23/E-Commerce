package com.example.hp.e_commerce;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;

/**
 * Created by hp on 05-10-2017.
 */

public class Orderplaced extends Activity {

    Cartdb cart[];
    int cartCount,j,index;
    User user;
    private DatabaseReference myRef , cartRef , proRef;
    private FirebaseDatabase userDatabase , cartDatabase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.orderplaced);

        Button hbutton = (Button)findViewById(R.id.Orderplacedhomepage);

        Intent i = getIntent();

        cart = (Cartdb[]) i.getSerializableExtra("cart");
        cartCount = i.getIntExtra("cartcount", 0);
        user = (User) i.getSerializableExtra("user");

        userDatabase = FirebaseDatabase.getInstance();
        myRef = userDatabase.getReference("users");

        myRef.addListenerForSingleValueEvent(new ValueEventListener() {
            int i=0;
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    User user = snapshot.getValue(User.class);
                    String key = snapshot.getKey();
                    if(user.email.equals(cart[0].UserName)){
                        index = Integer.parseInt(user.orders.index);
                        for(int j=0;j<cartCount;j++) {
                            HashMap<String, Object> update = new HashMap<String, Object>();
                            update.put("product" + String.valueOf(index), cart[j].Name);
                            update.put("productimage" + String.valueOf(index) , cart[j].imageURL);
                            index++;
                            update.put("index" , String.valueOf(index));
                            myRef.child(key).child("orders").updateChildren(update);
                            i++;
                        }
                    }
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        hbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                cartDatabase = FirebaseDatabase.getInstance();
                cartRef = cartDatabase.getReference("cart");

                proRef = FirebaseDatabase.getInstance().getReference();
                proRef = proRef.child("products");

                proRef.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                            Products pro = snapshot.getValue(Products.class);
                            for(j=0;j<cartCount;j++) {
                                if (cart[j].Name.equals(pro.Name)) {
                                    String key = snapshot.getKey();
                                    HashMap<String, Object> update = new HashMap<String, Object>();
                                    update.put("Bquantity", "0");
                                    FirebaseDatabase.getInstance().getReference().child("products").child(key).updateChildren(update);
                                }
                            }
                        }
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }
                });

                cartRef.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                            Cartdb cart1 = snapshot.getValue(Cartdb.class);
                            for(j=0;j<cartCount;j++) {
                                if (cart[j].UserName.equals(cart1.UserName)) {
                                    String key = snapshot.getKey();
                                    FirebaseDatabase.getInstance().getReference().child("cart").child(key).removeValue();
                                }
                            }
                        }
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }
                });

                Intent i = new Intent(Orderplaced.this , Homepage.class);
                i.putExtra("user", user);
                i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(i);
            }
        });

    }
}
