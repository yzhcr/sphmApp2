package com.nju.sphm.Model.DataHelper;

import com.nju.sphm.Bean.Users;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.net.URL;
import java.net.URLConnection;

/**
 * Created by hcr1 on 2015/1/12.
 */
public class LoginHelper {
    private NetWorkHelper networkHelper = NetWorkHelper.getInstance();
    public boolean login(Users user,String path){
        String LoginURL=networkHelper.getIp()+"/api/user/login"+path;
        try{
            // Configure and open a connection to the site you will send the request
            URL url = new URL(LoginURL);
            URLConnection urlConnection = url.openConnection();
            // 设置doOutput属性为true表示将使用此urlConnection写入数据
            urlConnection.setDoOutput(true);
            // 定义待写入数据的内容类型，我们设置为application/x-www-form-urlencoded类型
            urlConnection.setRequestProperty("content-type", "application/x-java-serialized-object");
            // 得到请求的输出流对象
            OutputStream outStrm = urlConnection.getOutputStream();

            ObjectOutputStream oos = new ObjectOutputStream(outStrm);

            oos.writeObject(user);

            oos.flush();

            oos.close();

            // 从服务器读取响应
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
            System.out.println(result);
        }catch(IOException e){
            e.printStackTrace();
        }
        return false;
    }
}
