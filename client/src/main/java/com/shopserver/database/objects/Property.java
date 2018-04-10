package com.shopserver.database.objects;

import lombok.Data;
import lombok.NonNull;

import java.io.Serializable;

@Data
public class Property implements Serializable{
    @NonNull private String property;
    @NonNull private String description;
}
