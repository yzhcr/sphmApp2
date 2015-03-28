package com.nju.sphm.Model.DataHelper;

import android.app.Activity;
import android.webkit.JavascriptInterface;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.google.gson.Gson;
import com.nju.sphm.Bean.ScoreBean;
import com.nju.sphm.Bean.StudentBean;
import com.nju.sphm.Bean.UploadDataBean;
import com.nju.sphm.Model.UIHelper.ChooseTestFiles;

import java.util.HashMap;

/**
 * Created by hcr1 on 2015/3/28.
 */
public class WebViewHelper {
    WebView webView;
    DBManager dbManager;
    StudentBean studentBean;
    ScoreBean scoreBean;
    String testName;
    String score;
    public WebViewHelper(Activity activity){
        webView=new WebView(activity);
        dbManager=new DBManager(activity);
    }

    public void countScore(final String score,final String testName,final String grade,final StudentBean studentBean){
        this.studentBean=studentBean;
        this.testName=testName;
        this.score=score;
        webView.loadUrl("file:///android_asset/gb2014.html");
        WebSettings webSettings = webView.getSettings();
        webSettings.setJavaScriptEnabled(true);
        webView.addJavascriptInterface(this, "wst");
        //webView.loadUrl("javascript:getGB2014Score(\"100\", \"坐位体前屈\", \"五年级\", \"男生\")");
        WebViewClient wvc = new WebViewClient() {
            @Override
            public void onPageFinished(WebView view, String url) {
                webView.loadUrl("javascript:getGB2014Score(\""+score+"\", \""+testName+"\", \""+grade+"\", \""+studentBean.getSex()+"\")");
                super.onPageFinished(view, url);
            }
        };
        webView.setWebViewClient(wvc);
    }
    @JavascriptInterface
    public void startFunction(String data) {
        data="{\"info\":"+data+"}";
        Gson gson = new Gson();
        scoreBean = gson.fromJson(data, ScoreBean.class);
        //LinkedTreeMap<String, Object> info=(LinkedTreeMap)scoreBean.getInfo().get("得分");
        //scoreMap=info;
        // data即js的返回值
        saveUpload();
    }

    private void saveUpload(){
        ChooseTestFiles chooseTestFiles=ChooseTestFiles.getInstance();
        String testFileName=chooseTestFiles.getChosenTestFileName();
        int schoolYear=chooseTestFiles.getSchoolYear();
        String type=chooseTestFiles.getType();
        String schoolId=chooseTestFiles.getSchoolId();
        UploadDataBean uploadDataBean=new UploadDataBean();
        uploadDataBean.setFileName(testFileName);
        uploadDataBean.setSchoolYear(schoolYear);
        uploadDataBean.setType(type);
        uploadDataBean.setOrganizationID(schoolId);
        HashMap<String, Object> info=studentBean.getTestFileRow().getInfo();
        if(testName.equals("BMI")){
            info.put(testName,score);
            info.put(testName+"_得分",scoreBean.getInfo().get("得分"));
        }
        else{
            info.put(testName+"_得分",scoreBean.getInfo().get("得分"));
            info.put(testName+"_上等级",scoreBean.getInfo().get("上等级"));
        }
        studentBean.getTestFileRow().setInfo(info);
        dbManager.addTestFileRow(studentBean.getTestFileRow());
        //System.out.println(scoreBean.getInfo().get("得分"));
        //System.out.println(scoreBean.getInfo().get("上等级"));
        //info.put(testName+"_得分",scoreBean.getInfo().get("得分"));
        //info.put(testName+"_上等级",scoreBean.getInfo().get("上等级"));
        uploadDataBean.addItem(studentBean.getStudentCode(), studentBean.getTestFileRow().getInfo());
        dbManager.addUploadData(uploadDataBean);
    }
}
