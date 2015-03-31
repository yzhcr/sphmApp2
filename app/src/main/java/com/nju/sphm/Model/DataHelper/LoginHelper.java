package com.nju.sphm.Model.DataHelper;

import com.google.gson.Gson;
import com.nju.sphm.Bean.LoginBean;
import com.nju.sphm.Bean.UserBean;
import com.nju.sphm.Bean.UserListBean;

import java.io.IOException;
import java.net.URLEncoder;
import java.util.ArrayList;

/**
 * Created by hcr1 on 2015/1/12.
 */
public class LoginHelper {
    private NetWorkHelper networkHelper = NetWorkHelper.getInstance();


    public boolean login(String username,String password,String path){

        String json="{\"username\":\""+username+"\",\"password\":\""+password+"\"}";
        //System.out.println(json);
        LoginBean loginBean=null;
        try{
            // Configure and open a connection to the site you will send the request

            String[] pathsplit=path.split("/");
            String newPath="";
            for(int i=0;i<pathsplit.length;i++){
                newPath=newPath+URLEncoder.encode(pathsplit[i],"UTF-8")+"/";
            }
            String loginURL=networkHelper.getIp()+"/api/user/login"+newPath;
            String result=networkHelper.requestDataByPost(loginURL, json);
            if(result != null){
                Gson gson = new Gson();
                loginBean = gson.fromJson(result, LoginBean.class);
            }
        }catch(IOException e){
            e.printStackTrace();
        }
        return loginBean.isStatus();
    }

    public ArrayList<UserBean> getUserId(String path){
        ArrayList<UserBean> beanList = new ArrayList<UserBean>();
         NetWorkHelper networkHelper = NetWorkHelper.getInstance();
         String urlHead = networkHelper.getIp()+"/api/user";
         String returnString = networkHelper.requestDataByGet(urlHead + path);
        if(returnString != null){
            Gson gson = new Gson();
            UserListBean userListBean = gson.fromJson(returnString, UserListBean.class);
            if(userListBean.getStatus())
                beanList = userListBean.getData();
        }
        return  beanList;
    }
}
