package com.nju.sphm.Controller.CountDownTimerActivity;

/**
 * Created by HuangQiushuo on 2015/1/8.
 */

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.media.AudioManager;
import android.media.SoundPool;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TableLayout;
import android.widget.TextView;

import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.lidroid.xutils.view.annotation.event.OnClick;
import com.nju.sphm.Bean.OrganizationBean;
import com.nju.sphm.Bean.StudentBean;
import com.nju.sphm.Controller.TableActivity.ClassPickerDialog;
import com.nju.sphm.Model.DataHelper.DBManager;
import com.nju.sphm.Model.UIHelper.GetClass;
import com.nju.sphm.Model.UIHelper.TableHelper;
import com.nju.sphm.R;

import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;

public class CountDownTimerActivity extends Activity {
    private long mlTimerUnit = 1000;
    @ViewInject(R.id.tvTimer)
    private TextView tvTime;
    @ViewInject(R.id.btnStart)
    private Button btnStart;
    @ViewInject(R.id.btnStop)
    private Button btnStop;
    private EditText etMin;
    @ViewInject(R.id.btnListen)
    private Button btnListen;
    private EditText etSec;
    private AlertDialog setTimeWindow;
    private Timer timer = null;
    private TimerTask task = null;
    private Handler handler = null;
    private Message msg = null;
    private boolean bIsRunningFlg = false;
    private boolean isTimeOver = false;
    private boolean isStop = false;
    private boolean isReady = false;
    private boolean isRinging = false;
    private int min = 0;
    private int sec = 0;
    private int initMin = 0;
    private int initSec = 0;
    private int totalSec = 0;
    private SoundPool soundPool;
    private int playRingId;
    private String startTime;
    @ViewInject(R.id.changeClass)
    private Button btn_choose;
    @ViewInject(R.id.choseclass)
    private TextView choseclass;
    @ViewInject(R.id.title)
    private TextView title;
    private DBManager dbManager=null;
    private String schoolid=null;
    private String schoolPath=null;
    private String testProject=null;
    private ArrayList<OrganizationBean> gradeList=null;
    private ArrayList<StudentBean> studentList=null;
    private GetClass getClass=GetClass.getInstance();
    @ViewInject(R.id.chooseSexLayout)
    private RelativeLayout chooseSexLayout;
    @ViewInject(R.id.timeListView)
    private LinearLayout timeListView;
    @ViewInject(R.id.recordTableView)
    private LinearLayout recordTableView;
    @ViewInject(R.id.recordTableReturn)
    private Button returnBtn;
    @ViewInject(R.id.listTimeList)
    private ListView recordTimeListView;
    private String classId;
    private int choseGrade;
    private int choseClass;
    private String tableTitleString;
    @ViewInject(R.id.tabletitle2)
    private TableLayout tableTitle;
    @ViewInject(R.id.tablelayout2)
    private TableLayout table;
    private String testFileID;
    private TableHelper tableHelper = new TableHelper();
    /**
     * Called when the activity is first created.
     */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.timer);
        ViewUtils.inject(this);
        initDialog();
        initRing();
        Intent intent=getIntent();
        schoolid=intent.getStringExtra("schoolid");
        schoolPath=intent.getStringExtra("schoolpath");
        testProject=intent.getStringExtra("testProject");
        startTime=intent.getStringExtra("starttime");
        testFileID=intent.getStringExtra("testFileId");
        tvTime.setText(startTime);
        initTime(startTime);
        tvTime.setClickable(false);
        title.setText(testProject);
        addClassInfo();
        choseGrade=getClass.getChoseGrade();
        choseClass=getClass.getChoseClass();
        choseclass.setText(choseGrade+"年"+choseClass+"班");
        chooseSexLayout.setVisibility(View.GONE);
        recordTimeListView.setVisibility(View.GONE);
        tableTitleString=intent.getStringExtra("tableTitle");
        initTable();

    }

    private void initTime(String time){
        String[] times = time.split(":");
        etMin.setText(times[0]);
        etSec.setText(times[1]);
        timeOK();

    }


    private void addClassInfo(){
        dbManager=new DBManager(this);
        gradeList=dbManager.getOrganizations(schoolid);
        ArrayList<OrganizationBean> sortGradeList=new ArrayList<OrganizationBean>();
        int gradeNum=gradeList.size();
        if(gradeNum==3){
            getClass.setGradeNumMax(3);
            getClass.setGradeNumMin(1);
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
            if(testProject.equals("1分钟仰卧起坐")) {
                getClass.setGradeNumMax(6);
                getClass.setGradeNumMin(3);
                //getClass.setChoseGrade(3);
                //getClass.setChoseClass(1);
                for (OrganizationBean o : gradeList) {
                    if (o.getName().equals("三年级")) {
                        sortGradeList.add(o);
                    }
                }
                for (OrganizationBean o : gradeList) {
                    if (o.getName().equals("四年级")) {
                        sortGradeList.add(o);
                    }
                }
                for (OrganizationBean o : gradeList) {
                    if (o.getName().equals("五年级")) {
                        sortGradeList.add(o);
                    }
                }
                for (OrganizationBean o : gradeList) {
                    if (o.getName().equals("六年级")) {
                        sortGradeList.add(o);
                    }
                }
            }
            else{
                getClass.setGradeNumMax(6);
                getClass.setGradeNumMin(1);
                //getClass.setChoseGrade(1);
                //getClass.setChoseClass(1);
                for (OrganizationBean o : gradeList) {
                    if (o.getName().equals("一年级")) {
                        sortGradeList.add(o);
                    }
                }
                for (OrganizationBean o : gradeList) {
                    if (o.getName().equals("二年级")) {
                        sortGradeList.add(o);
                    }
                }
                for (OrganizationBean o : gradeList) {
                    if (o.getName().equals("三年级")) {
                        sortGradeList.add(o);
                    }
                }
                for (OrganizationBean o : gradeList) {
                    if (o.getName().equals("四年级")) {
                        sortGradeList.add(o);
                    }
                }
                for (OrganizationBean o : gradeList) {
                    if (o.getName().equals("五年级")) {
                        sortGradeList.add(o);
                    }
                }
                for (OrganizationBean o : gradeList) {
                    if (o.getName().equals("六年级")) {
                        sortGradeList.add(o);
                    }
                }
            }
        }

        getClass.setGradeList(sortGradeList);
    }

    @OnClick(R.id.changeClass)
    public void showDialog(View v)
    {
        ClassPickerDialog dialog  = new ClassPickerDialog(this);
        dialog.setOnClassSetListener(new ClassPickerDialog.OnClassSetListener() {
            public void OnClassSet(AlertDialog dialog, int choseGrade, int choseClass) {
                choseclass.setText(choseGrade+"年"+choseClass+"班");
                GetClass getClass=GetClass.getInstance();
                getClass.setChoseGrade(choseGrade);
                getClass.setChoseClass(choseClass);
                refreshTable();
            }
        });
        dialog.show();
    }

    public void initRing() {
        soundPool = new SoundPool(10, AudioManager.STREAM_MUSIC, 5);
        soundPool.load(this, R.raw.ring, 1);
        btnListen.setVisibility(View.VISIBLE);
    }

    public void initDialog() {
        LayoutInflater factory = LayoutInflater.from(this);
        View view = factory.inflate(R.layout.set_time_window, null);
        btnStart.setClickable(true);
        setTimeWindow = new AlertDialog.Builder(this)
                .setTitle("设置初始时间")
                .setView(view)
                .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        timeOK();
                    }
                })
                .setNegativeButton("取消", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                    }
                }).create();
        etSec = (EditText) view.findViewById(R.id.etSec);
        etSec.addTextChangedListener(new TextWatcher() {


            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                // TODO Auto-generated method stub

            }

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count,
                                          int after) {
                // TODO Auto-generated method stub
//
            }

            @Override
            public void afterTextChanged(Editable s) {
                // TODO Auto-generated method stub
                try {
                    if (!s.toString().equals("")) {
                        int time = Integer.valueOf(s.toString());
                        if (time >= 60) {
                            etSec.setText("00");
                        }
                    } else {
                        etSec.setText("00");
                    }
                } catch (Exception e) {
                }
            }
        });

        etMin = (EditText) view.findViewById(R.id.etMin);
        etMin.addTextChangedListener(new TextWatcher() {


            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                // TODO Auto-generated method stub

            }

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count,
                                          int after) {
                // TODO Auto-generated method stub
//
            }

            @Override
            public void afterTextChanged(Editable s) {
                // TODO Auto-generated method stub
                try {
                    if (!s.toString().equals("")) {
                        int time = Integer.valueOf(s.toString());
                        if (s.length() >= 2 && time > 0) {
                            etSec.requestFocus();
                        }
                    } else {
                        etMin.setText("00");
                    }
                } catch (Exception e) {

                }
            }
        });

        // Handle timer message
    }

    @OnClick(R.id.btnStart)
    private void startAndClick(View v) {
        if (null == timer) {
            if (null == task) {
                if (handler != null) {
                    task = new TimerTask() {

                        @Override
                        public void run() {
                            // TODO Auto-generated method stub
                            if (null == msg) {
                                msg = new Message();
                            } else {
                                msg = Message.obtain();
                            }
                            msg.what = 1;
                            handler.sendMessage(msg);
                        }

                    };
                }
            }

            timer = new Timer(true);
            timer.schedule(task, mlTimerUnit, mlTimerUnit); // set timer duration
        }

        // start
        if (!bIsRunningFlg) {
            bIsRunningFlg = true;
            btnStart.setClickable(false);
            btnStop.setText("停止");
        } else {
            //跳转到记录界面
            System.out.println("jieshu");
            showRecordTableView();
        }
    }

    private void showRecordTableView(){
        recordTableView.setVisibility(View.VISIBLE);
        timeListView.setVisibility(View.GONE);
    }

    @OnClick(R.id.recordTableReturn)
    private void hideRecordTableView(View v){
        recordTableView.setVisibility(View.GONE);
        timeListView.setVisibility(View.VISIBLE);
    }

    @OnClick(R.id.btnStop)
    public void stop(View v) {
        bIsRunningFlg = false;
        btnStart.setClickable(true);
        if (isTimeOver) {
            ringOff();
            btnStop.setText("重置");
            btnStart.setText("登记");
            isTimeOver = false;
            bIsRunningFlg = true;
        } else {
            reset();
        }
    }

    public void reset() {
        btnStop.setText("停止");
        bIsRunningFlg = false;
        isTimeOver = false;
        isRinging = false;
        btnStart.setText("开始");
        btnStart.setClickable(true);
        totalSec = initMin * 60 + initSec;
        if (null != timer) {
            task.cancel();
            task = null;
            timer.cancel(); // Cancel timer
            timer.purge();
            timer = null;
            handler.removeMessages(msg.what);
        }
        tvTime.setText(String.format("%1$02d:%2$02d", initMin, initSec));

    }

    @OnClick(R.id.tvTimer)
    public void setBeginTime(View v) {
        totalSec = initMin * 60 + initSec;
        if (!isReady) {
            String str = tvTime.getText().toString();
            String m = str.split(":")[0];
            String s = str.split(":")[1];
            etMin.setText(m);
            etSec.setText(s);
            setTimeWindow.show();
        }
    }

    public void timeOK() {
        initMin = Integer.valueOf((etMin.getText().toString()));
        initSec = Integer.valueOf((etSec.getText().toString()));
        tvTime.setText(String.format("%1$02d:%2$02d", initMin, initSec));
        totalSec = initMin * 60 + initSec;
        if (totalSec > 0) {
            btnStart.setClickable(true);
            handler = new Handler() {
                @Override
                public void handleMessage(Message msg) {
                    // TODO Auto-generated method stub

                    switch (msg.what) {
                        case 1:
                            totalSec--;
                            // Set time display
                            min = (totalSec / 60);
                            sec = (totalSec % 60);
                            try {
                                tvTime.setText(String.format("%1$02d:%2$02d", min, sec));
                                if (totalSec == 0) {
                                    timer.cancel();
                                    timeOver();
                                }
                            } catch (Exception e) {
                                tvTime.setText(min + ":" + sec);
                                e.printStackTrace();
                            }
                            break;
                        default:
                            break;
                    }
                    super.handleMessage(msg);
                }

            };
        }
    }

    public void timeOver() {
        isTimeOver = true;
        btnStop.setText("结束");
        ringOn();
    }

    @OnClick(R.id.btnListen)
    public void listen(View v) {
//        if (isRinging) {
//            ringOff();
//            btnListen.setText("响铃试听");
//        } else {
//            ringOn();
//            btnListen.setText("停止");
//        }
        showRecordTableView();
    }

    public void ringOn() {
        isRinging = true;
        playRingId = soundPool.play(1, 1, 1, -1, 0, 1);
    }

    public void ringOff() {
        isRinging = false;
        soundPool.stop(playRingId);
    }

    private void initTable(){
        getStudentInfo();
        tableHelper.setTableTitle(tableTitle, tableTitleString, this);
        tableHelper.setTable(table, tableTitleString, studentList,testProject,null,this);
    }

    private void refreshTable(){
        getStudentInfo();
        tableHelper.setTable(table, tableTitleString, studentList,testProject,null,this);
    }

    private void getStudentInfo(){
        int chosenGrade=getClass.getChoseGrade();
        int chosenClass=getClass.getChoseClass();
        String classID=getClass.findClassId(chosenGrade,chosenClass);
        studentList=dbManager.getStudents(classID, testFileID);
        /*for(StudentBean s:studentList){
            HashMap<String, Object> info=s.getInfo();
            Iterator iterator = info.entrySet().iterator();
            while (iterator.hasNext()) {
                Map.Entry entry = (Map.Entry) iterator.next();
                Object key = entry.getKey();
                Object val = entry.getValue();
                System.out.println(key);
                System.out.println(val);
                System.out.println("-----------------");
            }
            System.out.println("******************");
        }*/
    }

}