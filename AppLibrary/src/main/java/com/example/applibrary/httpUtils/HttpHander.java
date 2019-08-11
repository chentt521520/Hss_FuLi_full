package com.example.applibrary.httpUtils;

import android.content.Context;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.alibaba.fastjson.JSON;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.example.applibrary.base.Netconfig;
import com.example.applibrary.custom.ToastUtils;
import com.example.applibrary.entity.ExpressInfo;
import com.example.applibrary.utils.NumberUtils;
import com.example.applibrary.utils.ObjectMapperUtils;
import com.example.applibrary.utils.StringUtils;
import com.google.gson.Gson;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Response;
import retrofit2.Retrofit;

//http网络请求
//返回数据结果出来
public class HttpHander extends Handler {
    //请求结果
    private static final int START_REQ = 1024;//开始请求
    private static final int ERROR_NET = -1;//网络异常
    private static final int REQ_OK = 0;//请求成功
    private static final int ERROR_HOST = 1;//请求服务器异常
    private static final int ERROR_URL = 2;//请求地址异常
    private static final int ERROR_DATA = 3;//数据解析异常
    private static final int ERROR_FAIL = 4;//连接异常

    public static Disposable disposable;//可丢弃

    Context context;
    String msgText; //弹出消息内容
    int reqFlag;    //标记谁的请求

    @Override
    public void handleMessage(Message msg) {
        if (msg.what != START_REQ) {
            String msgText = null;
        }
        switch (msg.what) {
            case START_REQ:
//                if (msgText==null)
//                    BaseActivity.upLoadDialog("数据请求中。。。").show();
//                else
//                    BaseActivity.upLoadDialog(msgText).show();
                break;
            case ERROR_NET:
                System.out.println("请求：HttpHander类-网络异常");
                toast("网络异常");
                break;
            case REQ_OK:
                System.out.println("请求：HttpHander类-成功   " + msg.obj.toString());
                onSucceed(msg);
                break;
            case ERROR_HOST:
                System.out.println("请求：HttpHander类-主机异常");
                toast("服务器地址异常");
                break;
            case ERROR_URL:
                System.out.println("请求：HttpHander类-地址异常");
                toast("请求地址异常");
                break;
            case ERROR_DATA:
                System.out.println("请求：HttpHander类-解析异常");
                toast("解析异常");
                break;
            case ERROR_FAIL:
                System.out.println("请求：HttpHander类-连接异常");
                toast("连接异常");
                break;
        }
    }

    private void toast(String text) {
        ToastUtils.getToastUtils().showToast(context, text);
    }

    //请求成功
    public void onSucceed(Message msg) {

    }

    /**
     * get 请求
     *
     * @param context
     * @param url     地址和参数
     * @param msgText 对话框信息
     * @param reqFlag 返回标记
     */
    public void getHttpGson(Context context, String url, String msgText, final int reqFlag) {
        this.context = context;
        this.msgText = msgText;
        this.reqFlag = reqFlag;
        Message msg = HttpHander.this.obtainMessage(START_REQ);
        msg.arg1 = reqFlag;
        HttpHander.this.sendMessage(msg);
        if (!Notwork.checkNet(context)) {//网络异常
            msg = HttpHander.this.obtainMessage(ERROR_NET);
            msg.arg1 = reqFlag;
            HttpHander.this.sendMessage(msg);
            return;
        }
        Retrofit retrofit = Notwork.getRetrofit();
        if (retrofit == null) {//主机异常
            msg = HttpHander.this.obtainMessage(ERROR_HOST);
            msg.arg1 = reqFlag;
            HttpHander.this.sendMessage(msg);
            return;
        }
        RequestInterface dataInterface = retrofit.create(RequestInterface.class);
        Call<ResponseBody> call = dataInterface.getRequest(url);//这里Call里面的内容必须与HttpJson内的Call内的一致

        call.enqueue(new retrofit2.Callback<ResponseBody>() {
            @Override
            public void onResponse(@Nullable Call<ResponseBody> call, @NonNull Response<ResponseBody> response) {
                try {
                    if (response.code() != 200) {//地址异常
                        Message msg = HttpHander.this.obtainMessage(ERROR_URL);
                        msg.arg1 = reqFlag;
                        HttpHander.this.sendMessage(msg);
                        return;
                    }
                    //200成功、404：没找到、
                    Message msg = HttpHander.this.obtainMessage(REQ_OK);
                    msg.arg1 = reqFlag;
                    String jsonStr = new String(response.body().bytes());//把原始数据转为字符串
                    try {
                        msg.obj = StringUtils.decodeUnicode(jsonStr);
                    } catch (Exception e) {
                        msg.obj = jsonStr;
                    } finally {
                        HttpHander.this.sendMessage(msg);
                    }
                } catch (Exception e) {//解析异常
                    Message msg = HttpHander.this.obtainMessage(ERROR_DATA);
                    msg.arg1 = reqFlag;
                    HttpHander.this.sendMessage(msg);
                }
            }

            @Override
            public void onFailure(@NonNull Call<ResponseBody> call, Throwable t) {//连接异常
                System.out.println("请求：连接异常" + t);
                Message msg = HttpHander.this.obtainMessage(ERROR_FAIL);
                msg.arg1 = reqFlag;
                HttpHander.this.sendMessage(msg);
            }
        });
    }

