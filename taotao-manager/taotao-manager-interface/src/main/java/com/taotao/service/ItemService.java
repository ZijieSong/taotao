package com.taotao.service;

import com.taotao.pojo.EasyUIDataGridResult;
import com.taotao.pojo.Item;
import com.taotao.pojo.TaotaoResult;

public interface ItemService {
    EasyUIDataGridResult getItemList(int pageNum, int pageSize);

    TaotaoResult addItem(Item item);
}
