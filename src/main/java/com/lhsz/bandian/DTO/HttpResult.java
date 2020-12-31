package com.lhsz.bandian.DTO;


import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * HTTP结果封装
 * @author lizhiguo
 * @date 2020-7-16
 */
@ApiModel("接口返回对象")
public class HttpResult {

    @ApiModelProperty(value = "代码")
    private int code = 1;
    @ApiModelProperty(value = "描述")
    private String message;
    @ApiModelProperty(value = "数据对象")
    private Object data;


    public static HttpResult error() {
        return error(HttpStatus.SC_INTERNAL_SERVER_ERROR, "未知异常，请联系管理员");
    }

    public static HttpResult error(String msg) {
        return error(HttpStatus.FAIL, msg);
    }

    public static HttpResult error(int code, String msg) {
        HttpResult r = new HttpResult();
        r.setCode(code);
        r.setMessage(msg);
        return r;
    }

    public static HttpResult ok(String msg) {
        HttpResult r = new HttpResult();
        r.setMessage(msg);
        r.setData(msg);
        return r;
    }

    public static HttpResult ok(Object data) {
        HttpResult r = new HttpResult();
        r.setData(data);
        r.setMessage("操作成功");
        return r;
    }

    public static HttpResult ok() {
        HttpResult r = new HttpResult();
        r.setMessage("操作成功");
        return r;
    }
    public static HttpResult succee() {
        HttpResult r = new HttpResult();
        r.setMessage("操作成功");
        r.setCode(HttpStatus.SUCCEE);
        return r;
    }
    public static HttpResult fail() {
        HttpResult r = new HttpResult();
        r.setMessage("操作失败");
        r.setCode(HttpStatus.FAIL);
        return r;
    }
    public static HttpResult succee(String msg) {
        HttpResult r = new HttpResult();
        r.setMessage(msg);
        r.setCode(HttpStatus.SUCCEE);
        return r;
    }
    public static HttpResult fail(String msg) {
        HttpResult r = new HttpResult();
        r.code=0;
        r.setMessage(msg);
        r.setCode(HttpStatus.FAIL);
        return r;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }

}