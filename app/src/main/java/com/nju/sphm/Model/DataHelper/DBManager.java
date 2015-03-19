package com.nju.sphm.Model.DataHelper;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.nju.sphm.Bean.OrganizationBean;
import com.nju.sphm.Bean.StudentBean;
import com.nju.sphm.Bean.TestFileBean;
import com.nju.sphm.Bean.TestFileRowBean;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by HuangQiushuo on 2015/1/22.
 */
public class DBManager {
    private DBHelper helper;
    private SQLiteDatabase db;

    public DBManager(Context context) {
        helper = new DBHelper(context);
        //因为getWritableDatabase内部调用了mContext.openOrCreateDatabase(mName, 0, mFactory);
        //所以要确保context已初始化,我们可以把实例化DBManager的步骤放在Activity的onCreate里
        db = helper.getWritableDatabase();
    }

    public void addStudents(List<StudentBean> studentList){
        db.beginTransaction();  //开始事务
        try {
            for (StudentBean student : studentList) {
                db.execSQL("REPLACE INTO students VALUES(?, ?, ?, ?, ?)", new Object[]{student.get_id(), student.get_v(),
                        student.getOrganization(), student.getStudentCode(), student.getInfoJSON()});
            }
            db.setTransactionSuccessful();  //设置事务成功完成
        }catch (Exception e) {
            e.printStackTrace();
        }finally
        {
            db.endTransaction();    //结束事务
        }
    }

    public ArrayList<StudentBean> getStudents(String organizationID, String testFileID){
        ArrayList<StudentBean> list = new ArrayList<StudentBean>();
        String[] params = {organizationID};
        Cursor c = db.rawQuery("SELECT * FROM students WHERE organizationID=?", params);
        while (c.moveToNext()) {
            StudentBean bean = new StudentBean();
            bean.set_id(c.getString(c.getColumnIndex("_id")));
            bean.set_v(c.getInt(c.getColumnIndex("_v")));
            bean.setInfoJSON(c.getString(c.getColumnIndex("info")));
            bean.setOrganization(c.getString(c.getColumnIndex("organizationID")));
            bean.setStudentCode(c.getString(c.getColumnIndex("studentCode")));
            TestFileRowBean tfrb = getTestFileRow(bean.getStudentCode(), testFileID);
            if(tfrb != null){
                bean.setTestFileRow(tfrb);
            }
            list.add(bean);
        }
        c.close();
        return list;
    }



    public void addTestFiles(List<TestFileBean> list){
        db.beginTransaction();  //开始事务
        try {
            for (TestFileBean bean : list) {
                db.execSQL("REPLACE INTO testfiles VALUES(?, ?, ?, ?, ?, ?, ?)", new Object[]{bean.get_id(), bean.get_v(),
                        bean.getFileName(), bean.getUser(), bean.getOrganization(), bean.getType(), bean.getSchoolYear()});
            }
            db.setTransactionSuccessful();  //设置事务成功完成
        }catch (Exception e) {
            e.printStackTrace();
        }finally
        {
            db.endTransaction();    //结束事务
        }
    }

    public ArrayList<TestFileBean> getTestFiles(String organizationID){
        ArrayList<TestFileBean> list = new ArrayList<TestFileBean>();
        String[] params = {organizationID};
        Cursor c = db.rawQuery("SELECT * FROM testfiles WHERE organizationID=?", params);
        while (c.moveToNext()) {
            TestFileBean bean = new TestFileBean();
            bean.set_id(c.getString(c.getColumnIndex("_id")));
            bean.set_v(c.getInt(c.getColumnIndex("_v")));
            bean.setFileName(c.getString(c.getColumnIndex("fileName")));
            bean.setOrganization(c.getString(c.getColumnIndex("organizationID")));
            bean.setType(c.getString(c.getColumnIndex("type")));
            bean.setUser(c.getString(c.getColumnIndex("userID")));
            bean.setSchoolYear(c.getInt(c.getColumnIndex("schoolYear")));
            list.add(bean);
        }
        c.close();
        return list;
    }

    public void addTestFileRows(List<TestFileRowBean> list){
        db.beginTransaction();  //开始事务
        try {
            for (TestFileRowBean bean : list) {
                db.execSQL("REPLACE INTO testfilerows VALUES(?, ?, ?, ?, ?)", new Object[]{bean.get_id(), bean.get_v(),
                        bean.getTestfile(), bean.getStudentCode(), bean.getInfoJSON()});
            }
            db.setTransactionSuccessful();  //设置事务成功完成
        }catch (Exception e) {
            e.printStackTrace();
        }finally
        {
            db.endTransaction();    //结束事务
        }
    }

    public TestFileRowBean getTestFileRow(String studentCode, String testFileID){
        TestFileRowBean bean = null;
        String[] params = {studentCode, testFileID};
        Cursor c = db.rawQuery("SELECT * FROM testfilerows WHERE studentCode=? AND testfileID=?", params);
        while (c.moveToNext()) {
            bean = new TestFileRowBean();
            bean.set_id(c.getString(c.getColumnIndex("_id")));
            bean.set_v(c.getInt(c.getColumnIndex("_v")));
            bean.setInfoJSON(c.getString(c.getColumnIndex("info")));
            bean.setTestfile(c.getString(c.getColumnIndex("testfileID")));
            bean.setStudentCode(c.getString(c.getColumnIndex("studentCode")));
        }
        c.close();
        return bean;
    }

