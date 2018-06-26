package com.example.hp.e_commerce;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import java.util.HashMap;

import static com.example.hp.e_commerce.R.id.Buynow;

/**
 * Created by hp on 22-09-2017.
 */

public class Productinfo extends Activity {
    private FirebaseDatabase cartDatabse, userDatabase;
    private DatabaseReference cartRef, proRef, userRef;
    Products p;
    User user;
    String str ="1";
    Cartdb cart[] = new Cartdb[10];
    ImageView myview;
    int flag = 1,cartCount, flag1 = 0;
    private String cartId;
    public int flagg=0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.product_info);

        Intent i = getIntent();
        p = (Products) i.getSerializableExtra("ppp");
        user = (User) i.getSerializableExtra("user");


        TextView tvname = (TextView) findViewById(R.id.Productinfo_name);
        TextView tvprice = (TextView) findViewById(R.id.Productinfo_price);
        TextView tvdesc = (TextView) findViewById(R.id.Productinfo_description);
        Button bleft = (Button) findViewById(R.id.click_left);
        Button bright = (Button) findViewById(R.id.click_right);
        Button buynow = (Button) findViewById(Buynow);
        Button addtocart = (Button) findViewById(R.id.Addtocart);

        tvname.setText(p.Name);
        tvprice.setText(p.Price);
        tvdesc.setText(p.Specifications);

        //Below  code is for image switcher

        myview = (ImageView) findViewById(R.id.image_switcher);

        Picasso.with(Productinfo.this).load(p.imageURL).into(myview);

        userDatabase = FirebaseDatabase.getInstance();
        userRef = userDatabase.getReference("users");

        proRef = FirebaseDatabase.getInstance().getReference();
        proRef = proRef.child("products");

        //Adding data to Cart Database
        cartDatabse = FirebaseDatabase.getInstance();
        cartRef = cartDatabse.getReference("cart");

        bleft.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (flag == 0) {
                    Picasso.with(Productinfo.this).load(p.imageURL2).into(myview);
                    flag = 2;
                } else if (flag == 1) {
                    Picasso.with(Productinfo.this).load(p.imageURL1).into(myview);
                    flag = 0;
                } else if (flag == 2) {
                    Picasso.with(Productinfo.this).load(p.imageURL).into(myview);
                    flag = 1;
                }
            }
        });

        bright.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (flag == 0) {
                    Picasso.with(Productinfo.this).load(p.imageURL).into(myview);
                    flag = 1;
                } else if (flag == 1) {
                    Picasso.with(Productinfo.this).load(p.imageURL2).into(myview);
                    flag = 2;
                } else if (flag == 2) {
                    Picasso.with(Productinfo.this).load(p.imageURL1).into(myview);
                    flag = 0;
                }
            }
        });

        addtocart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (TextUtils.isEmpty(cartId)) {
                        proRef.addListenerForSingleValueEvent(new ValueEventListener() {
                            @Override
                            public void onDataChange(DataSnapshot dataSnapshot) {
                                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                                    Products products = snapshot.getValue(Products.class);
                                    String key = snapshot.getKey();
                                    if (p.Name.equals(products.Name)) {
                                        if (products.Bquantity.equals("0")) {
                                            addProductToCart(p.Name, "1", p.Price, p.imageURL, user.email);
                                            HashMap<String, Object> update = new HashMap<String, Object>();
                                            update.put("Bquantity", "1");
                                            FirebaseDatabase.getInstance().getReference().child("products").child(key).updateChildren(update);
                                            update.put("Quantity", "4");
                                            FirebaseDatabase.getInstance().getReference().child("products").child(key).updateChildren(update);
                                            cartId = null;
                                            Toast.makeText(Productinfo.this, "Added to cart successfully", Toast.LENGTH_SHORT).show();
                                            Intent ii = new Intent(Productinfo.this, Homepage.class);
                                            ii.putExtra("user" , user);
                                            startActivity(ii);
                                            break;
                                        }
                                        else{
                                            Toast.makeText(Productinfo.this, "Product already present in Cart", Toast.LENGTH_SHORT).show();
                                            break;
                                        }
                                    }
                                }
                            }

                            @Override
                            public void onCancelled(DatabaseError databaseError) {

                            }
                        });
                    }
            }
        });
        buynow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (TextUtils.isEmpty(cartId)) {
                    proRef.addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(DataSnapshot dataSnapshot) {
                            for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                                final Products pro1 = snapshot.getValue(Products.class);
                                final String key = snapshot.getKey();
                                if(pro1.Name.equals(p.Name)){
                                    cartRef.addListenerForSingleValueEvent(new ValueEventListener() {
                                        @Override
                                        public void onDataChange(DataSnapshot dataSnapshot) {
                                            for (DataSnapshot snapshot : dataSnapshot.getChildren()){
                                                Cartdb c = snapshot.getValue(Cartdb.class);
                                                if(c.Name.equals(pro1.Name)){
                                                    if(c.UserName.equals(user.email)){
                                                        Toast.makeText(Productinfo.this, "Product already present in Cart", Toast.LENGTH_SHORT).show();
                                                        flag1 = 1;
                                                    }
                                                    else{
                                                            addProductToCart(p.Name, "1", p.Price, p.imageURL, user.email);
                                                            String str1 = pro1.Bquantity;
                                                            int temp = Integer.parseInt(str1);
                                                            temp++;
                                                            int temp1 = 5 - temp;
                                                            str1 = String.valueOf(temp);
                                                            String str2 = String.valueOf(temp1);
                                                            HashMap<String, Object> update = new HashMap<String, Object>();
                                                            update.put("Bquantity", str1);
                                                            HashMap<String, Object> update1 = new HashMap<String, Object>();
                                                            update1.put("Quantity", str2);
                                                            FirebaseDatabase.getInstance().getReference().child("products").child(key).updateChildren(update1);
                                                            FirebaseDatabase.getInstance().getReference().child("products").child(key).updateChildren(update);
                                                            flag1 = 1;

                                                            cartRef.addListenerForSingleValueEvent(new ValueEventListener() {
                                                                @Override
                                                                public void onDataChange(DataSnapshot dataSnapshot1) {
                                                                    int i = 0;
                                                                    for (DataSnapshot snapshot1 : dataSnapshot1.getChildren()) {
                                                                        cart[i] = snapshot1.getValue(Cartdb.class);
                                                                        i++;
                                                                    }
                                                                    if(dataSnapshot1.getChildrenCount()>0) {
                                                                        cartCount = i;
                                                                        Intent ii = new Intent(Productinfo.this, Cart.class);
                                                                        ii.putExtra("cart", cart);
                                                                        ii.putExtra("user", user);
                                                                        ii.putExtra("cartcount", cartCount);
                                                                        ii.putExtra("string", "fromProductInfo");
                                                                        Toast.makeText(Productinfo.this, "B4 going to cart", Toast.LENGTH_SHORT).show();
                                                                        startActivity(ii);
                                                                    }
                                                                }

                                                                @Override
                                                                public void onCancelled(DatabaseError databaseError) {

                                                                }
                                                            });
                                                    }
                                                }
                                            }
                                            if(flag1 == 0){
                                                    addProductToCart(p.Name, "1", p.Price, p.imageURL, user.email);
                                                    String str1 = pro1.Bquantity;
                                                    int temp = Integer.parseInt(str1);
                                                    temp++;
                                                    str1 = String.valueOf(temp);
                                                    int temp1 = 5 - temp;
                                                    String str2 = String.valueOf(temp1);
                                                    HashMap<String, Object> update = new HashMap<String, Object>();
                                                    update.put("Bquantity", str1);
                                                    HashMap<String, Object> update1 = new HashMap<String, Object>();
                                                    update1.put("Quantity", str2);
                                                    FirebaseDatabase.getInstance().getReference().child("products").child(key).updateChildren(update);
                                                    FirebaseDatabase.getInstance().getReference().child("products").child(key).updateChildren(update1);

                                                    cartRef.addListenerForSingleValueEvent(new ValueEventListener() {
                                                        @Override
                                                        public void onDataChange(DataSnapshot dataSnapshot1) {
                                                            int i = 0;
                                                            for (DataSnapshot snapshot1 : dataSnapshot1.getChildren()) {
                                                                cart[i] = snapshot1.getValue(Cartdb.class);
                                                                i++;
                                                            }
                                                            if(dataSnapshot1.getChildrenCount()>0) {
                                                                cartCount = i;
                                                                Intent ii = new Intent(Productinfo.this, Cart.class);
                                                                ii.putExtra("cart", cart);
                                                                ii.putExtra("user", user);
                                                                ii.putExtra("cartcount", cartCount);
                                                                ii.putExtra("string", "fromProductInfo");
                                                                startActivity(ii);
                                                            }
                                                        }

                                                        @Override
                                                        public void onCancelled(DatabaseError databaseError) {

                                                        }
                                                    });
                                            }
                                        }

                                        @Override
                                        public void onCancelled(DatabaseError databaseError) {

                                        }
                                    });

                                }
                            }
                        }

                        @Override
                        public void onCancelled(DatabaseError databaseError) {

                        }
                    });
                }
            }
        });
    }
    public void addProductToCart(String Name, String Quantity, String Price, String imageURL, String UserName){
        if(TextUtils.isEmpty(cartId)){
            cartId = cartRef.push().getKey();
        }
        Cartdb cart = new Cartdb(Name, Quantity, Price, imageURL, UserName);
        cartRef.child(cartId).setValue(cart);
        addUserChangeListener();
    }

    private void addUserChangeListener() {
        p.Bquantity = "1";
    }
}
