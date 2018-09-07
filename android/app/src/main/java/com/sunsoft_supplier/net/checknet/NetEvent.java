package com.sunsoft_supplier.net.checknet;

/**
 * Created by MJX on 2016/8/9.
 */
public class NetEvent {
    private boolean mNetFlag;
    public NetEvent(boolean netFlag){
        mNetFlag = netFlag;
    }
    public boolean getNetFlag(){
        return mNetFlag;
    }
}
