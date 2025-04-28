package com.watayouxiang.appupdate.entity;

import android.os.Parcel;
import android.os.Parcelable;

import com.watayouxiang.appupdate.R;

/**
 * @author hule
 * @date 2019/7/10 15:48
 * description:app下载更新的实体类,通过服务器返回的更新实体中取出赋值
 */
public class AppUpdate implements Parcelable {
    /**
     * 新版本的下载地址
     */
    private String newVersionUrl;
    /**
     * 是否采取强制更新
     */
    private boolean forceUpdate;
    /**
     * 新版本更新的内容
     */
    private String updateInfo;
    /**
     * 1.apk文件的md5值，用于校验apk文件签名是否一致，防止下载被拦截，
     * 2.用于校验文件大小的完整性
     * 3.若无MD5值，那么安装时不去校验
     */
    private String md5;
    /**
     * 更新提示框的title提示语
     */
    private String updateTitle;
    /**
     * 风格：true代表默认静默下载模式，只弹出下载更新框,下载完毕自动安装， false 代表配合使用进度框与下载失败弹框
     */
    private boolean isSilentMode;
    /**
     * 更新对话框的id
     */
    private int updateResourceId;

    public String getNewVersionUrl() {
        return newVersionUrl;
    }

    public boolean getForceUpdate() {
        return forceUpdate;
    }

    public String getUpdateInfo() {
        return updateInfo;
    }

    public String getMd5() {
        return md5;
    }

    public String getUpdateTitle() {
        return updateTitle;
    }

    public boolean getIsSilentMode() {
        return isSilentMode;
    }

    public int getUpdateResourceId() {
        return updateResourceId;
    }

    private AppUpdate(Builder builder) {
        this.newVersionUrl = builder.newVersionUrl;
        this.forceUpdate = builder.forceUpdate;
        this.updateInfo = builder.updateInfo;
        this.md5 = builder.md5;
        this.updateTitle = builder.updateTitle;
        this.isSilentMode = builder.isSilentMode;
        this.updateResourceId = builder.updateResourceId;
    }

    public static class Builder {
        private String newVersionUrl;
        private String updateInfo;
        private String md5;
        /**
         * 默认不采取 "强制更新"
         */
        private boolean forceUpdate = false;
        /**
         * 更新提示框的title提示语
         */
        private String updateTitle = "应用更新";
        /**
         * 默认不开启 "静默下载模式"
         */
        private boolean isSilentMode = false;
        /**
         * 更新对话框的id
         */
        private int updateResourceId = R.layout.dialog_update;

        public Builder(String newVersionUrl) {
            this.newVersionUrl = newVersionUrl;
        }

        public Builder forceUpdate(boolean forceUpdate) {
            this.forceUpdate = forceUpdate;
            return this;
        }

        public Builder updateInfo(String updateInfo) {
            this.updateInfo = updateInfo;
            return this;
        }

        public Builder md5(String md5) {
            this.md5 = md5;
            return this;
        }

        public Builder updateTitle(String updateTitle) {
            this.updateTitle = updateTitle;
            return this;
        }

        public Builder isSilentMode(boolean isSilentMode) {
            this.isSilentMode = isSilentMode;
            return this;
        }

        public Builder updateResourceId(int updateResourceId) {
            this.updateResourceId = updateResourceId;
            return this;
        }

        public AppUpdate build() {
            return new AppUpdate(this);
        }
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.newVersionUrl);
        dest.writeInt(this.forceUpdate ? 1 : 0);
        dest.writeString(this.updateInfo);
        dest.writeString(this.md5);
        dest.writeString(this.updateTitle);
        dest.writeByte(this.isSilentMode ? (byte) 1 : (byte) 0);
        dest.writeInt(this.updateResourceId);
    }

    protected AppUpdate(Parcel in) {
        this.newVersionUrl = in.readString();
        this.forceUpdate = in.readInt() != 0;
        this.updateInfo = in.readString();
        this.md5 = in.readString();
        this.updateTitle = in.readString();
        this.isSilentMode = in.readByte() != 0;
        this.updateResourceId = in.readInt();
    }

    public static final Creator<AppUpdate> CREATOR = new Creator<AppUpdate>() {
        @Override
        public AppUpdate createFromParcel(Parcel source) {
            return new AppUpdate(source);
        }

        @Override
        public AppUpdate[] newArray(int size) {
            return new AppUpdate[size];
        }
    };
}
