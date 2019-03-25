package com.taotao.sso.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class UserPageController {
    @RequestMapping("/page/{page}")
    public String showPages(@PathVariable String page,
                            @RequestParam(name = "redirect", required = false) String redirect,
                            Model model) {
        model.addAttribute("redirect", redirect);
        return page;
    }
}
