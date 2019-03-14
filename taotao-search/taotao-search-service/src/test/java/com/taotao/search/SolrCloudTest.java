package com.taotao.search;

import org.apache.solr.client.solrj.SolrQuery;
import org.apache.solr.client.solrj.SolrServerException;
import org.apache.solr.client.solrj.impl.CloudSolrServer;
import org.apache.solr.client.solrj.response.QueryResponse;
import org.apache.solr.common.SolrDocument;
import org.apache.solr.common.SolrDocumentList;
import org.apache.solr.common.SolrInputDocument;
import org.junit.Test;

import java.io.IOException;
import java.util.Collection;

public class SolrCloudTest {
    @Test
    public void addItem() throws IOException, SolrServerException {
        CloudSolrServer solrServer = new CloudSolrServer("10.211.55.3:2182,10.211.55.3:2183,10.211.55.3:2184");
        solrServer.setDefaultCollection("collection_taotao");
        SolrInputDocument doc = new SolrInputDocument();
        doc.addField("id",1);
        doc.addField("item_title","test_cloud");
        solrServer.add(doc);
        solrServer.commit();
    }

    @Test
    public void queryItem() throws SolrServerException {
        CloudSolrServer solrServer = new CloudSolrServer("10.211.55.3:2182,10.211.55.3:2183,10.211.55.3:2184");
        solrServer.setDefaultCollection("collection_taotao");
        SolrQuery solrQuery = new SolrQuery();
        solrQuery.setQuery("id:1");
        QueryResponse response = solrServer.query(solrQuery);
        SolrDocumentList results = response.getResults();
        for(SolrDocument solrDocument :results){
            System.out.println("id"+":"+solrDocument.get("id"));
            System.out.println("title"+":"+solrDocument.get("item_title"));
        }
    }
}
