package com.nju.sphm.Bean;

import java.util.ArrayList;

/**
 * Created by HuangQiushuo on 2015/1/21.
 */
public class OrganizationListBean {
    private boolean status = true;
    private ArrayList<OrganizationBean> data = new ArrayList<OrganizationBean>();

    public boolean getStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    public ArrayList<OrganizationBean> getData() {
        return data;
    }

    public void setData(ArrayList<OrganizationBean> data) {
        this.data = data;
    }
}
