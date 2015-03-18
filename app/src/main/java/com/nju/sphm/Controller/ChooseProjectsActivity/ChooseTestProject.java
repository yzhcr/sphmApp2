package com.nju.sphm.Controller.ChooseProjectsActivity;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.GridView;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.lidroid.xutils.view.annotation.event.OnClick;
import com.nju.sphm.Bean.TestFileBean;
import com.nju.sphm.Bean.TestFileRowBean;
import com.nju.sphm.Controller.CountDownTimerActivity.CountDownTimerActivity;
import com.nju.sphm.Controller.TableActivity.TableActivity;
import com.nju.sphm.Controller.TimerActivity.TimerActivity;
import com.nju.sphm.Model.ChooseTestProject.ChooseTestFiles;
import com.nju.sphm.Model.DataHelper.DBManager;
import com.nju.sphm.Model.FinishTheApp.SaveMainActivity;
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

    //TestFileBean chosenTestFile=null;
    private ChooseTestFiles chooseTestFiles=ChooseTestFiles.getInstance();
    private String testFileId=null;
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

        int chosenTestFile=chooseTestFiles.getChosenTestFile();
        choseTestData.setText(testFileList.get(chosenTestFile).getFileName());
        /*testFileList = new ArrayList<TestFileBean>();
        TestFileBean bean = new TestFileBean();
        bean.setSchoolYear(2014);
        bean.setFileName("2014-2015学年国标上报数据");
        testFileList.add(bean);*/
        chooseTestFiles.setTestFileList(testFileList);
        testFileId=chooseTestFiles.getChosenTestFileId();
        //chooseTestFiles.setChosenTestFile(0);
        //chosenTestFile=testFileList.get(0);

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
        fiftyM.put("ItemText", "50米");
        HashMap<String, Object> fiftyMeight = new HashMap<String, Object>();
        fiftyMeight.put("ItemImage", R.drawable.fifty_m_eight);
        fiftyMeight.put("ItemText", "50*8米");
        HashMap<String, Object> yangwo = new HashMap<String, Object>();
        yangwo.put("ItemImage", R.drawable.yangwo);
        yangwo.put("ItemText", "1分钟仰卧起坐");
        HashMap<String, Object> tiaosheng = new HashMap<String, Object>();
        tiaosheng.put("ItemImage", R.drawable.tiaosheng);
        tiaosheng.put("ItemText", "1分钟跳绳");
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
                case "50米": {
                    Intent i = new Intent();
                    i.putExtra("schoolid", schoolid);
                    i.putExtra("schoolpath", schoolPath);
                    i.putExtra("testProject","50米");
                    i.putExtra("testFileId",testFileId);
                    i.setClass(ChooseTestProject.this, TimerActivity.class);
                    startActivity(i);
                    break;
                }
                case "50*8米": {
                    Intent i = new Intent();
                    i.putExtra("schoolid", schoolid);
                    i.putExtra("schoolpath", schoolPath);
                    i.putExtra("testProject","50*8米");
                    i.putExtra("testFileId",testFileId);
                    i.setClass(ChooseTestProject.this, TimerActivity.class);
                    startActivity(i);
                    break;
                }
                case "1分钟仰卧起坐": {
                    Intent i = new Intent();
                    i.putExtra("schoolid", schoolid);
                    i.putExtra("schoolpath", schoolPath);
                    i.putExtra("testProject","1分钟仰卧起坐");
                    i.putExtra("starttime","01:00");
                    i.putExtra("testFileId",testFileId);
                    i.setClass(ChooseTestProject.this, CountDownTimerActivity.class);
                    startActivity(i);
                    break;
                }
                case "1分钟跳绳": {
                    Intent i = new Intent();
                    i.putExtra("schoolid", schoolid);
                    i.putExtra("schoolpath", schoolPath);
                    i.putExtra("starttime","01:00");
                    i.putExtra("testProject","1分钟跳绳");
                    i.putExtra("testFileId",testFileId);
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
                    SaveMainActivity.getInstance().getMainActivity().finish();
                    this.finish();
            }
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

}
