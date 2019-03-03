package com.taotao.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.taotao.mapper.ItemMapper;
import com.taotao.pojo.EasyUIDataGridResult;
import com.taotao.pojo.Item;
import com.taotao.service.ItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ItemServiceImpl implements ItemService {
    @Autowired
    ItemMapper itemMapper;

    @Override
    public EasyUIDataGridResult getItemList(int pageNum, int pageSize) {
        PageHelper.startPage(pageNum, pageSize);
        List<Item> itemList = itemMapper.selectAll();
        PageInfo<Item> pageInfo = new PageInfo<>(itemList);
        return new EasyUIDataGridResult((int)pageInfo.getTotal(), itemList);
    }
}
