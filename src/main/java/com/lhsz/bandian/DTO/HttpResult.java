package com.lhsz.bandian.DTO;


/**
 * HTTP结果封装
 * @author Louis
 * @date Jun 29, 2019
 */
public class HttpResult {

    private int code = 1;
    private String msg;
    private Object data;


    public static HttpResult error() {
        return error(HttpStatus.SC_INTERNAL_SERVER_ERROR, "未知异常，请联系管理员");
    }

    public static HttpResult error(String msg) {
        return error(HttpStatus.SC_INTERNAL_SERVER_ERROR, msg);
    }

    public static HttpResult error(int code, String msg) {
        HttpResult r = new HttpResult();
        r.setCode(code);
        r.setMsg(msg);
        return r;
    }

    public static HttpResult ok(String msg) {
        HttpResult r = new HttpResult();
        r.setMsg(msg);
        return r;
    }

    public static HttpResult ok(Object data) {
        HttpResult r = new HttpResult();
        r.setData(data);
        r.setMsg("操作成功");
        return r;
    }

    public static HttpResult ok() {
        HttpResult r = new HttpResult();
        r.setMsg("操作成功");
        return r;
    }
    public static HttpResult succee() {
        HttpResult r = new HttpResult();
        r.setMsg("操作成功");
        r.setCode(HttpStatus.SUCCEE);
        return r;
    }
    public static HttpResult fail() {
        HttpResult r = new HttpResult();
        r.setMsg("操作失败");
        r.setCode(HttpStatus.FAIL);
        return r;
    }
    public static HttpResult succee(String msg) {
        HttpResult r = new HttpResult();
        r.setMsg(msg);
        r.setCode(HttpStatus.SUCCEE);
        return r;
    }
    public static HttpResult fail(String msg) {
        HttpResult r = new HttpResult();
        r.code=0;
        r.setMsg(msg);
        r.setCode(HttpStatus.FAIL);
        return r;
    }

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

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }

}