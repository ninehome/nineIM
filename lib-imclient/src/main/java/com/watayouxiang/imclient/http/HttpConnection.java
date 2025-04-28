package com.watayouxiang.imclient.http;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.FutureTask;

class HttpConnection {
    private HttpConnection() {
    }

    private static HttpConnection mHttpConnection = new HttpConnection();

    static HttpConnection getInstance() {
        return mHttpConnection;
    }

    /**
     * GET请求
     *
     * @param url 带参数的地址
     * @return 请求结果
     */
    String get(final String url) throws ExecutionException, InterruptedException {
        FutureTask<String> task = new FutureTask<>(new Callable<String>() {
            @Override
            public String call() throws Exception {
                StringBuilder response = new StringBuilder();
                HttpURLConnection connection = null;
                BufferedReader reader = null;
                try {
                    URL requestUrl = new URL(url);
                    connection = (HttpURLConnection) requestUrl.openConnection();
                    connection.setRequestMethod("GET");
                    connection.setConnectTimeout(8000);
                    connection.setReadTimeout(8000);
                    if (connection.getResponseCode() == 200) {
                        reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
                        String line;
                        while ((line = reader.readLine()) != null) {
                            response.append(line);
                        }
                    }
                } finally {
                    if (reader != null) {
                        reader.close();
                    }
                    if (connection != null) {
                        connection.disconnect();
                    }
                }
                return response.toString();
            }
        });
        new Thread(task).start();
        return task.get();//阻塞式
    }

    /**
     * POST请求
     *
     * @param url 地址
     * @param map 参数map
     * @return 请求结果
     */
    String post(final String url, final Map<String, String> map) throws ExecutionException, InterruptedException {
        FutureTask<String> task = new FutureTask<>(new Callable<String>() {
            @Override
            public String call() throws Exception {
                StringBuilder response = new StringBuilder();
                HttpURLConnection connection = null;
                BufferedReader reader = null;
                try {
                    URL requestUrl = new URL(url);
                    connection = (HttpURLConnection) requestUrl.openConnection();
                    connection.setRequestMethod("POST");
                    connection.setConnectTimeout(8000);
                    connection.setReadTimeout(8000);
                    //发送post请求必须设置
                    connection.setDoOutput(true);
                    connection.setDoInput(true);
                    connection.setUseCaches(false);
                    connection.setInstanceFollowRedirects(true);
                    connection.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
                    DataOutputStream out = new DataOutputStream(connection.getOutputStream());
                    out.writeBytes(getParam(map));
                    out.flush();
                    out.close();
                    if (connection.getResponseCode() == 200) {
                        reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
                        String line;
                        while ((line = reader.readLine()) != null) {
                            response.append(line);
                        }
                    }
                } finally {
                    if (reader != null) {
                        reader.close();
                    }
                    if (connection != null) {
                        connection.disconnect();
                    }
                }
                return response.toString();
            }
        });
        new Thread(task).start();
        return task.get();//阻塞式
    }

    private String getParam(Map<String, String> map) throws UnsupportedEncodingException {
        StringBuilder request = new StringBuilder();
        List<String> keyList = new ArrayList<>(map.keySet());
        int size = keyList.size();
        for (int i = 0; i < size; i++) {
            String key = keyList.get(i);
            String value = map.get(key);
            request.append(key).append("=").append(URLEncoder.encode(value, "UTF-8"));
            if (i < size - 1) {
                request.append("&");
            }
        }
        return request.toString();
    }
}
