package com.nju.sphm.Model.DataHelper;

import com.google.gson.Gson;
import com.nju.sphm.Bean.ClassBean;
import com.nju.sphm.Bean.ClassListBean;

import java.util.ArrayList;

/**
 * Created by HuangQiushuo on 2015/1/20.
 */
public class StudentHelper {
    private NetWorkHelper networkHelper = NetWorkHelper.getInstance();
    private String urlHead = networkHelper.getIp()+"/api/student";
    private String yearHead = "?schoolYear=";

    public ArrayList<ClassBean> getClassList(String path, int schoolYear) {
        ArrayList<ClassBean> classBeanList = new ArrayList<ClassBean>();
        String returnString = networkHelper.requestDataByGet(urlHead + path + yearHead + schoolYear);
        if(returnString != null){
            Gson gson = new Gson();
            ClassListBean classListBean = gson.fromJson(returnString, ClassListBean.class);
            if(classListBean.getStatus())
                classBeanList = classListBean.getData();
        }
        return classBeanList;
    }
}


