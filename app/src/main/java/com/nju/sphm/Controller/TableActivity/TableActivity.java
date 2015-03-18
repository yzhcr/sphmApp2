package com.nju.sphm.Controller.TableActivity;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.text.InputType;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TableRow.LayoutParams;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.lidroid.xutils.view.annotation.event.OnClick;
import com.nju.sphm.Bean.OrganizationBean;
import com.nju.sphm.Bean.StudentBean;
import com.nju.sphm.Bean.TestFileRowBean;
import com.nju.sphm.Model.DataHelper.DBManager;
import com.nju.sphm.Model.School.GetClass;
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
    private int columnNum;
    private String test1Name;
    private String test2Name;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Intent intent=getIntent();
        schoolid=intent.getStringExtra("schoolid");
        schoolPath=intent.getStringExtra("schoolpath");
        testProject=intent.getStringExtra("testProject");
        String testFileId=intent.getStringExtra("testFileId");
        columnNum=Integer.parseInt(intent.getStringExtra("columnNum"));
        test1Name=intent.getStringExtra("test1Name");
        if(columnNum==4) {
            setContentView(R.layout.activity_table);
        }else if(columnNum==5){
            test2Name=intent.getStringExtra("test2Name");
            setContentView(R.layout.activity_table_two_data);
        }
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
        setTable();


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
                //setTable();
            }
        });
        dialog.show();
    }

    private void getStudentInfo(){
        int chosenGrade=getClass.getChoseGrade();
        int chosenClass=getClass.getChoseClass();
        String classID=getClass.findClassId(chosenGrade,chosenClass);
        studentList=dbManager.getStudents(classID);
        /*for(StudentBean s:studentList){
            HashMap<String, Object> info=s.getInfo();
            Iterator iterator = info.entrySet().iterator();
            while (iterator.hasNext()) {
                Map.Entry entry = (Map.Entry) iterator.next();
                Object key = entry.getKey();
                Object val = entry.getValue();
                System.out.println(key);
                System.out.println(val);
                System.out.println("-----------------");
            }
            System.out.println("******************");
        }*/
    }

    private void setTable(){
        table.setStretchAllColumns(true);
        for (int i = 0; i < studentList.size(); i++) {
            TableRow tablerow = new TableRow(TableActivity.this);
            tablerow.setBackgroundColor(Color.WHITE);
            for (int j = 0; j < columnNum; j++) {
                int width = this.getWindowManager().getDefaultDisplay().getWidth() / 4;
                LayoutParams layoutParams=new LayoutParams(width,LayoutParams.FILL_PARENT);
                layoutParams.setMargins(0,0,1,0);
                if (j==1) {
                    TextView textView=new TextView(TableActivity.this);
                    textView.setLines(1);
                    textView.setGravity(Gravity.CENTER);
                    textView.setBackgroundColor(Color.TRANSPARENT);//背景黑色
                    textView.setText(studentList.get(i).getStudentNumberLastSixNum());
                    textView.setTextColor(Color.BLACK);
                    textView.setTextSize(15);
                    textView.setPadding(0,10,0,10);
                    tablerow.addView(textView,layoutParams);
                }
                if (j==2) {
                    TextView textView=new TextView(TableActivity.this);
                    textView.setLines(1);
                    textView.setGravity(Gravity.CENTER);
                    textView.setBackgroundColor(Color.TRANSPARENT);//背景黑色
                    textView.setText(studentList.get(i).getName());
                    textView.setTextColor(Color.BLACK);
                    textView.setTextSize(15);
                    textView.setPadding(0,10,0,10);
                    tablerow.addView(textView,layoutParams);
                }
                if (j==3) {
                    TextView textView=new TextView(TableActivity.this);
                    textView.setLines(1);
                    textView.setGravity(Gravity.CENTER);
                    textView.setBackgroundColor(Color.TRANSPARENT);//背景黑色
                    textView.setText(studentList.get(i).getName());
                    textView.setTextColor(Color.BLACK);
                    textView.setTextSize(15);
                    textView.setPadding(0,10,0,10);
                    tablerow.addView(textView,layoutParams);
                }
                if(j==4){
                    EditText editText = new EditText(TableActivity.this);
                    //testview.setBackgroundResource(R.drawable.shape);
                    // testview.setText("选择");
                    editText.setLines(1);
                    editText.setGravity(Gravity.CENTER);
                    editText.setBackgroundColor(Color.TRANSPARENT);//背景黑色
                    //editText.setText(String.valueOf(tableCell.value));
                    editText.setTextColor(Color.BLACK);
                    editText.setInputType(InputType.TYPE_CLASS_NUMBER);
                    editText.setTextSize(15);
                    editText.setPadding(0,10,0,10);
                    tablerow.addView(editText,layoutParams);
                }

            }
            TableLayout.LayoutParams tableParam=new TableLayout.LayoutParams(
                    LayoutParams.FILL_PARENT, LayoutParams.WRAP_CONTENT);
            tableParam.setMargins(0,1,0,1);
            table.addView(tablerow,tableParam);
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
