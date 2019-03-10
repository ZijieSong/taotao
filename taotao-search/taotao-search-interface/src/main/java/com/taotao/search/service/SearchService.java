package com.taotao.search.service;

import com.taotao.pojo.TaotaoResult;
import com.taotao.search.pojo.SearchResult;

import java.io.IOException;

public interface SearchService {
    TaotaoResult addAllSolrDocuments() throws Exception;

    SearchResult getSearchResult(String queryString, Integer pageNum, Integer pageSize) throws Exception;
}
