package com.example.applibrary.httpUtils;

public enum ErrorEnum {

    ERROR_10001(10001, "连接失败"),
    ERROR_10002(10002, "返回结果为空"),
    ERROR_10003(10003, "解析结果为空"),
    ERROR_10004(10004, "解析异常"),
    ERROR_10005(10005, "返回结果中没有code"),
    ERROR_10006(10006, "返回结果中没有msg");

    int code;
    String msg;

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    ErrorEnum(int code, String message) {
        this.code = code;
        this.msg = message;
    }
}
