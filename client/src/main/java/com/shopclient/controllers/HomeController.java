package com.shopclient.controllers;

import com.shopclient.dataservices.DataManager;
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
    private DataManager dataManager;







    @RequestMapping("/home")
    public String home(ModelMap model, HttpServletRequest request){
        Authorize client=dataManager.currentClient(model, request);
        model.addAttribute("client", client);
        model.addAttribute("categoryList",  dataManager.getCategoryList());
        return "home";
    }

    @RequestMapping("/update")
    public void update(){
        dataManager.update();
    }

    @RequestMapping("/products/{product}")
    public String productPage(ModelMap model, HttpServletRequest request,@PathVariable("product") String prod){
        Authorize client=dataManager.currentClient(model, request);
        Product product = dataManager.productInfo(prod);

        model.addAttribute("productmes", new ProductMessage("",0));
        model.addAttribute("client", client);
        model.addAttribute("product", product);
        model.addAttribute("categoryList",  dataManager.getCategoryList());

        return "product";
    }

    @RequestMapping("/delete/{product}")
    public String deleteProd(ModelMap model, HttpServletRequest request,@PathVariable("product") String prod){
        Authorize client=dataManager.currentClient(model, request);
        client.getBasket().prodDelete(dataManager.productInfo(prod));

        return "redirect:/home";
    }

    @RequestMapping("/{category}/{subcategory}")
    public String categoryPage(ModelMap model, HttpServletRequest request,@PathVariable("category") String category, @PathVariable("subcategory") String subcategory){
        Authorize client=dataManager.currentClient(model, request);
        List<Product> product = dataManager.productCategoryList(category, subcategory);
        model.addAttribute("client", client);
        model.addAttribute("categoryList",  dataManager.getCategoryList());
        model.addAttribute("products", product);
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
        if(dataManager.registration(client)){
            Authorize authorize= new Authorize(client, request.getRemoteAddr(),new Basket(new ArrayList<Product>(), new ArrayList<>(), 0));
            dataManager.findandchange(authorize,  request.getRemoteAddr());
            redirectAttributes.addFlashAttribute("client", authorize);
            return "redirect:/home";
        }
        return "redirect:/registration";
    }

    @PostMapping("/authorize")
    public String authorizeForm(@ModelAttribute Login clientaut, HttpServletRequest request, RedirectAttributes redirectAttributes){
        Client client= dataManager.authorize(clientaut.getLogin(), clientaut.getPassword());
        if(client!=null) {
            Authorize authorize= new Authorize(client, request.getRemoteAddr(),new Basket(new ArrayList<Product>(), new ArrayList<>(), 0));
            dataManager.findandchange(authorize,  request.getRemoteAddr());
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
    public String buy(@ModelAttribute ProductMessage pm, ModelMap model, HttpServletRequest request){
        Authorize client=dataManager.currentClient(model, request);
        if(pm.getNumber()==0) pm.setNumber(1);
        client.getBasket().getProductList().add(dataManager.productInfo(pm.getUrl()));
        client.getBasket().getCount().add(pm.getNumber());
        client.getBasket().reccount();
        return "redirect:/home";
    }



}


