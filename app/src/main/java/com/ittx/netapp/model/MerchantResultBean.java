package com.ittx.netapp.model;

/**
 * Created by Administrator on 2016/1/5.
 */
public class MerchantResultBean {
    private String resultInfo;

    public String getResultInfo() {
        return resultInfo;
    }

    public void setResultInfo(String resultInfo) {
        this.resultInfo = resultInfo;
    }

    public int getResultCode() {
        return resultCode;
    }

    public void setResultCode(int resultCode) {
        this.resultCode = resultCode;
    }

    public InfoBean getInfo() {
        return info;
    }

    public void setInfo(InfoBean info) {
        this.info = info;
    }

    private int resultCode;
    private InfoBean info;
}
