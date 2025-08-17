package com.tiocloud.chat.mvp.upload;

import android.app.Activity;

import com.blankj.utilcode.util.ToastUtils;
import com.lzy.okgo.model.Progress;
import com.lzy.okgo.request.base.Request;
import com.tiocloud.chat.R;
import com.tiocloud.chat.feature.session.common.SessionActivity;
import com.watayouxiang.androidutils.util.TioLogger;
import com.watayouxiang.androidutils.widget.TioToast;
import com.watayouxiang.androidutils.widget.dialog.progress.SingletonProgressDialog;
import com.watayouxiang.httpclient.TioHttpClient;
import com.watayouxiang.httpclient.callback.TioCallback;
import com.watayouxiang.httpclient.model.BaseResp;
import com.watayouxiang.httpclient.model.request.UploadFileReq;
import com.watayouxiang.httpclient.model.request.UploadImgReq;
import com.watayouxiang.httpclient.model.request.UploadLocationReq;
import com.watayouxiang.httpclient.model.request.UploadVideoReq;

import java.util.List;

/**
 * author : TaoWang
 * date : 2020/3/9
 * desc :
 */
public class UploadPresenter extends UploadContract.Presenter {
    public UploadPresenter(UploadContract.View view) {
        super(new UploadModel(false), view);
    }

    @Override
    public void uploadImg(List<String> chatlinkids, String filePath) {
        setCount(chatlinkids.size());
        showUploadDialog();
        for (String chatlinkid : chatlinkids){
            UploadImgReq req = new UploadImgReq(chatlinkid, filePath);
            req.setCancelTag(this);
            TioHttpClient.upload(req, new TioCallback<String>() {
                @Override
                public void onStart(Request<BaseResp<String>, ? extends Request> request) {
                    super.onStart(request);
                    TioLogger.i("正在上传中...");

                }

                @Override
                public void uploadProgress(Progress progress) {
                    super.uploadProgress(progress);
                    TioLogger.i("uploadProgress: " + progress);
                }

                @Override
                public void onFinish() {
                    super.onFinish();
                    countDown();
                    if (getCount() <= 0){
                        hideUploadDialog();
                        sendComplete();
                    }
                }

                @Override
                public void onTioSuccess(String s) {
                    TioLogger.i("上传完成: " + s);
                }

                @Override
                public void onTioError(String msg) {
                    TioLogger.i("上传出错: " + msg);
                    TioToast.showShort(msg);
                }
            });
        }

    }

    @Override
    public void uploadVideo(List<String> chatlinkids, String filePath) {
        setCount(chatlinkids.size());
        showUploadDialog();
        for (String chatlinkid : chatlinkids){
            UploadVideoReq req = new UploadVideoReq(chatlinkid, filePath);
            req.setCancelTag(this);
            TioHttpClient.upload(req, new TioCallback<String>() {
                @Override
                public void onStart(Request<BaseResp<String>, ? extends Request> request) {
                    super.onStart(request);
                    TioLogger.i("正在上传中...");

                }

                @Override
                public void uploadProgress(Progress progress) {
                    super.uploadProgress(progress);
                    TioLogger.i("uploadProgress: " + progress);
                }

                @Override
                public void onFinish() {
                    super.onFinish();
                    countDown();
                    if (getCount() <= 0){
                        hideUploadDialog();
                        sendComplete();
                    }

                }

                @Override
                public void onTioSuccess(String s) {
                    TioLogger.i("上传完成: " + s);
                }

                @Override
                public void onTioError(String msg) {
                    TioLogger.i("上传出错: " + msg);
                    TioToast.showShort(msg);
                }
            });
        }

    }

    volatile int count;

    public synchronized void setCount(int count) {
        this.count = count;
    }

    public synchronized int getCount() {
        return count;
    }
    public synchronized void countDown(){
        count--;
    }

    @Override
    public void uploadFile(List<String> chatlinkids, String filePath) {
        setCount(chatlinkids.size());
        showUploadDialog();
        for (String chatlinkid : chatlinkids){
            UploadFileReq req = new UploadFileReq(chatlinkid, filePath);
            req.setCancelTag(this);
            TioHttpClient.upload(req, new TioCallback<String>() {
                @Override
                public void onStart(Request<BaseResp<String>, ? extends Request> request) {
                    super.onStart(request);
                    TioLogger.i("正在上传中...");
                }

                @Override
                public void uploadProgress(Progress progress) {
                    super.uploadProgress(progress);
                    TioLogger.i("uploadProgress: " + progress);
                }

                @Override
                public void onFinish() {
                    super.onFinish();

                    countDown();
                    if (getCount() <= 0){
                        hideUploadDialog();
                        sendComplete();
                    }
                }

                @Override
                public void onTioSuccess(String s) {
                    TioLogger.i("上传完成: " + s);
                }

                @Override
                public void onTioError(String msg) {
                    TioLogger.i("上传出错: " + msg);
                    TioToast.showShort(msg);
                }
            });
        }

    }

    @Override
    public void uploadLocation(List<String> chatlinkids, String filePath, String lat, String lng, String address) {
        setCount(chatlinkids.size());
        showUploadDialog();
        for (String chatlinkid : chatlinkids){
            UploadLocationReq req = new UploadLocationReq(chatlinkid, filePath, lat, lng, address);
            req.setCancelTag(this);
            TioHttpClient.upload(req, new TioCallback<String>() {
                @Override
                public void onStart(Request<BaseResp<String>, ? extends Request> request) {
                    super.onStart(request);
                    TioLogger.i("正在上传中...");
                }

                @Override
                public void uploadProgress(Progress progress) {
                    super.uploadProgress(progress);
                    TioLogger.i("uploadProgress: " + progress);
                }

                @Override
                public void onFinish() {
                    super.onFinish();

                    countDown();
                    if (getCount() <= 0){
                        hideUploadDialog();
                        sendComplete();
//                        ToastUtils.showShort("发送成功");
                    }
                }

                @Override
                public void onTioSuccess(String s) {
                    TioLogger.i("上传完成: " + s);
                }

                @Override
                public void onTioError(String msg) {
                    TioLogger.i("上传出错: " + msg);
                    TioToast.showShort("error:"+msg);
                }
            });
        }
    }

    private void showUploadDialog() {
        Activity activity = getView().getActivity();
        if (activity != null) {
            SingletonProgressDialog.show_unCancel(activity, activity.getString(R.string.shangchuanzhong));
        }
    }

    private void hideUploadDialog() {
        SingletonProgressDialog.dismiss();
    }

    private void sendComplete(){
        Activity activity = getView().getActivity();
        if (activity != null && activity instanceof SessionActivity){
            ((SessionActivity)activity).sendComplete();
        }
    }
}
