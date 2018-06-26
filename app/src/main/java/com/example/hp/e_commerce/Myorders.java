package com.example.hp.e_commerce;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

/**
 * Created by hp on 06-10-2017.
 */

public class Myorders extends Activity {

    User user;
    String products[] = new String[11];
    String productimage[] = new String[11];
    int count;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.myorders_layout);

        Intent i = getIntent();
        user = (User) i.getSerializableExtra("user");

        products[0] = user.orders.product0;
        products[1] = user.orders.product1;
        products[2] = user.orders.product2;
        products[3] = user.orders.product3;
        products[4] = user.orders.product4;
        products[5] = user.orders.product5;
        products[6] = user.orders.product6;
        products[7] = user.orders.product7;
        products[8] = user.orders.product8;
        products[9] = user.orders.product9;
        products[10] = user.orders.product10;

        productimage[0] = user.orders.productimage0;
        productimage[1] = user.orders.productimage1;
        productimage[2] = user.orders.productimage2;
        productimage[3] = user.orders.productimage3;
        productimage[4] = user.orders.productimage4;
        productimage[5] = user.orders.productimage5;
        productimage[6] = user.orders.productimage6;
        productimage[7] = user.orders.productimage7;
        productimage[8] = user.orders.productimage8;
        productimage[9] = user.orders.productimage9;
        productimage[10] = user.orders.productimage10;


        count = Integer.parseInt(user.orders.index);

        ListView listView = (ListView) findViewById(R.id.myorderslist);

        customAdapter4 customAdapter4 = new customAdapter4();

        listView.setAdapter(customAdapter4);

    }

    public class customAdapter4 extends BaseAdapter{

        @Override
        public int getCount() {
            return count;
        }

        @Override
        public Object getItem(int position) {
            return null;
        }

        @Override
        public long getItemId(int position) {
            return 0;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {

            convertView = getLayoutInflater().inflate(R.layout.myorderscustom_layout , null);

            TextView name = (TextView)convertView.findViewById(R.id.textView33);
            ImageView image = (ImageView) convertView.findViewById(R.id.imageView5);

            name.setText(products[position]);

            Picasso.with(Myorders.this).load(productimage[position]).into(image);

            return convertView;
        }
    }
}
