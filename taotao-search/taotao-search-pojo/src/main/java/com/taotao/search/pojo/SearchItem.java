package com.taotao.search.pojo;

import org.apache.commons.lang3.StringUtils;

import java.io.Serializable;

public class SearchItem implements Serializable {
    private Long id;//商品的id
    private String title;//商品标题
    private String sellPoint;//商品卖点
    private Long price;//价格
    private String image;//商品图片的路径
    private String categoryName;//商品分类名称
    private String itemDesc;//商品的描述

    public SearchItem(Long id, String title, String sellPoint, Long price, String image, String categoryName, String itemDesc) {
        this.id = id;
        this.title = title;
        this.sellPoint = sellPoint;
        this.price = price;
        this.image = image;
        this.categoryName = categoryName;
        this.itemDesc = itemDesc;
    }

    public SearchItem() {
        super();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getSellPoint() {
        return sellPoint;
    }

    public void setSellPoint(String sellPoint) {
        this.sellPoint = sellPoint;
    }

    public Long getPrice() {
        return price;
    }

    public void setPrice(Long price) {
        this.price = price;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getCategoryName() {
        return categoryName;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }

    public String getItemDesc() {
        return itemDesc;
    }

    public void setItemDesc(String itemDesc) {
        this.itemDesc = itemDesc;
    }

    public String[] getImages(){
        if(StringUtils.isNotBlank(getImage()))
            return getImage().split(",");
        return null;
    }
}
