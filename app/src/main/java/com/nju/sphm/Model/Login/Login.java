package com.nju.sphm.Model.Login;

import com.nju.sphm.Bean.LoginBean;
import com.nju.sphm.Bean.UserBean;
import com.nju.sphm.Model.DataHelper.LoginHelper;

import java.util.ArrayList;

/**
 * Created by hcr1 on 2015/1/12.
 */
public class Login {
    LoginHelper loginHelper =new LoginHelper();

    public LoginBean login(String username,String password,String path){
        LoginBean loginBean=loginHelper.login(username,password,path);
        return loginBean;
    }

    public String getUserID(String userName,String path){
        ArrayList<UserBean> userList=loginHelper.getUserId(path);
        for(UserBean user:userList){
            if(user.getUsername().equals(userName)){
                return user.get_id();
            }
        }
        return null;
    }
}
