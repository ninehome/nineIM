package com.watayouxiang.httpclient.model.response;

/**
 * <pre>
 *     author : TaoWang
 *     e-mail : watayouxiang@qq.com
 *     time   : 2020/11/18
 *     desc   :
 * </pre>
 */
public class PayGetClientTokenResp {

    /**
     * walletId : 6288883250000000103
     * token : 20201118016750513738578980093952
     * createDateTime : 2020-11-18 15:33:42
     */

    private String walletId;
    private String token;
    private String createDateTime;

    public String getWalletId() {
        return walletId;
    }

    public void setWalletId(String walletId) {
        this.walletId = walletId;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getCreateDateTime() {
        return createDateTime;
    }

    public void setCreateDateTime(String createDateTime) {
        this.createDateTime = createDateTime;
    }
}
