package com.taotao.search;

import org.apache.solr.client.solrj.SolrQuery;
import org.apache.solr.client.solrj.SolrServer;
import org.apache.solr.client.solrj.SolrServerException;
import org.apache.solr.client.solrj.impl.HttpSolrServer;
import org.apache.solr.client.solrj.response.QueryResponse;
import org.apache.solr.common.SolrDocument;
import org.apache.solr.common.SolrDocumentList;
import org.apache.solr.common.SolrInputDocument;
import org.junit.Test;
import org.springframework.util.CollectionUtils;

import java.io.IOException;
import java.util.List;
import java.util.Map;

public class SolrTest {
    @Test
    public void addDocument() throws IOException, SolrServerException {
        SolrServer solrServer = new HttpSolrServer("http://10.211.55.3:8080/solr");
        SolrInputDocument solrInputFields = new SolrInputDocument();
        solrInputFields.addField("id", 3);
        solrInputFields.addField("item_price", 3000);
        solrInputFields.addField("item_title","试3");
        solrInputFields.addField("item_sell_point","测试卖点");
//        solrInputFields.addField("item_title","test");
        solrServer.add(solrInputFields);
        solrServer.commit();
    }

    @Test
    public void deleteDocument() throws IOException, SolrServerException {
        SolrServer solrServer = new HttpSolrServer("http://10.211.55.3:8080/solr");
        solrServer.deleteById("2");
        solrServer.commit();
    }

    @Test
    public void deleteDocumentByQuery() throws IOException, SolrServerException {
        SolrServer solrServer = new HttpSolrServer("http://10.211.55.3:8080/solr");
        //删除item_price是1000的数据
        solrServer.deleteByQuery("item_price:1000");
        solrServer.commit();
    }

    @Test
    public void queryDocuments() throws SolrServerException {
        SolrServer solrServer = new HttpSolrServer("http://10.211.55.3:8080/solr");
        //查询条件对象
        SolrQuery solrQuery = new SolrQuery();
        solrQuery.setQuery("*:*");
        //执行查询
        QueryResponse response = solrServer.query(solrQuery);
        //取出结果
        SolrDocumentList results = response.getResults();
        System.out.println("打印查询结果总记录数"+results.getNumFound());
        results.forEach(solrDocument-> System.out.println(solrDocument.get("id")+";"+solrDocument.get("item_price")));
    }

    @Test
    public void queryDocumentsWithHighLighting() throws SolrServerException {
        SolrServer solrServer = new HttpSolrServer("http://10.211.55.3:8080/solr");
        //设置查询条件
        SolrQuery solrQuery = new SolrQuery();
        solrQuery.setQuery("测试");
        //设置默认搜索域
        solrQuery.set("df","item_keywords");
        //开启高亮
        solrQuery.setHighlight(true);
        //高亮显示的域(为item_title)
        solrQuery.addHighlightField("item_title");
        solrQuery.setHighlightSimplePre("<em>");
        solrQuery.setHighlightSimplePost("</em>");

        //执行查询
        QueryResponse response = solrServer.query(solrQuery);
        //取出搜索数据
        SolrDocumentList documentList = response.getResults();
        //取出高亮数据
        Map<String, Map<String, List<String>>> highlighting = response.getHighlighting();
        //遍历结果
        for(SolrDocument solrDocument: documentList){
            System.out.println(solrDocument.get("id"));
            List<String> strings = highlighting.get(solrDocument.get("id")).get("item_title");
            String title = CollectionUtils.isEmpty(strings)? (String) solrDocument.get("item_title") :strings.get(0);
            System.out.println(title);
            System.out.println(solrDocument.get("item_sell_point"));
        }


    }
}
