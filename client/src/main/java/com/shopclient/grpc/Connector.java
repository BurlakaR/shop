package com.shopclient.grpc;



import com.google.protobuf.ByteString;
import com.shop.*;

import com.shopserver.database.objects.Authorize;
import com.shopserver.database.objects.Category;
import com.shopserver.database.objects.Client;
import com.shopserver.database.objects.Product;
import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;
import org.springframework.stereotype.Service;
import org.springframework.util.SerializationUtils;

import javax.annotation.PostConstruct;
import java.io.Serializable;
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

    public static Object convertFromBytes(byte[] bytes) {
        return SerializationUtils.deserialize(bytes);
    }

    public static byte[] convertToBytes(Serializable object) {
        return SerializationUtils.serialize(object);
    }

    public Product takeProductGrpc(String url) {
        try {
            ProductRequest request = ProductRequest.newBuilder().setUrl(url).build();
            ProductResponse response = blockingStub.takeProduct(request);
            ByteString byteString=response.getProduct();
            byte [] mas = byteString.toByteArray();
            Object object = convertFromBytes(mas);
            Product product = (Product) object;
            return product;
        } catch (RuntimeException e) {
            logger.log(Level.WARNING, "Request to grpc server failed", e);
            return null;
        }
    }

    public List<Product> takeProductListGrpc(){
        List<Product> arrayList=new ArrayList<>();
        try {
            ProductListRequest request = ProductListRequest.newBuilder().build();
            Iterator<ProductResponse> response = blockingStub.takeProductList(request);
            ProductResponse res;
            while(response.hasNext()){
                res =response.next();
                ByteString byteString=res.getProduct();
                byte [] mas = byteString.toByteArray();
                Object object = convertFromBytes(mas);
                Product product = (Product) object;
                arrayList.add(product);
            }
        } catch (RuntimeException e) {
            logger.log(Level.WARNING, "Request to grpc server failed", e);
        }
        return arrayList;
    }

    public List<Category> takeCategoriesGrpc(){
        List<Category> categoryList=new ArrayList<>();
        try {
            CategoryRequest request = CategoryRequest.newBuilder().build();
            Iterator<CategoryResponse> response = blockingStub.takeCategories(request);
            CategoryResponse res;
            while(response.hasNext()){
                res =response.next();
                ByteString byteString =res.getCategory();
                byte mas[] = byteString.toByteArray();
                Object object = convertFromBytes(mas);
                Category category=(Category)object;
                categoryList.add(category);
            }
        } catch (RuntimeException e) {
            logger.log(Level.WARNING, "Request to grpc server failed", e);
        }
        return categoryList;
    }

    public List<Client> takeClientsGrpc(){
        List<Client> clientList=new ArrayList<>();
        try {
            ClientListRequest request = ClientListRequest.newBuilder().build();
            Iterator<ClientResponse> response = blockingStub.takeClientList(request);
            ClientResponse res;
            while(response.hasNext()){
                res =response.next();
                ByteString byteString =res.getClient();
                byte mas[] = byteString.toByteArray();
                Object object = convertFromBytes(mas);
                Client client = (Client) object;
                clientList.add(client);
            }
        } catch (RuntimeException e) {
            logger.log(Level.WARNING, "Request to grpc server failed", e);
        }
        return clientList;
    }

    public void saveClientGrpc(Client client){
        try {
            ByteString byteString = ByteString.copyFrom(convertToBytes(client));
            SaveClientRequest request = SaveClientRequest.newBuilder().setClient(byteString).build();
            SaveResponse response = blockingStub.saveClient(request);
        } catch (RuntimeException e) {
            logger.log(Level.WARNING, "Request to grpc server failed", e);
        }
    }

    public void buyGrpc(Authorize authorize){
        try {
            ByteString byteString = ByteString.copyFrom(convertToBytes(authorize));
            BuyRequest request = BuyRequest.newBuilder().setCheck(byteString).build();
            BuyResponse response = blockingStub.buy(request);
        } catch (RuntimeException e) {
            logger.log(Level.WARNING, "Request to grpc server failed", e);
        }
    }
}
