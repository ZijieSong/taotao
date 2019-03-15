package com.taotao.item.controller;

import com.taotao.item.viewObject.ItemVO;
import com.taotao.pojo.Item;
import com.taotao.pojo.ItemDesc;
import com.taotao.service.ItemService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class ItemController {
    @Autowired
    ItemService itemService;

    @RequestMapping("/item/{itemId}")
    public String getItemDetails(@PathVariable Long itemId, Model model){
        Item item = itemService.getItem(itemId);
        ItemDesc itemDesc = itemService.getItemDesc(itemId);
        model.addAttribute("item",generateVOFromItem(item));
        model.addAttribute("itemDesc",itemDesc);
        return "item";
    }




    private ItemVO generateVOFromItem(Item item){
        ItemVO itemVO = new ItemVO();
        BeanUtils.copyProperties(item,itemVO);
        return itemVO;
    }
}
