package com.nju.sphm.Model.Login;

import com.nju.sphm.Bean.Users;
import com.nju.sphm.Model.DataHelper.LoginHelper;

import java.util.ArrayList;

/**
 * Created by hcr1 on 2015/1/12.
 */
public class Login {
    LoginHelper loginHelper =new LoginHelper();
    ArrayList<Users> userList;
    public boolean isTrue(String schoolid,String user,String password){
        //userList= loginHelper.getUsers();
        /*for(Users u:userList) {
            if(u.getOrganization().get$oid().equals(schoolid)){
                if(u.getUsername().equals(user)&&u.getPassword().equals(password)){
                    return true;
                }
            }
        }*/
        return true;
    }
}
