package com.taotao.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class PageController {

    @RequestMapping("/")
    public String getIndex(){
        return "index";
    }

    @RequestMapping("/{menu}")
    public String getMenu(@PathVariable String menu){
        return menu;
    }
}
