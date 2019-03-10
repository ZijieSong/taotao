package com.taotao.search.service.impl;

import com.taotao.pojo.TaotaoResult;
import com.taotao.search.dao.SearchDao;
import com.taotao.search.mapper.SearchMapper;
import com.taotao.search.pojo.SearchItem;
import com.taotao.search.pojo.SearchResult;
import com.taotao.search.service.SearchService;
import org.apache.solr.client.solrj.SolrQuery;
import org.apache.solr.client.solrj.SolrServer;
import org.apache.solr.client.solrj.SolrServerException;
import org.apache.solr.common.SolrInputDocument;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Service
public class SearchServiceImpl implements SearchService {
    @Autowired
    SearchMapper searchMapper;
    @Autowired
    SearchDao searchDao;
    @Autowired
    SolrServer solrServer;

    @Override
    public TaotaoResult addAllSolrDocuments() throws Exception {
        List<SearchItem> searchItemList = searchMapper.selectAllSearchItems();
        List<SolrInputDocument> documentList = new ArrayList<>();
        searchItemList.forEach(searchItem -> {
            SolrInputDocument document = new SolrInputDocument();
            document.addField("id",searchItem.getId());
            document.addField("item_title",searchItem.getTitle());
            document.addField("item_sell_point",searchItem.getSellPoint());
            document.addField("item_price",searchItem.getPrice());
            document.addField("item_image",searchItem.getImage());
            document.addField("item_category_name",searchItem.getCategoryName());
            document.addField("item_desc",searchItem.getItemDesc());
            documentList.add(document);
        });
        solrServer.add(documentList);
        solrServer.commit();
        return TaotaoResult.ok();
    }

    @Override
    public SearchResult getSearchResult(String queryString, Integer pageNum, Integer pageSize) throws Exception {
        //创建solrQuery对象
        SolrQuery solrQuery = new SolrQuery();
        //设置查询条件
        solrQuery.setQuery(queryString);
        //设置分页显示
        solrQuery.setStart((pageNum-1)*pageSize);
        solrQuery.setRows(pageSize);
        //设置默认域
        solrQuery.set("df","item_keywords");
        //设置高亮
        solrQuery.setHighlight(true);
        solrQuery.addHighlightField("item_title");
        solrQuery.setHighlightSimplePre("<em>");
        solrQuery.setHighlightSimplePost("</em>");
        //调用dao查询
        SearchResult searchResult = searchDao.search(solrQuery);
        searchResult.setTotalPages(searchResult.getTotalItems()%pageSize==0?searchResult.getTotalItems()/pageSize:searchResult.getTotalItems()/pageSize+1);
        return searchResult;
    }
}
