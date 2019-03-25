package com.taotao.cart.controller;

import com.taotao.cart.service.BO.CartItem;
import com.taotao.cart.service.CartService;
import com.taotao.common.pojo.TaotaoResult;
import com.taotao.common.utils.CookieUtils;
import com.taotao.common.utils.JsonUtil;
import com.taotao.pojo.Item;
import com.taotao.pojo.User;
import com.taotao.service.ItemService;
import org.apache.commons.lang3.StringUtils;
import org.codehaus.jackson.type.TypeReference;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.List;

@Controller
public class CartController {
    @Autowired
    private CartService cartService;
    @Autowired
    private ItemService itemService;
    @Value("${CART_COOKIE_KEY}")
    private String cartKey;
    @Value("${CART_COOKIE_EXPIRE}")
    private int cartCookieExp;

    @RequestMapping("/cart/add/{itemId}")
    public String addCartItem(HttpServletRequest request,
                              HttpServletResponse response,
                              @PathVariable Long itemId,
                              @RequestParam(name = "num", defaultValue = "1") Integer num) {
        User user = (User) request.getAttribute("USER");
        if (user != null) {
            //登陆保存在redis中
            cartService.addCartItem(user.getId(), itemId, num);
            return "cartSuccess";
        }
        //没登陆状态保存在cookie中
        List<CartItem> cartItemList = getCartFromCookie(request);
        Boolean isExist = false;
        //遍历集合，有的话直接相加，没有的话新增
        for (CartItem cartItem : cartItemList) {
            if (cartItem.getId().equals(itemId)) {
                cartItem.setNum(cartItem.getNum() + num);
                isExist = true;
                break;
            }
        }
        if (!isExist) {
            Item item = itemService.getItem(itemId);
            if (item != null) {
                CartItem cartItem = new CartItem(item);
                cartItem.setNum(num);
                cartItemList.add(cartItem);
            }
        }
        //设置过期时间，编码utf-8
        CookieUtils.setCookie(request, response, cartKey, JsonUtil.objToString(cartItemList), cartCookieExp, true);
        return "cartSuccess";
    }

    @RequestMapping( "/cart/cart")
    public String showCartList(HttpServletRequest request, HttpServletResponse response) {
        User user = (User) request.getAttribute("USER");
        //从cookie中取出cartItemList
        List<CartItem> cartItemList = getCartFromCookie(request);
        if (user != null) {
            //从cookie中取购物车列表，合并到该用户redis中存放的购物车信息中
            cartService.mergeCartItems(cartItemList, user.getId());
            //删除cookie中的购物车信息
            CookieUtils.delCookie(request, response, cartKey);
            //返回redis中存储的购物车列表
            cartItemList = cartService.showCartItems(user.getId());
        }
        //效果同model
        request.setAttribute("cartList", cartItemList);
        return "cart";
    }


    @RequestMapping("/cart/update/num/{itemId}/{num}")
    @ResponseBody
    public TaotaoResult updateCartItemNum(HttpServletRequest request,HttpServletResponse response,
                                          @PathVariable Long itemId, @PathVariable Integer num) {
        User user = (User) request.getAttribute("USER");
        if(user!=null){
            cartService.updateCartItemNum(user.getId(),itemId,num);
        }else {
            List<CartItem> cartItemList = getCartFromCookie(request);
            for (CartItem cartItem : cartItemList) {
                if (cartItem.getId().equals(itemId)) {
                    cartItem.setNum(num);
                    break;
                }
            }
            CookieUtils.setCookie(request, response, cartKey, JsonUtil.objToString(cartItemList), cartCookieExp, true);
        }
        return TaotaoResult.ok();
    }

    @RequestMapping("/cart/delete/{itemId}")
    public String deleteCartItem(HttpServletRequest request, HttpServletResponse response,
                                 @PathVariable Long itemId){
        User user = (User)request.getAttribute("USER");
        if(user!=null){
            cartService.delCartItemById(user.getId(),itemId);
        }else{
            List<CartItem> cartItemList = getCartFromCookie(request);
            for(CartItem cartItem:cartItemList){
                if(cartItem.getId().equals(itemId)) {
                    cartItemList.remove(cartItem);
                    break;
                }
            }
            CookieUtils.setCookie(request,response,cartKey,JsonUtil.objToString(cartItemList),cartCookieExp,true);
        }
        return "redirect:/cart/cart.html";
    }


    private List<CartItem> getCartFromCookie(HttpServletRequest request) {
        String cartItemListJson = CookieUtils.getCookieValue(request, cartKey, true);
        List<CartItem> cartItemList = new ArrayList<>();
        if (StringUtils.isNotBlank(cartItemListJson)) {
            cartItemList = JsonUtil.stringToObject(cartItemListJson, new TypeReference<List<CartItem>>() {
            });
        }
        return cartItemList;
    }

}
