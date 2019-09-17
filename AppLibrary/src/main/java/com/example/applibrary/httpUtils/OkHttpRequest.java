package com.example.applibrary.httpUtils;

import android.os.Handler;
import android.os.Looper;
import android.text.TextUtils;
import android.util.Log;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.example.applibrary.base.Netconfig;
import com.google.gson.JsonArray;

import org.apache.http.conn.ConnectTimeoutException;

import java.io.IOException;
import java.net.ConnectException;
import java.net.SocketException;
import java.net.SocketTimeoutException;
import java.net.UnknownHostException;
import java.util.Map;

import javax.net.ssl.SSLProtocolException;

/**
 * Created by Allen on 2018/1/30.
 * WinnerZhike的网络请求
 */

public class OkHttpRequest {
    private static ExecutorDelivery delivery;
    private static Handler mHandler = new Handler(Looper.getMainLooper());

    static {
        delivery = new ExecutorDelivery(mHandler);
    }


    public interface IRequestCallBack<T> {
        T success(String responseStr) throws Exception;
    }


    /**
     * okhttp get请求
     *
     * @param url             地址
     * @param callback        结果回调
     * @param requestCallBack 将请求的结果回调到主线程
     * @param <T>             要解析的类型
     */
    public static <T> void okHttpGet(String url, final OnHttpCallback<T> callback, final IRequestCallBack<T> requestCallBack) {
        url = Netconfig.httpHost + url;
        OkhttpTool.getOkhttpTool().get(url, new okhttp3.Callback() {
            @Override
            public void onFailure(okhttp3.Call call, IOException e) {
                if (callback != null) {
                    OkHttpRequest.delivery.postError(-3, netError(e), callback);
                }
            }

            @Override
            public void onResponse(okhttp3.Call call, okhttp3.Response response) throws IOException {
                if (callback == null) {
                    response.body().close();
                    Log.e("OkHttpCall", "callback is null");
                } else {
                    if (response.isSuccessful()) {
                        String jsonStr = new String(response.body().bytes());//把原始数据转为字符串
                        requestResult(jsonStr, callback, requestCallBack, true);
                    } else {
                        OkHttpRequest.delivery.postError(response.code(), response.message(), callback);
                    }
                }
            }
        });
    }


    /**
     * okhttp post请求
     *
     * @param url             地址
     * @param map             参数
     * @param callback        结果回调
     * @param requestCallBack 将请求的结果回调到主线程
     * @param <T>             要解析的类型
     */
    public static <T> void okHttpMapPost(String url, Map<String, Object> map, final OnHttpCallback<T> callback, final IRequestCallBack<T> requestCallBack) {
        url = Netconfig.httpHost + url;
        String json = null;
        if (map != null) {
            json = mapToJson(map);
        }
        OkhttpTool.getOkhttpTool().uploadJson(url, json, new okhttp3.Callback() {
            @Override
            public void onFailure(okhttp3.Call call, IOException e) {
                if (callback != null) {
                    OkHttpRequest.delivery.postError(10010, netError(e), callback);
                }
            }

            @Override
            public void onResponse(okhttp3.Call call, okhttp3.Response response) throws IOException {

                if (callback == null) {
                    response.body().close();
                    Log.e("OkHttpCall", "callback is null");
                } else {
                    if (response.isSuccessful()) {
                        String jsonStr = response.body().string();//把原始数据转为 字符串
                        requestResult(jsonStr, callback, requestCallBack, true);
                    } else {
                        OkHttpRequest.delivery.postError(response.code(), response.message(), callback);
                    }
                }
            }
        });
    }


    /**
     * okhttp post请求
     *
     * @param url      地址
     * @param map      参数
     * @param callback 结果回调
     */
    public static void okHttpMapPost(String url, Map<String, Object> map, final OnHttpCallback callback) {
        url = Netconfig.httpHost + url;
        OkhttpTool.getOkhttpTool().post(url, map, new okhttp3.Callback() {
            @Override
            public void onFailure(okhttp3.Call call, IOException e) {
                if (callback != null) {
                    OkHttpRequest.delivery.postError(10010, netError(e), callback);
                }
            }

            @Override
            public void onResponse(okhttp3.Call call, okhttp3.Response response) throws IOException {

                if (callback == null) {
                    response.body().close();
                    Log.e("OkHttpCall", "callback is null");
                } else {
                    if (response.isSuccessful()) {
                        String jsonStr = new String(response.body().bytes());//把原始数据转为字符串
                        requestResult(jsonStr, callback, null, false);
                    } else {
                        OkHttpRequest.delivery.postError(response.code(), response.message(), callback);
                    }
                }
            }
        });

    }


    private static String netError(IOException e) {
        if (e instanceof UnknownHostException) {
            return "IP地址/域名无法解析";
        } else if (e instanceof ConnectException) {
            return "网络连接异常";
        } else if (e instanceof ConnectTimeoutException) {
            return "网络连接超时，请检查网络";
        } else if (e instanceof SocketException) {
            return "网络异常，请检查服务器地址是否正确";
        } else if (e instanceof SSLProtocolException) {
            return "发生了SSL错误，无法建立与该服务器的安全连接";
        } else if (e instanceof SocketTimeoutException) {
            return "数据连接超时，请检查网络";
        } else {
            return "网络异常";
        }
    }


    private static <T> void requestResult(String jsonStr, final OnHttpCallback<T> callback, final IRequestCallBack<T> requestCallBack, boolean hasResult) {
        if (TextUtils.isEmpty(jsonStr)) {
            OkHttpRequest.delivery.postError(ErrorEnum.ERROR_10002.getCode(), ErrorEnum.ERROR_10002.getMsg(), callback);
        } else {
            try {
                JSONObject jsonObject = JSONObject.parseObject(jsonStr);
                if (jsonObject == null) {
                    OkHttpRequest.delivery.postError(ErrorEnum.ERROR_10003.getCode(), ErrorEnum.ERROR_10003.getMsg(), callback);
                } else {
                    if (jsonObject.containsKey("code")) {
                        int code = jsonObject.getInteger("code");
                        if (code == 200) {
                            String str = JSON.toJSONString(jsonObject.get("data"));
                            if (TextUtils.equals("[]", str)) {
                                OkHttpRequest.delivery.postSuccess(callback, null);
                            } else {
                                OkHttpRequest.delivery.postSuccess(callback, hasResult ? requestCallBack.success(str) : null);
                            }
                        } else {
                            OkHttpRequest.delivery.postError(code, TextUtils.isEmpty(jsonObject.getString("msg")) ? ErrorEnum.ERROR_10006.getMsg() : jsonObject.getString("msg"), callback);
                        }
                    } else {
                        OkHttpRequest.delivery.postError(ErrorEnum.ERROR_10005.getCode(), ErrorEnum.ERROR_10005.getMsg(), callback);
                    }
                }
            } catch (Exception e) {
                OkHttpRequest.delivery.postError(ErrorEnum.ERROR_10004.getCode(), e.getMessage(), callback);
            }
        }

    }


    //map to json
    private static String mapToJson(Map<String, Object> map) {
        JSONObject jsonObject = (JSONObject) JSONObject.toJSON(map);
        return jsonObject.toString();
    }
}
