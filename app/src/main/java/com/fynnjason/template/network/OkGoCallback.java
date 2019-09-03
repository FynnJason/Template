package com.fynnjason.template.network;


public interface OkGoCallback {
    void success(String json);

    void error(int code, String msg);
}
