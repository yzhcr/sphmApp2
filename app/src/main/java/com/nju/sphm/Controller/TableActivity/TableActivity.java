package com.nju.sphm.Controller.TableActivity;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout.LayoutParams;
import android.widget.ListView;
import android.widget.TextView;

import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.lidroid.xutils.view.annotation.event.OnClick;
import com.nju.sphm.Bean.OrganizationBean;
import com.nju.sphm.Bean.StudentBean;
import com.nju.sphm.Controller.TableActivity.TableAdapter.TableCell;
import com.nju.sphm.Controller.TableActivity.TableAdapter.TableRow;
import com.nju.sphm.Model.DataHelper.DBManager;
import com.nju.sphm.Model.School.GetClass;
import com.nju.sphm.R;

import java.util.ArrayList;


public class TableActivity extends Activity {
    @ViewInject(R.id.changeClass)
    private Button btn_choose;
    @ViewInject(R.id.choseclass)
    private TextView choseclass;
    @ViewInject(R.id.StudentList)
    ListView lv;
    @ViewInject(R.id.title)
    TextView title;
    DBManager dbManager=null;
    String schoolid=null;
    String schoolPath=null;
    String testProject=null;
    ArrayList<OrganizationBean> gradeList=null;
    GetClass getClass=GetClass.getInstance();
    ArrayList<StudentBean> studentList=null;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_table);
        ViewUtils.inject(this);
       // lv=(ListView)findViewById(R.id.ListView01);

        Intent intent=getIntent();
        schoolid=intent.getStringExtra("schoolid");
        schoolPath=intent.getStringExtra("schoolpath");
        testProject=intent.getStringExtra("testProject");
        title.setText(testProject);
        addClassInfo();
        int choseGrade=getClass.getChoseGrade();
        int choseClass=getClass.getChoseClass();
        choseclass.setText(choseGrade+"年"+choseClass+"班");
        //System.out.println(schoolid);
        getStudentInfo();

        setTable();
        /*studentList=dbManager.getStudents("5445f752fa40c7df3ad57f07");
        for(StudentBean o:studentList){
            System.out.println(o.get_id());
        }*/
        //btn_choose=(Button)this.findViewById(R.id.changeClass);
        //choseclass=(TextView)this.findViewById(R.id.choseclass);
        /*btn_choose.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                showDialog();
            }
        });*/

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
                setTable();
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
        if(testProject.equals("BMI")) {
            ArrayList<TableRow> table = new ArrayList<TableRow>();
            TableCell[] titles = new TableCell[5];
            int width = this.getWindowManager().getDefaultDisplay().getWidth() / titles.length;
            titles[0] = new TableCell("学号", width, LayoutParams.FILL_PARENT, TableCell.STRING);
            titles[1] = new TableCell("姓名", width, LayoutParams.FILL_PARENT, TableCell.STRING);
            titles[2] = new TableCell("性别", width, LayoutParams.FILL_PARENT, TableCell.STRING);
            titles[3] = new TableCell("身高(cm)", width, LayoutParams.FILL_PARENT, TableCell.STRING);
            titles[4] = new TableCell("体重(kg)", width, LayoutParams.FILL_PARENT, TableCell.STRING);

            table.add(new TableRow(titles));

            for(StudentBean student:studentList){

                TableCell[] cells = new TableCell[5];
                cells[0] = new TableCell(student.getStudentNumberLastSixNum(),
                    titles[0].width, LayoutParams.FILL_PARENT,
                    TableCell.STRING);
                cells[1] = new TableCell(student.getName(),
                    titles[1].width, LayoutParams.FILL_PARENT,
                    TableCell.STRING);
                cells[2] = new TableCell(student.getSex(),
                    titles[2].width, LayoutParams.FILL_PARENT,
                    TableCell.STRING);
                cells[3] = new TableCell("",
                    titles[3].width, LayoutParams.FILL_PARENT,
                    TableCell.INPUT);
                cells[4] = new TableCell("",
                    titles[4].width, LayoutParams.FILL_PARENT,
                    TableCell.INPUT);
                table.add(new TableRow(cells));
            }
            TableAdapter tableAdapter = new TableAdapter(this, table);
            lv.setAdapter(tableAdapter);
        }
        else if(testProject.equals("肺活量")){
            ArrayList<TableRow> table = new ArrayList<TableRow>();
            TableCell[] titles = new TableCell[4];
            int width = this.getWindowManager().getDefaultDisplay().getWidth() / titles.length;
            titles[0] = new TableCell("学号", width, LayoutParams.FILL_PARENT, TableCell.STRING);
            titles[1] = new TableCell("姓名", width, LayoutParams.FILL_PARENT, TableCell.STRING);
            titles[2] = new TableCell("性别", width, LayoutParams.FILL_PARENT, TableCell.STRING);
            titles[3] = new TableCell("肺活量(ml)", width, LayoutParams.FILL_PARENT, TableCell.STRING);

            table.add(new TableRow(titles));

            for(StudentBean student:studentList){

                TableCell[] cells = new TableCell[4];
                cells[0] = new TableCell(student.getStudentNumberLastSixNum(),
                    titles[0].width, LayoutParams.FILL_PARENT,
                    TableCell.STRING);
                cells[1] = new TableCell(student.getName(),
                    titles[1].width, LayoutParams.FILL_PARENT,
                    TableCell.STRING);
                cells[2] = new TableCell(student.getSex(),
                    titles[2].width, LayoutParams.FILL_PARENT,
                    TableCell.STRING);
                cells[3] = new TableCell("",
                    titles[3].width, LayoutParams.FILL_PARENT,
                    TableCell.INPUT);
                table.add(new TableRow(cells));
            }
            TableAdapter tableAdapter = new TableAdapter(this, table);
            lv.setAdapter(tableAdapter);
        }
        else if(testProject.equals("坐位体前屈")){
            ArrayList<TableRow> table = new ArrayList<TableRow>();
            TableCell[] titles = new TableCell[4];
            int width = this.getWindowManager().getDefaultDisplay().getWidth() / titles.length;
            titles[0] = new TableCell("学号", width, LayoutParams.FILL_PARENT, TableCell.STRING);
            titles[1] = new TableCell("姓名", width, LayoutParams.FILL_PARENT, TableCell.STRING);
            titles[2] = new TableCell("性别", width, LayoutParams.FILL_PARENT, TableCell.STRING);
            titles[3] = new TableCell("长度(cm)", width, LayoutParams.FILL_PARENT, TableCell.STRING);

            table.add(new TableRow(titles));

            for(StudentBean student:studentList){

                TableCell[] cells = new TableCell[4];
                cells[0] = new TableCell(student.getStudentNumberLastSixNum(),
                    titles[0].width, LayoutParams.FILL_PARENT,
                    TableCell.STRING);
                cells[1] = new TableCell(student.getName(),
                    titles[1].width, LayoutParams.FILL_PARENT,
                    TableCell.STRING);
                cells[2] = new TableCell(student.getSex(),
                    titles[2].width, LayoutParams.FILL_PARENT,
                    TableCell.STRING);
                cells[3] = new TableCell("",
                    titles[3].width, LayoutParams.FILL_PARENT,
                    TableCell.INPUT);
                table.add(new TableRow(cells));
            }
            TableAdapter tableAdapter = new TableAdapter(this, table);
            lv.setAdapter(tableAdapter);
        }
    }
    //将班级信息添加到GetCLass中，方便使用
    private void addClassInfo(){
        dbManager=new DBManager(this);
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
