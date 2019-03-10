package com.taotao.search.pojo;

import java.io.Serializable;
import java.util.List;

public class SearchResult implements Serializable {
    private List<SearchItem> searchItemList;
    private Long totalPages;
    private Long totalItems;

    public List<SearchItem> getSearchItemList() {
        return searchItemList;
    }

    public void setSearchItemList(List<SearchItem> searchItemList) {
        this.searchItemList = searchItemList;
    }

    public Long getTotalPages() {
        return totalPages;
    }

    public void setTotalPages(Long totalPages) {
        this.totalPages = totalPages;
    }

    public Long getTotalItems() {
        return totalItems;
    }

    public void setTotalItems(Long totalItems) {
        this.totalItems = totalItems;
    }
}
