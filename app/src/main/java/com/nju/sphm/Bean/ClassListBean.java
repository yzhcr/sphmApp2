package com.nju.sphm.Bean;

import java.util.ArrayList;

/**
 * Created by HuangQiushuo on 2015/1/20.
 */
public class ClassListBean {
    private boolean status = true;
    private ArrayList<ClassBean> data = new ArrayList<ClassBean>();

    public boolean getStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    public ArrayList<ClassBean> getData() {
        return data;
    }

    public void setData(ArrayList<ClassBean> data) {
        this.data = data;
    }
}
