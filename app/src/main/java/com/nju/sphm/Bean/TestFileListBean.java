package com.nju.sphm.Bean;

import java.util.ArrayList;

/**
 * Created by HuangQiushuo on 2015/1/21.
 */
public class TestFileListBean {
    private boolean status = true;

    private ArrayList<TestFileBean> data = new ArrayList<TestFileBean>();

    public boolean getStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    public ArrayList<TestFileBean> getData() {
        return data;
    }

    public void setData(ArrayList<TestFileBean> data) {
        this.data = data;
    }
}
