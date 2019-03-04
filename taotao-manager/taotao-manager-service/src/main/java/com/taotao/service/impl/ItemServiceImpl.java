package com.taotao.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.taotao.mapper.ItemMapper;
import com.taotao.pojo.EasyUIDataGridResult;
import com.taotao.pojo.Item;
import com.taotao.pojo.KindEditorResult;
import com.taotao.pojo.TaotaoResult;
import com.taotao.service.ItemService;
import com.taotao.utils.FtpUtil;
import com.taotao.utils.IDUtils;
import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Date;
import java.util.List;

@Service
public class ItemServiceImpl implements ItemService {
    @Autowired
    private ItemMapper itemMapper;

    @Override
    public EasyUIDataGridResult getItemList(int pageNum, int pageSize) {
        PageHelper.startPage(pageNum, pageSize);
        List<Item> itemList = itemMapper.selectAll();
        PageInfo<Item> pageInfo = new PageInfo<>(itemList);
        return new EasyUIDataGridResult((int) pageInfo.getTotal(), itemList);
    }

    @Override
    public TaotaoResult addItem(Item item) {
        item.setId(IDUtils.genItemId());
        item.setStatus((byte) 1);
        item.setCreated(new Date());
        item.setUpdated(new Date());

        itemMapper.insertSelective(item);
        return TaotaoResult.ok();
    }
}
