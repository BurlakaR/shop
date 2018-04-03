package com.shopclient.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

@EnableWebMvc
@Controller
public class CategoryController {

    @RequestMapping("/category")
    public String home(ModelMap model){
        return "category";
    }

}
