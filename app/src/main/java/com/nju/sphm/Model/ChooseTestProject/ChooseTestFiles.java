package com.nju.sphm.Model.ChooseTestProject;

import com.nju.sphm.Bean.TestFileBean;

import java.util.ArrayList;

/**
 * Created by hcr1 on 2015/2/14.
 */
public class ChooseTestFiles {
    ArrayList<TestFileBean> testFileList;
    private ChooseTestFiles(){
    }

    private static ChooseTestFiles instance=null;

    public static ChooseTestFiles getInstance(){
        if(instance==null){
            instance=new ChooseTestFiles();
        }
        return instance;
    }

    public void setTestFileList(ArrayList<TestFileBean> testFileList){
        this.testFileList=testFileList;
    }

    public ArrayList<TestFileBean> getTestFileList() {
        return testFileList;
    }
}
