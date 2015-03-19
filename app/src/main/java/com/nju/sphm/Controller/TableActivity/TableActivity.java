package com.nju.sphm.Controller.TableActivity;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TableLayout;
import android.widget.TextView;

import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.lidroid.xutils.view.annotation.event.OnClick;
import com.nju.sphm.Bean.OrganizationBean;
import com.nju.sphm.Bean.StudentBean;
import com.nju.sphm.Bean.TestFileRowBean;
import com.nju.sphm.Model.DataHelper.DBManager;
import com.nju.sphm.Model.UIHelper.GetClass;
import com.nju.sphm.Model.UIHelper.TableHelper;
import com.nju.sphm.R;

import java.util.ArrayList;


public class TableActivity extends Activity {
    @ViewInject(R.id.changeClass)
    private Button btn_choose;
    @ViewInject(R.id.choseclass)
    private TextView choseclass;
    /*@ViewInject(R.id.StudentList)
    private ListView lv;*/
    @ViewInject(R.id.title)
    private TextView title;
    @ViewInject(R.id.tablelayout)
    private TableLayout table;
    private DBManager dbManager=null;
    private String schoolid=null;
    private String schoolPath=null;
    private String testProject=null;
    private ArrayList<OrganizationBean> gradeList=null;
    private GetClass getClass=GetClass.getInstance();
    private ArrayList<StudentBean> studentList=null;
    private ArrayList<TestFileRowBean> testFileRowList=null;
    private String tableTitleString;
    @ViewInject(R.id.tabletitle)
    private TableLayout tableTitle;
    TableHelper tableHelper;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_table);

        Intent intent=getIntent();
        schoolid=intent.getStringExtra("schoolid");
        schoolPath=intent.getStringExtra("schoolpath");
        testProject=intent.getStringExtra("testProject");
        String testFileId=intent.getStringExtra("testFileId");
        tableTitleString=intent.getStringExtra("tableTitle");

        ViewUtils.inject(this);
       // lv=(ListView)findViewById(R.id.ListView01);
        dbManager=new DBManager(this);
        testFileRowList=dbManager.getTestFileRows(testFileId);
        title.setText(testProject);
        addClassInfo();
        int choseGrade=getClass.getChoseGrade();
        int choseClass=getClass.getChoseClass();
        choseclass.setText(choseGrade+"年"+choseClass+"班");
        //System.out.println(schoolid);
        getStudentInfo();
        //setTableTitle();
        //setTable();
        tableHelper=new TableHelper();
        tableHelper.setTableTitle(tableTitle, tableTitleString, TableActivity.this);
        if(testProject.equals("BMI"))
            tableHelper.setTable(table, tableTitleString, studentList,"身高","体重",TableActivity.this);
        else
            tableHelper.setTable(table, tableTitleString, studentList,testProject,null,TableActivity.this);
    }

    @OnClick(R.id.changeClass)
    public void showDialog(View v)
    {
        ClassPickerDialog dialog  = new ClassPickerDialog(this);
        dialog.setOnClassSetListener(new ClassPickerDialog.OnClassSetListener() {
            public void OnClassSet(AlertDialog dialog, int choseGrade, int choseClass) {
                choseclass.setText(choseGrade+"年"+choseClass+"班");
                GetClass getClass=GetClass.getInstance();
                getClass.setChoseGrade(choseGrade);
                getClass.setChoseClass(choseClass);
                getStudentInfo();
                if(testProject.equals("BMI"))
                    tableHelper.setTable(table, tableTitleString, studentList,"身高","体重",TableActivity.this);
                else
                    tableHelper.setTable(table, tableTitleString, studentList,testProject,null,TableActivity.this);
            }
        });
        dialog.show();
    }

    private void getStudentInfo(){
        int chosenGrade=getClass.getChoseGrade();
        int chosenClass=getClass.getChoseClass();
        String classID=getClass.findClassId(chosenGrade,chosenClass);
        studentList=dbManager.getStudents(classID, testProject);

    }
    //将班级信息添加到GetCLass中，方便使用
    private void addClassInfo(){

        gradeList=dbManager.getOrganizations(schoolid);
        ArrayList<OrganizationBean> sortGradeList=new ArrayList<OrganizationBean>();
        int gradeNum=gradeList.size();
        if(gradeNum==3){
            getClass.setGradeNumMax(3);
            getClass.setGradeNumMin(1);
            for(OrganizationBean o:gradeList){
                if(o.getName().equals("一年级")){
                    sortGradeList.add(o);
                }
            }
            for(OrganizationBean o:gradeList){
                if(o.getName().equals("二年级")){
                    sortGradeList.add(o);
                }
            }
            for(OrganizationBean o:gradeList){
                if(o.getName().equals("三年级")){
                    sortGradeList.add(o);
                }
            }
        }
        if(gradeNum==6){
            getClass.setGradeNumMax(6);
            getClass.setGradeNumMin(1);
           // getClass.setChoseGrade(1);
            //getClass.setChoseClass(1);
            for(OrganizationBean o:gradeList){
                if(o.getName().equals("一年级")){
                    sortGradeList.add(o);
                }
            }
            for(OrganizationBean o:gradeList){
                if(o.getName().equals("二年级")){
                    sortGradeList.add(o);
                }
            }
            for(OrganizationBean o:gradeList){
                if(o.getName().equals("三年级")){
                    sortGradeList.add(o);
                }
            }
            for(OrganizationBean o:gradeList){
                if(o.getName().equals("四年级")){
                    sortGradeList.add(o);
                }
            }
            for(OrganizationBean o:gradeList){
                if(o.getName().equals("五年级")){
                    sortGradeList.add(o);
                }
            }
            for(OrganizationBean o:gradeList){
                if(o.getName().equals("六年级")){
                    sortGradeList.add(o);
                }
            }
        }

        getClass.setGradeList(sortGradeList);
    }

}