    public static <T> T fromJson(String json, Class<T> clazz) {

        try {
            return clazz.equals(String.class) ? (T) json : JSON.parseObject(json, clazz);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }

    /**
     * post 请求
     *
     * @param context
     * @param url     地址和参数
     * @param msgText 对话框信息
     * @param reqFlag 返回标记
     */
    public void postHttpGson(Context context, String url, String msgText, final int reqFlag) {
        this.context = context;
        this.msgText = msgText;
        this.reqFlag = reqFlag;
        Message msg = HttpHander.this.obtainMessage(START_REQ);
        msg.arg1 = reqFlag;
        HttpHander.this.sendMessage(msg);
        if (!Notwork.checkNet(context)) {//网络异常
            msg = HttpHander.this.obtainMessage(ERROR_NET);
            msg.arg1 = reqFlag;
            HttpHander.this.sendMessage(msg);
            return;
        }
        Retrofit retrofit = Notwork.getRetrofit();
        if (retrofit == null) {//主机异常
            msg = HttpHander.this.obtainMessage(ERROR_HOST);
            msg.arg1 = reqFlag;
            HttpHander.this.sendMessage(msg);
            return;
        }
        RequestInterface dataInterface = retrofit.create(RequestInterface.class);
        Call<ResponseBody> call = dataInterface.postRequest(url);//这里Call里面的内容必须与HttpJson内的Call内的一致

        call.enqueue(new retrofit2.Callback<ResponseBody>() {
            @Override
            public void onResponse(@Nullable Call<ResponseBody> call, @NonNull Response<ResponseBody> response) {
                try {
                    if (response.code() != 200) {//地址异常
                        Message msg = HttpHander.this.obtainMessage(ERROR_URL);
                        msg.arg1 = reqFlag;
                        HttpHander.this.sendMessage(msg);
                        return;
                    }
                    //200成功、404：没找到、
                    Message msg = HttpHander.this.obtainMessage(REQ_OK);
                    msg.arg1 = reqFlag;
                    String jsonStr = new String(response.body().bytes());//把原始数据转为字符串
                    try {
                        msg.obj = StringUtils.decodeUnicode(jsonStr); //转换
                    } catch (Exception e) {
                        msg.obj = jsonStr;
                    } finally {
                        HttpHander.this.sendMessage(msg);
                    }
                } catch (Exception e) {//解析异常
                    Message msg = HttpHander.this.obtainMessage(ERROR_DATA);
                    msg.arg1 = reqFlag;
                    HttpHander.this.sendMessage(msg);
                }
            }

            @Override
            public void onFailure(@NonNull Call<ResponseBody> call, Throwable t) {//连接异常
                Message msg = HttpHander.this.obtainMessage(ERROR_FAIL);
                msg.arg1 = reqFlag;
                HttpHander.this.sendMessage(msg);
            }
        });
    }

    /**
     * post 请求，可以取消请求   （暂不用）
     *
     * @param context
     * @param url     地址和参数
     * @param msgText 对话框信息
     * @param reqFlag 返回标记
     */
    public void postHttpGsonReq(Context context, String url, String msgText, final int reqFlag) {
        this.context = context;
        this.msgText = msgText;
        this.reqFlag = reqFlag;
        Message msg = HttpHander.this.obtainMessage(START_REQ);
        msg.arg1 = reqFlag;
        HttpHander.this.sendMessage(msg);
        if (!Notwork.checkNet(context)) {//网络异常
            msg = HttpHander.this.obtainMessage(ERROR_NET);
            msg.arg1 = reqFlag;
            HttpHander.this.sendMessage(msg);
            return;
        }
        Retrofit retrofit = Notwork.getRetrofit();
        if (retrofit == null) {//主机异常
            msg = HttpHander.this.obtainMessage(ERROR_HOST);
            msg.arg1 = reqFlag;
            HttpHander.this.sendMessage(msg);
            return;
        }
        RequestInterface dataInterface = retrofit.create(RequestInterface.class);
        unsubscribe();
        disposable = dataInterface.postReqMsg(url)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<RequestMsg>() {
                    @Override
                    public void accept(RequestMsg info) throws Exception {
                        Message msg = HttpHander.this.obtainMessage(REQ_OK);
                        msg.arg1 = reqFlag;
                        msg.obj = info.obj;
                        HttpHander.this.sendMessage(msg);
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        Message msg = HttpHander.this.obtainMessage(ERROR_DATA);
                        msg.arg1 = reqFlag;
                        HttpHander.this.sendMessage(msg);
                    }
                });
    }

