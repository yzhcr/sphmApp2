package com.nju.sphm.Model.DataHelper;

import android.app.Activity;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;

import com.nju.sphm.Model.Interface.NetWorkHelperInterface;

import org.apache.http.client.HttpClient;
import org.apache.http.impl.client.DefaultHttpClient;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.CookieHandler;
import java.net.CookieManager;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by HuangQiushuo on 2015/1/22.
 */
public class NetWorkHelper implements NetWorkHelperInterface{
    private NetWorkHelper(){}
    private static NetWorkHelper instance=null;
    private static CookieManager cookieManager;
    public static NetWorkHelper getInstance(){
        if(instance==null){
            instance=new NetWorkHelper();
            initCookie();
        }
        return instance;
    }

    private static void initCookie()
    {
        cookieManager = new CookieManager();
        CookieHandler.setDefault(cookieManager);
    }


    private HttpClient client = new DefaultHttpClient();
    private String ip;
    public String getIp(){
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

//    public String requestDataByGet(String url){
//        BufferedReader in = null;
//        String result = null;
//        try {
//
//            HttpGet request = new HttpGet(url);
//            if(sessionid != null) {
//                System.out.println(sessionid);
//                request.addHeader("Cookie", sessionid);
//            }
//            HttpResponse response = client.execute(request);
//            in = new BufferedReader(new InputStreamReader(response.getEntity().getContent()));
//            StringBuffer sb = new StringBuffer("");
//            String line = "";
//            while ((line = in.readLine()) != null) {
//                sb.append(line);
//            }
//            in.close();
//            result = sb.toString();
//            System.out.println(result);
//        }catch (Exception e){
//            e.printStackTrace();
//        }
//        return result;
//    }

    public String requestDataByGet(String url){
        return requestDataByGet(url,null,null);
    }

    public String requestDataByGet(String urlStr, Handler handler, String fileName){
        String result = null;
        char buffer[] = new char[64];
        int size=1;
        int len=0;
        int hasRead=0;
        int index=0;
        StringBuffer sb = new StringBuffer("");

        Message message;
        try {
            URL url = new URL(urlStr);
            HttpURLConnection connection = (HttpURLConnection)url.openConnection();
            size = connection.getContentLength();
            InputStream inputStream = connection.getInputStream();
            InputStreamReader reader = new InputStreamReader(inputStream, "UTF-8");
            while((len=reader.read(buffer))!=-1){
                String str = new String(buffer, 0, len);
                hasRead+=str.getBytes().length;
                sb.append(str);
                //System.out.println(str);
                if(handler!=null) {
                    index = (int) (hasRead * 100) / size;
                    message = new Message();
                    message.what = 1;
                    Bundle bundle = new Bundle();
                    bundle.putInt("index", index);
                    bundle.putString("fileName", fileName);
                    message.setData(bundle);
                    handler.sendMessage(message);
                }
            }
            System.out.println(size+":"+hasRead);
            result = sb.toString();
            System.out.println(result);
            inputStream.close();
        } catch (Exception e) {

            e.printStackTrace();
        }

        return result;
    }

//    public String requestDataByGet(String urlStr, Handler handler, String fileName){
//        String result = null;
//        byte buffer[] = new byte[0];
//        //char buffer[] = new char[1024];
//        int size=1;
//        int len=0;
//        int hasRead=0;
//        int index=0;
//        StringBuffer sb = new StringBuffer("");
//
//        Message message;
//        try {
//            URL url = new URL(urlStr);
//            HttpURLConnection connection = (HttpURLConnection)url.openConnection();
//            size = connection.getContentLength();
//            InputStream inputStream = connection.getInputStream();
//            InputStreamReader reader = new InputStreamReader(inputStream, "UTF-8");
//            while((len=inputStream.read(buffer))!=-1){
//                hasRead+=len;
//                //String str = new String(buffer,"utf-8");
//                String str = new String(buffer);
//                sb.append(str);
//                //System.out.println(str);
//                if(handler!=null) {
//                    index = (int) (hasRead * 100) / size;
//                    message = new Message();
//                    message.what = 1;
//                    Bundle bundle = new Bundle();
//                    bundle.putInt("index", index);
//                    bundle.putString("fileName", fileName);
//                    message.setData(bundle);
//                    handler.sendMessage(message);
//                }
//                buffer = new byte[inputStream.available()];
//            }
//            result = sb.toString();
//            System.out.println(result);
//            inputStream.close();
//        } catch (Exception e) {
//
//            e.printStackTrace();
//        }
//
//        return result;
//    }

    public String requestDataByPost(String accessURL, String json){
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
            System.out.println(result);
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
