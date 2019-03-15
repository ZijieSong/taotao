package com.taotao.item.viewObject;

import org.apache.commons.lang3.StringUtils;

public class ItemVO {
    private Long id;

    private String title;

    private String sellPoint;

    private Long price;

    private String image;

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

    public String[] getImages(){
        if(StringUtils.isNotBlank(getImage()))
            return getImage().split(",");
        return null;
    }
}
