package com.nju.sphm.Controller.LoginActivity;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.StrictMode;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.lidroid.xutils.view.annotation.event.OnClick;
import com.nju.sphm.Bean.LoginBean;
import com.nju.sphm.Controller.ChooseProjectsActivity.ChooseTestProject;
import com.nju.sphm.Model.DataHelper.NetWorkHelper;
import com.nju.sphm.Model.Login.Login;
import com.nju.sphm.R;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;


public class MainActivity extends Activity {


    @ViewInject(R.id.school)
    TextView chooseSchool;
    @ViewInject(R.id.login)
    Button login;
    @ViewInject(R.id.user)
    EditText userText;
    @ViewInject(R.id.password)
    EditText passwordText;
    String password=null;
    String user=null;
    String schoolid=null;
    String schoolPath=null;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);

        StrictMode.ThreadPolicy policy=new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);

        String ip=readip();
        NetWorkHelper networkHelper = NetWorkHelper.getInstance();
        networkHelper.setIp(ip);

        ViewUtils.inject(this);
        Intent intent=getIntent();
        String schoolName=intent.getStringExtra("schoolname");
        schoolid=intent.getStringExtra("schoolid");
        schoolPath=intent.getStringExtra("schoolpath");
        //System.out.println(schoolPath);

        if(schoolName!=null) {
            chooseSchool.setText(schoolName);
            chooseSchool.setTextColor(Color.BLACK);
        }

    }

    @OnClick(R.id.school)
    public void chooseSchool(View v){
        Intent i=new Intent(MainActivity.this,ChooseSchoolActivity.class);
        startActivity(i);
    }

    @OnClick(R.id.login)
    public void login(View v){
        user=userText.getText().toString();
        password=passwordText.getText().toString();

        if(schoolid==null){
            Toast toast=Toast.makeText(getApplicationContext(), "请选择学校", Toast.LENGTH_SHORT);
            toast.show();
        }
        else {
            Login loginlogic = new Login();
            LoginBean loginBean=loginlogic.login(user, password, schoolPath);
            boolean infoIsTrue = loginBean.isStatus();
            if(infoIsTrue){
                Intent i=new Intent(MainActivity.this,ChooseTestProject.class);
                startActivity(i);
            }
            else{
                Toast toast=Toast.makeText(getApplicationContext(), "用户名密码错误", Toast.LENGTH_SHORT);
                toast.show();
            }
        }
    }

    /*@Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }*/

    private String readip(){
        InputStream input = getResources().openRawResource(R.raw.ipsetting);
        BufferedReader read = new BufferedReader(new InputStreamReader(input));
        String line = "";
        try {
            line=read.readLine();
        }catch (Exception e){
            e.printStackTrace();
        }
        return line;
    }
}
