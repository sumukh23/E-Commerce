package com.example.hp.e_commerce;

import java.io.Serializable;

/**
 * Created by hp on 27-09-2017.
 */

public class Cartdb implements Serializable{
    public String Name,imageURL,Quantity,Price,UserName;

    public Cartdb(){

    }
    public Cartdb(String Name, String Quantity, String Price, String imageURL, String UserName){
        this.Name = Name;
        this.Quantity = Quantity;
        this.Price = Price;
        this.imageURL = imageURL;
        this.UserName = UserName;
    }
}
