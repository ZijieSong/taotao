package com.taotao.controller;

import com.taotao.pojo.EasyUIDataGridResult;
import com.taotao.service.ItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class ItemController {
    @Autowired
    ItemService itemService;

    @RequestMapping("/item/list")
    @ResponseBody
    public EasyUIDataGridResult getItemList(@RequestParam(value = "page", defaultValue = "1")Integer pageNum,
                                            @RequestParam(value = "rows", defaultValue = "10")Integer pageSize){
        return itemService.getItemList(pageNum,pageSize);
    }
}
