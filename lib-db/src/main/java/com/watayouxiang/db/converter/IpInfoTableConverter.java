package com.watayouxiang.db.converter;

import androidx.annotation.NonNull;

import com.watayouxiang.db.table.IpInfoTable;
import com.watayouxiang.httpclient.model.response.UserCurrResp;

/**
 * <pre>
 *     author : TaoWang
 *     e-mail : watayouxiang@qq.com
 *     time   : 2020/09/03
 *     desc   :
 * </pre>
 */
public class IpInfoTableConverter {
    @NonNull
    public static IpInfoTable getInstance(@NonNull UserCurrResp.IpInfoBean ipInfo, int id) {
        IpInfoTable table = new IpInfoTable();

        table.setId((long) id);
        table.setArea(ipInfo.area);
        table.setCity(ipInfo.city);
        table.setCountry(ipInfo.country);
        table.setOperator(ipInfo.operator);
        table.setProvince(ipInfo.province);

        return table;
    }
}
