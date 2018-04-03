package com.shopclient.controllers;


import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

import javax.swing.*;

@EnableWebMvc
@Controller
public class RegistrationController {
    @RequestMapping("/registration")
    public String home(ModelMap model){
        return "registration";
    }



}
