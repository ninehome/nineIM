package com.watayouxiang.imclient.event;

public class NoticeClearBean {
    private boolean showNotice;

    public NoticeClearBean(boolean showNotice) {
        this.showNotice = showNotice;
    }

    public boolean isShowNotice() {
        return showNotice;
    }

    public void setShowNotice(boolean showNotice) {
        this.showNotice = showNotice;
    }
}
