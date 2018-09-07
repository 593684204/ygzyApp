package com.sunsoft_supplier.net.network;

/**
 * 请求数据的二次回调接口
 * Created by MJX on 2017/1/9.
 */
public interface ISecondaryCallBackData<T>{
    void  OnSuccess(String tag, T result);
    void OnError(String tag, String error);
}
