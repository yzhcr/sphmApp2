package com.nju.sphm.Model.Interface;

import com.nju.sphm.Bean.OrganizationBean;
import com.nju.sphm.Bean.StudentBean;
import com.nju.sphm.Bean.TestFileBean;
import com.nju.sphm.Bean.TestFileRowBean;
import com.nju.sphm.Bean.UploadDataBean;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by HuangQiushuo on 2015/4/13.
 */
public interface DBManagerInterface {
    public void addStudents(List<StudentBean> studentList);

    public ArrayList<StudentBean> getStudents(String organizationID, String testFileID);

    public void addTestFiles(List<TestFileBean> list);

    public ArrayList<TestFileBean> getTestFiles(String organizationID);

    public void addTestFileRows(List<TestFileRowBean> list);

    public TestFileRowBean getTestFileRow(String studentCode, String testFileID);

    public void addTestFileRow(TestFileRowBean bean);

    public ArrayList<TestFileRowBean> getTestFileRows(String testFileID);

    public void addOrganization(OrganizationBean organization);

    public void addOrganizations(List<OrganizationBean> list);

    public ArrayList<OrganizationBean> getOrganizations(String id);

    public boolean addUploadData(UploadDataBean bean);

    public ArrayList<UploadDataBean> getUploadDatas(String organizationID);

    public boolean cleanUploadDatas();
}
