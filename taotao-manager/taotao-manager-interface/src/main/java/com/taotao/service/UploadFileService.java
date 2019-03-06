package com.taotao.service;

import com.taotao.pojo.KindEditorResult;

public interface UploadFileService {
    KindEditorResult uploadImageByFastDFS(byte[] uploadFile, String fileName);
}
