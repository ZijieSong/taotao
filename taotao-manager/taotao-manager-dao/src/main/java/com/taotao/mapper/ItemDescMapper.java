package com.taotao.mapper;

import com.taotao.pojo.ItemDesc;

public interface ItemDescMapper {
    int insert(ItemDesc record);

    int insertSelective(ItemDesc record);

    ItemDesc selectByItemId(Long itemId);
}