package com.taotao.service;

import com.taotao.pojo.EasyUIDataGridResult;
import com.taotao.pojo.Item;
import com.taotao.pojo.ItemDesc;
import com.taotao.pojo.TaotaoResult;

public interface ItemService {
    EasyUIDataGridResult getItemList(int pageNum, int pageSize);

    TaotaoResult addItem(Item item, String desc, String itemParams);

    TaotaoResult compAddItem(Item item, String desc, String itemParams);

    Item getItem(Long itemId);

    ItemDesc getItemDesc(Long itemId);
}
