package com.nju.sphm.Model.DataHelper;

import android.os.Handler;

import com.google.gson.Gson;
import com.nju.sphm.Bean.OrganizationBean;
import com.nju.sphm.Bean.OrganizationListBean;

import java.net.URLEncoder;
import java.util.ArrayList;

/**
 * Created by HuangQiushuo on 2015/1/21.
 */
public class OrganizationHelper {
    private NetWorkHelper networkHelper = NetWorkHelper.getInstance();
    private String urlHead = networkHelper.getIp()+"/api/organization";
    private String yearHead = "?schoolYear=";


    public ArrayList<OrganizationBean> getOrganizationList(String path, int schoolYear, Handler handler) {
        String[] pathsplit=path.split("/");
        String newPath="";
        try {
            if(pathsplit.length!=0) {
                for (int i = 0; i < pathsplit.length; i++) {
                    newPath = newPath + URLEncoder.encode(pathsplit[i], "UTF-8") + "/";
                }
                path = newPath;
            }
        }catch(Exception e){
            e.printStackTrace();
        }
        ArrayList<OrganizationBean> beanList = new ArrayList<OrganizationBean>();
        String returnString = networkHelper.requestDataByGet(urlHead + path + yearHead + schoolYear, handler, "组织结构");
        System.out.println(returnString);
        System.out.println(beanList.size());
        if(returnString != null){
            Gson gson = new Gson();
            System.out.println(beanList.size());
            OrganizationListBean organizationListBean = gson.fromJson(returnString, OrganizationListBean.class);
            System.out.println(beanList.size());
            if(organizationListBean.getStatus())
                beanList = organizationListBean.getData();
        }
        System.out.println(beanList.size());

        return beanList;
    }
}
