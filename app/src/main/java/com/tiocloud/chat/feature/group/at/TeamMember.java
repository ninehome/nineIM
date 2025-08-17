package com.tiocloud.chat.feature.group.at;

import java.io.Serializable;

/**
 * <pre>
 *     author : TaoWang
 *     e-mail : watayouxiang@qq.com
 *     time   : 2020/07/24
 *     desc   :
 * </pre>
 */
public class TeamMember implements Serializable {
    public String name;
    public String uid;

    public TeamMember(String uid, String name) {
        this.uid = uid;
        this.name = name;
    }
}
