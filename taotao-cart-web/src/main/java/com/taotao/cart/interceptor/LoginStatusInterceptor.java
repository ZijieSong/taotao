package com.taotao.cart.interceptor;

import com.taotao.common.pojo.TaotaoResult;
import com.taotao.common.utils.CookieUtils;
import com.taotao.sso.service.UserService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class LoginStatusInterceptor implements HandlerInterceptor {
    @Value("${USER_INFO_COOKIE_KEY}")
    private String cookieName;
    @Autowired
    private UserService userService;

    @Override
    public boolean preHandle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o) throws Exception {
        //判断用户是否登陆
        String token = CookieUtils.getCookieValue(httpServletRequest, cookieName);
        if(StringUtils.isBlank(token))
            return true;
        TaotaoResult taotaoResult = userService.queryUserByToken(token);
        if(taotaoResult.getStatus()!=200)
            return true;
        //此时用户处于登陆状态
        httpServletRequest.setAttribute("USER",taotaoResult.getData());
        return true;
    }

    @Override
    public void postHandle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o, ModelAndView modelAndView) throws Exception {

    }

    @Override
    public void afterCompletion(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o, Exception e) throws Exception {

    }
}
