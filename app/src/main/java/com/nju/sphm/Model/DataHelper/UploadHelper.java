package com.nju.sphm.Model.DataHelper;

import com.google.gson.Gson;
import com.nju.sphm.Bean.UploadBean;

/**
 * Created by hcr1 on 2015/3/28.
 */
public class UploadHelper {
    NetWorkHelper netWorkHelper=NetWorkHelper.getInstance();
    public boolean upload(String json){
        String url=netWorkHelper.getIp()+"/api/testfile/";
        String result=netWorkHelper.requestDataByPost(url, json);
        UploadBean uploadBean=null;
        System.out.println(result);
        if(result!=null){
            Gson gson = new Gson();
            uploadBean = gson.fromJson(result, UploadBean.class);
        }
        return uploadBean.isStatus();
    }
}
