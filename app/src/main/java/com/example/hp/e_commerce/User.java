package com.example.hp.e_commerce;

import java.io.Serializable;

/**
 * Created by hp on 16-09-2017.
 */

public class User implements Serializable {
    public String name,address,number,dob,email,password;
    public Orders orders;

    public User(){

    }

    public User(String name , String address , String number , String dob , String email , String password, Orders orders ){
        this.name = name;
        this.address = address;
        this.number = number;
        this.dob = dob;
        this.email = email;
        this.password = password;
        this.orders = orders;
    }
}
