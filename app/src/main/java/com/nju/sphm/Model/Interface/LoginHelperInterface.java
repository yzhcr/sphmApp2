package com.nju.sphm.Model.Interface;

import com.nju.sphm.Bean.UserBean;

import java.util.ArrayList;

/**
 * Created by hcr1 on 2015/4/24.
 */
public interface LoginHelperInterface {
    public boolean login(String username,String password,String path);
    public ArrayList<UserBean> getUserId(String path);

}
