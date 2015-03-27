package com.nju.sphm.Controller.ChooseProjectsActivity;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.webkit.JavascriptInterface;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ProgressBar;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.internal.LinkedTreeMap;
import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.lidroid.xutils.view.annotation.event.OnClick;
import com.nju.sphm.Bean.LoginBean;
import com.nju.sphm.Bean.OrganizationBean;
import com.nju.sphm.Bean.ScoreBean;
import com.nju.sphm.Bean.TestFileBean;
import com.nju.sphm.Bean.TestFileRowBean;
import com.nju.sphm.Controller.CountDownTimerActivity.CountDownTimerActivity;
import com.nju.sphm.Controller.TableActivity.TableActivity;
import com.nju.sphm.Controller.TimerActivity.TimerActivity;
import com.nju.sphm.Model.DataHelper.DBManager;
import com.nju.sphm.Model.DataHelper.NetWorkHelper;
import com.nju.sphm.Model.Download.DownloadWorker;
import com.nju.sphm.Model.Login.Login;
import com.nju.sphm.Model.UIHelper.ChooseTestFiles;
import com.nju.sphm.Model.UIHelper.GetClass;
import com.nju.sphm.R;

import java.util.ArrayList;
import java.util.HashMap;

public class ChooseTestProject extends Activity {
    @ViewInject(R.id.changeTestData)
    private Button changeTestData;
    @ViewInject(R.id.chosetestdata)
    private TextView choseTestData;
    @ViewInject(R.id.gridview)
    private GridView gridView;
    private DBManager dbManager=null;
    private ArrayList<TestFileBean> testFileList;
    private ArrayList<TestFileRowBean> testFileRowList;
    private String schoolid=null;
    private String schoolPath=null;
    @ViewInject(R.id.webView)
    private WebView webView;
    @ViewInject(R.id.downloadButton)
    private Button downloadButton;
    private AlertDialog downloadWindow;
    private ProgressBar downloadProgressBar;
    private TextView downloadTextView;
    private DownloadHandler handler = new DownloadHandler();

