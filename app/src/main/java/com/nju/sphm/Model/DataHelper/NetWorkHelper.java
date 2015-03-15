package com.nju.sphm.Model.DataHelper;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;

import java.io.BufferedReader;
import java.io.InputStreamReader;

/**
 * Created by HuangQiushuo on 2015/1/22.
 */
public class NetWorkHelper {
    private NetWorkHelper(){}
    private static NetWorkHelper instance=null;

    public static NetWorkHelper getInstance(){
        if(instance==null){
            instance=new NetWorkHelper();
        }
        return instance;
    }

    private HttpClient client = new DefaultHttpClient();
    private String ip;
    public String getIp(){
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public String requestDataByGet(String url){
        BufferedReader in = null;
        String result = null;
        try {

            HttpGet request = new HttpGet(url);
            HttpResponse response = client.execute(request);
            in = new BufferedReader(new InputStreamReader(response.getEntity().getContent()));
            StringBuffer sb = new StringBuffer("");
            String line = "";
            while ((line = in.readLine()) != null) {
                sb.append(line);
            }
            in.close();
            result = sb.toString();
        }catch (Exception e){
            e.printStackTrace();
        }
        return result;
    }
}
