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
        //System.out.println(schoolid);
        addClassInfo();

        /*studentList=dbManager.getStudents("5445f752fa40c7df3ad57f07");
        for(StudentBean o:studentList){
            System.out.println(o.get_id());
        }*/


        ArrayList<TableRow> table=new ArrayList<TableRow>();
        TableCell[] titles=new TableCell[4];
        int width = this.getWindowManager().getDefaultDisplay().getWidth()/titles.length;
        titles[0]=new TableCell("1",width,LayoutParams.FILL_PARENT,TableCell.STRING);
        titles[1]=new TableCell("2",width,LayoutParams.FILL_PARENT,TableCell.STRING);
        titles[2]=new TableCell("3",width,LayoutParams.FILL_PARENT,TableCell.STRING);
        titles[3]=new TableCell("4",width,LayoutParams.FILL_PARENT,TableCell.STRING);

        table.add(new TableRow(titles));

        //for(int i=0;i<cityList.size();i++){

            TableCell[] cells = new TableCell[4];
            cells[0]=new TableCell(1,
                    titles[0].width,LayoutParams.FILL_PARENT,
                    TableCell.STRING);
            cells[1]=new TableCell("李二宇",
                    titles[1].width,LayoutParams.FILL_PARENT,
                    TableCell.STRING);
            cells[2]=new TableCell("",
                    titles[2].width,LayoutParams.FILL_PARENT,
                    TableCell.INPUT);
            cells[3]=new TableCell("",
                    titles[3].width,LayoutParams.FILL_PARENT,
                    TableCell.INPUT);
            table.add(new TableRow(cells));
        //}
        TableAdapter tableAdapter = new TableAdapter(this, table);
        lv.setAdapter(tableAdapter);

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
                System.out.println(findClassId(choseGrade,choseClass));
            }
        });
        dialog.show();
    }

    private void addClassInfo(){
        dbManager=new DBManager(this);
        gradeList=dbManager.getOrganizations(schoolid);
        ArrayList<OrganizationBean> sortGradeList=new ArrayList<OrganizationBean>();
        int gradeNum=gradeList.size();
        if(gradeNum==3){
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
        GetClass getClass=GetClass.getInstance();
        getClass.setGradeList(sortGradeList);
    }

    private String findClassId(int gradeNum,int classNum){
        GetClass getClass=GetClass.getInstance();
        ArrayList<OrganizationBean> sortGradeList=getClass.getGradeList();
        ArrayList<OrganizationBean> classList=sortGradeList.get(gradeNum-1).getChildren();
        String classId=null;
        switch (gradeNum){
            case 1:
                for(OrganizationBean o:classList){
                    if(o.getName().equals("一（"+classNum+"）班")) {
                        classId = o.get_id();
                        break;
                    }
                }
                break;
            case 2:
                for(OrganizationBean o:classList){
                    if(o.getName().equals("二（"+classNum+"）班")) {
                        classId = o.get_id();
                        break;
                    }
                }
                break;
            case 3:
                for(OrganizationBean o:classList){
                    if(o.getName().equals("三（"+classNum+"）班")) {
                        classId = o.get_id();
                        break;
                    }
                }
                break;
            case 4:
                for(OrganizationBean o:classList){
                    if(o.getName().equals("四（"+classNum+"）班")) {
                        classId = o.get_id();
                        break;
                    }
                }
                break;
            case 5:
                for(OrganizationBean o:classList){
                    if(o.getName().equals("五（"+classNum+"）班")) {
                        classId = o.get_id();
                        break;
                    }
                }
                break;
            case 6:
                for(OrganizationBean o:classList){
                    if(o.getName().equals("六（"+classNum+"）班")) {
                        classId = o.get_id();
                        break;
                    }
                }
                break;
        }
        return classId;
    }

    /**
     * 将长时间格式字符串转换为时间 yyyy-MM-dd HH:mm:ss
     *
    public static String getStringDate(Long date)
    {
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String dateString = formatter.format(date);

        return dateString;
    }*/

}
