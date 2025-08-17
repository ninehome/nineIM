package com.watayouxiang.httpclient.model.response;

import java.util.Date;
import java.util.List;

public class CollectEmotionListResp {

    public int count;

    public List<CollectEmotion> data;

    public static class CollectEmotion{
        public int id;
//        public Date createTime;
//        public Date updateTime;
        public int collectType;
        public String content;
    }
}
