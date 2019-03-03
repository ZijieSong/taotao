package com.taotao.mapper;

import com.taotao.pojo.OrderItem;

public interface OrderItemMapper {
    int insert(OrderItem record);

    int insertSelective(OrderItem record);
}