package com.shopserver.database.objects;

import lombok.Data;
import lombok.NonNull;

import java.io.Serializable;
import java.util.List;


@Data
public class Product implements Serializable{

    @NonNull private String url;
    @NonNull private String img;

    @NonNull private List<String> subcategoryList;
    @NonNull private String name;
    @NonNull private int price;
    @NonNull private List<Property> propertyList;
}