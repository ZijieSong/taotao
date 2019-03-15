package com.taotao.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.taotao.mapper.ItemDescMapper;
import com.taotao.mapper.ItemMapper;
import com.taotao.mapper.ItemParamItemMapper;
import com.taotao.pojo.*;
import com.taotao.service.ItemService;
import com.taotao.utils.IDUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.aop.framework.AopContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.jms.core.MessageCreator;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.jms.Destination;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.Session;
import java.util.Date;
import java.util.List;

@Service
public class ItemServiceImpl implements ItemService {
    private static final Logger log = LoggerFactory.getLogger(ItemServiceImpl.class);
    @Autowired
    private ItemMapper itemMapper;
    @Autowired
    private ItemDescMapper itemDescMapper;
    @Autowired
    private ItemParamItemMapper itemParamItemMapper;
    @Autowired
    private JmsTemplate jmsTemplate;
    @Resource(name = "topicDestination")
    private Destination topicDestination;

    @Override
    public EasyUIDataGridResult getItemList(int pageNum, int pageSize) {
        PageHelper.startPage(pageNum, pageSize);
        List<Item> itemList = itemMapper.selectAll();
        PageInfo<Item> pageInfo = new PageInfo<>(itemList);
        return new EasyUIDataGridResult((int) pageInfo.getTotal(), itemList);
    }

    @Override
    public TaotaoResult addItem(Item item, String desc, String itemParams) {
        //插入商品信息表
        item.setId(IDUtils.genItemId());
        item.setStatus((byte) 1);
        item.setCreated(new Date());
        item.setUpdated(new Date());
        itemMapper.insertSelective(item);

        //插入商品描述表
        ItemDesc itemDesc = new ItemDesc();
        itemDesc.setItemId(item.getId());
        itemDesc.setItemDesc(desc);
        itemDesc.setCreated(new Date());
        itemDesc.setUpdated(new Date());
        itemDescMapper.insertSelective(itemDesc);

        //插入商品规格表
        ItemParamItem itemParamItem = new ItemParamItem();
        itemParamItem.setItemId(item.getId());
        itemParamItem.setParamData(itemParams);
        itemParamItem.setCreated(new Date());
        itemParamItem.setUpdated(new Date());
        itemParamItemMapper.insert(itemParamItem);

        return TaotaoResult.ok(item.getId());
    }

    @Override
    public TaotaoResult compAddItem(Item item, String desc, String itemParams){
        //调用方法的目的是为了防止add这个事务方法未提交之前消息就发出去被接收到，
        //接收者查询数据库，但事务还没提交，也就没这条记录导致空指针异常
        //同一个类 非事务调用事务会不生效，因为不走代理对象而直接走真实对象，因此要获取代理对象
        TaotaoResult result = ((ItemService)AopContext.currentProxy()).addItem(item, desc, itemParams);
        //发送消息
        log.info("生产者发送消息");
        jmsTemplate.send(topicDestination, new MessageCreator() {
            @Override
            public Message createMessage(Session session) throws JMSException {
                return session.createTextMessage(result.getData().toString());
            }
        });
        return result;
    }

    @Override
    public Item getItem(Long itemId) {
        return itemMapper.selectByPrimaryKey(itemId);
    }

    @Override
    public ItemDesc getItemDesc(Long itemId) {
        return itemDescMapper.selectByItemId(itemId);
    }
}
