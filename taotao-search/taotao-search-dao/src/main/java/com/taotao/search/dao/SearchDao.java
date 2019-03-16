package com.taotao.search.dao;

import com.taotao.search.pojo.SearchItem;
import com.taotao.search.pojo.SearchResult;
import org.apache.solr.client.solrj.SolrQuery;
import org.apache.solr.client.solrj.SolrServer;
import org.apache.solr.client.solrj.SolrServerException;
import org.apache.solr.client.solrj.response.QueryResponse;
import org.apache.solr.common.SolrDocument;
import org.apache.solr.common.SolrDocumentList;
import org.apache.solr.common.SolrInputDocument;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Repository
public class SearchDao {
    @Autowired
    SolrServer solrServer;

    public SearchResult search(SolrQuery solrQuery) throws SolrServerException {
        //查询solr库
        QueryResponse response = solrServer.query(solrQuery);
        SolrDocumentList documentList = response.getResults();
        Map<String, Map<String, List<String>>> highlighting = response.getHighlighting();

        //封装SearchResult
        SearchResult searchResult = new SearchResult();
        List<SearchItem> searchItems = new ArrayList<>();
        for (SolrDocument document : documentList) {
            SearchItem searchItem = new SearchItem();
            searchItem.setId(Long.valueOf(document.get("id").toString()));
            //取高亮显示
            List<String> hl = highlighting.get(document.get("id")).get("item_title");
            searchItem.setTitle(CollectionUtils.isEmpty(hl) ? (String) document.get("item_title") : hl.get(0));
            Long price = document.get("item_price") == null ? Long.valueOf(0) : (Long) document.get("item_price");
            searchItem.setPrice(price);
            searchItem.setSellPoint((String) document.get("item_sell_point"));
            searchItem.setImage((String) document.get("item_image"));
            searchItem.setCategoryName((String) document.get("item_category_name"));
            searchItem.setItemDesc((String) document.get("item_desc"));
            searchItems.add(searchItem);
        }
        searchResult.setSearchItemList(searchItems);
        searchResult.setTotalItems(documentList.getNumFound());
        return searchResult;
    }

    public void addDocuments(List<SearchItem> searchItemList) throws Exception {
        if (!CollectionUtils.isEmpty(searchItemList)) {
            List<SolrInputDocument> documentList = new ArrayList<>();
            searchItemList.forEach(searchItem -> {
                SolrInputDocument document = new SolrInputDocument();
                document.addField("id", searchItem.getId());
                document.addField("item_title", searchItem.getTitle());
                document.addField("item_sell_point", searchItem.getSellPoint());
                document.addField("item_price", searchItem.getPrice());
                document.addField("item_image", searchItem.getImage());
                document.addField("item_category_name", searchItem.getCategoryName());
                document.addField("item_desc", searchItem.getItemDesc());
                documentList.add(document);
            });
            solrServer.add(documentList);
            solrServer.commit();
        }
    }
}
