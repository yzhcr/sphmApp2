package com.nju.sphm.Model.Download;

import android.content.Context;

import com.nju.sphm.Bean.ClassBean;
import com.nju.sphm.Bean.OrganizationBean;
import com.nju.sphm.Bean.StudentBean;
import com.nju.sphm.Bean.TestFileBean;
import com.nju.sphm.Bean.TestFileRowBean;
import com.nju.sphm.Model.DataHelper.DBManager;
import com.nju.sphm.Model.DataHelper.OrganizationHelper;
import com.nju.sphm.Model.DataHelper.StudentHelper;
import com.nju.sphm.Model.DataHelper.TestFileHelper;

import java.util.ArrayList;

/**
 * Created by HuangQiushuo on 2015/2/3.
 */
public class DownloadWorker {
    private OrganizationHelper organizationHelper;
    private StudentHelper studentHelper;
    private TestFileHelper testFileHelper;
    private DBManager dbm;

    public DownloadWorker(Context context){
        organizationHelper = new OrganizationHelper();
        studentHelper = new StudentHelper();
        testFileHelper = new TestFileHelper();
        dbm = new DBManager(context);
    }

    //path是学校的路径，userid是该学校账号的oid
    public boolean download(String path, String userId, int year){
        try {
            ArrayList<OrganizationBean> organizationList = organizationHelper.getOrganizationList(path, year);
            ArrayList<StudentBean> studentList = new ArrayList<StudentBean>();
            ArrayList<ClassBean> classList = studentHelper.getClassList(path, year);
            ArrayList<TestFileBean> testFileList = testFileHelper.getTestFileList(userId, year);
            System.out.println("class+" + classList.size());
            System.out.println("testfile+"+testFileList.size());
            dbm.addTestFiles(testFileList);
            dbm.addOrganizations(organizationList);
            for (ClassBean cb : classList) {
                dbm.addStudents(cb.getStudents());
            }
            for (TestFileBean tfb : testFileList) {
                ArrayList<TestFileRowBean> testFileRowBeanList = testFileHelper.getTestFileRowList(tfb.get_id());
                dbm.addTestFileRows(testFileRowBeanList);
            }
        } catch (Exception e){
            e.printStackTrace();
            return false;
        }
        return true;
    }
}
