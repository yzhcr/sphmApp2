package com.nju.sphm.Bean;

import java.util.ArrayList;

/**
 * Created by HuangQiushuo on 2015/1/22.
 */
public class TestFileRowListBean {
    private ArrayList<TestFileRowBean> data = new ArrayList<TestFileRowBean>();
    private boolean status;

    public boolean getStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    public ArrayList<TestFileRowBean> getData() {
        return data;
    }

    public void setData(ArrayList<TestFileRowBean> data) {
        this.data = data;
    }
}
