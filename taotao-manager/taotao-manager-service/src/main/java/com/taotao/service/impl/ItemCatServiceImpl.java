package com.taotao.service.impl;

import com.taotao.mapper.ItemCatMapper;
import com.taotao.common.pojo.EasyUIDataTreeNode;
import com.taotao.pojo.ItemCat;
import com.taotao.service.ItemCatService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ItemCatServiceImpl implements ItemCatService {
    @Autowired
    ItemCatMapper itemCatMapper;

    @Override
    public List<EasyUIDataTreeNode> getTreeChildren(Long parentId) {
        List<ItemCat> itemCatList = itemCatMapper.selectByParentId(parentId);
        List<EasyUIDataTreeNode> treeNodeList = new ArrayList<>();
        itemCatList.forEach(itemCat -> {
            EasyUIDataTreeNode treeNode = new EasyUIDataTreeNode();
            treeNode.setId(itemCat.getId());
            treeNode.setText(itemCat.getName());
            treeNode.setState(itemCat.getIsParent()?"closed":"open");
            treeNodeList.add(treeNode);
        });
        return treeNodeList;
    }
}
