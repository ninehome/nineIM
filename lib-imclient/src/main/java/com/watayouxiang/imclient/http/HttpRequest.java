package com.watayouxiang.imclient.http;

import com.watayouxiang.imclient.TioIMClient;
import com.watayouxiang.imclient.model.ImServer;

import java.util.HashMap;
import java.util.Map;

public class HttpRequest {
    /**
     * POST方式获取：
     * https://www.t-io.org/mytio/im/imserver.tio_x?tio_site_from_android=1&groupid=/blog/index.html
     * 其中groupid可以不传
     */
    private static final String URL_IM_SERVER = "https://www.t-io.org/mytio/im/imserver.tio_x";

    /**
     * 请求获取IM服务器地址
     *
     * @param groupId 群组id
     * @return IM服务器地址
     */
    public ImServer.Address getImServerAddress(String groupId) {
        try {
            Map<String, String> param = new HashMap<>();
            param.put("tio_site_from_android", "1");
            if (groupId != null) {
                param.put("groupid", groupId);
            }
            String jsonString = HttpConnection.getInstance().post(URL_IM_SERVER, param);
            ImServer imServer = TioIMClient.getInstance().getJsonEngine().string2Object(jsonString, ImServer.class);
            if (imServer.ok) {
                return imServer.data;
            }
        } catch (Exception ignored) {
        }
        return null;
    }
}
