package com.taotao.content.service;

import com.taotao.pojo.EasyUIDataTreeNode;
import com.taotao.pojo.TaotaoResult;

import java.util.List;

public interface ContentManageService {
    List<EasyUIDataTreeNode> showContentCatList(Long parentId);

    TaotaoResult createContentCat(Long parentId, String name);

    TaotaoResult updateContentCat(Long id, String name);

    TaotaoResult deleteContentCat(Long id);
}
