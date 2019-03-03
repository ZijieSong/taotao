package com.taotao.mapper;

import com.taotao.pojo.Order;

public interface OrderMapper {
    int insert(Order record);

    int insertSelective(Order record);
}