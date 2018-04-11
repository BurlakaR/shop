package com.shopclient.controllers;

import com.shopclient.grpc.Connector;
import com.shopserver.database.objects.*;
import org.hibernate.validator.constraints.LuhnCheck;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.annotation.PostConstruct;
import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;



@EnableWebMvc
@Controller
public class HomeController {
    @Autowired
    private Connector connector;


    private final static Client guest =new Client("-","guest", "", "", new Date());


    List<Category> categoryList;
    List<Product> productList;
    List<Client> clientList;
    List<Authorize> authorizeList;


    @PostConstruct
    public void init(){
        categoryList = connector.takeCategoriesGrpc();
        clientList = connector.takeClientsGrpc();
        authorizeList=new ArrayList<>();
        productList=connector.takeProductListGrpc();
    }

    @RequestMapping("/home")
    public String home(ModelMap model, HttpServletRequest request){
        Authorize client=currentClient(model, request);
        model.addAttribute("client", client);
        model.addAttribute("categoryList",  categoryList);
        System.out.println(client.getClientAutor().getLogin());
        return "home";
    }

    @RequestMapping("/update")
    public void update(){
        categoryList = connector.takeCategoriesGrpc();
        clientList = connector.takeClientsGrpc();
        productList=connector.takeProductListGrpc();
    }

    @RequestMapping("/category/{subcategory}/{product}")
    public String productPage(ModelMap model, HttpServletRequest request,@PathVariable("subcategory") String category,@PathVariable("product") String prod){
        Authorize client=currentClient(model, request);
        Product product = productInfo(prod);
        model.addAttribute("client", client);
        model.addAttribute("product", product);
        return "product";
    }

    @RequestMapping("/category/{subcategory}")
    public String categoryPage(ModelMap model, HttpServletRequest request, @PathVariable("subcategory") String category){
        Authorize client=currentClient(model, request);
        List<Product> product = productCategoryList(category);
        model.addAttribute("client", client);
        model.addAttribute("products", product);
        model.addAttribute("categoryList",  categoryList);
        System.out.println(category);
        return "category";
    }



    @GetMapping("/registration")
    public String registationPage(ModelMap model){
        Date date=new Date();
        model.addAttribute("client", new Client("","", "","", date));
        model.addAttribute("clientaut", new Login("",""));
        return "registration";
    }



    @PostMapping("/registration")
    public String registrationForm(@ModelAttribute Client client,  HttpServletRequest request, RedirectAttributes redirectAttributes){
        if(registration(client)){
            clientList.add(client);
            Authorize authorize= new Authorize(client, request.getRemoteAddr(),new Basket(new ArrayList<Product>(), 0));
            findandchange(authorize,  request.getRemoteAddr());
            redirectAttributes.addFlashAttribute("client", authorize);
            connector.saveClientGrpc(client);
            return "redirect:/home";
        }
        return "redirect:/registration";
    }

    @PostMapping("/authorize")
    public String authorizeForm(@ModelAttribute Login clientaut, HttpServletRequest request, RedirectAttributes redirectAttributes){
        Client client= authorize(clientaut.getLogin(), clientaut.getPassword());
        if(client!=null) {
            Authorize authorize= new Authorize(client, request.getRemoteAddr(),new Basket(new ArrayList<Product>(), 0));
            findandchange(authorize,  request.getRemoteAddr());
            redirectAttributes.addFlashAttribute("client", authorize);
            return "redirect:/home";
        }
        else
            return "redirect:/registration";
    }

    @RequestMapping("/logout")
        public String logout(ModelMap model, HttpServletRequest request){

            return "redirect:/registration.html#tologin";
    }

    @RequestMapping("/buy")
    public String buy(ModelMap model, HttpServletRequest request){
        Authorize client=currentClient(model, request);
        connector.buyGrpc(client);
        return "redirect:/home";
    }



    private Authorize currentClient(ModelMap model, HttpServletRequest request){
        Authorize client=(Authorize) model.get("client");
        if(client==null) client=find(request.getRemoteAddr());
        return client;
    }



    private Client authorize(String login, String password){
        for(int i=0;i<clientList.size(); i++){
            if(login.equals(clientList.get(i).getLogin())&&password.equals(clientList.get(i).getPassword())){
                return clientList.get(i);
            }
        }
        return  null;
    }


    private boolean registration(Client client){
        for(int i=0;i<clientList.size(); i++){
            if(client.getLogin().equals(clientList.get(i).getLogin())){
                return false;
            }
        }
        return  true;
    }

    private Authorize find(String ip){
        for(int i=0;i<authorizeList.size(); i++){
            if(authorizeList.get(i).getClientIp().equals(ip)){
                return authorizeList.get(i);
            }
        }
        authorizeList.add(new Authorize(guest, ip, new Basket(new ArrayList<Product>(), 0)));
        return  new Authorize(guest, ip, new Basket(new ArrayList<Product>(), 0));
    }

    private void findandchange(Authorize authorize,String ip){
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

    private List<Product> productCategoryList(String category){
        List<Product> productListBuf = new ArrayList<>();
        for(int i=0; i<productList.size(); i++){
            for(int j=0; j<productList.get(i).getSubcategoryList().size(); j++){
                if(category.equals(productList.get(i).getSubcategoryList().get(j))){
                    productListBuf.add(productList.get(i));
                    break;
                }
            }

        }
        return productListBuf;
    }

    private Product productInfo(String url){
        for(int i=0; i<productList.size(); i++){
            if(productList.get(i).getUrl().equals(url)){
                return productList.get(i);
            }
        }
        return null;
    }
}