    public void addTestFileRow(TestFileRowBean bean){
        db.beginTransaction();  //开始事务
        try {
            db.execSQL("REPLACE INTO testfilerows VALUES(?, ?, ?, ?, ?)", new Object[]{bean.get_id(), bean.get_v(),
                    bean.getTestfile(), bean.getStudentCode(), bean.getInfoJSON()});
            db.setTransactionSuccessful();  //设置事务成功完成
        }catch (Exception e) {
            e.printStackTrace();
        }finally
        {
            db.endTransaction();    //结束事务
        }
    }

    public ArrayList<TestFileRowBean> getTestFileRows(String testFileID){
        ArrayList<TestFileRowBean> list = new ArrayList<TestFileRowBean>();
        String[] params = {testFileID};
        Cursor c = db.rawQuery("SELECT * FROM testfilerows WHERE testfileID=?", params);
        while (c.moveToNext()) {
            TestFileRowBean bean = new TestFileRowBean();
            bean.set_id(c.getString(c.getColumnIndex("_id")));
            bean.set_v(c.getInt(c.getColumnIndex("_v")));
            bean.setInfoJSON(c.getString(c.getColumnIndex("info")));
            bean.setTestfile(c.getString(c.getColumnIndex("testfileID")));
            bean.setStudentCode(c.getString(c.getColumnIndex("studentCode")));
            list.add(bean);
        }
        c.close();
        return list;
    }

    public void addOrganization(OrganizationBean organization){
        db.beginTransaction();  //开始事务

        try {
            String children = "";
            List<OrganizationBean> childrenList = organization.getChildren();
            if(childrenList != null && childrenList.size() > 0) {
                for (OrganizationBean child : childrenList) {
                    children = children + child.get_id() + ";";
                    //addOrganization(child);
                }
                //System.out.println(children);
            }
            db.execSQL("REPLACE INTO organizations VALUES(?, ?, ?, ?, ?, ?, ?, ?, ?)", new Object[]{organization.get_id(),
                    organization.get_v(), organization.getCreateDate(),
                    organization.getFullPath(), organization.getLabel(), organization.getName(),
                    organization.getType(), children, organization.getSchoolYear()});
            addOrganizations(childrenList);
            db.setTransactionSuccessful();  //设置事务成功

        }catch (Exception e) {
            e.printStackTrace();
        }finally
        {
            db.endTransaction();    //结束事务
        }
    }

    public void addOrganizations(List<OrganizationBean> list){
          //开始事务
        try {
            for (OrganizationBean organization : list) {
                String children = "";
                List<OrganizationBean> childrenList = organization.getChildren();
                if(childrenList != null && childrenList.size() > 0) {
                    for (OrganizationBean child : childrenList) {
                        children = children + child.get_id() + ";";
                    }
                    addOrganizations(childrenList);
                }

                db.execSQL("REPLACE INTO organizations VALUES(?, ?, ?, ?, ?, ?, ?, ?, ?)", new Object[]{organization.get_id(),
                        organization.get_v(), organization.getCreateDate(),
                        organization.getFullPath(), organization.getLabel(), organization.getName(),
                        organization.getType(), children, organization.getSchoolYear()});
            }
              //设置事务成功完成
        }catch (Exception e) {
            e.printStackTrace();
        }finally
        {
                //结束事务
        }
    }

    public void test(){
        Cursor c = db.rawQuery("SELECT * FROM organizations",null);
        while (c.moveToNext()) {
            System.out.println(c.getString(c.getColumnIndex("fullPath")));
        }
    }

    /**
     * 参数为父节点的id，结果为子节点的集合
     * @param id 父节点的id
     * @return ArrayList<OrganizationBean>
     */
    public ArrayList<OrganizationBean> getOrganizations(String id){
        ArrayList<OrganizationBean> list = new ArrayList<OrganizationBean>();
        if(id == null || id.equals(""))
            return list;
        String[] params = {id};
        Cursor c = db.rawQuery("SELECT * FROM organizations WHERE _id=?", params);
        c.moveToNext();
        String tmp = c.getString(c.getColumnIndex("childrens"));
        String[] childrenIDList = tmp.split(";");
        for(String childID : childrenIDList) {
            if(childID==null||childID.equals("")){
                continue;
            }
            OrganizationBean bean = new OrganizationBean();
            String[] searchId = {childID};
            Cursor t = db.rawQuery("SELECT * FROM organizations WHERE _id=?", searchId);
            t.moveToNext();
            bean.set_id(t.getString(t.getColumnIndex("_id")));
            bean.set_v(t.getInt(t.getColumnIndex("_v")));
            bean.setSchoolYear(t.getInt(t.getColumnIndex("schoolYear")));
            bean.setType(t.getString(t.getColumnIndex("type")));
            bean.setCreateDate(t.getString(t.getColumnIndex("createDate")));
            bean.setFullPath(t.getString(t.getColumnIndex("fullPath")));
            bean.setLabel(t.getString(t.getColumnIndex("label")));
            bean.setName(t.getString(t.getColumnIndex("name")));
            bean.setChildren(getOrganizations(childID));
            list.add(bean);
            t.close();
        }
        c.close();
        return list;
    }

}
