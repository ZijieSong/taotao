package com.taotao.controller;

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
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@Controller
public class ItemController {
    @Autowired
    ItemService itemService;

    @Value("${ftp.host}")
    private String ftpHost;
    @Value("${ftp.port}")
    private int ftpPort;
    @Value("${ftp.username}")
    private String ftpUsername;
    @Value("${ftp.password}")
    private String ftpPassword;
    @Value("${ftp.basePath}")
    private String ftpBasePath;
    @Value("${image.url.prefix}")
    private String urlPrefix;

    @RequestMapping("/item/list")
    @ResponseBody
    public EasyUIDataGridResult getItemList(@RequestParam(value = "page", defaultValue = "1") Integer pageNum,
                                            @RequestParam(value = "rows", defaultValue = "10") Integer pageSize) {
        return itemService.getItemList(pageNum, pageSize);
    }

    @RequestMapping("/pic/upload")
    @ResponseBody
    public KindEditorResult uploadFile(@RequestParam MultipartFile uploadFile) {
        String originName = uploadFile.getOriginalFilename();
        String newName = IDUtils.genImageName() + originName.substring(originName.lastIndexOf('.'));
        String filePath = new DateTime().toString("/yyyy/MM/dd");
        //上传图片
        try {
            boolean result = FtpUtil.uploadFile(ftpHost, ftpPort, ftpUsername, ftpPassword, ftpBasePath, filePath, newName, uploadFile.getInputStream());
            if (result)
                return KindEditorResult.success(urlPrefix + filePath + "/" + newName);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return KindEditorResult.fail("上传失败");
    }

    @RequestMapping(value = "/item/save",method = RequestMethod.POST)
    @ResponseBody
    public TaotaoResult saveItem(Item item, String desc, @RequestParam String itemParams){
        return itemService.addItem(item,desc,itemParams);
    }
}
