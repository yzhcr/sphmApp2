package com.nju.sphm.Controller.TableActivity;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ScrollView;
import android.widget.TableLayout;
import android.widget.TextView;

import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.lidroid.xutils.view.annotation.event.OnClick;
import com.nju.sphm.Bean.OrganizationBean;
import com.nju.sphm.Bean.StudentBean;
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
    private ArrayList<StudentBean> maleStudentList=new ArrayList<StudentBean>();
    private ArrayList<StudentBean> femaleStudentList=new ArrayList<StudentBean>();
    private int whichIsChosen=0;
    private String tableTitleString;
    @ViewInject(R.id.tabletitle)
    private TableLayout tableTitle;
    TableHelper tableHelper;
    String testFileId;
    @ViewInject(R.id.showAll)
    private TextView showAll;
    @ViewInject(R.id.showMale)
    private TextView showMale;
    @ViewInject(R.id.showFemale)
    private TextView showFemale;
    @ViewInject(R.id.tablescroll)
    private ScrollView scrollView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_table);

        Intent intent=getIntent();
        schoolid=intent.getStringExtra("schoolid");
        schoolPath=intent.getStringExtra("schoolpath");
        testProject=intent.getStringExtra("testProject");
        testFileId=intent.getStringExtra("testFileId");
        tableTitleString=intent.getStringExtra("tableTitle");

        ViewUtils.inject(this);

        dbManager=new DBManager(this);
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
        tableHelper.setDbManager(dbManager);
        tableHelper.setTableTitle(tableTitle, tableTitleString, TableActivity.this);
        setTable(studentList);
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
                switch (whichIsChosen){
                    case 0:
                        setTable(studentList);
                        break;
                    case 1:
                        setTable(maleStudentList);
                        break;
                    case 2:
                        setTable(femaleStudentList);
                        break;
                }
                scrollToTop();
            }
        });
        dialog.show();
    }

    private void scrollToTop(){
        scrollView.post(new Runnable() {
            @Override
            public void run() {
                // TODO Auto-generated method stub
                scrollView.fullScroll(ScrollView.FOCUS_UP);
            }
        });
    }

    private void setTable(ArrayList<StudentBean> list){
        if(testProject.equals("BMI"))
            tableHelper.setTable(table, tableTitleString, list,"身高","体重",TableActivity.this);
        else
            tableHelper.setTable(table, tableTitleString, list,testProject,null,TableActivity.this);
    }

    @OnClick(R.id.showAll)
    public void showAll(View v){
        whichIsChosen=0;
        showAll.setTextColor(Color.WHITE);
        showAll.setBackgroundResource(R.drawable.showall_click);
        showMale.setTextColor(Color.BLACK);
        showMale.setBackgroundResource(R.drawable.showmale);
        showFemale.setTextColor(Color.BLACK);
        showFemale.setBackgroundResource(R.drawable.showfemale);
        setTable(studentList);
        scrollToTop();
    }

    @OnClick(R.id.showMale)
    public void showMale(View v){
        whichIsChosen=1;
        showAll.setTextColor(Color.BLACK);
        showAll.setBackgroundResource(R.drawable.showall);
        showMale.setTextColor(Color.WHITE);
        showMale.setBackgroundResource(R.drawable.showmale_click);
        showFemale.setTextColor(Color.BLACK);
        showFemale.setBackgroundResource(R.drawable.showfemale);
        setTable(maleStudentList);
        scrollToTop();
    }

    @OnClick(R.id.showFemale)
    public void showFemale(View v){
        whichIsChosen=2;
        showAll.setTextColor(Color.BLACK);
        showAll.setBackgroundResource(R.drawable.showall);
        showMale.setTextColor(Color.BLACK);
        showMale.setBackgroundResource(R.drawable.showmale);
        showFemale.setTextColor(Color.WHITE);
        showFemale.setBackgroundResource(R.drawable.showfemale_click);
        setTable(femaleStudentList);
        scrollToTop();
    }

    private void getStudentInfo(){
        int chosenGrade=getClass.getChoseGrade();
        int chosenClass=getClass.getChoseClass();
        String classID=getClass.findClassId(chosenGrade,chosenClass);
        studentList=dbManager.getStudents(classID, testFileId);
        maleStudentList.clear();
        femaleStudentList.clear();
        for(StudentBean student:studentList){
            if(student.getSex().equals("男生"))
                maleStudentList.add(student);
            else
                femaleStudentList.add(student);
        }
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
