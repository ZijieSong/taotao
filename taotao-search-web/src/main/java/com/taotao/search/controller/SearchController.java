package com.taotao.search.controller;

import com.taotao.search.pojo.SearchResult;
import com.taotao.search.service.SearchService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class SearchController {
    @Autowired
    SearchService searchService;

    @RequestMapping(value = "/search",method = RequestMethod.GET)
    public String searchItems(@RequestParam("q") String queryStr,
                              @RequestParam(value = "page",defaultValue = "1") Integer page, Model model) throws Exception {
        //解决get中文乱码问题
        queryStr = new String(queryStr.getBytes("iso8859-1"),"utf-8");
//        int i = 1/0;
        SearchResult searchResult = searchService.getSearchResult(queryStr, page, 20);
        model.addAttribute("query",queryStr);
        model.addAttribute("totalPages",searchResult.getTotalPages());
        model.addAttribute("itemList",searchResult.getSearchItemList());
        model.addAttribute("page",page);

        return "search";
    }
}
