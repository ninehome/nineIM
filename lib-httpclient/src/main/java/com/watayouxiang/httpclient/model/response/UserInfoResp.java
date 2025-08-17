package com.watayouxiang.httpclient.model.response;

import java.io.Serializable;
import java.util.List;

/**
 * author : TaoWang
 * date : 2020-02-10
 * desc :
 */
public class UserInfoResp implements Serializable {

    /**
     * nick : 叶孤城
     * country : 中国
     * province : 香港
     * city : 香港
     * roles : [2,6,7]
     * id : 23440
     * avatar : /user/base/avatar/20200115/1/1432471217333790939160576.png
     * "sign":"这是我在tio 的第二条个性签名，心情非常鸡冻，希望有人能看到，给我点鼓励，后面为随机打字，您是一位朋友家吃饭的人多的时"
     * remarkname : 备注一下20
     */

    public String nick;
    public String remarkname;
    public String country;
    public String province;
    public String city;
    public int id;
    public String avatar;
    public List<Integer> roles;
    public String sign;
}
