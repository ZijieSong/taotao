package com.taotao.cart.service;

import com.taotao.cart.service.BO.CartItem;
import com.taotao.common.pojo.TaotaoResult;

import java.util.List;

public interface CartService {
    TaotaoResult addCartItem(Long userId, Long itemId, Integer num);

    TaotaoResult mergeCartItems(List<CartItem> cartItemList, Long userId);

    List<CartItem> showCartItems(Long userId);

    TaotaoResult updateCartItemNum(Long userId, Long itemId, Integer num);

    TaotaoResult delCartItemById(Long userId, Long itemId);
}