    //释放
    public void unsubscribe() {
        if (disposable != null && !disposable.isDisposed()) {
            disposable.dispose();
        }
    }

    /**
     * okhttpget 请求 （可以不用）
     *
     * @param url 地址包含参数
     */
    public void okHttpGet(Context context, String url, final int reqFlag) {
        this.context = context;
        url = Netconfig.httpHost + url;
        OkhttpTool.getOkhttpTool().get(url, new okhttp3.Callback() {
            @Override
            public void onFailure(okhttp3.Call call, IOException e) {
                Message msg = HttpHander.this.obtainMessage(ERROR_FAIL);
                msg.arg1 = reqFlag;
                HttpHander.this.sendMessage(msg);
            }

            @Override
            public void onResponse(okhttp3.Call call, okhttp3.Response response) throws IOException {
                Message msg = HttpHander.this.obtainMessage(REQ_OK);
                msg.arg1 = reqFlag;
                String jsonStr = new String(response.body().bytes());//把原始数据转为字符串
                try {
                    msg.obj = StringUtils.decodeUnicode(jsonStr); //转换
                } catch (Exception e) {
                    msg.obj = jsonStr;
                } finally {
                    HttpHander.this.sendMessage(msg);
                }
            }
        });
    }

    /**
     * okhttppost 请求 （可以不用）
     *
     * @param url 地址
     */
    public void okHttpPost(Context context, String url,final int reqFlag) {
        this.context = context;
        url = Netconfig.httpHost + url;
        OkhttpTool.getOkhttpTool().post(url, null, new okhttp3.Callback() {
            @Override
            public void onFailure(okhttp3.Call call, IOException e) {
                Message msg = HttpHander.this.obtainMessage(ERROR_FAIL);
                msg.arg1 = reqFlag;
                HttpHander.this.sendMessage(msg);
            }

            @Override
            public void onResponse(okhttp3.Call call, okhttp3.Response response) throws IOException {
                Message msg = HttpHander.this.obtainMessage(REQ_OK);
                msg.arg1 = reqFlag;
                String jsonStr = new String(response.body().bytes());//把原始数据转为字符串
                try {
                    msg.obj = StringUtils.decodeUnicode(jsonStr); //转换
                } catch (Exception e) {
                    msg.obj = jsonStr;
                } finally {
                    HttpHander.this.sendMessage(msg);
                }
            }
        });
    }

    /**
     * 带上传的json数据请求
     *
     * @param url
     * @param json
     */
    public void okHttpJsonPost(Context context, String url, String json, final int reqFlag) {
        this.context = context;
        url = Netconfig.httpHost + url;
        OkhttpTool.getOkhttpTool().uploadJson(url, json, new okhttp3.Callback() {
            @Override
            public void onFailure(okhttp3.Call call, IOException e) {
                Message msg = HttpHander.this.obtainMessage(ERROR_FAIL);
                msg.arg1 = reqFlag;
                HttpHander.this.sendMessage(msg);
            }

            @Override
            public void onResponse(okhttp3.Call call, okhttp3.Response response) throws IOException {
                Message msg = HttpHander.this.obtainMessage(REQ_OK);
                msg.arg1 = reqFlag;
                String jsonStr = new String(response.body().bytes());//把原始数据转为字符串
                try {
                    msg.obj = StringUtils.decodeUnicode(jsonStr); //转换
                } catch (Exception e) {
                    msg.obj = jsonStr;
                } finally {
                    HttpHander.this.sendMessage(msg);
                }
            }
        });

    }

    /**
     * 带上传的map数据请求
     *
     * @param url
     * @param map
     */
    public void okHttpMapPost(Context context, String url, Map<String, Object> map, final int reqFlag) {
        this.context = context;
        url = Netconfig.httpHost + url;
        String json = mapToJson(map);
        OkhttpTool.getOkhttpTool().uploadJson(url, json, new okhttp3.Callback() {
            @Override
            public void onFailure(okhttp3.Call call, IOException e) {
                Message msg = HttpHander.this.obtainMessage(ERROR_FAIL);
                msg.arg1 = reqFlag;
                HttpHander.this.sendMessage(msg);
            }

            @Override
            public void onResponse(okhttp3.Call call, okhttp3.Response response) throws IOException {
                Message msg = HttpHander.this.obtainMessage(REQ_OK);
                msg.arg1 = reqFlag;
                String jsonStr = new String(response.body().bytes());//把原始数据转为字符串
                try {
                    msg.obj = StringUtils.decodeUnicode(jsonStr); //转换
                } catch (Exception e) {
                    msg.obj = jsonStr;
                } finally {
                    HttpHander.this.sendMessage(msg);
                }
            }
        });
    }



