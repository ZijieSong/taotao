package com.taotao.service;

import com.taotao.common.pojo.EasyUIDataTreeNode;

import java.util.List;

public interface ItemCatService {
    List<EasyUIDataTreeNode> getTreeChildren (Long parentId);
}
