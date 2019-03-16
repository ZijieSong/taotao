package com.taotao.manager.controller;

import com.taotao.common.pojo.TaotaoResult;
import com.taotao.search.service.SearchService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class SearchManageController {
    @Autowired
    SearchService searchService;

    @RequestMapping(value = "/solr/input/all", method = RequestMethod.POST)
    @ResponseBody
    public TaotaoResult inputAllSolrData() throws Exception {
        return searchService.addAllSolrDocuments();
    }
}
