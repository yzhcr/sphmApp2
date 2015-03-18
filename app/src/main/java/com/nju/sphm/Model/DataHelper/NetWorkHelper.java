package com.nju.sphm.Model.DataHelper;

import android.app.Activity;
import android.net.ConnectivityManager;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by HuangQiushuo on 2015/1/22.
 */
public class NetWorkHelper {
    private NetWorkHelper(){}
    private static NetWorkHelper instance=null;
    private static String sessionid = null;
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
            if(sessionid != null) {
                System.out.println(sessionid);
                request.addHeader("Cookie", sessionid);
            }
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

    public String postDataByGet(String accessURL,String json){
        String result=null;
        try{
            // Configure and open a connection to the site you will send the request
            //System.out.println(path);
            URL url = new URL(accessURL);
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
            result = sb.toString();

            String cookieval = urlConnection.getHeaderField("set-cookie");
            if(cookieval != null) {
                sessionid = cookieval.substring(0, cookieval.indexOf(";"));
            }

        }catch(IOException e){
            e.printStackTrace();
        }
        return  result;
    }

    public boolean hasWifi(Activity activity){
        ConnectivityManager con=(ConnectivityManager)activity.getSystemService(Activity.CONNECTIVITY_SERVICE);
        boolean wifi=con.getNetworkInfo(ConnectivityManager.TYPE_WIFI).isConnectedOrConnecting();
        return wifi;
    }

    public boolean hasInternet(Activity activity){
        ConnectivityManager con=(ConnectivityManager)activity.getSystemService(Activity.CONNECTIVITY_SERVICE);
        boolean internet=con.getNetworkInfo(ConnectivityManager.TYPE_MOBILE).isConnectedOrConnecting();
        return internet;
    }
}
