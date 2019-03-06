package com.taotao.service.impl;

import com.taotao.pojo.KindEditorResult;
import com.taotao.pojo.TaotaoResult;
import com.taotao.service.UploadFileService;
import com.taotao.utils.FastDFSClient;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class UploadFileServiceImpl implements UploadFileService {
    @Value("${image.url.prefix}")
    String imageUrlPrefix;

    @Override
    public KindEditorResult uploadImageByFastDFS(byte[] uploadFile, String fileName) {
        try {
            FastDFSClient fastDFSClient = new FastDFSClient("classpath:properties/FastDFS.conf");
            String uploadResult = fastDFSClient.uploadFile(uploadFile,fileName.substring(fileName.lastIndexOf('.')+1));
            if(StringUtils.isNotBlank(uploadResult)){
                return KindEditorResult.success(imageUrlPrefix+uploadResult);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return KindEditorResult.fail("上传失败");
    }
}
