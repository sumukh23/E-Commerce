package com.example.hp.e_commerce;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

/**
 * Created by hp on 21-09-2017.
 */
public class Productlist extends AppCompatActivity {

    int[] image = new int[2];
    String temp;
    Products p[];
    User user;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.product_layout);

        Intent ii = getIntent();
        p = (Products[]) ii.getSerializableExtra("aaa");
        user = (User) ii.getSerializableExtra("user");

        ListView listview = (ListView)findViewById(R.id.listView);

        final CustomAdapter customAdapter = new CustomAdapter();

        listview.setAdapter(customAdapter);
        listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if(position == 0){
                    Intent i = new Intent(Productlist.this , Productinfo.class);
                    i.putExtra("ppp" , p[0]);
                    i.putExtra("user", user);
                    startActivity(i);
                }
                if(position == 1){
                    Intent i = new Intent(Productlist.this  , Productinfo.class);
                    i.putExtra("ppp" , p[1]);
                    i.putExtra("user", user);
                    startActivity(i);

                }
                if(position == 2){
                    Intent i = new Intent(Productlist.this , Productinfo.class);
                    i.putExtra("ppp" , p[2]);
                    i.putExtra("user", user);
                    startActivity(i);
                }
            }
        });
    }

    class CustomAdapter extends BaseAdapter {

        @Override
        public int getCount() {
            return p.length;
        }

        @Override
        public Object getItem(int i) {
            return i;
        }

        @Override
        public long getItemId(int i) {
            return 0;
        }

        @Override
        public View getView(int i, View view, ViewGroup parent) {
            view = getLayoutInflater().inflate(R.layout.custom_layout,null);

            TextView textview_name = (TextView)view.findViewById(R.id.textView_name);
            TextView textview_cost = (TextView)view.findViewById(R.id.textView_cost);
            TextView textView_ofs = (TextView)view.findViewById(R.id.Productoutofstock);
            ImageView imageview = (ImageView)view.findViewById(R.id.imageView);

            textview_name.setText(p[i].Name);
            temp = String.valueOf(p[i].Price);
            textview_cost.setText(temp);

            if(Integer.parseInt(p[i].Quantity) == 0){
                textView_ofs.setText("Out of Stock");
            }

            Picasso.with(Productlist.this).load(p[i].imageURL).into(imageview);

            return view;
        }
    }
}
