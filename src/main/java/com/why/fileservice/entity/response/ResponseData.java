package com.why.fileservice.entity.response;

import java.util.Collections;

/**
 * @program: mesense
 * @description: 返回数据包装
 * @author: LiuShuZ
 * @create: 2019-11-08 14:02
 **/
public class ResponseData<T> {

    //code码
    private Integer statusCode;

    private String message;
    //返回的数据
    private T data = (T) Collections.emptyList();


    public Integer getStatusCode() {
        return statusCode;
    }

    private ResponseData<T> setStatusCode(Integer statusCode) {
        this.statusCode = statusCode;
        return this;
    }

    //转json时 如果为null 应该为 ""
    public String getMessage() {
        if (this.message == null) {
            return "";
        }
        return message;
    }

    public ResponseData<T> setMessage(String message) {
        this.message = message;
        return this;
    }

    //message 对象转换成json是 如果data 为null 返回空数组
    public T getData() {
        return data;
    }

    public  ResponseData<T> setData(T data) {
        this.data = data;
        return this;
    }

    /**
     * 默认为成功 避免忘记设置参数 啥都没有
     * 获取实例后应该如果非成功状态应该选取其他实例
     */
    private ResponseData() {

    }

    public static <U> ResponseData<U> success() {
        return new ResponseData<U>().setMessage("success").setStatusCode(UnifiedCode.SUCCESS);
    }

    public static <U> ResponseData<U> success(U u) {
        return new ResponseData<U>().setMessage("success").setStatusCode(UnifiedCode.SUCCESS).setData(u);
    }


    public static <U> ResponseData<U> error(String message) {
        return new ResponseData<U>().setMessage(message).setStatusCode(UnifiedCode.ERROR);
    }

    public static <U> ResponseData<U> of(Integer unifiedCode,U data){
        ResponseData<U> responseData = new ResponseData<>();
        responseData.data = data;
        responseData.statusCode = unifiedCode;
        return responseData;
    }

    public static <U> ResponseData<U> redirect(String message) {
        return new ResponseData<U>().setMessage(message).setStatusCode(UnifiedCode.REDIRECT);
    }

    public static <U> ResponseData<U> permission(String message) {
        return new ResponseData<U>().setMessage(message).setStatusCode(UnifiedCode.PERMISSION);
    }



}
