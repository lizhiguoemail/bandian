package com.lhsz.bandian.DTO.page;


import java.util.Locale;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Component
public class ResponseResult {
    public static ResponseResult response = new ResponseResult();

    private  ResultCode result;

    private  String code;		//自定义编号

    private  String message;

    private  Object data;

    private OtherMessage otherMessage;

    @Autowired
    private MessageSource messageSource;

    @JsonIgnore
    public  Locale locale = LocaleContextHolder.getLocale();


    public ResponseResult() {

    }

    public static ResponseResult builder(String s, String 微博授权失败) {
        return response;
    }

    @PostConstruct
    public void init() {
        response = this;
        response.messageSource = this.messageSource;
    }

    public static ResponseResult ok() {
        response.clean();
        response.setCode(ResultCode.SUCCESS.getCode());
        response.setMessage(ResultCode.SUCCESS.getMessage());
        response.setResult(ResultCode.SUCCESS);
        return response;
    }

    public static ResponseResult error() {
        response.clean();
        response.setCode(ResultCode.ERROR.getCode());
        response.setMessage(ResultCode.ERROR.getMessage());
        response.setResult(ResultCode.ERROR);
        return response;
    }

    /**
     * 实例化
     * @param code
     * @param message
     * @param data
     * @return
     */
    public ResponseResult render(int code,String message,Object data) {
        return render(String.valueOf(code),message,data);
    }

    /**
     * 实例化
     * @param code
     * @param message
     * @param data
     * @return
     */
    public ResponseResult render(String code,String message,Object data) {
        ResultCode _result = response.result;
        clean();
        response.setResult(_result);
        response.setCode(code);
        response.setMessage(message);
        response.setData(data);
        return response;
    }

    /**
     * 实例化
     * @param code
     * @param message
     * @return
     */
    public ResponseResult render(String code,String message) {
        ResultCode _result = response.result;
        clean();
        response.setResult(_result);
        response.setCode(code);
        response.setMessage(message);
        return response;
    }

    public ResponseResult render(int code,String message) {
        return render(String.valueOf(code),message);
    }

    /**
     * 实例化
     * @param code
     * @param data
     * @return
     */
    public ResponseResult render(String code,Object data) {
        ResultCode _result = response.result;
        clean();
        response.setResult(_result);
        response.setCode(code);
        try {
            response.setMessage(messageSource.getMessage(code, null, locale));
        }catch (Exception e){
            response.setMessage(null);
        }
        response.setData(data);
        return response;
    }

    public ResponseResult render(int code,Object data) {
        return render(String.valueOf(code),data);
    }

    /**
     * 实例化
     * @param code
     * @return
     */
    public ResponseResult render(String code) {
        ResultCode _result = response.result;
        clean();
        response.setResult(_result);
        response.setCode(code);
        try {
            response.setMessage(messageSource.getMessage(code, null, locale));
        }catch (Exception e){
            response.setMessage(null);
        }
        return response;
    }

    /**
     * 实例化
     * @param message
     * @return
     */
    public ResponseResult renderOnlyMessage(String message) {
        ResultCode _result = response.result;
        clean();
        response.setResult(_result);
        response.setMessage(message);
        return response;
    }

    public ResponseResult render(int code) {
        return render(String.valueOf(code));
    }

    /**
     * 实例化
     * @param data
     * @return
     */
    public ResponseResult render(Object data) {
        ResultCode _result = response.result;
        clean();
        if(data!=null) {
            response.setCode(ResultCode.SUCCESS.getCode());
            response.setMessage(ResultCode.SUCCESS.getMessage());
        }
        else{
            response.setCode(ResultCode.ERROR.getCode());
            response.setMessage(ResultCode.ERROR.getMessage());
        }
        response.setResult(_result);
        response.setData(data);
        return response;
    }

    /**
     * 实例化
     * @param result
     * @param code
     * @param message
     * @param obj
     * @return
     */
    public ResponseResult render(ResultCode result, String code,String message,Object obj) {
        clean();
        response.setResult(result);
        response.setCode(code);
        try {
            response.setMessage(messageSource.getMessage(code, null, locale));
        }catch (Exception e){
            response.setMessage(null);
        }
        response.setData(obj);
        return response;
    }

    public ResponseResult render(ResultCode result, int code,String message,Object obj) {
        return render(result,String.valueOf(code),message,obj);
    }

    /**
     * 实例化
     * @param result
     * @param message
     * @param obj
     * @return
     */
    public ResponseResult render(ResultCode result, String message, Object obj) {
        clean();
        response.setResult(result);
        if(StringUtils.isEmpty(message)) {
            response.setMessage(result.getMessage());
        }else {
            response.setMessage(message);
        }
        response.setData(obj);
        return response;
    }

    /**
     * 实例化,失败
     *
     * @param result
     * @param message
     * @return
     */
    public  ResponseResult render(ResultCode result, String message) {
        clean();
        response.setResult(result);
        if(StringUtils.isEmpty(message)) {
            response.setMessage(result.getMessage());
        }else {
            response.setMessage(message);
        }
        return response;
    }


    /**
     * 实例化
     *
     * @param result
     * @param obj
     * @return
     */
    public  ResponseResult render(ResultCode result, Object obj) {
        clean();
        response.setResult(result);
        if(StringUtils.isEmpty(message)) {
            response.setMessage(result.getMessage());
        }
        response.setData(obj);
        return response;
    }

    /**
     * 实例化
     *
     * @param otherStr
     * @return
     */
    public  ResponseResult setOther(String otherStr) {
        otherMessage = new OtherMessage();
        otherMessage.setOther(otherStr);
        response.setOtherMessage(otherMessage);
        return response;
    }

    private  void clean() {
        // TODO Auto-generated method stub
        response.setCode(null);
        response.setResult(null);
        response.setData(null);
        response.setMessage(null);
    }

    public  ResultCode getResult() {
        return result;
    }

    public  void setResult(ResultCode result) {
        this.result = result;
    }

    public  Object getData() {
        return data;
    }

    public  void setData(Object data) {
        this.data = data;
    }

    public  String getMessage() {
        return message;
    }

    public  void setMessage(String message) {
        this.message = message;
    }

    public  String getCode() {
        return code;
    }


    public  void setCode(String code) {
        this.code = code;
    }

    public OtherMessage getOtherMessage() {
        return otherMessage;
    }

    public void setOtherMessage(OtherMessage otherMessage) {
        this.otherMessage = otherMessage;
    }
}
