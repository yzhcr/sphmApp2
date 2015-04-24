package com.nju.sphm.Model.UIHelper;

import com.nju.sphm.Bean.TestFileBean;

import java.util.ArrayList;

/**
 * Created by hcr1 on 2015/2/14.
 */
public class ChooseTestFilesHelper {
    ArrayList<TestFileBean> testFileList;
    private int chosenTestFile=0;
    private ChooseTestFilesHelper(){
    }

    private static ChooseTestFilesHelper instance=null;

    public static ChooseTestFilesHelper getInstance(){
        if(instance==null){
            instance=new ChooseTestFilesHelper();
        }
        return instance;
    }

    public void setTestFileList(ArrayList<TestFileBean> testFileList){
        this.testFileList=testFileList;
    }

    public ArrayList<TestFileBean> getTestFileList() {
        return testFileList;
    }

    public int getChosenTestFile() {
        return chosenTestFile;
    }

    public void setChosenTestFile(int chosenTestFile) {
        this.chosenTestFile = chosenTestFile;
    }

    public String getChosenTestFileId(){
        return testFileList.get(chosenTestFile).get_id();
    }

    public String getChosenTestFileName(){
        return testFileList.get(chosenTestFile).getFileName();
    }

    public int getSchoolYear(){
        return testFileList.get(chosenTestFile).getSchoolYear();
    }

    public String getType(){
        return testFileList.get(chosenTestFile).getType();
    }

    public String getSchoolId(){
        return testFileList.get(chosenTestFile).getOrganization();
    }

}
