package com.nju.sphm.Controller.ChooseProjectsActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.lidroid.xutils.view.annotation.event.OnClick;
import com.nju.sphm.Controller.TableActivity.TableActivity;
import com.nju.sphm.R;

public class ChooseTestProject extends Activity {
    @ViewInject(R.id.chooseTestProject)
    Button chooseTestProject;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_choose_test_project);
        ViewUtils.inject(this);
    }

    @OnClick(R.id.chooseTestProject)
    public void chooseTestProject(View v){
        Intent i=new Intent(ChooseTestProject.this,TableActivity.class);
        startActivity(i);
    }

}
