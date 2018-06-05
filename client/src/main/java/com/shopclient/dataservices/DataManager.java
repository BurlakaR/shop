package com.shopclient.dataservices;

import com.shopclient.grpc.Connector;
import com.shopserver.database.objects.*;
import lombok.Data;
import lombok.Getter;
import lombok.NonNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.ui.ModelMap;

import javax.annotation.PostConstruct;
import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Component
public class DataManager {
    @Autowired
    private Connector connector;

    private final static Client guest =new Client("-","guest", "", "", new Date());


    @Getter List<Category> categoryList;
    @Getter List<Product> productList;
    @Getter List<Client> clientList;
    @Getter List<Authorize> authorizeList;

    @PostConstruct
    public void init(){
        categoryList = connector.takeCategoriesGrpc();
        clientList = connector.takeClientsGrpc();
        authorizeList=new ArrayList<>();
        productList=connector.takeProductListGrpc();
    }

    public void update(){
        categoryList = connector.takeCategoriesGrpc();
        clientList = connector.takeClientsGrpc();
        productList=connector.takeProductListGrpc();
    }

    public Authorize currentClient(ModelMap model, HttpServletRequest request){
        Authorize client=(Authorize) model.get("client");
        if(client==null) client=find(request.getRemoteAddr());
        return client;
    }



    public Client authorize(String login, String password){
        for(int i=0;i<clientList.size(); i++){
            if(login.equals(clientList.get(i).getLogin())&&password.equals(clientList.get(i).getPassword())){
                return clientList.get(i);
            }
        }
        return  null;
    }


    public boolean registration(Client client){
        for(int i=0;i<clientList.size(); i++){
            if(client.getLogin().equals(clientList.get(i).getLogin())){
                return false;
            }
        }
        clientList.add(client);
        connector.saveClientGrpc(client);
        return  true;
    }

    public Authorize find(String ip){
        for(int i=0;i<authorizeList.size(); i++){
            if(authorizeList.get(i).getClientIp().equals(ip)){
                return authorizeList.get(i);
            }
        }
        authorizeList.add(new Authorize(guest, ip, new Basket(new ArrayList<Product>(), new ArrayList<>(), 0)));
        return  new Authorize(guest, ip, new Basket(new ArrayList<Product>(), new ArrayList<>(), 0));
    }

    public void findandchange(Authorize authorize,String ip){
        boolean find=true;
        for(int i=0;i<authorizeList.size(); i++){
            if(authorizeList.get(i).getClientIp().equals(ip)||authorizeList.get(i).getClientAutor().getLogin().equals(authorize.getClientAutor().getLogin())){
                authorizeList.set(i,authorize);
                find=false;
                break;
            }
        };
        if(find)
            authorizeList.add(authorize);
    }

    public List<Product> productCategoryList(String category, String subcategory){
        List<Product> productListBuf = new ArrayList<>();
        boolean categoryBull=false;
        boolean subcategoryBull=false;
        for(int i=0; i<productList.size(); i++){
            for(int j=0; j<productList.get(i).getSubcategoryList().size(); j++){
                if(category.equals(productList.get(i).getSubcategoryList().get(j))){
                    categoryBull=true;
                }
                if(subcategory.equals(productList.get(i).getSubcategoryList().get(j))){
                    subcategoryBull=true;
                }
            }
            if(categoryBull&&subcategoryBull) productListBuf.add(productList.get(i));

        }
        return productListBuf;
    }

    public Product productInfo(String url){
        for(int i=0; i<productList.size(); i++){
            if(productList.get(i).getUrl().equals(url)){
                return productList.get(i);
            }
        }
        return null;
    }


    public void buy(Authorize basket){
        connector.buyGrpc(basket);
    }
}
