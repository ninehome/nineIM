package com.watayouxiang.httpclient.model.response;

import com.google.gson.Gson;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

/**
 * author : TaoWang
 * date : 2020-01-22
 * desc :
 */
public class GatewayResp implements Serializable {

    public Integer userid;
    public String host="78979";
    public String res;
    public Integer expire;




    @Override
    public String toString() {

        return "gateway:"+new Gson().toJson(this);
    }
}
