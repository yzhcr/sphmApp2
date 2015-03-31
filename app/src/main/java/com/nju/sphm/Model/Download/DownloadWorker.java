package com.nju.sphm.Model.Download;

import android.content.Context;
import android.os.Handler;

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
    public boolean download(String path, int year, Handler handler){
        try {
            String fatherPath = "";
            String[] l = path.split("/");
            for(int i=0;i<l.length-1;i++){
                fatherPath = fatherPath+l[i]+"/";
            }
            String name = l[l.length-1];
            //System.out.println(fatherPath);
            ArrayList<OrganizationBean> organizationList = organizationHelper.getOrganizationList(fatherPath, year, handler);
            OrganizationBean o = new OrganizationBean();
            for(OrganizationBean organizationBean : organizationList){
                if(organizationBean.getName().equals(name)){
                    //System.out.println(organizationBean.getName());
                    o = organizationBean;
                    break;
                }
            }
            o.setChildren(organizationHelper.getOrganizationList(path, year, handler));
            ArrayList<StudentBean> studentList = new ArrayList<StudentBean>();
            ArrayList<ClassBean> classList = studentHelper.getClassList(path, year, handler);
            ArrayList<TestFileBean> testFileList = testFileHelper.getTestFileList( year, handler);
            dbm.addTestFiles(testFileList);
            dbm.addOrganizations(organizationList);
            dbm.addOrganization(o);
            for (ClassBean cb : classList) {
                dbm.addStudents(cb.getStudents());
            }
            for (TestFileBean tfb : testFileList) {
                System.out.println(tfb.getFileName());
                ArrayList<TestFileRowBean> testFileRowBeanList = testFileHelper.getTestFileRowList(tfb.get_id(), handler);
                dbm.addTestFileRows(testFileRowBeanList);
            }
        } catch (Exception e){
            e.printStackTrace();
            return false;
        }
        return true;
    }
}
