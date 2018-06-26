package com.example.hp.e_commerce;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

/**
 * Created by hp on 05-10-2017.
 */

public class Paymentoptions extends Activity {
    Cartdb cart[];
    int cartCount,total;
    User user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.paymentoptions_layout);

        Intent ii = getIntent();
        cart = (Cartdb[]) ii.getSerializableExtra("cart");
        cartCount = ii.getIntExtra("cartcount", 0);
        user = (User) ii.getSerializableExtra("user");
        total = ii.getIntExtra("total" , 0);


        Button confirmb = (Button)findViewById(R.id.paymentoptionsbutton);

        confirmb.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(Paymentoptions.this , "Your bill is "+String.valueOf(total) , Toast.LENGTH_SHORT).show();
                Intent i = new Intent(Paymentoptions.this , Orderplaced.class);
                i.putExtra("cart" , cart);
                i.putExtra("cartcount", cartCount);
                i.putExtra("user", user);
                startActivity(i);
            }
        });
    }
}
