package com.taotao.service;

import com.taotao.pojo.EasyUIDataGridResult;

public interface ItemService {
    EasyUIDataGridResult getItemList(int pageNum, int pageSize);
}
