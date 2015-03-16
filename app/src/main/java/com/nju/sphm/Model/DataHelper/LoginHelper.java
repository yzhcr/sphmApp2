package com.nju.sphm.Model.DataHelper;

import com.google.gson.Gson;
import com.nju.sphm.Bean.LoginBean;
import com.nju.sphm.Bean.UserBean;
import com.nju.sphm.Bean.UserListBean;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;

/**
 * Created by hcr1 on 2015/1/12.
 */
public class LoginHelper {
    private NetWorkHelper networkHelper = NetWorkHelper.getInstance();


    public LoginBean login(String username,String password,String path){

        String json="{\"username\":\""+username+"\",\"password\":\""+password+"\"}";
        //System.out.println(json);
        LoginBean loginBean=null;

        try{
            // Configure and open a connection to the site you will send the request
            //System.out.println(path);
            String[] pathsplit=path.split("/");
            String newPath="";
            for(int i=0;i<pathsplit.length;i++){
                newPath=newPath+URLEncoder.encode(pathsplit[i],"UTF-8")+"/";
            }


            String LoginURL=networkHelper.getIp()+"/api/user/login"+newPath;

            URL url = new URL(LoginURL);

            HttpURLConnection urlConnection = (HttpURLConnection)url.openConnection();
            // 设置doOutput属性为true表示将使用此urlConnection写入数据
            urlConnection.setDoOutput(true);
            urlConnection.setDoInput(true);
            urlConnection.setUseCaches(false);
            urlConnection.setRequestMethod("POST"); // 设置请求方式
            urlConnection.setRequestProperty("Accept", "application/json"); // 设置接收数据的格式
            urlConnection.setRequestProperty("Content-Type", "application/json");
            urlConnection.connect();

            OutputStreamWriter out = new OutputStreamWriter(
                    urlConnection.getOutputStream(), "UTF-8");
            out.append(json);
            //System.out.println(json);
            out.flush();
            out.close();

            InputStream inputStream = urlConnection.getInputStream();
            InputStreamReader inputStreamReader=new InputStreamReader(inputStream);
            BufferedReader in=new BufferedReader(inputStreamReader);
            StringBuffer sb = new StringBuffer("");
            String line = "";
            while ((line = in.readLine()) != null) {
                sb.append(line);
            }
            in.close();
            String result = sb.toString();
            //System.out.println(result);
            if(result != null){
                Gson gson = new Gson();
                loginBean = gson.fromJson(result, LoginBean.class);
            }
        }catch(IOException e){
            e.printStackTrace();
        }
        return loginBean;
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
