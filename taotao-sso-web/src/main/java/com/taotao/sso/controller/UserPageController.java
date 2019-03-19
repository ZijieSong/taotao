package com.taotao.sso.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class UserPageController {
    @RequestMapping("/page/{page}")
    public String showPages(@PathVariable String page){
        return page;
    }
}
