package com.shopserver.database.objects;


import lombok.Data;
import lombok.NonNull;

import java.io.Serializable;
import java.util.List;


@Data
public class Basket implements Serializable{
    @NonNull List<Product> productList;
    @NonNull int totalPrice;
}
