package com.example.hp.e_commerce;

import java.io.Serializable;

/**
 * Created by hp on 07-10-2017.
 */

public class Orders implements Serializable {
    String index;
    String product0,product1,product2,product3,product4,product5,product6,product7,product8,product9,product10;
    String productimage0,productimage1,productimage2,productimage3,productimage4,productimage5,productimage6,productimage7,productimage8,productimage9,productimage10;

    Orders(){
        index = "0";
    }
}