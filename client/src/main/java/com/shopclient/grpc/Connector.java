package com.shopclient.grpc;



import com.shop.*;
import com.shopclient.object.Product;
import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.logging.Level;
import java.util.logging.Logger;

@Service
public class Connector {
    private static final Logger logger = Logger.getLogger(Connector.class.getName());

    private  ManagedChannel channel;
    private ProductServiceGrpc.ProductServiceBlockingStub blockingStub;
    final static private String HOST="localhost";
    final static private int port=42425;

    @PostConstruct
    public void init() {
        channel = ManagedChannelBuilder.forAddress(HOST, port)
                .usePlaintext(true)
                .build();
        blockingStub = ProductServiceGrpc.newBlockingStub(channel);
    }

    public void shutdown() throws InterruptedException {
        channel.shutdown().awaitTermination(5, TimeUnit.SECONDS);
    }

    public Product takeProductGrpc(String url) {
        try {
            ProductRequest request = ProductRequest.newBuilder().setUrl(url).build();
            ProductResponse response = blockingStub.takeProduct(request);
            return new Product(response.getUrl(), response.getCategory(), response.getName(), response.getPrice());
        } catch (RuntimeException e) {
            logger.log(Level.WARNING, "Request to grpc server failed", e);
            return null;
        }
    }

    public List<Product> takeProductListGrpc(String url){
        List<Product> arrayList=new ArrayList<>();
        try {
            ProductListRequest request = ProductListRequest.newBuilder().setUrl(url).build();
            Iterator<ProductResponse> response = blockingStub.takeProductList(request);
            ProductResponse res;
            while(response.hasNext()){
                res =response.next();
                arrayList.add(new Product(res.getUrl(), res.getCategory(), res.getName(), res.getPrice()));
            }
        } catch (RuntimeException e) {
            logger.log(Level.WARNING, "Request to grpc server failed", e);
        }
        return arrayList;
    }

    public List<String> takeCategoriesGrpc(){
        List<String> categoryList=new ArrayList<>();
        try {
            CategoryRequest request = CategoryRequest.newBuilder().build();
            Iterator<CategoryResponse> response = blockingStub.takeCategories(request);
            CategoryResponse res;
            while(response.hasNext()){
                res =response.next();
                categoryList.add(res.getUrl());
            }
        } catch (RuntimeException e) {
            logger.log(Level.WARNING, "Request to grpc server failed", e);
        }
        return categoryList;
    }
}
