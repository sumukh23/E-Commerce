package com.example.hp.e_commerce;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

public class Homepage extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    User user;
    String tempname,tempprice;
    public String str;
    Cartdb cart[] = new Cartdb[10];
    int cartCount;
    private DatabaseReference prodRef, myRef;
    private FirebaseDatabase cartDatabse, userDatabase;
    private DatabaseReference cartRef;
    private  boolean doubleBacktoExitpressedonce;
    NavigationView navigationView;
    Menu menu;
    Boolean b = false, flag = false, flagg = false;
    Products plaptops[] = new Products[3];
    Products pmobiles[] = new Products[3];
    Products ppens[] = new Products[2];
    Products ppencilbox[] = new Products[2];
    Products pmale[] = new Products[2];
    Products pfemale[] = new Products[2];
    Products psofa[] = new Products[2];
    Products pbed[] = new Products[2];
    Products psports[] = new Products[2];
    Products pslippers[] = new Products[2];
    Products pcasual[] = new Products[2];
    String searchproducts[] = new String[24];
    int k1=0,k2=0,k3=0,k4=0,k5=0,k6=0,k7=0,k8=0,k9=0,k10=0,k11=0,flag1=0,searchk=0;
    Productinfo obj = new Productinfo();
    ArrayAdapter<String> adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_homepage);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        menu = navigationView.getMenu();


        //To assign username in menu bar of homepage
        Intent ii = getIntent();
        user = (User) ii.getSerializableExtra("user");
        str = user.name;
        TextView tv = (TextView) navigationView.getHeaderView(0).findViewById(R.id.Drawerusername);
        tv.setText(str);

        //Assigning info to grid elements

        prodRef = FirebaseDatabase.getInstance().getReference();
        prodRef = prodRef.child("products");


        prodRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    Products prod = snapshot.getValue(Products.class);
                    if(prod.Bquantity.equals("1")){
                        flag = true;
                        flagg = flag;
                    }
                    if(prod.Bquantity.equals("2")){
                        flag = true;
                        flagg = flag;
                    }
                    if(prod.Bquantity.equals("3")){
                        flag = true;
                        flagg = flag;
                    }
                    if(prod.Bquantity.equals("4")){
                        flag = true;
                        flagg = flag;
                    }
                    if(prod.Bquantity.equals("5")){
                        flag = true;
                        flagg = flag;
                    }
                    if(prod.Cat.equals("Mobiles")){
                        pmobiles[k2] = prod;
                        k2++;
                    }
                    else if(prod.Cat.equals("Laptops")){
                        plaptops[k1] = prod;
                        k1++;
                    }
                    else if(prod.Cat.equals("Pens")){
                        ppens[k3] = prod;
                        k3++;
                    }
                    else if(prod.Cat.equals("Pencilbox")){
                        ppencilbox[k4] = prod;
                        k4++;
                    }
                    else if(prod.Cat.equals("Male")){
                        pmale[k5] = prod;
                        k5++;
                    }
                    else if(prod.Cat.equals("Female")){
                        pfemale[k6] = prod;
                        k6++;
                    }
                    else if(prod.Cat.equals("Sofa")){
                        psofa[k7] = prod;
                        k7++;
                    }
                    else if(prod.Cat.equals("Bed")){
                        pbed[k8] = prod;
                        k8++;
                    }
                    else if(prod.Cat.equals("Sports")){
                        psports[k9] = prod;
                        k9++;
                    }
                    else if(prod.Cat.equals("Slippers")){
                        pslippers[k10] = prod;
                        k10++;
                    }
                    else if(prod.Cat.equals("Casual")){
                        pcasual[k11] = prod;
                        k11++;
                    }
                    searchproducts[searchk] = prod.Name;
                    searchk++;
                }

                TextView tv1 = (TextView)findViewById(R.id.textView15);
                tempname = pmobiles[0].Name;
                tv1.setText(tempname);
                TextView tv2 = (TextView)findViewById(R.id.textView16);
                tempprice = String.valueOf(pmobiles[0].Price);
                tv2.setText(tempprice);
                ImageButton ib1 = (ImageButton)findViewById(R.id.Homepagep1);
                Picasso.with(Homepage.this).load(pmobiles[0].imageURL).into(ib1);

                TextView tv3 = (TextView)findViewById(R.id.textView17);
                tempname = plaptops[0].Name;
                tv3.setText(tempname);
                TextView tv4 = (TextView)findViewById(R.id.textView18);
                tempprice = String.valueOf(plaptops[0].Price);
                tv4.setText(tempprice);
                ImageButton ib2 = (ImageButton)findViewById(R.id.Homepagep2);
                Picasso.with(Homepage.this).load(plaptops[0].imageURL).into(ib2);

                TextView tv5 = (TextView)findViewById(R.id.textView19);
                tempname = psports[0].Name;
                tv5.setText(tempname);
                TextView tv6 = (TextView)findViewById(R.id.textView20);
                tempprice = String.valueOf(psports[0].Price);
                tv6.setText(tempprice);
                ImageButton ib3 = (ImageButton)findViewById(R.id.Homepagep3);
                Picasso.with(Homepage.this).load(psports[0].imageURL).into(ib3);

                TextView tv7 = (TextView)findViewById(R.id.textView21);
                tempname = pslippers[0].Name;
                tv7.setText(tempname);
                TextView tv8 = (TextView)findViewById(R.id.textView22);
                tempprice = String.valueOf(pslippers[0].Price);
                tv8.setText(tempprice);
                ImageButton ib4 = (ImageButton)findViewById(R.id.Homepagep4);
                Picasso.with(Homepage.this).load(pslippers[0].imageURL).into(ib4);

                TextView tv9 = (TextView)findViewById(R.id.textView23);
                tempname = psofa[0].Name;
                tv9.setText(tempname);
                TextView tv10 = (TextView)findViewById(R.id.textView24);
                tempprice = String.valueOf(psofa[0].Price);
                tv10.setText(tempprice);
                ImageButton ib5 = (ImageButton)findViewById(R.id.Homepagep5);
                Picasso.with(Homepage.this).load(psofa[0].imageURL).into(ib5);

                TextView tv11 = (TextView)findViewById(R.id.textView25);
                tempname = pbed[0].Name;
                tv11.setText(tempname);
                TextView tv12 = (TextView)findViewById(R.id.textView26);
                tempprice = String.valueOf(pbed[0].Price);
                tv12.setText(tempprice);
                ImageButton ib6 = (ImageButton)findViewById(R.id.Homepagep6);
                Picasso.with(Homepage.this).load(pbed[0].imageURL).into(ib6);

                TextView tv13 = (TextView)findViewById(R.id.textView27);
                tempname = ppens[0].Name;
                tv13.setText(tempname);
                TextView tv14 = (TextView)findViewById(R.id.textView28);
                tempprice = String.valueOf(ppens[0].Price);
                tv14.setText(tempprice);
                ImageButton ib7 = (ImageButton)findViewById(R.id.Homepagep7);
                Picasso.with(Homepage.this).load(ppens[0].imageURL).into(ib7);

                TextView tv15 = (TextView)findViewById(R.id.textView29);
                tempname = ppencilbox[0].Name;
                tv15.setText(tempname);
                TextView tv16 = (TextView)findViewById(R.id.textView30);
                tempprice = String.valueOf(ppencilbox[0].Price);
                tv16.setText(tempprice);
                ImageButton ib8 = (ImageButton)findViewById(R.id.Homepagep8);
                Picasso.with(Homepage.this).load(ppencilbox[0].imageURL).into(ib8);

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        ImageButton ib1 = (ImageButton)findViewById(R.id.Homepagep1);
        ImageButton ib2 = (ImageButton)findViewById(R.id.Homepagep2);
        ImageButton ib3 = (ImageButton)findViewById(R.id.Homepagep3);
        ImageButton ib4 = (ImageButton)findViewById(R.id.Homepagep4);
        ImageButton ib5 = (ImageButton)findViewById(R.id.Homepagep5);
        ImageButton ib6 = (ImageButton)findViewById(R.id.Homepagep6);
        ImageButton ib7 = (ImageButton)findViewById(R.id.Homepagep7);
        ImageButton ib8 = (ImageButton)findViewById(R.id.Homepagep8);

        ib1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(Homepage.this , Productinfo.class);
                i.putExtra("ppp" , pmobiles[0]);
                i.putExtra("user", user);
                startActivity(i);
            }
        });

        ib2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(Homepage.this , Productinfo.class);
                i.putExtra("ppp" , plaptops[0]);
                i.putExtra("user", user);
                startActivity(i);
            }
        });

        ib3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(Homepage.this , Productinfo.class);
                i.putExtra("ppp" , psports[0]);
                i.putExtra("user", user);
                startActivity(i);
            }
        });

        ib4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(Homepage.this , Productinfo.class);
                i.putExtra("ppp" , pslippers[0]);
                i.putExtra("user", user);
                startActivity(i);
            }
        });

        ib5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(Homepage.this , Productinfo.class);
                i.putExtra("ppp" , psofa[0]);
                i.putExtra("user", user);
                startActivity(i);
            }
        });

        ib6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(Homepage.this , Productinfo.class);
                i.putExtra("ppp" , pbed[0]);
                i.putExtra("user", user);
                startActivity(i);
            }
        });

        ib7.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(Homepage.this , Productinfo.class);
                i.putExtra("ppp" , ppens[0]);
                i.putExtra("user", user);
                startActivity(i);
            }
        });

        ib8.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(Homepage.this , Productinfo.class);
                i.putExtra("ppp" , ppencilbox[0]);
                i.putExtra("user", user);
                startActivity(i);
            }
        });

        //Below code is for search
        final AutoCompleteTextView search = (AutoCompleteTextView)findViewById(R.id.searchAutotext);
        adapter = new ArrayAdapter<String>(this , R.layout.searchcustomlist, R.id.productname , searchproducts);
        search.setAdapter(adapter);

        search.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                final String selectedItem = search.getAdapter().getItem(position).toString();
                search.setText("");
                prodRef.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        for (DataSnapshot snapshot : dataSnapshot.getChildren()){
                            Products p = snapshot.getValue(Products.class);
                            if(p.Name.equals(selectedItem)){
                                Intent i = new Intent(Homepage.this, Productinfo.class);
                                i.putExtra("ppp", p);
                                i.putExtra("user", user);
                                startActivity(i);
                            }
                        }
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }
                });
            }
        });

        drawer.closeDrawer(GravityCompat.START);
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {

            if(doubleBacktoExitpressedonce){
                Intent intent = new Intent(Intent.ACTION_MAIN);
                intent.addCategory(Intent.CATEGORY_HOME);
                startActivity(intent);
            }

            this.doubleBacktoExitpressedonce = true;
            Toast.makeText( this , "Press again to Exit", Toast.LENGTH_SHORT).show();

            new Handler().postDelayed(new Runnable() {

                @Override
                public void run() {
                    doubleBacktoExitpressedonce=false;
                }
            }, 3000);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.homepage, menu);
        return true;
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if(id == R.id.Drawerlogout){
            Intent i = new Intent(Homepage.this , LoginActivity.class);
            i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(i);
        }
        else if(id == R.id.Drawermyorders){

            Intent iii = new Intent(Homepage.this , Myorders.class);
            iii.putExtra("user" , user);
            startActivity(iii);
        }
        else if(id == R.id.Draweraccount){
            Intent iii = new Intent(Homepage.this , Account.class);
            iii.putExtra("user" , user);
            startActivity(iii);
        }
        else if(id == R.id.Drawerelectronics){

            if(!b) {
                menu.findItem(R.id.Drawermobiles).setVisible(true);
                menu.findItem(R.id.Drawerlaptops).setVisible(true);
                b = true;
            }
            else{
                menu.findItem(R.id.Drawermobiles).setVisible(false);
                menu.findItem(R.id.Drawerlaptops).setVisible(false);
                b = false;
            }
        }
        else if (id == R.id.Drawermobiles){
            Intent i = new Intent(Homepage.this, Productlist.class);
            i.putExtra("aaa" , pmobiles);
            i.putExtra("user", user);
            startActivity(i);
        }
        else if(id == R.id.Drawerlaptops){
            Intent i = new Intent(Homepage.this, Productlist.class);
            i.putExtra("aaa" , plaptops);
            i.putExtra("user", user);
            startActivity(i);
        }
        else if(id == R.id.Drawerstationary){

            if(!b) {
                menu.findItem(R.id.Drawerpen).setVisible(true);
                menu.findItem(R.id.Drawerpencilbox).setVisible(true);
                b = true;
            }
            else{
                menu.findItem(R.id.Drawerpen).setVisible(false);
                menu.findItem(R.id.Drawerpencilbox).setVisible(false);
                b = false;
            }
        }
        else if(id == R.id.Drawerpen){
            Intent i = new Intent(Homepage.this, Productlist.class);
            i.putExtra("aaa" , ppens);
            i.putExtra("user", user);
            startActivity(i);
        }
        else if(id == R.id.Drawerpencilbox){
            Intent i = new Intent(Homepage.this, Productlist.class);
            i.putExtra("aaa" , ppencilbox);
            i.putExtra("user", user);
            startActivity(i);
        }
        else if(id == R.id.Drawerclothes){

            if(!b) {
                menu.findItem(R.id.Drawermen).setVisible(true);
                menu.findItem(R.id.Drawerwomen).setVisible(true);
                b = true;
            }
            else{
                menu.findItem(R.id.Drawermen).setVisible(false);
                menu.findItem(R.id.Drawerwomen).setVisible(false);
                b = false;
            }
        }
        else if(id == R.id.Drawermen){
            Intent i = new Intent(Homepage.this, Productlist.class);
            i.putExtra("aaa" , pmale);
            i.putExtra("user", user);
            startActivity(i);
        }
        else if(id == R.id.Drawerwomen){
            Intent i = new Intent(Homepage.this, Productlist.class);
            i.putExtra("aaa" , pfemale);
            i.putExtra("user", user);
            startActivity(i);
        }
        else if(id == R.id.Drawerfurniture){

            if(!b) {
                menu.findItem(R.id.Drawersofa).setVisible(true);
                menu.findItem(R.id.Drawerbed).setVisible(true);
                b = true;
            }
            else{
                menu.findItem(R.id.Drawersofa).setVisible(false);
                menu.findItem(R.id.Drawerbed).setVisible(false);
                b = false;
            }
        }
        else if(id == R.id.Drawersofa){
            Intent i = new Intent(Homepage.this, Productlist.class);
            i.putExtra("aaa" , psofa);
            i.putExtra("user", user);
            startActivity(i);
        }
        else if(id == R.id.Drawerbed){
            Intent i = new Intent(Homepage.this, Productlist.class);
            i.putExtra("aaa" , pbed);
            i.putExtra("user", user);
            startActivity(i);
        }
        else if(id == R.id.Drawerfootwear){

            if(!b) {
                menu.findItem(R.id.Drawershoes).setVisible(true);
                menu.findItem(R.id.Drawerslippers).setVisible(true);
                menu.findItem(R.id.Drawersnickers).setVisible(true);
                b = true;
            }
            else{
                menu.findItem(R.id.Drawershoes).setVisible(false);
                menu.findItem(R.id.Drawerslippers).setVisible(false);
                menu.findItem(R.id.Drawersnickers).setVisible(false);
                b = false;
            }
        }
        else if(id == R.id.Drawershoes){
            Intent i = new Intent(Homepage.this, Productlist.class);
            i.putExtra("aaa" , psports);
            i.putExtra("user", user);
            startActivity(i);
        }
        else if(id == R.id.Drawerslippers){
            Intent i = new Intent(Homepage.this, Productlist.class);
            i.putExtra("aaa" , pslippers);
            i.putExtra("user", user);
            startActivity(i);
        }
        else if(id == R.id.Drawersnickers){
            Intent i = new Intent(Homepage.this, Productlist.class);
            i.putExtra("aaa" , pcasual);
            i.putExtra("user", user);
            startActivity(i);
        }
        else if(id == R.id.Drawercart){
           if(flagg == false) {
                Intent i = new Intent(Homepage.this, Empty_cart.class);
                i.putExtra("user" , user);
                startActivity(i);
           }
            else{
                cartDatabse = FirebaseDatabase.getInstance();
                cartRef = cartDatabse.getReference("cart");

               userDatabase = FirebaseDatabase.getInstance();
               myRef = userDatabase.getReference("users");

                cartRef.addListenerForSingleValueEvent(new ValueEventListener() {
                   int i=0;
                   @Override
                   public void onDataChange(DataSnapshot dataSnapshot) {
                       for (DataSnapshot snapshot : dataSnapshot.getChildren()){
                            Cartdb c = snapshot.getValue(Cartdb.class);
                           if(c.UserName.equals(user.email)){
                               cart[i] = snapshot.getValue(Cartdb.class);
                               i++;
                           }
                       }
                       if(dataSnapshot.getChildrenCount() > 0){
                           cartCount = i;
                           Intent ii = new Intent(Homepage.this, Cart.class);
                           ii.putExtra("user" , user);
                           ii.putExtra("cart", cart);
                           ii.putExtra("cartcount", cartCount);
                           ii.putExtra("string", "fromHomepage");
                           startActivity(ii);
                       }
                   }

                   @Override
                   public void onCancelled(DatabaseError databaseError) {

                   }
               });
            }
        }

        return true;
    }
}
