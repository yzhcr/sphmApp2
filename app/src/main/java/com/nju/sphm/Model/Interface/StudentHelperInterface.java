package com.nju.sphm.Model.Interface;

import android.os.Handler;

import com.nju.sphm.Bean.ClassBean;

import java.util.ArrayList;

/**
 * Created by HuangQiushuo on 2015/4/13.
 */
public interface StudentHelperInterface {
    public ArrayList<ClassBean> getClassList(String path, int schoolYear, Handler handler);
}
