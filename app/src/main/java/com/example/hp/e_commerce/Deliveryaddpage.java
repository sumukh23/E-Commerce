package com.example.hp.e_commerce;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

/**
 * Created by hp on 05-10-2017.
 */

public class Deliveryaddpage extends Activity {
    Cartdb cart[];
    int cartCount,total;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.deliveryaddpage_layout);

        Intent ii = getIntent();
        final User user = (User) ii.getSerializableExtra("user");
        cart = (Cartdb[]) ii.getSerializableExtra("cart");
        cartCount = ii.getIntExtra("cartcount", 0);
        total = ii.getIntExtra("total" , 0);

        TextView daddress = (TextView)findViewById(R.id.deliveryaddress);
        daddress.setText(user.address);

        Button nbutton = (Button) findViewById(R.id.daddressbutton);

        nbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(Deliveryaddpage.this , Paymentoptions.class);
                i.putExtra("cart" , cart);
                i.putExtra("cartcount", cartCount);
                i.putExtra("user", user);
                i.putExtra("total" , total);
                startActivity(i);
            }
        });
    }
}
