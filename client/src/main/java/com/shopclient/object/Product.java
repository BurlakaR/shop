package com.shopclient.object;


import lombok.Data;
import lombok.NonNull;

@Data
public class Product {

    @NonNull private String url;
    @NonNull private String category;
    @NonNull private String name;
    @NonNull private int price;
}