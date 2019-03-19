package com.taotao.sso.service.impl;

import com.taotao.common.jedis.JedisClient;
import com.taotao.common.pojo.TaotaoResult;
import com.taotao.common.utils.CookieUtils;
import com.taotao.common.utils.JsonUtil;
import com.taotao.mapper.UserMapper;
import com.taotao.pojo.User;
import com.taotao.sso.service.UserService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.DigestUtils;

import javax.annotation.Resource;
import java.util.Date;
import java.util.UUID;

@Service
public class UserServiceImpl implements UserService {
    @Autowired
    private UserMapper userMapper;
    @Resource(name = "jedisPoolClient")
    private JedisClient jedisClient;

    @Value("${USER_INFO_REDIS_KEY_PREFIX}")
    private String userInfoRedisKeyPre;
    @Value("${USER_INFO_KEY_EXPIRE}")
    private Integer userInfoKeyExpire;

    @Override
    public TaotaoResult checkParams(String param, Integer type) {
        User result = null;
        switch (type) {
            case 1:
                result = userMapper.selectByUsername(param);
                break;
            case 2:
                result = userMapper.selectByPhone(param);
                break;
            case 3:
                result = userMapper.selectByEmail(param);
                break;
            default:
                return TaotaoResult.build(400,"非法参数",false);
        }
        return result == null ? TaotaoResult.ok(true) : TaotaoResult.ok(false);
    }

    @Override
    public TaotaoResult register(User user) {
        if(StringUtils.isBlank(user.getUsername()) || !(boolean)checkParams(user.getUsername(),1).getData()){
            return TaotaoResult.build(400,"注册失败,请校验参数后重试");
        }
        if(StringUtils.isBlank(user.getPassword())){
            return TaotaoResult.build(400,"注册失败,请校验参数后重试");
        }
        if(StringUtils.isNotBlank(user.getPhone())&&!(boolean)checkParams(user.getPhone(),2).getData()){
            return TaotaoResult.build(400,"注册失败,请校验参数后重试");
        }
        if(StringUtils.isNotBlank(user.getEmail())&&!(boolean)checkParams(user.getEmail(),3).getData()){
            return TaotaoResult.build(400,"注册失败,请校验参数后重试");
        }

        //校验成功
        user.setPassword(DigestUtils.md5DigestAsHex(user.getPassword().getBytes()));
        user.setCreated(new Date());
        user.setUpdated(new Date());

        userMapper.insertSelective(user);
        return TaotaoResult.ok();
    }

    @Override
    public TaotaoResult login(String username, String password) {
        User user = userMapper.selectByUserNameAndPassword(username, DigestUtils.md5DigestAsHex(password.getBytes()));
        if(user==null)
            return TaotaoResult.build(400,"用户名或密码错误");
        String token = UUID.randomUUID().toString();
        user.setPassword(null);
        jedisClient.set(userInfoRedisKeyPre+":"+token,JsonUtil.objToString(user));
        jedisClient.expire(userInfoRedisKeyPre+":"+token,userInfoKeyExpire);
        return TaotaoResult.ok(token);
    }

    @Override
    public TaotaoResult queryUserByToken(String token) {
        jedisClient.expire(userInfoRedisKeyPre+":"+token,userInfoKeyExpire);
        String userJson = jedisClient.get(userInfoRedisKeyPre + ":" + token);
        User user = StringUtils.isBlank(userJson)?null:JsonUtil.stringToObject(userJson,User.class);
        return user == null?TaotaoResult.build(400,"用户已过期或不存在"):TaotaoResult.ok(user);
    }

    @Override
    public TaotaoResult logout(String token) {
        jedisClient.del(userInfoRedisKeyPre+":"+token);
        return TaotaoResult.ok();
    }
}
