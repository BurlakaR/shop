package com.shopserver.database.objects;

import lombok.Data;
import lombok.NonNull;

@Data
public class Property {
    @NonNull private String property;
    @NonNull private String description;
}
