package com.zjut.shop.response;

import lombok.Data;

import java.io.Serializable;

@Data
public class BaseResult implements Serializable {

    private Object data;

    private Meta meta;

    public BaseResult(){}

    public BaseResult(Meta meta, Object data){
        this.meta = meta;
        this.data = data;
    }

    public BaseResult(String msg,Integer status, Object data){
        this.meta = new Meta(msg, status);
        this.data = data;
    }

    public static BaseResult handlerSuccess(String msg, Object data) {
        BaseResult baseResult = new BaseResult();
        Meta meta = new Meta(msg, 200);
        baseResult.setMeta(meta);
        baseResult.setData(data);
        return baseResult;
    }

}
