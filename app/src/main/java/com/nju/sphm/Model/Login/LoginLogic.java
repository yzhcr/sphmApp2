package com.nju.sphm.Model.Login;

import com.nju.sphm.Model.DataHelper.LoginHelper;
import com.nju.sphm.Model.Interface.LoginLogicInterface;

/**
 * Created by hcr1 on 2015/1/12.
 */
public class LoginLogic implements LoginLogicInterface {
    LoginHelper loginHelper =new LoginHelper();

    public boolean login(String username,String password,String path){
        boolean infoIsTrue=loginHelper.login(username,password,path);
        return infoIsTrue;
    }

}
