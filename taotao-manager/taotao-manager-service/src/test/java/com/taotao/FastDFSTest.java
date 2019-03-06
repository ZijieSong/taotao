package com.taotao;

import com.taotao.utils.FastDFSClient;
import org.junit.Test;

public class FastDFSTest {
    @Test
    public void uploadTest() throws Exception {
        FastDFSClient fastDFSClient = new FastDFSClient("/Users/songzijie/Documents/project/taotao/taotao-manager/taotao-manager-service/src/main/resources/properties/FastDFS.conf");
        String path = fastDFSClient.uploadFile("/Users/songzijie/Documents/81f8a509gy1fnjdvkkwgoj20zk0m8ak8.jpg", "jpg");
        System.out.println(path);
    }
}
