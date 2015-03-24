package com.nju.sphm.Model.DataHelper;

import android.os.Handler;

import com.google.gson.Gson;
import com.nju.sphm.Bean.TestFileBean;
import com.nju.sphm.Bean.TestFileListBean;
import com.nju.sphm.Bean.TestFileRowBean;
import com.nju.sphm.Bean.TestFileRowListBean;

import java.util.ArrayList;

/**
 * Created by HuangQiushuo on 2015/1/21.
 */
public class TestFileHelper {
    private NetWorkHelper networkHelper = NetWorkHelper.getInstance();
    private String testFileHead = networkHelper.getIp()+"/api/testfile/user/?";
    private String yearHead = "schoolYear=";
    private String testFileRowHead = networkHelper.getIp()+"/api/testfile/";

    public ArrayList<TestFileRowBean> getTestFileRowList(String testFileId, Handler handler){
        ArrayList<TestFileRowBean> testFileRowBeanList = new ArrayList<TestFileRowBean>();
        String returnString = networkHelper.requestDataByGet(testFileRowHead + testFileId, handler, "学生体测数据");
        Gson gson = new Gson();
        if(returnString != null) {
            TestFileRowListBean testFileRowListBean = gson.fromJson(returnString, TestFileRowListBean.class);
            if (testFileRowListBean.getStatus())
                testFileRowBeanList = testFileRowListBean.getData();
        }
        return testFileRowBeanList;
    }

    public ArrayList<TestFileBean> getTestFileList(String userId, int schoolYear, Handler handler) {
        ArrayList<TestFileBean> testFileBeanList = new ArrayList<TestFileBean>();
        String returnString = networkHelper.requestDataByGet(testFileHead + yearHead + schoolYear, handler, "体测数据目录");
        if(returnString != null){
            Gson gson = new Gson();
            TestFileListBean testFileListBean = gson.fromJson(returnString, TestFileListBean.class);
            if(testFileListBean.getStatus())
                testFileBeanList = testFileListBean.getData();
        }
        return testFileBeanList;
    }
}

