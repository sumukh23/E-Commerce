package com.example.hp.e_commerce;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

/**
 * Created by hp on 21-09-2017.
 */

public class Account extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_account);

        Intent iii = getIntent();
        User ac = (User) iii.getSerializableExtra("user");

        TextView tv1 = (TextView)findViewById(R.id.Accountname);
        TextView tv2 = (TextView)findViewById(R.id.Accountaddress);
        TextView tv3 = (TextView)findViewById(R.id.Accountnumber);
        TextView tv4 = (TextView)findViewById(R.id.Accountdob);
        TextView tv5 = (TextView)findViewById(R.id.Accountemail);

        tv1.setText(ac.name);
        tv2.setText(ac.address);
        tv3.setText(ac.number);
        tv4.setText(ac.dob);
        tv5.setText(ac.email);
    }

    }
