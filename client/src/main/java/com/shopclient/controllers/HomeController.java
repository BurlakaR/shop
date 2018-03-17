package com.shopclient.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import java.util.ArrayList;
import java.util.List;

/*@Controller
//@RequestMapping("/")
public class HomeController {
    @GetMapping("/home")
    public ModelAndView Home(){
        return new ModelAndView("home");
    }
}*/

@Controller
//@RequestMapping (value = "/home")
public class HomeController {
    //@RequestMapping(method = RequestMethod.GET)
    @RequestMapping("/home")
    public String home(ModelMap model){
        List<String> list = new ArrayList<String>();
        list.add("one");
        list.add("two");
        list.add("four");
        

        model.addAttribute("lists",  list);


        return "home";
    }
}
