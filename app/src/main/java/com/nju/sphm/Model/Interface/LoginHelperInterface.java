package com.nju.sphm.Model.Interface;

import com.nju.sphm.Bean.UserBean;

import java.util.ArrayList;

/**
 * Created by HuangQiushuo on 2015/4/13.
 */
public interface LoginHelperInterface {
    public boolean login(String username,String password,String path);
    public ArrayList<UserBean> getUserId(String path);
}
