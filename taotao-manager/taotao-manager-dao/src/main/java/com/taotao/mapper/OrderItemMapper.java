package com.taotao.mapper;

import com.taotao.pojo.OrderItem;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface OrderItemMapper {
    int insert(OrderItem record);

    int insertSelective(OrderItem record);

    int insertBatch(@Param("orderItemList")List<OrderItem> orderItemList);
}