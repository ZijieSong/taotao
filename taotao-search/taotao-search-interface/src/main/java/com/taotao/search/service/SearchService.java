package com.taotao.search.service;

import com.taotao.common.pojo.TaotaoResult;
import com.taotao.search.pojo.SearchItem;
import com.taotao.search.pojo.SearchResult;

import java.util.List;

public interface SearchService {
    TaotaoResult addAllSolrDocuments() throws Exception;

    TaotaoResult addSolrDocuments(List<SearchItem> searchItems) throws Exception;

    SearchResult getSearchResult(String queryString, Integer pageNum, Integer pageSize) throws Exception;
}
