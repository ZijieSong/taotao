package com.taotao.manager.controller;

import com.taotao.content.service.ContentManageService;
import com.taotao.pojo.EasyUIDataTreeNode;
import com.taotao.pojo.TaotaoResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

@Controller
public class ContentController {
    @Autowired
    ContentManageService contentManageService;

    @RequestMapping(value = "/content/category/list", method = RequestMethod.GET)
    @ResponseBody
    public List<EasyUIDataTreeNode> showContentCatList(@RequestParam(value = "id",defaultValue = "0") Long id) {
        return contentManageService.showContentCatList(id);
    }

    @RequestMapping(value = "/content/category/create",method = RequestMethod.POST)
    @ResponseBody
    public TaotaoResult createContentCat(Long parentId, String name){
        return contentManageService.createContentCat(parentId,name);
    }

    @RequestMapping(value = "/content/category/update",method = RequestMethod.POST)
    @ResponseBody
    public TaotaoResult updateContentCat(Long id, String name){
        return contentManageService.updateContentCat(id, name);
    }

    @RequestMapping(value = "/content/category/delete/",method = RequestMethod.POST)
    @ResponseBody
    public TaotaoResult deleteContentCat(Long id){
        return contentManageService.deleteContentCat(id);
    }
}
