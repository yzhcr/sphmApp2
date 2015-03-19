package com.nju.sphm.Controller.LoginActivity;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.lidroid.xutils.view.annotation.event.OnClick;
import com.nju.sphm.Bean.LoginBean;
import com.nju.sphm.Controller.ChooseProjectsActivity.ChooseTestProject;
import com.nju.sphm.Model.DataHelper.NetWorkHelper;
import com.nju.sphm.Model.Download.DownloadWorker;
import com.nju.sphm.Model.FinishTheApp.SaveMainActivity;
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
    @ViewInject(R.id.autologin)
    CheckBox autoLogin;
    String password=null;
    String user=null;
    String schoolid=null;
    String schoolPath=null;
    boolean isautoLogin=false;
    private Thread thread;
    private Thread loginThread;
    private String userId;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);

       // StrictMode.ThreadPolicy policy=new StrictMode.ThreadPolicy.Builder().permitAll().build();
        //StrictMode.setThreadPolicy(policy);
        //根据文件里的ip设置好ip
        String ip=readip();
        NetWorkHelper networkHelper = NetWorkHelper.getInstance();
        networkHelper.setIp(ip);
        //存下mainactivity，方便和chooseTestProject一起退出
        SaveMainActivity.getInstance().setMainActivity(this);

        ViewUtils.inject(this);

        SharedPreferences sharedPreferences =getSharedPreferences("loginMessage", Context.MODE_PRIVATE);
        isautoLogin=sharedPreferences.getBoolean("isAutoLogin",false);
        if(isautoLogin){
            schoolid=sharedPreferences.getString("schoolid",null);
            schoolPath=sharedPreferences.getString("schoolPath",null);
            user=sharedPreferences.getString("user",null);
            password=sharedPreferences.getString("password",null);
            //login.performClick();
            Intent i = new Intent();
            i.putExtra("schoolid", schoolid);
            i.putExtra("schoolpath", schoolPath);
            i.setClass(MainActivity.this, ChooseTestProject.class);
            startActivity(i);
        }
        else {
            Intent intent = getIntent();
            String schoolName = intent.getStringExtra("schoolname");
            schoolid = intent.getStringExtra("schoolid");
            schoolPath = intent.getStringExtra("schoolpath");
            //System.out.println(schoolPath);

            if (schoolName != null) {
                chooseSchool.setText(schoolName);
                chooseSchool.setTextColor(Color.BLACK);
            }
        }

    }

    @OnClick(R.id.school)
    public void chooseSchool(View v){
        Intent i=new Intent(MainActivity.this,ChooseSchoolActivity.class);
        startActivity(i);
    }

    @OnClick(R.id.login)
    public void login(View v){
            if (schoolid == null) {
                Toast toast = Toast.makeText(getApplicationContext(), "请选择学校", Toast.LENGTH_SHORT);
                toast.show();
            } else {
                if(userText.getText().toString().equals("")||passwordText.getText().toString().equals("")){
                    Toast toast=Toast.makeText(getApplicationContext(), "请输入用户名密码", Toast.LENGTH_SHORT);
                    toast.show();
                }else {
                    user = userText.getText().toString();
                    password = passwordText.getText().toString();
                    NetWorkHelper netWorkHelper = NetWorkHelper.getInstance();
                    if (!netWorkHelper.hasWifi(this)) {
                        final AlertDialog.Builder builder = new Builder(MainActivity.this);
                        builder.setTitle("您未处于WiFi环境下");
                        builder.setMessage("下载数据会产生大量流量，是否继续？");
                        //builder.setIcon(R.drawable.ic_launcher);
                        builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                // TODO Auto-generated method stub
                                loginThread = new Thread(loginRunnable);
                                loginThread.start();
                            }
                        });
                        builder.setNegativeButton("取消", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                // TODO Auto-generated method stub

                            }
                        });
                        builder.create().show();
                    } else {
                        loginThread = new Thread(loginRunnable);
                        loginThread.start();
                    }
                }
            }
    }

    private Handler mHandler = new Handler() {
        // 重写handleMessage()方法，此方法在UI线程运行
                @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                // 如果成功，则显示从网络获取到的图片
                case 1: {
                    Toast.makeText(getApplication(),
                            "下载成功",
                            Toast.LENGTH_LONG).show();
                    Intent i = new Intent();
                    i.putExtra("schoolid", schoolid);
                    i.putExtra("schoolpath", schoolPath);
                    i.setClass(MainActivity.this, ChooseTestProject.class);
                    startActivity(i);
                    break;
                }
                // 否则提示失败
                case 0:
                    Toast.makeText(getApplication(),
                            "下载失败",
                            Toast.LENGTH_LONG).show();
                    break;
                }
            }
        };
    Runnable runnable = new Runnable() {
        // 重写run()方法，此方法在新的线程中运行
                @Override
        public void run() {
            try{
                DownloadWorker downloadWorker = new DownloadWorker(getApplication());
                boolean ok = downloadWorker.download(schoolPath, userId, 2014);
                if(ok){
                    mHandler.obtainMessage(1).sendToTarget();
                }else{
                    mHandler.obtainMessage(0).sendToTarget();
                }
                } catch (Exception e) {
                mHandler.obtainMessage(0).sendToTarget();
                }
            }
        };


    private Handler loginHandler = new Handler() {
        // 重写handleMessage()方法，此方法在UI线程运行
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                // 如果成功，则显示从网络获取到的图片
                case 1: {
                    Login loginlogic = new Login();
                    if (autoLogin.isChecked()) {
                        SharedPreferences sharedPreferences = getSharedPreferences("loginMessage", Context.MODE_PRIVATE);
                        SharedPreferences.Editor editor = sharedPreferences.edit();//获取编辑器
                        editor.putString("user", user);
                        editor.putString("password", password);
                        editor.putString("schoolPath", schoolPath);
                        editor.putString("schoolid", schoolid);
                        editor.putBoolean("isAutoLogin", true);
                        editor.commit();
                    } else {
                        SharedPreferences sharedPreferences = getSharedPreferences("loginMessage", Context.MODE_PRIVATE);
                        SharedPreferences.Editor editor = sharedPreferences.edit();
                        editor.putBoolean("isAutoLogin", false);
                    }
                    thread = new Thread(runnable);
                    thread.start();
                    break;
                }
                // 否则提示失败
                case 2: {
                    Toast toast = Toast.makeText(getApplicationContext(), "用户名密码错误", Toast.LENGTH_SHORT);
                    toast.show();
                    user = null;
                    password = null;
                    break;
                }
                case 0:{
                    Toast toast = Toast.makeText(getApplicationContext(), "网络错误", Toast.LENGTH_SHORT);
                    toast.show();
                    break;
                }
            }
        }
    };
    Runnable loginRunnable = new Runnable() {
        // 重写run()方法，此方法在新的线程中运行
        @Override
        public void run() {
            try{
                Login loginlogic = new Login();
                LoginBean loginBean = loginlogic.login(user, password, schoolPath);
                userId = loginlogic.getUserID(user, schoolPath);
                boolean infoIsTrue = loginBean.isStatus();
                if (infoIsTrue&&userId!=null) {
                    loginHandler.obtainMessage(1).sendToTarget();

                } else {
                    loginHandler.obtainMessage(2).sendToTarget();
                }
            } catch (Exception e) {
                loginHandler.obtainMessage(0).sendToTarget();
            }
        }
    };

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

    private long exitTime = 0;
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if(keyCode == KeyEvent.KEYCODE_BACK && event.getAction() == KeyEvent.ACTION_DOWN){
            if((System.currentTimeMillis()-exitTime) > 2000){
                Toast.makeText(getApplicationContext(), "再按一次退出程序", Toast.LENGTH_SHORT).show();
                exitTime = System.currentTimeMillis();
            } else {
                this.finish();
            }
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }
}
