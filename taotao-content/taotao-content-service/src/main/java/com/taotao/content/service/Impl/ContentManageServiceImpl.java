package com.taotao.content.service.Impl;

import com.taotao.content.service.ContentManageService;
import com.taotao.mapper.ContentCategoryMapper;

import com.taotao.pojo.ContentCategory;
import com.taotao.pojo.EasyUIDataTreeNode;
import com.taotao.pojo.TaotaoResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class ContentManageServiceImpl implements ContentManageService {
    @Autowired
    ContentCategoryMapper contentCategoryMapper;
    @Override
    public List<EasyUIDataTreeNode> showContentCatList(Long parentId) {
        List<ContentCategory> contentCategoryList = contentCategoryMapper.selectByParentId(parentId, 1);
        List<EasyUIDataTreeNode> treeNodeList = new ArrayList<>();
        contentCategoryList.forEach(contentCategory -> {
            EasyUIDataTreeNode easyUIDataTreeNode = new EasyUIDataTreeNode();
            easyUIDataTreeNode.setId(contentCategory.getId());
            easyUIDataTreeNode.setText(contentCategory.getName());
            easyUIDataTreeNode.setState(contentCategory.getIsParent()?"closed":"open");
            treeNodeList.add(easyUIDataTreeNode);
        });
        return treeNodeList;
    }

    @Override
    public TaotaoResult createContentCat(Long parentId, String name) {
        ContentCategory contentCategory = new ContentCategory();
        contentCategory.setCreated(new Date());
        contentCategory.setUpdated(new Date());
        contentCategory.setIsParent(false);
        contentCategory.setName(name);
        contentCategory.setParentId(parentId);
        contentCategory.setSortOrder(1);
        contentCategory.setStatus(1);

        contentCategoryMapper.insert(contentCategory);

        if(!contentCategoryMapper.selectByPrimaryKey(parentId).getIsParent()){
            ContentCategory updateParent = new ContentCategory();
            updateParent.setId(parentId);
            updateParent.setIsParent(true);
            contentCategoryMapper.updateByPrimaryKeySelective(updateParent);
        }
        return TaotaoResult.ok(contentCategory);
    }

    @Override
    public TaotaoResult updateContentCat(Long id, String name) {
        ContentCategory updateContentCat = new ContentCategory();
        updateContentCat.setId(id);
        updateContentCat.setName(name);
        contentCategoryMapper.updateByPrimaryKeySelective(updateContentCat);
        return TaotaoResult.ok();
    }

    @Override
    public TaotaoResult deleteContentCat(Long id) {
        //批量删除当前节点及其子节点
        List<Long> childrenIds = new ArrayList<>();
        findChildrenIds(id,childrenIds);
        List<ContentCategory> updateList = new ArrayList<>();
        childrenIds.forEach(childrenId->{
            ContentCategory update = new ContentCategory();
            update.setId(childrenId);
            update.setStatus(0);
            updateList.add(update);
        });
        contentCategoryMapper.updateStatusBatch(updateList);
        //查询出父节点id
        Long parentId = contentCategoryMapper.selectByPrimaryKey(id).getParentId();
        //更新父节点状态
        if(CollectionUtils.isEmpty(contentCategoryMapper.selectByParentId(parentId,1))){
            ContentCategory updateParent = new ContentCategory();
            updateParent.setId(parentId);
            updateParent.setIsParent(false);
            contentCategoryMapper.updateByPrimaryKeySelective(updateParent);
        }
        return TaotaoResult.ok();
    }

    private void findChildrenIds(Long parentId,List<Long> childrenIds){
        childrenIds.add(parentId);
        List<ContentCategory> children = contentCategoryMapper.selectByParentId(parentId,1);
        if(!CollectionUtils.isEmpty(children)) {
            children.forEach(child -> findChildrenIds(child.getId(), childrenIds));
        }
    }


}
