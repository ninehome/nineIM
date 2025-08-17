package com.sensetime.liveness.silent.interfaces;

/**
 * @Author YMM
 * @Date 2020/7/27
 * @Description TODO 人脸识别回调
 */
public interface FaceCallBack {

    void callback(String status, String errorMessage, String path);
}
