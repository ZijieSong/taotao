package com.taotao.mapper;

import com.taotao.pojo.ContentCategory;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface ContentCategoryMapper {
    int deleteByPrimaryKey(Long id);

    int insert(ContentCategory record);

    int insertSelective(ContentCategory record);

    ContentCategory selectByPrimaryKey(Long id);

    List<ContentCategory> selectByParentId(@Param("parentId") Long parentId, @Param("status") int status);

    int updateByPrimaryKeySelective(ContentCategory record);

    int updateByPrimaryKey(ContentCategory record);

    int updateStatusBatch(@Param("contentCatList") List<ContentCategory> contentCatList);
}