    //map to json
    public String mapToJson(Map<String, Object> map) {
        JSONObject jsonObject = (JSONObject) JSONObject.toJSON(map);
        return jsonObject.toString();
    }


    //返回字符串Map解析数据集合
    public Map<String, String> getStringMap(Map<String, Object> map, String... strings) {
        Map<String, String> result = new HashMap<>();
        for (int i = 0; i < strings.length; i++) {
            String value = "";
            if (map.get(strings[i]) != null)
                value = map.get(strings[i]) + "";
            result.put(strings[i], value);
        }
        return result;
    }


    //返回DoubleMap解析数据集合
    public Map<String, Integer> getIntegerMap(Map<String, Object> map, String... strings) {
        Map<String, Integer> result = new HashMap<>();
        for (int i = 0; i < strings.length; i++) {
            double value = 0;
            try {
                if (map.get(strings[i]) != null) {
                    value = Double.parseDouble(map.get(strings[i]) + "");
                    value = NumberUtils.getTwoDouble(value);
                }
            } catch (Exception e) {
                value = 0;
            } finally {
                result.put(strings[i], (int) value);
            }
        }
        return result;
    }

    //返回DoubleMap解析数据集合
    public Map<String, Long> getLongMap(Map<String, Object> map, String... strings) {
        Map<String, Long> result = new HashMap<>();
        for (int i = 0; i < strings.length; i++) {
            long value = 0L;
            try {
                if (map.get(strings[i]) != null) {
                    value = Long.valueOf(map.get(map.get(strings[i])) + "");
                }
            } catch (Exception e) {

            } finally {
                result.put(strings[i], value);
            }
        }
        return result;
    }

    //返回字符串数据
    public String getString(Map<String, Object> map, String code) {
        String value = "";
        if (map.get(code) != null)
            value = map.get(code) + "";
        return value;
    }

    //返回Double数据
    public double getDouble(Map<String, Object> map, String code) {
        double value = 0;
        if (map.get(code) != null) {
            try {
                value = Double.parseDouble(map.get(code) + "");
                value = NumberUtils.getTwoDouble(value);
            } catch (Exception e) {
                return 0;
            }
        }
        return value;
    }

    //返回List数据
    public ArrayList<Object> getList(Map<String, Object> map, String code) {
        ArrayList<Object> value = new ArrayList<>();
        if (map.get(code) != null)
            value = (ArrayList<Object>) map.get(code);
        return value;
    }

    //返回Map数据
    public Map<String, Object> getMap(Map<String, Object> map, String code) {
        Map<String, Object> value = new HashMap<>();
        if (map.get(code) != null)
            try {
                value = (Map<String, Object>) map.get(code);
            } catch (Exception e) {
                return value;
            }

        return value;
    }

    public static String delHTMLTag(String htmlStr) {
        //定义script的正则表达式
        String scriptRegex = "<script[^>]*?>[\\s\\S]*?<\\/script>";
        //定义style的正则表达式
        String styleRegex = "<style[^>]*?>[\\s\\S]*?<\\/style>";
        //定义HTML标签的正则表达式，去除标签，只提取文字内容
        String htmlRegex = "<[^>]+>";
        //定义空格,回车,换行符,制表符
        String spaceRegex = "\\s*|\t|\r|\n|";

        //此处由于java代码将HTML标签的<>进行了转义 所以先给他还原
        htmlStr = htmlStr.replaceAll("&lt;", "<");
        htmlStr = htmlStr.replaceAll("&gt;", ">");
        // 过滤script标签
        htmlStr = htmlStr.replaceAll(scriptRegex, "");
        // 过滤style标签
        htmlStr = htmlStr.replaceAll(styleRegex, "");
        // 过滤html标签
        htmlStr = htmlStr.replaceAll(htmlRegex, "");
        // 过滤空格等
        htmlStr = htmlStr.replaceAll(spaceRegex, "");
        //下面去掉一些java转义的特殊符号
        // &nbsp;
        htmlStr = htmlStr.replaceAll("&nbsp;", "");
        // nbsp;
        htmlStr = htmlStr.replaceAll("nbsp;", "");
        // &amp;
        htmlStr = htmlStr.replaceAll("&amp;", "");
//         htmlStr = htmlStr.replace("\\","");
//        System.out.println("请求json:——："+htmlStr);
        return htmlStr; // 返回文本字符串

    }

}

