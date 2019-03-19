package com.taotao.sso.controller;

import com.taotao.common.pojo.TaotaoResult;
import com.taotao.common.utils.CookieUtils;
import com.taotao.sso.service.UserService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.MappingJacksonValue;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Controller
public class UserLoginController {
    @Autowired
    UserService userService;
    @Value("${USER_INFO_COOKIE_KEY}")
    String USER_INFO_COOKIE_KEY;
    @Value("${LOGOUT_REDIRECT_URL}")
    String LOGOUT_REDIRECT_URL;

    @RequestMapping(value = "/user/login", method = RequestMethod.POST)
    @ResponseBody
    public TaotaoResult login(String username, String password, HttpServletRequest request, HttpServletResponse response) {
        TaotaoResult taotaoResult = userService.login(username, password);
        if (taotaoResult.getStatus() == 200) {
            CookieUtils.setCookie(request,response,USER_INFO_COOKIE_KEY,taotaoResult.getData().toString());
        }
        return taotaoResult;
    }

    @RequestMapping(value = "/user/token/{token}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    @ResponseBody
    public Object queryUserInfo(@PathVariable String token, @RequestParam String callback){
        TaotaoResult taotaoResult = userService.queryUserByToken(token);
        if(StringUtils.isNotEmpty(callback)){
            //说明这是一个需要支持jsonp的请求,需要把返回的json包装成js
            //创建mapping对象，设置其值为taotaoResult
            MappingJacksonValue mappingJacksonValue = new MappingJacksonValue(taotaoResult);
            //设置JsonP的方法名称，进行包装
            mappingJacksonValue.setJsonpFunction(callback);
            return mappingJacksonValue;
        }
        return taotaoResult;
    }

    @RequestMapping(value = "/user/logout",method = RequestMethod.GET)
    public String logout(HttpServletRequest request, HttpServletResponse response){
        String token = CookieUtils.getCookieValue(request, USER_INFO_COOKIE_KEY);
        TaotaoResult taotaoResult = userService.logout(token);
        CookieUtils.delCookie(request,response,USER_INFO_COOKIE_KEY);
        return "redirect:"+LOGOUT_REDIRECT_URL;
    }
}
