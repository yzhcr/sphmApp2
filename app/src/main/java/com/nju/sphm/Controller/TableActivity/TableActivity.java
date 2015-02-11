package com.nju.sphm.Controller.TableActivity;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.LinearLayout.LayoutParams;
import android.widget.ListView;
import android.widget.TextView;

import com.nju.sphm.Bean.OrganizationBean;
import com.nju.sphm.Bean.StudentBean;
import com.nju.sphm.Controller.TableActivity.TableAdapter.TableCell;
import com.nju.sphm.Controller.TableActivity.TableAdapter.TableRow;
import com.nju.sphm.Model.DataHelper.DBManager;
import com.nju.sphm.R;

import java.text.SimpleDateFormat;
import java.util.ArrayList;


public class TableActivity extends Activity {
    private Button btn_choose;
    private TextView choseclass;
    ListView lv;
    DBManager dbManager=null;
    String schoolid=null;
    String schoolPath=null;
    ArrayList<OrganizationBean> gradeList=null;
    ArrayList<StudentBean> studentList=null;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_table);
        lv=(ListView)findViewById(R.id.ListView01);

        Intent intent=getIntent();
        schoolid=intent.getStringExtra("schoolid");
        schoolPath=intent.getStringExtra("schoolpath");
        System.out.println(schoolid);

        dbManager=new DBManager(this);
        /*gradeList=dbManager.getOrganizations(schoolid);
        for(OrganizationBean o:gradeList){
            System.out.println(o.getName());
        }*/
        studentList=dbManager.getStudents("5445f752fa40c7df3ad57f07");
        for(StudentBean o:studentList){
            System.out.println(o.get_id());
        }


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

        btn_choose=(Button)this.findViewById(R.id.changeClass);
        choseclass=(TextView)this.findViewById(R.id.choseclass);
        btn_choose.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                showDialog();
            }
        });

    }
    public void showDialog()
    {
        ClassPickerDialog dialog  = new ClassPickerDialog(this, System.currentTimeMillis());
        dialog.setOnClassSetListener(new ClassPickerDialog.OnClassSetListener() {
            public void OnClassSet(AlertDialog dialog, int choseGrade, int choseClass) {
                choseclass.setText(choseGrade+"年"+choseClass+"班");
            }
        });
        dialog.show();
    }
    /**
     * 将长时间格式字符串转换为时间 yyyy-MM-dd HH:mm:ss
     *
     */
    public static String getStringDate(Long date)
    {
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String dateString = formatter.format(date);

        return dateString;
    }

}
