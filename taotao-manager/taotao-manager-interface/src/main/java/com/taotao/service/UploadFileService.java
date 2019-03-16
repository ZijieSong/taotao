package com.taotao.service;

import com.taotao.common.pojo.KindEditorResult;

public interface UploadFileService {
    KindEditorResult uploadImageByFastDFS(byte[] uploadFile, String fileName);
}
