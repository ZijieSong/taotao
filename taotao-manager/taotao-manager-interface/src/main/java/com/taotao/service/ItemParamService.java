package com.taotao.service;

import com.taotao.common.pojo.TaotaoResult;

public interface ItemParamService {
    TaotaoResult getItemCatParam(Long cid);
    TaotaoResult addItemCatParam(Long cid, String catParams);
    String showItemParams(Long itemId);
}
