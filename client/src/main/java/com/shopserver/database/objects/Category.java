package com.shopserver.database.objects;

import lombok.Data;
import lombok.NonNull;

import java.io.Serializable;
import java.util.List;


@Data
public class Category implements Serializable{

    @NonNull private String url;
    //@NonNull private String name;
    @NonNull private List<String> subcategories;
}
