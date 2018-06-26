package com.example.hp.e_commerce;

import java.io.Serializable;

/**
 * Created by hp on 20-09-2017.
 */

public class Products implements Serializable {
    public String Name, Specifications, Cat, Price, Bquantity;
    public String Quantity;
    public String imageURL,imageURL1,imageURL2;

    public Products() {

    }

    public Products(String Name, String Specifications, String Price, String Cat) {
        this.Name = Name;
        this.Specifications = Specifications;
        this.Price = Price;
        this.Cat = Cat;
    }

}
