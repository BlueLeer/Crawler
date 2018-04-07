package com.lee.crawler;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

/**
 * @CreateAuthor: KingIsHappy
 * @CreateDate: 2017/4/7
 * @Description:
 */
public class HttpUtils {
    private ResultCallback mResultCallback;

    public static void getResult(String urlPath, ResultCallback resultCallback) {
        URL url = null;
        HttpURLConnection connection = null;
        StringBuilder sb = new StringBuilder();
        try {
            url = new URL(urlPath);
            connection = (HttpURLConnection) url.openConnection();
            connection.setConnectTimeout(4000);
            connection.setRequestMethod("GET");
            connection.connect();
            BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            String line;
            while ((line = reader.readLine()) != null) {
                sb.append(line);
            }

            // 回调方法
            if (resultCallback != null) {
                resultCallback.onResult(sb.toString());
            }
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (connection != null) {
                connection.disconnect();
            }
        }

    }

    // 得到response以后回调的接口
    interface ResultCallback {
        void onResult(String result);
    }

    public void setResultCallback(ResultCallback resultCallback) {
        mResultCallback = resultCallback;
    }
}
