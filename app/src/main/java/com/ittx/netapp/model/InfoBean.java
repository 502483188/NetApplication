package com.ittx.netapp.model;

import java.util.List;

/**
 * Created by Administrator on 2016/1/5.
 */
public class InfoBean {
    private PageBean pageInfo;

    public PageBean getPageInfo() {
        return pageInfo;
    }

    public void setPageInfo(PageBean pageInfo) {
        this.pageInfo = pageInfo;
    }

    public List<MerchantBean> getMerchantKey() {
        return merchantKey;
    }

    public void setMerchantKey(List<MerchantBean> merchantKey) {
        this.merchantKey = merchantKey;
    }

    private List<MerchantBean> merchantKey;

}