    //TestFileBean chosenTestFile=null;
    private ChooseTestFiles chooseTestFiles=ChooseTestFiles.getInstance();
    private String testFileId=null;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_choose_test_project);
        ViewUtils.inject(this);

        initDialog();

        Intent intent=getIntent();
        schoolid=intent.getStringExtra("schoolid");
        schoolPath=intent.getStringExtra("schoolpath");

        dbManager=new DBManager(this);
        testFileList=dbManager.getTestFiles(schoolid);

        int chosenTestFile=chooseTestFiles.getChosenTestFile();
        choseTestData.setText(testFileList.get(chosenTestFile).getFileName());

        chooseTestFiles.setTestFileList(testFileList);
        testFileId=chooseTestFiles.getChosenTestFileId();

        loadGrid();
        webView.loadUrl("file:///android_asset/gb2014.html");
        WebSettings webSettings = webView.getSettings();
        webSettings.setJavaScriptEnabled(true);
        webView.addJavascriptInterface(this, "wst");
        //webView.loadUrl("javascript:getGB2014Score(\"100\", \"坐位体前屈\", \"五年级\", \"男生\")");
        WebViewClient wvc = new WebViewClient() {
            @Override
            public void onPageFinished(WebView view, String url) {
                System.out.println(11111);
                webView.loadUrl("javascript:getGB2014Score(\"100\",\"坐位体前屈\",\"五年级\",\"男生\")");
                super.onPageFinished(view, url);
            }
        };
        webView.setWebViewClient(wvc);

    }
    @JavascriptInterface
    public void startFunction(String data) {
        System.out.println(data);
        data="{\"info\":"+data+"}";
        Gson gson = new Gson();
        ScoreBean scoreBean = gson.fromJson(data, ScoreBean.class);
        LinkedTreeMap<String, Object> info=(LinkedTreeMap)scoreBean.getInfo().get("得分");
        System.out.println(info.get("value"));
        // data即js的返回值
    }

    private void loadGrid(){
        ArrayList<HashMap<String, Object>> lstImageItem = new ArrayList<HashMap<String, Object>>();
        HashMap<String, Object> bmi = new HashMap<String, Object>();
        bmi.put("ItemImage", R.drawable.bmi);
        bmi.put("ItemText", "BMI");
        HashMap<String, Object> fei = new HashMap<String, Object>();
        fei.put("ItemImage", R.drawable.feihuoliang);
        fei.put("ItemText", "肺活量");
        HashMap<String, Object> zuowei = new HashMap<String, Object>();
        zuowei.put("ItemImage", R.drawable.zuowei);
        zuowei.put("ItemText", "坐位体前屈");
        HashMap<String, Object> fiftyM = new HashMap<String, Object>();
        fiftyM.put("ItemImage", R.drawable.fifty_m);
        fiftyM.put("ItemText", "50米跑");
        HashMap<String, Object> fiftyMeight = new HashMap<String, Object>();
        fiftyMeight.put("ItemImage", R.drawable.fifty_m_eight);
        fiftyMeight.put("ItemText", "50米×8往返跑");
        HashMap<String, Object> yangwo = new HashMap<String, Object>();
        yangwo.put("ItemImage", R.drawable.yangwo);
        yangwo.put("ItemText", "一分钟仰卧起坐");
        HashMap<String, Object> tiaosheng = new HashMap<String, Object>();
        tiaosheng.put("ItemImage", R.drawable.tiaosheng);
        tiaosheng.put("ItemText", "一分钟跳绳");
        lstImageItem.add(bmi);
        lstImageItem.add(fei);
        lstImageItem.add(zuowei);
        lstImageItem.add(fiftyM);
        lstImageItem.add(fiftyMeight);
        lstImageItem.add(yangwo);
        lstImageItem.add(tiaosheng);

        SimpleAdapter saImageItems = new SimpleAdapter(this,
                lstImageItem,//数据来源
                R.layout.testprojectietm,
                //动态数组与ImageItem对应的子项
                new String[] {"ItemImage","ItemText"},
                //ImageItem的XML文件里面的一个ImageView,两个TextView ID
                new int[] {R.id.ItemImage,R.id.ItemText});

        gridView.setAdapter(saImageItems);
        //添加消息处理
        gridView.setOnItemClickListener(new ItemClickListener());
    }

    class  ItemClickListener implements OnItemClickListener {
        public void onItemClick(AdapterView<?> arg0,//The AdapterView where the click happened
                                View arg1,//The view within the AdapterView that was clicked
                                int arg2,//The position of the view in the adapter
                                long arg3//The row id of the item that was clicked
        ) {
            //在本例中arg2=arg3
            HashMap<String, Object> item = (HashMap<String, Object>) arg0.getItemAtPosition(arg2);
            //显示所选Item的ItemText
            //setTitle((String) item.get("ItemText"));
            /*Toast.makeText(getApplicationContext(), (String) item.get("ItemText"),
                    Toast.LENGTH_SHORT).show();*/
            String choseProject=(String) item.get("ItemText");
            ArrayList<OrganizationBean> gradeList=dbManager.getOrganizations(schoolid);
            GetClass getClass=GetClass.getInstance();
            getClass.addClassInfo(gradeList,choseProject);
            switch (choseProject){
                case "BMI": {
                    Intent i = new Intent();
                    i.putExtra("schoolid", schoolid);
                    i.putExtra("schoolpath", schoolPath);
                    i.putExtra("testProject","BMI");
                    i.putExtra("testFileId",testFileId);
                    i.putExtra("tableTitle","学号:姓名:性别:身高(cm):体重(kg)");
                    i.setClass(ChooseTestProject.this, TableActivity.class);
                    startActivity(i);
                    break;
                }
                case "肺活量": {
                    Intent i = new Intent();
                    i.putExtra("schoolid", schoolid);
                    i.putExtra("schoolpath", schoolPath);
                    i.putExtra("testProject","肺活量");
                    i.putExtra("testFileId",testFileId);
                    i.putExtra("tableTitle","学号:姓名:性别:肺活量(ml)");
                    i.setClass(ChooseTestProject.this, TableActivity.class);
                    startActivity(i);
                    break;
                }
                case "坐位体前屈": {
                    Intent i = new Intent();
                    i.putExtra("schoolid", schoolid);
                    i.putExtra("schoolpath", schoolPath);
                    i.putExtra("testProject","坐位体前屈");
                    i.putExtra("testFileId",testFileId);
                    i.putExtra("tableTitle","学号:姓名:性别:长度(cm)");
                    i.setClass(ChooseTestProject.this, TableActivity.class);
                    startActivity(i);
                    break;
                }
                case "50米跑": {
                    Intent i = new Intent();
                    i.putExtra("schoolid", schoolid);
                    i.putExtra("schoolpath", schoolPath);
                    i.putExtra("testProject","50米跑");
                    i.putExtra("testFileId",testFileId);
                    i.putExtra("tableTitle","学号:姓名:性别:时间");
                    i.setClass(ChooseTestProject.this, TimerActivity.class);
                    startActivity(i);
                    break;
                }
                case "50米×8往返跑": {
                    Intent i = new Intent();
                    i.putExtra("schoolid", schoolid);
                    i.putExtra("schoolpath", schoolPath);
                    i.putExtra("testProject","50米×8往返跑");
                    i.putExtra("testFileId",testFileId);
                    i.putExtra("tableTitle","学号:姓名:性别:时间");
                    i.setClass(ChooseTestProject.this, TimerActivity.class);
                    startActivity(i);
                    break;
                }
                case "一分钟仰卧起坐": {
                    Intent i = new Intent();
                    i.putExtra("schoolid", schoolid);
                    i.putExtra("schoolpath", schoolPath);
                    i.putExtra("testProject","一分钟仰卧起坐");
                    i.putExtra("starttime","01:00");
                    i.putExtra("testFileId",testFileId);
                    i.putExtra("tableTitle","学号:姓名:性别:个数");
                    i.setClass(ChooseTestProject.this, CountDownTimerActivity.class);
                    startActivity(i);
                    break;
                }
                case "一分钟跳绳": {
                    Intent i = new Intent();
                    i.putExtra("schoolid", schoolid);
                    i.putExtra("schoolpath", schoolPath);
                    i.putExtra("starttime","01:00");
                    i.putExtra("testProject","一分钟跳绳");
                    i.putExtra("testFileId",testFileId);
                    i.putExtra("tableTitle","学号:姓名:性别:个数");
                    i.setClass(ChooseTestProject.this, CountDownTimerActivity.class);
                    startActivity(i);
                    break;
                }
                default: {
                    break;
                }
            }
        }
    }

    @OnClick(R.id.changeTestData)
    public void showDialog(View v)
    {
        TestFilePickerDialog dialog  = new TestFilePickerDialog(this);
        dialog.setOnTestFileSetListener(new TestFilePickerDialog.OnTestFileSetListener() {
            public void OnTestFileSet(AlertDialog dialog, int choseTestFile) {
                choseTestData.setText(testFileList.get(choseTestFile-1).getFileName());
                chooseTestFiles.setChosenTestFile(choseTestFile-1);
                testFileId=chooseTestFiles.getChosenTestFileId();
            }
        });
        dialog.show();
    }




    private long exitTime = 0;
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if(keyCode == KeyEvent.KEYCODE_BACK && event.getAction() == KeyEvent.ACTION_DOWN){
            if((System.currentTimeMillis()-exitTime) > 2000){
                Toast.makeText(getApplicationContext(), "再按一次退出程序", Toast.LENGTH_SHORT).show();
                exitTime = System.currentTimeMillis();
            } else {
                    //SaveMainActivity.getInstance().getMainActivity().finish();
                    this.finish();
            }
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

    @OnClick(R.id.downloadButton)
    public void downloadButton(View v){


        NetWorkHelper netWorkHelper = NetWorkHelper.getInstance();

        if (!netWorkHelper.hasWifi(this)) {
            final AlertDialog.Builder builder = new AlertDialog.Builder(ChooseTestProject.this);
            builder.setTitle("您未处于WiFi环境下");
            builder.setMessage("下载数据会产生大量流量，是否继续？");
            //builder.setIcon(R.drawable.ic_launcher);
            builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    // TODO Auto-generated method stub
                    Thread loginThread = new Thread(loginRunnable);
                    loginThread.start();

                }
            });
            builder.setNegativeButton("取消", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    // TODO Auto-generated method stub

                }
            });
            builder.setCancelable(false);
            builder.create().show();
        } else {
            Thread loginThread = new Thread(loginRunnable);
            loginThread.start();
        }

    }
    private Handler loginHandler = new Handler() {
        // 重写handleMessage()方法，此方法在UI线程运行
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                // 如果成功，则显示从网络获取到的图片
                case 1: {
                    Thread thread = new Thread(downloadRunnable);
                    downloadWindow.show();
                    thread.start();
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
                SharedPreferences sharedPreferences =getSharedPreferences("loginMessage", Context.MODE_PRIVATE);
                schoolPath=sharedPreferences.getString("schoolPath",null);
                String user=sharedPreferences.getString("user",null);
                String password=sharedPreferences.getString("password",null);
                Login loginlogic = new Login();
                LoginBean loginBean = loginlogic.login(user, password, schoolPath);
                boolean infoIsTrue = loginBean.isStatus();
                if (infoIsTrue) {
                    loginHandler.obtainMessage(1).sendToTarget();

                } else {
                    loginHandler.obtainMessage(0).sendToTarget();
                }
            } catch (Exception e) {
                loginHandler.obtainMessage(0).sendToTarget();
            }
        }
    };

    Runnable downloadRunnable = new Runnable() {
        // 重写run()方法，此方法在新的线程中运行
        @Override
        public void run() {

            try{
                DownloadWorker downloadWorker = new DownloadWorker(getApplication());
                boolean ok = downloadWorker.download(schoolPath, 2014, handler);
                if(ok){
                    handler.obtainMessage(-1).sendToTarget();
                }else{
                    handler.obtainMessage(-2).sendToTarget();
                }
            } catch (Exception e) {
                e.printStackTrace();

                //mHandler.obtainMessage(0).sendToTarget();
            }
        }
    };

    public void initDialog() {
        LayoutInflater factory = LayoutInflater.from(this);
        View view = factory.inflate(R.layout.download_window, null);
        downloadProgressBar = (ProgressBar)view.findViewById(R.id.downloadProgressBar);
        downloadTextView = (TextView)view.findViewById(R.id.downloadTextView);
        downloadWindow = new AlertDialog.Builder(this)
                .setTitle("正在下载数据")
                .setView(view)
                .create();
        downloadWindow.setCancelable(false);
    }

    class DownloadHandler extends Handler {

        @Override
        public void handleMessage(Message msg) {
            int index;
            String fileName;
            if (msg.what == 1) {
                index = msg.getData().getInt("index");
                if (index == -1) {
                    String rs = "下载完成";
                    Toast.makeText(ChooseTestProject.this, rs, Toast.LENGTH_SHORT).show();
                }else{
                    fileName = msg.getData().getString("fileName");
                    downloadProgressBar.setProgress(index);
                    downloadTextView.setText(fileName);
                }
            }

            if(msg.what == -1){
                String rs = "下载完成";
                Toast.makeText(ChooseTestProject.this, rs, Toast.LENGTH_SHORT).show();
                downloadWindow.hide();
            }

            if(msg.what == -2){
                String rs = "下载失败，请检查网络";
                Toast.makeText(ChooseTestProject.this, rs, Toast.LENGTH_SHORT).show();
            }

            super.handleMessage(msg);
        }

    }

}
