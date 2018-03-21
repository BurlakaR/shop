package com.shopclient.controllers;

import com.shopclient.grpc.Connector;
import com.shopserver.database.objects.Category;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

import javax.annotation.PostConstruct;
import java.util.List;



@EnableWebMvc
@Controller
public class HomeController {
    @Autowired
    private Connector connector;


    List<Category> categoryList;


    @PostConstruct
    public void init(){
        categoryList = connector.takeCategoriesGrpc();
    }

    @RequestMapping("/home")
    public String home(ModelMap model){
        model.addAttribute("categoryList",  categoryList);
        return "home";
    }

    @RequestMapping("/update")
    public void update(){
        init();
    }
}
