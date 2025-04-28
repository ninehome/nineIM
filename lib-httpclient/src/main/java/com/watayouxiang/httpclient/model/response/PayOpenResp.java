package com.watayouxiang.httpclient.model.response;

/**
 * <pre>
 *     author : TaoWang
 *     e-mail : watayouxiang@qq.com
 *     time   : 2020/11/04
 *     desc   :
 * </pre>
 */
public class PayOpenResp {
    /**
     * walletId : 6288883250000000103
     * idCardRzStatus : SUCCESS
     * walletStatus : ACTIVATE
     * operatorRzStatus : SUCCESS
     * riskScore : 0
     */

    private String walletId;
    private String idCardRzStatus;
    private String walletStatus;
    private String operatorRzStatus;
    private String riskScore;

    public String getWalletId() {
        return walletId;
    }

    public void setWalletId(String walletId) {
        this.walletId = walletId;
    }

    public String getIdCardRzStatus() {
        return idCardRzStatus;
    }

    public void setIdCardRzStatus(String idCardRzStatus) {
        this.idCardRzStatus = idCardRzStatus;
    }

    public String getWalletStatus() {
        return walletStatus;
    }

    public void setWalletStatus(String walletStatus) {
        this.walletStatus = walletStatus;
    }

    public String getOperatorRzStatus() {
        return operatorRzStatus;
    }

    public void setOperatorRzStatus(String operatorRzStatus) {
        this.operatorRzStatus = operatorRzStatus;
    }

    public String getRiskScore() {
        return riskScore;
    }

    public void setRiskScore(String riskScore) {
        this.riskScore = riskScore;
    }
}
