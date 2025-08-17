package com.watayouxiang.wallet.yanxun;

public enum  WalletDetailTypeNum {
    RECHARGE(1) ,WITHDRAW(2), RED(3),OTHER(4);

    private Integer mode;

    WalletDetailTypeNum(Integer type) {
        this.mode = type;
    }

    public Integer getMode() {
        return mode;
    }
}
