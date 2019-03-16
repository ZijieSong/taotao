package com.taotao.common.pojo;

import java.io.Serializable;

public class KindEditorResult implements Serializable {
    private int error;
    private String url;
    private String message;

    public static KindEditorResult success(String url){
        KindEditorResult result = new KindEditorResult();
        result.setError(0);
        result.setUrl(url);
        return result;
    }

    public static KindEditorResult fail(String msg){
        KindEditorResult result = new KindEditorResult();
        result.setError(1);
        result.setMessage(msg);
        return result;
    }

    public int getError() {
        return error;
    }

    public void setError(int error) {
        this.error = error;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
