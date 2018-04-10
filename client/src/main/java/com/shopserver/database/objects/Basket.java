package com.shopserver.database.objects;


import lombok.Data;
import lombok.NonNull;

import java.util.List;


@Data
public class Basket {
    @NonNull List<Product> productList;
    @NonNull int totalPrice;
}
