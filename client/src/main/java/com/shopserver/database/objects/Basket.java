package com.shopserver.database.objects;


import lombok.Data;
import lombok.NonNull;

import java.io.Serializable;
import java.util.List;


@Data
public class Basket implements Serializable{
    @NonNull List<Product> productList;
    @NonNull List<Integer> count;
    @NonNull int totalPrice;

    public void reccount(){
        totalPrice=0;
        for (int i=0; i<productList.size();i++){
            totalPrice+=productList.get(i).getPrice()*count.get(i);
        }
    }

    public void prodDelete(Product product){
        int buf=productList.indexOf(product);
        productList.remove(buf);
        count.remove(buf);
    }
}
