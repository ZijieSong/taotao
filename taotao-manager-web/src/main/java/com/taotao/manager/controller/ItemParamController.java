package com.taotao.manager.controller;

import com.taotao.pojo.TaotaoResult;
import com.taotao.service.ItemParamService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("/item/param")
public class ItemParamController {
    @Autowired
    private ItemParamService itemParamService;

    @RequestMapping("/query/itemcatid/{cid}")
    @ResponseBody
    public TaotaoResult queryItemParam(@PathVariable Long cid){
        return itemParamService.getItemCatParam(cid);
    }

    @RequestMapping("/save/{cid}")
    @ResponseBody
    public TaotaoResult saveItemParam(@PathVariable Long cid,
                                      @RequestParam String paramData){
        return itemParamService.addItemCatParam(cid,paramData);
    }

    @RequestMapping("/show/itemId/{itemId}")
    public String showItemParams(@PathVariable Long itemId, Model model){
        String htmlCut = itemParamService.showItemParams(itemId);
        model.addAttribute("htmlCut",htmlCut);
        return "item-param-show";
    }
}
