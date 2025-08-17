package com.watayouxiang.imclient.model.body.wx.msg;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.watayouxiang.imclient.R;
import com.watayouxiang.imclient.utils.ConstantUtils;

/**
 * <pre>
 *     author : TaoWang
 *     e-mail : watayouxiang@qq.com
 *     time   : 2020/08/10
 *     desc   :
 * </pre>
 */
public class WxMessageUtils {

    /**
     * 获取显示的 "消息内容"
     *
     * @param contentType 消息类型
     * @param content     消息内容
     * @return 显示的 "消息内容"
     */
    public static String getShowContent(int contentType, String content) {
        String temp;
        switch (contentType) {
            case 3:
                temp = ConstantUtils.context.getString(R.string.fenxiangyigewenjian);
                break;
            case 4:
                temp = ConstantUtils.context.getString(R.string.yuyinxiaoxi);
                break;
            case 5:
                temp = ConstantUtils.context.getString(R.string.fenxiangshipen);
                break;
            case 6:
                temp = ConstantUtils.context.getString(R.string.fenxiangtupian);
                break;
            case 9:
                temp = ConstantUtils.context.getString(R.string.fenxiangmingpian);
                break;
            case 10:
                temp = ConstantUtils.context.getString(R.string.shipintonghua);
                break;
            case 11:
                temp = ConstantUtils.context.getString(R.string.yuyintonghua);
                break;
            case 12:
                temp = ConstantUtils.context.getString(R.string.falegehongbao);
                break;
            case 13:
                JsonObject jsonObject = new Gson().fromJson(content, JsonObject.class);
                temp = jsonObject.get("applymsg").getAsString();
                break;
            case 14:
                temp = ConstantUtils.context.getString(R.string.weizhixinxi);
                break;
            case 15:
                temp = ConstantUtils.context.getString(R.string.biaoqing);
                break;
            case 16:
                temp = ConstantUtils.context.getString(R.string.zhuanzhang);
                break;
            case 17:
                temp = ConstantUtils.context.getString(R.string.xiaoxijilu);
                break;
            default:
                temp = content;
                break;
        }
        return temp;
    }

}
