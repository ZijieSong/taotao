package com.taotao.cart.service.impl;

import com.taotao.cart.service.BO.CartItem;
import com.taotao.cart.service.CartService;
import com.taotao.common.jedis.JedisClient;
import com.taotao.common.pojo.TaotaoResult;
import com.taotao.common.utils.JsonUtil;
import com.taotao.mapper.ItemMapper;
import com.taotao.pojo.Item;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class CartServiceImpl implements CartService {
    @Autowired
    ItemMapper itemMapper;
    @Resource(name = "jedisPoolClient")
    JedisClient jedisClient;
    @Value("${CART_KEY_PREFIX}")
    String cartPre;

    @Override
    public TaotaoResult addCartItem(Long userId, Long itemId, Integer num) {
        //取出该用户购物车，判断是否存在该商品，如果存在，直接数量相加，如果不存在，就新增一条
        //用redis的hash结构，key是用户Id，field是itemId，值是商品信息，这样修改的时候不用所有记录都修改，只用修改一条即可
        String cartItemJson = jedisClient.hget(cartPre + ":" + userId, String.valueOf(itemId));
        CartItem cartItem = null;
        if (StringUtils.isNotEmpty(cartItemJson)) {
            cartItem = JsonUtil.stringToObject(cartItemJson, CartItem.class);
            cartItem.setNum(cartItem.getNum() + num);
        } else {
            Item item = itemMapper.selectByPrimaryKey(itemId);
            cartItem = new CartItem(item);
            cartItem.setNum(num);
        }
        jedisClient.hset(cartPre + ":" + userId, String.valueOf(itemId), JsonUtil.objToString(cartItem));
        return TaotaoResult.ok();
    }

    @Override
    public TaotaoResult mergeCartItems(List<CartItem> cartItemList, Long userId) {
        cartItemList.forEach(cartItem -> addCartItem(userId,cartItem.getId(),cartItem.getNum()));
        return TaotaoResult.ok();
    }

    @Override
    public List<CartItem> showCartItems(Long userId) {
        //取出redis中hash结构的所有的value，就是cartItem的json集合
        List<String> cartItemListJson = jedisClient.hvals(cartPre + ":" + userId);
        List<CartItem> cartItemList = cartItemListJson.stream().map(cartItemJson -> JsonUtil.stringToObject(cartItemJson, CartItem.class)).collect(Collectors.toList());
        return cartItemList;
    }

    @Override
    public TaotaoResult updateCartItemNum(Long userId, Long itemId, Integer num) {
        String cartItemJson = jedisClient.hget(cartPre + ":" + userId, String.valueOf(itemId));
        if(StringUtils.isNotBlank(cartItemJson)){
            CartItem cartItem = JsonUtil.stringToObject(cartItemJson, CartItem.class);
            cartItem.setNum(num);
            jedisClient.hset(cartPre + ":" + userId, String.valueOf(itemId),JsonUtil.objToString(cartItem));
        }
        return TaotaoResult.ok();
    }

    @Override
    public TaotaoResult delCartItemById(Long userId, Long itemId) {
        jedisClient.hdel(cartPre+":"+userId, String.valueOf(itemId));
        return TaotaoResult.ok();
    }

    @Override
    public TaotaoResult delAllCartItemsByUserId(Long userId) {
        jedisClient.del(cartPre + ":" + userId);
        return TaotaoResult.ok();
    }
}
