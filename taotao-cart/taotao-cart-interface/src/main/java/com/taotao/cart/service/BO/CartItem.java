package com.taotao.cart.service.BO;


import com.taotao.pojo.Item;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;

import java.io.Serializable;

public class CartItem implements Serializable {
    private Long id;

    private String title;

    private String sellPoint;

    private Long price;

    //购买的数量
    private Integer num;

    private String barcode;

    private String image;

    private Long cid;

    private Byte status;

    public CartItem() {
    }

    public CartItem(Item item){
        String image = StringUtils.isEmpty(item.getImage())?null:item.getImage().split(",")[0];
        BeanUtils.copyProperties(item,this);
        this.num=0;
        this.image=image;
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

    public Integer getNum() {
        return num;
    }

    public void setNum(Integer num) {
        this.num = num;
    }

    public String getBarcode() {
        return barcode;
    }

    public void setBarcode(String barcode) {
        this.barcode = barcode;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public Long getCid() {
        return cid;
    }

    public void setCid(Long cid) {
        this.cid = cid;
    }

    public Byte getStatus() {
        return status;
    }

    public void setStatus(Byte status) {
        this.status = status;
    }

}
