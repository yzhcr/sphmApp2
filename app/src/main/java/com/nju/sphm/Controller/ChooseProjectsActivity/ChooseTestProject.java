package com.nju.sphm.Controller.ChooseProjectsActivity;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.lidroid.xutils.view.annotation.event.OnClick;
import com.nju.sphm.Bean.TestFileBean;
import com.nju.sphm.Bean.TestFileRowBean;
import com.nju.sphm.Controller.TableActivity.TableActivity;
import com.nju.sphm.Model.ChooseTestProject.ChooseTestFiles;
import com.nju.sphm.Model.DataHelper.DBManager;
import com.nju.sphm.R;

import java.util.ArrayList;

public class ChooseTestProject extends Activity {
    @ViewInject(R.id.chooseTestProject)
    Button chooseTestProject;
    @ViewInject(R.id.changeTestData)
    Button changeTestData;
    @ViewInject(R.id.chosetestdata)
    TextView choseTestData;
    DBManager dbManager=null;
    ArrayList<TestFileBean> testFileList;
    ArrayList<TestFileRowBean> testFileRowList;
    String schoolid=null;
    String schoolPath=null;
    TestFileBean chosenTestFile=null;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_choose_test_project);
        ViewUtils.inject(this);

        Intent intent=getIntent();
        schoolid=intent.getStringExtra("schoolid");
        schoolPath=intent.getStringExtra("schoolpath");

        dbManager=new DBManager(this);
        testFileList=dbManager.getTestFiles(schoolid);
        ChooseTestFiles.getInstance().setTestFileList(testFileList);
        chosenTestFile=testFileList.get(0);

        /*testFileRowList=dbManager.getTestFileRows(chosenTestFile.get_id());
        TestFileRowBean t=testFileRowList.get(1);
        HashMap<String, Object> info=t.getInfo();
        Iterator iterator = info.entrySet().iterator();
        while (iterator.hasNext()) {
            Map.Entry entry = (Map.Entry) iterator.next();
            Object key = entry.getKey();
            Object val = entry.getValue();
            System.out.println(key);
            System.out.println(val);
            System.out.println("-----------------");
        }*/
        //System.out.println("*************");
        //System.out.println(t.getInfoJSON());


    }

    @OnClick(R.id.chooseTestProject)
    public void chooseTestProject(View v){
        Intent i=new Intent();
        i.putExtra("schoolid",schoolid);
        i.putExtra("schoolpath",schoolPath);
        i.setClass(ChooseTestProject.this, TableActivity.class);
        startActivity(i);
    }

    @OnClick(R.id.changeTestData)
    public void showDialog(View v)
    {
        TestFilePickerDialog dialog  = new TestFilePickerDialog(this);
        dialog.setOnTestFileSetListener(new TestFilePickerDialog.OnTestFileSetListener() {
            public void OnTestFileSet(AlertDialog dialog, int choseTestFile) {
                choseTestData.setText(testFileList.get(choseTestFile-1).getFileName());
            }
        });
        dialog.show();
    }

}
