package com.example.applibrary.httpUtils;

import android.os.Handler;


import java.util.concurrent.Executor;

/**
 * <p>请求结果回调的执行
 * 通过值主线程的Handle在主线程执行子线程请求的回调结果</p>
 *
 * @author chentt 2018/3/14.
 */

public class ExecutorDelivery {

    private final Executor mResponsePoster;

    /**
     * 构造方法
     *
     * @param handler 该线程的Handler对象
     */
    public ExecutorDelivery(final Handler handler) {
        this.mResponsePoster = new Executor() {
            public void execute(Runnable command) {
                handler.post(command);
            }
        };
    }

    /**
     * 在handler的线程回调成功方法
     *
     * @param callback 回调接口
     * @param t        结果实体对象
     * @param <T>      结果实体的类
     */
    public <T> void postSuccess(OnHttpCallback<T> callback, T t) {
        this.mResponsePoster.execute(new ExecutorDelivery.ResponseDeliveryRunnable(0, t, callback));
    }

    /**
     * 在handler的线程回调失败的方法
     *
     * @param errorCode    错误码
     * @param errorMessage 错误信息
     * @param callback     回调接口
     * @param <T>          结果实体类
     */
    public <T> void postError(int errorCode, String errorMessage, OnHttpCallback<T> callback) {
        this.mResponsePoster.execute(new ExecutorDelivery.ResponseDeliveryRunnable(1, errorCode, errorMessage, callback,  null));
    }

    /**
     * 自定义Runnable接空实现类，封装请求结果的回调
     *
     * @param <T> 结果的实体类
     */
    private class ResponseDeliveryRunnable<T> implements Runnable {
        private int code;
        private String message;
        private OnHttpCallback<T> callback;
        private T t;
        private int postCode;

        public ResponseDeliveryRunnable(int postCode, int code, String message, OnHttpCallback<T> callback, T t) {
            this.postCode = postCode;
            this.code = code;
            this.message = message;
            this.callback = callback;
            this.t = t;
        }

        public ResponseDeliveryRunnable(int postCode, T t, OnHttpCallback<T> callback) {
            this.postCode = postCode;
            this.callback = callback;
            this.t = t;
        }

        public void run() {
            if (this.postCode == 0) {
                this.callback.success(this.t);
            } else if (this.postCode == 1) {
                this.callback.error(this.code, this.message);
            }

        }
    }
}
