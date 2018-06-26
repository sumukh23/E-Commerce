package com.example.hp.e_commerce;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

/**
 * Created by hp on 27-09-2017.
 */

public class Empty_cart extends Activity {

    User user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.empty_cart);

        Intent i = getIntent();
        user = (User) i.getSerializableExtra("user");

        Button btn = (Button)findViewById(R.id.EmptyCartbtn);

        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent ij = new Intent(Empty_cart.this, Homepage.class);
                ij.putExtra("user" , user);
                startActivity(ij);
            }
        });
    }
}
