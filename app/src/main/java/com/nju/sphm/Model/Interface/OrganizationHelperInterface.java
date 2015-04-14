package com.nju.sphm.Model.Interface;

import android.os.Handler;

import com.nju.sphm.Bean.OrganizationBean;

import java.util.ArrayList;

/**
 * Created by HuangQiushuo on 2015/4/13.
 */
public interface OrganizationHelperInterface {
    public ArrayList<OrganizationBean> getOrganizationList(String path, int schoolYear, Handler handler);
}
