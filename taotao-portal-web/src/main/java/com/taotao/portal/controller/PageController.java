package com.taotao.portal.controller;

import com.taotao.content.service.ContentManageService;
import com.taotao.pojo.Content;
import com.taotao.portal.VO.Ad1Node;
import com.taotao.common.utils.JsonUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.ArrayList;
import java.util.List;

@Controller
public class PageController {
    @Autowired
    private ContentManageService contentManageService;
    @Value("${AD1_CATEGORY_ID}")
    private Long categoryId;
    @Value("${AD1_HEIGHT}")
    private String height;
    @Value("${AD1_HEIGHT_B}")
    private String heightB;
    @Value("${AD1_WIDTH}")
    private String width;
    @Value("${AD1_WIDTH_B}")
    private String widthB;


    @RequestMapping("/index")
    public String showIndex(Model model){
        List<Content> contentList = contentManageService.showContentListByCatId(categoryId);
        List<Ad1Node> ad1NodeList = new ArrayList<>();
        contentList.forEach(content -> {
            Ad1Node ad1Node = new Ad1Node();
            ad1Node.setAlt(content.getSubTitle());
            ad1Node.setHeight(height);
            ad1Node.setHeightB(heightB);
            ad1Node.setWidth(width);
            ad1Node.setWidthB(widthB);
            ad1Node.setHref(content.getUrl());
            ad1Node.setSrc(content.getPic());
            ad1Node.setSrcB(content.getPic2());
            ad1NodeList.add(ad1Node);
        });
        model.addAttribute("ad1",JsonUtil.objToString(ad1NodeList));
        return "index";
    }
}
