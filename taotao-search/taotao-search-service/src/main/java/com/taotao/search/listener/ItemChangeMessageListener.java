package com.taotao.search.listener;

import com.taotao.search.mapper.SearchMapper;
import com.taotao.search.pojo.SearchItem;
import com.taotao.search.service.SearchService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.jms.Message;
import javax.jms.MessageListener;
import javax.jms.TextMessage;
import java.util.ArrayList;
import java.util.List;

@Component
public class ItemChangeMessageListener implements MessageListener {
    private static final Logger log = LoggerFactory.getLogger(ItemChangeMessageListener.class);
    @Autowired
    private SearchService searchService;
    @Autowired
    private SearchMapper searchMapper;

    @Override
    public void onMessage(Message message) {
        try {
            if (message instanceof TextMessage) {
                SearchItem searchItem = searchMapper.selectSearchItemById(Long.valueOf(((TextMessage) message).getText()));
                List<SearchItem> searchItems = new ArrayList<>();
                searchItems.add(searchItem);
                searchService.addSolrDocuments(searchItems);
            }
        }catch (Exception e){
            log.error("error message:",e);
        }
    }
}
