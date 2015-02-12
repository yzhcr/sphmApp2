package com.nju.sphm.Controller;

import android.content.Intent;
import android.os.Bundle;
import android.os.StrictMode;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.lidroid.xutils.view.annotation.event.OnClick;
import com.nju.sphm.Bean.OrganizationBean;
import com.nju.sphm.Controller.CountDownTimerActivity.CountDownTimerActivity;
import com.nju.sphm.Controller.LoginActivity.MainActivity;
import com.nju.sphm.Controller.TimerActivity.TimerActivity;
import com.nju.sphm.Model.DataHelper.DBManager;
import com.nju.sphm.Model.DataHelper.NetWorkHelper;
import com.nju.sphm.Model.DataHelper.OrganizationHelper;
import com.nju.sphm.Model.Download.DownloadWorker;
import com.nju.sphm.R;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;


public class TestMainActivity extends ActionBarActivity {

    @ViewInject(R.id.btn1)
    Button clock;
    @ViewInject(R.id.btn2)
    Button countDownClock;
    @ViewInject(R.id.btn3)
    Button db;
    @ViewInject(R.id.btn4)
    Button login;
    @ViewInject(R.id.btn5)
    Button download;
    DBManager dbm;

    @Override

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test_main);
        StrictMode.ThreadPolicy policy=new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);
        dbm = new DBManager(this);
        ViewUtils.inject(this);
        String ip="http://192.168.204.128:8080";
        NetWorkHelper networkHelper = NetWorkHelper.getInstance();
        networkHelper.setIp(ip);


    }

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

    @OnClick(R.id.btn2)
    public void btn2(View v) {
        Intent intent = new Intent();
        intent.setClass(this, CountDownTimerActivity.class);
        startActivity(intent);
    }

    @OnClick(R.id.btn1)
    public void btn1(View v) {
        Intent intent = new Intent();
        intent.setClass(this, TimerActivity.class);
        startActivity(intent);
    }

    @OnClick(R.id.btn3)
    public void btn3(View v) {
        try {
//            Runnable r = new NetWorkHandler();
//            Thread thread = new Thread(r);
//            thread.start();
            //ArrayList<String> list = dao.getClassIdList("5445bd0bfa40c7df3ad57efe");
//            LoginDAO dao = new LoginDAO();
//            ArrayList list= dao.getUsers();
            //db.setText(list.size() + "");
//            TestFileHelper helper = new TestFileHelper();
//            System.out.println(helper.getTestFileRowList("54508151802097dd4e306470").size());
//            StudentHelper helper = new StudentHelper();
//            ArrayList<ClassBean> list = helper.getClassList("/运营服务中心/江苏省/南京市/江宁区/南京市江宁区铜山中心小学/",2014);
//            ArrayList<StudentBean> sl = list.get(1).getStudents();
//            dbm.addStudents(sl);
//            String o = list.get(1).get_id();
//            ArrayList<StudentBean> s2 = dbm.getStudents(o);
//            for(StudentBean bean:s2){
//                System.out.println(bean.getInfoJSON());
//            }
            OrganizationHelper helper = new OrganizationHelper();
            ArrayList<OrganizationBean> l = helper.getOrganizationList("/运营服务中心/江苏省/南京市/江宁区/南京市江宁区铜山中心小学/",2014);
            dbm.addOrganizations(l);
            int size1=1;
            int size2=0;
            for(OrganizationBean b: l){
                size1+=b.getChildren().size();
                size2+=dbm.getOrganizations(b.get_id()).size();
            }
            System.out.println(size1+";"+size2);

        }catch (Exception e) {
            e.printStackTrace();
        }
    }

    @OnClick(R.id.btn4)
    public void login(View v){
        Intent intent = new Intent();
        intent.setClass(this, MainActivity.class);
        startActivity(intent);
    }

    @OnClick(R.id.btn5)
    public void download(View v){
        DownloadWorker dw = new DownloadWorker(this.getApplicationContext());
        boolean success = dw.download("/运营服务中心/江苏省/南京市/江宁区/南京市江宁区铜山中心小学/", "544d9bcc802097dd4e2d0a08", 2014 );
        if(success){

        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    private class NetWorkHandler implements Runnable {


        @Override
        public void run() {
            try {

            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
