package com.taotao.search.mapper;

import com.taotao.search.pojo.SearchItem;

import java.util.List;

public interface SearchMapper {
    List<SearchItem> selectAllSearchItems();
}
