package com.nju.sphm.Model.DataHelper;

import android.app.Activity;
import android.webkit.JavascriptInterface;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.google.gson.Gson;
import com.google.gson.internal.LinkedTreeMap;
import com.nju.sphm.Bean.ScoreBean;

/**
 * Created by hcr1 on 2015/3/28.
 */
public class WebViewHelper {
    WebView webView;
    DBManager dbManager;
    public WebViewHelper(Activity activity){
        webView=new WebView(activity);
        dbManager=new DBManager(activity);
    }

    public void countScore(final String score,final String testName,final String grade,final String sex){
        webView.loadUrl("file:///android_asset/gb2014.html");
        WebSettings webSettings = webView.getSettings();
        webSettings.setJavaScriptEnabled(true);
        webView.addJavascriptInterface(this, "wst");
        //webView.loadUrl("javascript:getGB2014Score(\"100\", \"坐位体前屈\", \"五年级\", \"男生\")");
        WebViewClient wvc = new WebViewClient() {
            @Override
            public void onPageFinished(WebView view, String url) {
                webView.loadUrl("javascript:getGB2014Score(\""+score+"\", \""+testName+"\", \""+grade+"\", \""+sex+"\")");
                super.onPageFinished(view, url);
            }
        };
        webView.setWebViewClient(wvc);
    }
    @JavascriptInterface
    public void startFunction(String data) {
        System.out.println(data);
        data="{\"info\":"+data+"}";
        Gson gson = new Gson();
        ScoreBean scoreBean = gson.fromJson(data, ScoreBean.class);
        LinkedTreeMap<String, Object> info=(LinkedTreeMap)scoreBean.getInfo().get("得分");
        System.out.println(info.get("value"));
        // data即js的返回值
    }
}
