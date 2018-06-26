package com.example.hp.e_commerce;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import java.util.HashMap;

/**
 * Created by Rohan on 25/09/2017.
 */

public class Cart extends AppCompatActivity {
    private FirebaseDatabase cartDatabse;
    private DatabaseReference cartRef, proRef;
    Products p, p1[];
    Cartdb cart[];
    Cartdb tempcart[] = new Cartdb[10];
    Cartdb cart1 = new Cartdb();
    String str, fromActivity;
    String tempString[] = new String[2];
    String tempString1[] = new String[5];
    int initialcost[] = new int[24];
    int cartCount,filtercartCount,temp=1,totalprice = 0,flag1=0,rquantity;
    TextView textview_quantity;
    TextView carttotal;
    TextView product_total;
    ListView listView;
    Products pro;
    User user;
    int protemp;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.cart_layout);

        Intent ii = getIntent();

        user = (User) ii.getSerializableExtra("user");
        fromActivity = ii.getStringExtra("string");
        cart = (Cartdb[]) ii.getSerializableExtra("cart");
        cartCount = ii.getIntExtra("cartcount", 0);
        filtercartCount = 0;
        for(int j=0;j<cartCount;j++){
            if(user.email.equals(cart[j].UserName)){
                tempcart[filtercartCount] = cart[j];
                filtercartCount++;
            }
        }


        Button cartb = (Button)findViewById(R.id.CartPage1btn);

        listView = (ListView) findViewById(R.id.listView2);

        final CustomAdapterr customAdapter1 = new CustomAdapterr();
        listView.setAdapter(customAdapter1);


        carttotal = (TextView)findViewById(R.id.CartTotalPrice);

        cartb.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(Cart.this , Deliveryaddpage.class);
                i.putExtra("user" , user);
                i.putExtra("cart" , tempcart);
                i.putExtra("cartcount", filtercartCount);
                i.putExtra("total" , totalprice);
                startActivity(i);
            }
        });
    }

    class CustomAdapterr extends BaseAdapter{
        int quant , cindex=0;
        public int alltotal=0;
        @Override
        public int getCount() {
            return filtercartCount;
        }

        @Override
        public Object getItem(int i) {
            return i;
        }

        @Override
        public long getItemId(int i) {
            return 0;
        }

        private class ViewHolder {

            protected TextView itemName;

        }

        @Override
        public View getView(final int i, View view, ViewGroup parent) {
                view = getLayoutInflater().inflate(R.layout.custom_layout_cart, null);

                final TextView textview_name = (TextView) view.findViewById(R.id.ProductName);
                product_total = (TextView) view.findViewById(R.id.Producttotal);
                textview_quantity = (TextView) view.findViewById(R.id.ProductQuantityy);


                Button btn1 = (Button) view.findViewById(R.id.QuantitySubtract);
                Button btn2 = (Button) view.findViewById(R.id.Quantityadd);
                Button btn3 = (Button) view.findViewById(R.id.RemoveProduct);
                ImageView imageview = (ImageView) view.findViewById(R.id.imageView4);

                textview_name.setText(tempcart[i].Name);
                Picasso.with(Cart.this).load(tempcart[i].imageURL).into(imageview);

                quant = temp;
                totalprice = 0;

                cartRef = FirebaseDatabase.getInstance().getReference();
                cartRef = cartRef.child("cart");


                btn2.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        v = getViewByPosition(i, listView);
                        final TextView tv = (TextView) v.findViewById(R.id.ProductQuantityy);
                        final TextView tvprice = (TextView) v.findViewById(R.id.Producttotal);
                            proRef = FirebaseDatabase.getInstance().getReference();
                            proRef = proRef.child("products");

                            proRef.addListenerForSingleValueEvent(new ValueEventListener() {
                                @Override
                                public void onDataChange(DataSnapshot dataSnapshot) {
                                    for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                                        pro = snapshot.getValue(Products.class);
                                        String key = snapshot.getKey();
                                        if (tempcart[i].Name.equals(pro.Name)) {
                                            if(Integer.parseInt(pro.Bquantity) < 5 ){
                                                protemp = Integer.parseInt(pro.Bquantity);
                                                protemp++;
                                                HashMap<String, Object> update = new HashMap<String, Object>();
                                                update.put("Bquantity", String.valueOf(protemp));
                                                FirebaseDatabase.getInstance().getReference().child("products").child(key).updateChildren(update);
                                                cartRef.addListenerForSingleValueEvent(new ValueEventListener() {
                                                    @Override
                                                    public void onDataChange(DataSnapshot dataSnapshot) {
                                                        for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                                                            Cartdb c = snapshot.getValue(Cartdb.class);
                                                            if (c.Name.equals(tempcart[i].Name)) {
                                                                String key1 = snapshot.getKey();
                                                                HashMap<String, Object> update1 = new HashMap<String, Object>();
                                                                update1.put("Quantity", String.valueOf(protemp));
                                                                FirebaseDatabase.getInstance().getReference().child("cart").child(key1).updateChildren(update1);
                                                            }
                                                        }
                                                    }

                                                    @Override
                                                    public void onCancelled(DatabaseError databaseError) {

                                                    }
                                                });
                                                quant = protemp;
                                                rquantity = Integer.parseInt(pro.Quantity) - 1;
                                                update.put("Quantity", String.valueOf(rquantity));
                                                FirebaseDatabase.getInstance().getReference().child("products").child(key).updateChildren(update);
                                                totalprice = (initialcost[i]) * quant;
                                                alltotal = alltotal + (initialcost[i]);

                                                tv.setText(String.valueOf(protemp));
                                                tvprice.setText("₹" + String.valueOf(totalprice));
                                                carttotal.setText("₹" + String.valueOf(alltotal));
                                                flag1 = 1;

                                            }
                                            else{
                                                Toast.makeText(Cart.this , "Maximum quantity reached" , Toast.LENGTH_SHORT).show();
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

                btn1.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        v = getViewByPosition(i, listView);
                        final TextView tv = (TextView) v.findViewById(R.id.ProductQuantityy);
                        final TextView tvprice = (TextView) v.findViewById(R.id.Producttotal);
                            proRef = FirebaseDatabase.getInstance().getReference();
                            proRef = proRef.child("products");

                            proRef.addListenerForSingleValueEvent(new ValueEventListener() {
                                @Override
                                public void onDataChange(DataSnapshot dataSnapshot) {
                                    for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                                        Products pro = snapshot.getValue(Products.class);
                                        String key = snapshot.getKey();
                                        if (tempcart[i].Name.equals(pro.Name)) {
                                            if(Integer.parseInt(pro.Bquantity) >= 2) {
                                                protemp = Integer.parseInt(pro.Bquantity);
                                                protemp--;
                                                HashMap<String, Object> update = new HashMap<String, Object>();
                                                update.put("Bquantity", String.valueOf(protemp));
                                                FirebaseDatabase.getInstance().getReference().child("products").child(key).updateChildren(update);
                                                cartRef.addListenerForSingleValueEvent(new ValueEventListener() {
                                                    @Override
                                                    public void onDataChange(DataSnapshot dataSnapshot) {
                                                        for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                                                            Cartdb c = snapshot.getValue(Cartdb.class);
                                                            if (c.Name.equals(tempcart[i].Name)) {
                                                                String key1 = snapshot.getKey();
                                                                HashMap<String, Object> update1 = new HashMap<String, Object>();
                                                                update1.put("Quantity", String.valueOf(protemp));
                                                                FirebaseDatabase.getInstance().getReference().child("cart").child(key1).updateChildren(update1);
                                                            }
                                                        }
                                                    }

                                                    @Override
                                                    public void onCancelled(DatabaseError databaseError) {

                                                    }
                                                });
                                                quant = protemp;
                                                rquantity = Integer.parseInt(pro.Quantity) + 1;
                                                update.put("Quantity", String.valueOf(rquantity));
                                                FirebaseDatabase.getInstance().getReference().child("products").child(key).updateChildren(update);
                                                totalprice = (initialcost[i]) * quant;
                                                alltotal = alltotal - (initialcost[i]);

                                                tv.setText(String.valueOf(protemp));
                                                tvprice.setText("₹" + String.valueOf(totalprice));
                                                carttotal.setText("₹" + String.valueOf(alltotal));
                                                flag1 = 1;
                                            }
                                            else{
                                                Toast.makeText(Cart.this , "Cannot reduce quantity" , Toast.LENGTH_SHORT).show();
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

                btn3.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        cartDatabse = FirebaseDatabase.getInstance();
                        cartRef = cartDatabse.getReference("cart");

                        proRef = FirebaseDatabase.getInstance().getReference();
                        proRef = proRef.child("products");
                        //To update Bquantity of a product in Database
                        proRef.addListenerForSingleValueEvent(new ValueEventListener() {
                            int c = 0;

                            @Override
                            public void onDataChange(DataSnapshot dataSnapshot) {
                                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                                    Products pro = snapshot.getValue(Products.class);
                                    String key = snapshot.getKey();
                                    if (pro.Name.equals(tempcart[i].Name)) {
                                        HashMap<String, Object> update = new HashMap<String, Object>();
                                        update.put("Bquantity", "0");
                                        rquantity = Integer.parseInt(pro.Quantity) + 1;
                                        update.put("Quantity", String.valueOf(rquantity));
                                        FirebaseDatabase.getInstance().getReference().child("products").child(key).updateChildren(update);
                                    }
                                }
                            }

                            @Override
                            public void onCancelled(DatabaseError databaseError) {

                            }
                        });
                        //TO refresh cart page on deleting an item from cart
                        cartRef.addListenerForSingleValueEvent(new ValueEventListener() {
                            int count;

                            @Override
                            public void onDataChange(DataSnapshot dataSnapshot) {
                                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                                    cart1 = snapshot.getValue(Cartdb.class);
                                    count = filtercartCount - 1;
                                    if (tempcart[i].Name.equals(cart1.Name)) {
                                        String key = snapshot.getKey();
                                        FirebaseDatabase.getInstance().getReference().child("cart").child(key).removeValue();
                                        cartRef.addListenerForSingleValueEvent(new ValueEventListener() {
                                            int countcart = 0;

                                            @Override
                                            public void onDataChange(DataSnapshot dataSnapshot) {
                                                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                                                    tempcart[countcart] = snapshot.getValue(Cartdb.class);
                                                    countcart++;
                                                }
                                                filtercartCount = countcart;
                                                if (filtercartCount > 0) {
                                                    //if cart is not empty
                                                    Intent intent = new Intent(Cart.this, Cart.class);
                                                    intent.putExtra("cart", tempcart);
                                                    intent.putExtra("cartcount", filtercartCount);
                                                    intent.putExtra("user", user);
                                                    intent.putExtra("string", "fromProductInfo");
                                                    intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                                    startActivity(intent);
                                                } else {
                                                    //When cart becomes empty
                                                    Intent intent = new Intent(Cart.this, Empty_cart.class);
                                                    intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                                    intent.putExtra("user" , user);
                                                    startActivity(intent);
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
                });


                if (flag1 == 0) {
                    tempString = tempcart[i].Price.split("₹");
                    tempString1 = tempString[1].split(",");
                    int x = tempString1.length;
                    int index = 1;
                    while (x != 1) {
                        tempString1[0] = tempString1[0] + (tempString1[index]);
                        index++;
                        x--;
                    }

                    totalprice = (Integer.parseInt(tempString1[0]) * 1);

                    initialcost[cindex] = totalprice;
                    cindex++;
                    alltotal = alltotal + totalprice*Integer.parseInt(tempcart[i].Quantity);

                    textview_quantity.setText(tempcart[i].Quantity);
                    product_total.setText("₹" + String.valueOf(totalprice));
                    carttotal.setText("₹" + String.valueOf(alltotal));
                }
            return view;
        }
        public View getViewByPosition(int pos, ListView listView) {
            final int firstListItemPosition = listView.getFirstVisiblePosition();
            final int lastListItemPosition = firstListItemPosition + listView.getChildCount() - 1;

            if (pos < firstListItemPosition || pos > lastListItemPosition ) {
                return listView.getAdapter().getView(pos, null, listView);
            } else {
                final int childIndex = pos - firstListItemPosition;
                return listView.getChildAt(childIndex);
            }
        }
    }
}
