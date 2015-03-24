package com.nju.sphm.Model.DataHelper;

import android.os.Handler;

import com.google.gson.Gson;
import com.nju.sphm.Bean.OrganizationBean;
import com.nju.sphm.Bean.OrganizationListBean;

import java.util.ArrayList;

/**
 * Created by HuangQiushuo on 2015/1/21.
 */
public class OrganizationHelper {
    private NetWorkHelper networkHelper = NetWorkHelper.getInstance();
    private String urlHead = networkHelper.getIp()+"/api/organization";
    private String yearHead = "?schoolYear=";


    public ArrayList<OrganizationBean> getOrganizationList(String path, int schoolYear, Handler handler) {
        ArrayList<OrganizationBean> beanList = new ArrayList<OrganizationBean>();
        String returnString = networkHelper.requestDataByGet(urlHead + path + yearHead + schoolYear, handler, "组织结构");
        if(returnString != null){
            Gson gson = new Gson();
            OrganizationListBean organizationListBean = gson.fromJson(returnString, OrganizationListBean.class);
            if(organizationListBean.getStatus())
                beanList = organizationListBean.getData();
        }
        return beanList;
    }
}
