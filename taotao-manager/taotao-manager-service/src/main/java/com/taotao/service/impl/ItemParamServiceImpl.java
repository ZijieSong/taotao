package com.taotao.service.impl;

import com.taotao.mapper.ItemParamItemMapper;
import com.taotao.mapper.ItemParamMapper;
import com.taotao.pojo.ItemParam;
import com.taotao.pojo.ItemParamItem;
import com.taotao.common.pojo.TaotaoResult;
import com.taotao.service.ItemParamService;
import com.taotao.common.utils.JsonUtil;
import org.codehaus.jackson.type.TypeReference;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Map;

@Service
public class ItemParamServiceImpl implements ItemParamService {
    @Autowired
    ItemParamMapper itemParamMapper;
    @Autowired
    ItemParamItemMapper itemParamItemMapper;

    @Override
    public TaotaoResult getItemCatParam(Long cid) {
        ItemParam itemParam = itemParamMapper.selectByCategoryId(cid);
        return TaotaoResult.ok(itemParam);
    }

    @Override
    public TaotaoResult addItemCatParam(Long cid, String catParams) {
        ItemParam itemParam = new ItemParam();
        itemParam.setItemCatId(cid);
        itemParam.setParamData(catParams);
        itemParam.setCreated(new Date());
        itemParam.setUpdated(new Date());
        itemParamMapper.insert(itemParam);
        return TaotaoResult.ok();
    }

    @Override
    public String showItemParams(Long itemId) {
        ItemParamItem itemParamItem = itemParamItemMapper.selectByItemId(itemId);
        StringBuffer sb = new StringBuffer();
        if (itemParamItem != null) {
            sb.append("<div data-tab=\"item\" class=\"hide\" style=\"display: block;\">\n")
                    .append("    <div class=\"Ptable\">\n");
            List<Map> params = JsonUtil.stringToObject(itemParamItem.getParamData(), new TypeReference<List<Map>>() {
            });
            if (params != null) {
                for (Map map : params) {
                    sb.append("        <div class=\"Ptable-item\">\n")
                            .append("            <h3>" + map.get("group") + "</h3>\n")
                            .append("            <dl>\n");
                    for (Map content : (List<Map>) map.get("params")) {
                        sb.append("                <dl class=\"clearfix\" style=\"margin:0\">\n")
                                .append("                    <dt>" + content.get("k") + "</dt>\n")
                                .append("                    <dd>" + content.get("v") + "</dd>\n")
                                .append("                </dl>\n");
                    }
                    sb.append("            </dl>\n")
                            .append("        </div>\n");
                }
                sb.append("    </div>\n")
                        .append("</div>");
            }
        }
        return sb.toString();
    }
}
