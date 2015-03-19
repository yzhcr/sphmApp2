package com.nju.sphm.Bean;

import java.util.ArrayList;

/**
 * Created by hcr1 on 2015/3/16.
 */
public class UserListBean {
    private ArrayList<UserBean> data = new ArrayList<UserBean>();
    private boolean status = true;

    public boolean getStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    public ArrayList<UserBean> getData() {
        return data;
    }

    public void setData(ArrayList<UserBean> data) {
        this.data = data;
    }
}
