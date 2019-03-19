package com.taotao.sso.service;

import com.taotao.common.pojo.TaotaoResult;
import com.taotao.pojo.User;

public interface UserService {
    TaotaoResult checkParams(String param, Integer type);

    TaotaoResult register(User user);

    TaotaoResult login(String username,String password);

    TaotaoResult queryUserByToken(String token);

    TaotaoResult logout(String token);
}
