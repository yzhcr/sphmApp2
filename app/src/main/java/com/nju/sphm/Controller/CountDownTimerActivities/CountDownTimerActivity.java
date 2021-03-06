package com.nju.sphm.Controller.CountDownTimerActivities;

/**
 * Created by HuangQiushuo on 2015/1/8.
 */

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.media.AudioManager;
import android.media.SoundPool;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TableLayout;
import android.widget.TextView;

import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.lidroid.xutils.view.annotation.event.OnClick;
import com.nju.sphm.Bean.OrganizationBean;
import com.nju.sphm.Bean.StudentBean;
import com.nju.sphm.Controller.TableActivities.ClassPickerDialog;
import com.nju.sphm.Model.DataHelper.DBManager;
import com.nju.sphm.Model.UIHelper.ChooseClassHelper;
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
    private ImageButton btn_choose;
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
    private ArrayList<StudentBean> maleStudentList=new ArrayList<StudentBean>();
    private ArrayList<StudentBean> femaleStudentList=new ArrayList<StudentBean>();
    private int whichIsChosen=0;
    private ChooseClassHelper chooseClassHelper = ChooseClassHelper.getInstance();
    @ViewInject(R.id.chooseSexLayout)
    private RelativeLayout chooseSexLayout;
    @ViewInject(R.id.clockLayout)
    private LinearLayout timeListView;
    @ViewInject(R.id.recordTableView)
    private LinearLayout recordTableView;
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
    @ViewInject(R.id.showAll)
    private TextView showAll;
    @ViewInject(R.id.showMale)
    private TextView showMale;
    @ViewInject(R.id.showFemale)
    private TextView showFemale;
    @ViewInject(R.id.tablescroll)
    private ScrollView scrollView;
    /**
     * Called when the activity is first created.
     */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.countdowntimer);
        ViewUtils.inject(this);
        chooseSexLayout.setVisibility(View.GONE);
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
        choseGrade= chooseClassHelper.getChoseGrade();
        choseClass= chooseClassHelper.getChoseClass();
        choseclass.setText(choseGrade+"年"+choseClass+"班");
        //chooseSexLayout.setVisibility(View.GONE);
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
            chooseClassHelper.setGradeNumMax(3);
            chooseClassHelper.setGradeNumMin(1);
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
                chooseClassHelper.setGradeNumMax(6);
                chooseClassHelper.setGradeNumMin(3);
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
                chooseClassHelper.setGradeNumMax(6);
                chooseClassHelper.setGradeNumMin(1);
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

        chooseClassHelper.setGradeList(sortGradeList);
    }

    @OnClick(R.id.changeClass)
    public void showDialog(View v)
    {
        ClassPickerDialog dialog  = new ClassPickerDialog(this);
        dialog.setOnClassSetListener(new ClassPickerDialog.OnClassSetListener() {
            public void OnClassSet(AlertDialog dialog, int choseGrade, int choseClass) {
                choseclass.setText(choseGrade+"年"+choseClass+"班");
                ChooseClassHelper chooseClassHelper = ChooseClassHelper.getInstance();
                chooseClassHelper.setChoseGrade(choseGrade);
                chooseClassHelper.setChoseClass(choseClass);
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

            showRecordTableView();
        }
    }

    private void showRecordTableView(){
        recordTableView.setVisibility(View.VISIBLE);
        timeListView.setVisibility(View.GONE);
        chooseSexLayout.setVisibility(View.VISIBLE);
    }

    private void hideRecordTableView(){
        recordTableView.setVisibility(View.GONE);
        timeListView.setVisibility(View.VISIBLE);
        chooseSexLayout.setVisibility(View.GONE);
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
        tableHelper.setDbManager(dbManager);
        tableHelper.setTableTitle(tableTitle, tableTitleString, this);
        tableHelper.setTable(table, tableTitleString, studentList,testProject,null,this);
    }

    private void refreshTable(){
        getStudentInfo();
        switch (whichIsChosen){
            case 0:
                tableHelper.setTable(table, tableTitleString, studentList,testProject,null,this);
                break;
            case 1:
                tableHelper.setTable(table, tableTitleString, maleStudentList,testProject,null,this);
                break;
            case 2:
                tableHelper.setTable(table, tableTitleString, femaleStudentList,testProject,null,this);
                break;
        }
        scrollToTop();
    }

    private void getStudentInfo(){
        int chosenGrade= chooseClassHelper.getChoseGrade();
        int chosenClass= chooseClassHelper.getChoseClass();
        String classID= chooseClassHelper.findClassId(chosenGrade,chosenClass);
        studentList=dbManager.getStudents(classID, testFileID);
        maleStudentList.clear();
        femaleStudentList.clear();
        for(StudentBean student:studentList){
            if(student.getSex().equals("男生"))
                maleStudentList.add(student);
            else
                femaleStudentList.add(student);
        }
    }

    @OnClick(R.id.showAll)
    public void showAll(View v){
        whichIsChosen=0;
        showAll.setTextColor(Color.WHITE);
        showAll.setBackgroundResource(R.drawable.showall_click);
        showMale.setTextColor(Color.BLACK);
        showMale.setBackgroundResource(R.drawable.showmale);
        showFemale.setTextColor(Color.BLACK);
        showFemale.setBackgroundResource(R.drawable.showfemale);
        tableHelper.setTable(table, tableTitleString, studentList,testProject,null,this);
        scrollToTop();
    }

    @OnClick(R.id.showMale)
    public void showMale(View v){
        whichIsChosen=1;
        showAll.setTextColor(Color.BLACK);
        showAll.setBackgroundResource(R.drawable.showall);
        showMale.setTextColor(Color.WHITE);
        showMale.setBackgroundResource(R.drawable.showmale_click);
        showFemale.setTextColor(Color.BLACK);
        showFemale.setBackgroundResource(R.drawable.showfemale);
        tableHelper.setTable(table, tableTitleString, maleStudentList,testProject,null,this);
        scrollToTop();
    }

    @OnClick(R.id.showFemale)
    public void showFemale(View v){
        whichIsChosen=2;
        showAll.setTextColor(Color.BLACK);
        showAll.setBackgroundResource(R.drawable.showall);
        showMale.setTextColor(Color.BLACK);
        showMale.setBackgroundResource(R.drawable.showmale);
        showFemale.setTextColor(Color.WHITE);
        showFemale.setBackgroundResource(R.drawable.showfemale_click);
        tableHelper.setTable(table, tableTitleString, femaleStudentList,testProject,null,this);
        scrollToTop();
    }

    private void scrollToTop(){
        scrollView.post(new Runnable() {
            @Override
            public void run() {
                // TODO Auto-generated method stub
                scrollView.fullScroll(ScrollView.FOCUS_UP);
            }
        });
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if(keyCode == KeyEvent.KEYCODE_BACK && event.getAction() == KeyEvent.ACTION_DOWN){
            if(recordTableView.getVisibility()==View.VISIBLE){
                hideRecordTableView();
                return true;
            }
        }
        return super.onKeyDown(keyCode, event);
    }
}