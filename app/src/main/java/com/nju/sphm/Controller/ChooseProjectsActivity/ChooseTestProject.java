package com.nju.sphm.Controller.ChooseProjectsActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.lidroid.xutils.view.annotation.event.OnClick;
import com.nju.sphm.Bean.TestFileBean;
import com.nju.sphm.Controller.TableActivity.TableActivity;
import com.nju.sphm.Model.DataHelper.DBManager;
import com.nju.sphm.R;

import java.util.ArrayList;

public class ChooseTestProject extends Activity {
    @ViewInject(R.id.chooseTestProject)
    Button chooseTestProject;
    DBManager dbManager=null;
    ArrayList<TestFileBean> testFileList;
    String schoolid=null;
    String schoolPath=null;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_choose_test_project);
        ViewUtils.inject(this);

        Intent intent=getIntent();
        schoolid=intent.getStringExtra("schoolid");
        schoolPath=intent.getStringExtra("schoolpath");

        dbManager=new DBManager(this);
        /*testFileList=dbManager.getTestFiles(schoolid);
        for(TestFileBean t:testFileList) {
            System.out.println(t.getFileName());
        }*/

    }

    @OnClick(R.id.chooseTestProject)
    public void chooseTestProject(View v){
        Intent i=new Intent();
        i.putExtra("schoolid",schoolid);
        i.putExtra("schoolpath",schoolPath);
        i.setClass(ChooseTestProject.this, TableActivity.class);
        startActivity(i);
    }

}
