package com.nju.sphm.Controller.TimerActivity;

/**
 * Created by HuangQiushuo on 2015/1/6.
 */

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.SimpleAdapter;
import android.widget.TableLayout;
import android.widget.TextView;

import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.lidroid.xutils.view.annotation.event.OnClick;
import com.nju.sphm.Bean.OrganizationBean;
import com.nju.sphm.Bean.StudentBean;
import com.nju.sphm.Controller.TableActivity.ClassPickerDialog;
import com.nju.sphm.Model.DataHelper.DBManager;
import com.nju.sphm.Model.UIHelper.GB2Helper;
import com.nju.sphm.Model.UIHelper.GetClass;
import com.nju.sphm.Model.UIHelper.TableHelper;
import com.nju.sphm.R;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;

public class TimerActivity extends Activity {
    private long mlCount = 0;
    private long mlTimerUnit = 100;
    @ViewInject(R.id.tvTimer)
    private TextView tvTime;
    @ViewInject(R.id.btnStart)
    private Button btnStart;
    @ViewInject(R.id.btnStop)
    private Button btnStop;
    @ViewInject(R.id.listTimeList)
    private ListView listTimeListView;
    @ViewInject(R.id.clockLayout)
    private LinearLayout clockLayout;
    private Timer timer = null;
    private TimerTask task = null;
    private Handler handler = null;
    private Message msg = null;
    private boolean bIsRunningFlg = false;
    private boolean isPause = false;
    private boolean isStop = false;
    private int min = 0;
    private int sec = 0;
    private int msec = 0;
    private ArrayList<String> timeList = new ArrayList<String>();
    private LinkedList<Map<String, Object>> timeItemList;
    private SimpleAdapter adapter;
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
    private ArrayList<StudentBean> studentList=new ArrayList<StudentBean>();
    private ArrayList<StudentBean> maleStudentList=new ArrayList<StudentBean>();
    private ArrayList<StudentBean> femaleStudentList=new ArrayList<StudentBean>();
    private ArrayList<StudentBean> allStudentList=new ArrayList<StudentBean>();
    private GetClass getClass=GetClass.getInstance();
    private String classId;
    private int choseGrade;
    private int choseClass;
    @ViewInject(R.id.showAll)
    private TextView showAll;
    @ViewInject(R.id.showMale)
    private TextView showMale;
    @ViewInject(R.id.showFemale)
    private TextView showFemale;
    private int whichIsChosen=0;
    private String tableTitleString;
    @ViewInject(R.id.tabletitle2)
    private TableLayout tableTitle;
    @ViewInject(R.id.tablelayout2)
    private TableLayout table;
    private String testFileID;
    private TableHelper tableHelper = new TableHelper();
    @ViewInject(R.id.tablescroll)
    private ScrollView scrollView;
    @ViewInject(R.id.recordTableView)
    private LinearLayout recordTableView;

    @ViewInject(R.id.listRecordTimeList)
    private ListView recordTimeListView;
    @ViewInject(R.id.listStudentSearchListView)
    private ListView studentListView;
    @ViewInject(R.id.recordTimeMainView)
    private RelativeLayout recordTimeMainView;
    @ViewInject(R.id.recordTimeSearchView)
    private LinearLayout recordTimeSearchView;
    @ViewInject(R.id.recordTimeSearchTextView)
    private EditText tvSearch;
    private Map<String, Object> clickedTimeItem;
    private InputMethodManager inputmanger;
    Map<String, String> recordMap = new HashMap<String, String>();
    private SimpleAdapter timeAdapter;
    private LinkedList<Map<String, Object>> studentItemList;
    private SimpleAdapter studentAdapter;
    @ViewInject(R.id.btnListen)
    private Button showScoreButton;


    /**
     * Called when the activity is first created.
     */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.timer);
        ViewUtils.inject(this);
        showScoreButton.setText("成绩查看");
        tvTime.setText(R.string.init_time_100millisecond);
        initHandler();
        initListView();
        Intent intent=getIntent();
        schoolid=intent.getStringExtra("schoolid");
        schoolPath=intent.getStringExtra("schoolpath");
        testProject=intent.getStringExtra("testProject");
        testFileID=intent.getStringExtra("testFileId");
        title.setText(testProject);
        addClassInfo();
        choseGrade=getClass.getChoseGrade();
        choseClass=getClass.getChoseClass();
        choseclass.setText(choseGrade+"年"+choseClass+"班");
        inputmanger = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        getStudentInfo();
        tableTitleString=intent.getStringExtra("tableTitle");
        initTable();
        tableHelper.setAllEditTextUnEdited();
    }

    @OnClick(R.id.btnListen)
    public void showTable(View v){
        recordTableView.setVisibility(View.VISIBLE);
        listTimeListView.setVisibility(View.GONE);
        recordTimeMainView.setVisibility(View.GONE);
        recordTimeSearchView.setVisibility(View.GONE);
        clockLayout.setVisibility(View.GONE);
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

    private void scrollToTop(){
        scrollView.post(new Runnable() {
            @Override
            public void run() {
                // TODO Auto-generated method stub
                scrollView.fullScroll(ScrollView.FOCUS_UP);
            }
        });
    }

    //将班级信息添加到GetCLass中，方便使用
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
            if(testProject.equals("50米×8往返跑")) {
                getClass.setGradeNumMax(6);
                getClass.setGradeNumMin(5);
                //getClass.setChoseGrade(5);
                //getClass.setChoseClass(1);
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

    private void initListView() {
        timeItemList = new LinkedList<Map<String, Object>>();
        adapter = new SimpleAdapter(this, timeItemList, R.layout.record_time_listview_time_item,
                new String[]{"num", "recordTime"},
                new int[]{R.id.tvNum, R.id.tvRecordTime});
        listTimeListView.setAdapter(adapter);
        adapter.notifyDataSetChanged();
    }

    private void initHandler() {
        // Handle timer message
        handler = new Handler() {
            @Override
            public void handleMessage(Message msg) {
                // TODO Auto-generated method stub
                switch (msg.what) {
                    case 1:
                        mlCount++;
                        int totalSec = 0;
                        msec = 0;
                        totalSec = (int) (mlCount / 10);
                        msec = (int) (mlCount % 10);
                        // Set time display
                        min = (totalSec / 60);
                        sec = (totalSec % 60);
                        try {
                            if(min>0){
                                tvTime.setText(String.format("%1$02d:%2$02d", min, sec));
                            }else {
                                tvTime.setText(String.format("%1$02d:%2$02d.%3$d", min, sec, msec));
                            }
                        } catch (Exception e) {
                            tvTime.setText(String.format("%1$02d:%2$02d.%3$d", min, sec, msec));
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

    @OnClick(R.id.btnStart)
    private void startAndClick(View v) {
        if (isStop) {
            showRecordTimeView();
        } else {
            if (null == timer) {
                if (null == task) {
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

                timer = new Timer(true);
                timer.schedule(task, mlTimerUnit, mlTimerUnit); // set timer duration
            }

            // start
            if (!bIsRunningFlg) {
                bIsRunningFlg = true;
                btnStart.setText("计次");
                btnStop.setText("停止");
                isPause = true;
            } else { // pause
                try {
                    recordTime();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }

    @OnClick(R.id.btnStop)
    public void stop(View v) {
        if (isPause) {
            btnStop.setText("重置");
            task.cancel();
            isPause = false;
            btnStart.setText("登记");
            isStop = true;
        } else {
            reset();
        }
    }

    public void reset() {
        btnStop.setText("停止");
        if (null != timer) {
            task.cancel();
            task = null;
            timer.cancel(); // Cancel timer
            timer.purge();
            timer = null;
            handler.removeMessages(msg.what);
        }
        mlCount = 0;
        bIsRunningFlg = false;
        isStop = false;
        btnStart.setText("开始");
        initListView();
        tvTime.setText(R.string.init_time_100millisecond);
        timeList = new ArrayList<String>();
    }

    public void recordTime() {
        String time = formatTimeString(min, sec, msec);
        timeList.add(time);
        int count = timeList.size();
        Map<String, Object> timeItemMap = new HashMap<String, Object>();
        //"num","recordTime"
        timeItemMap.put("num", count);
        timeItemMap.put("recordTime", time);
        timeItemList.push(timeItemMap);
        adapter.notifyDataSetChanged();
    }

    public String formatTimeString(int min, int s, int ms){
        if(min == 0){
            return s + "." +ms;
        }else{
            return min + "'" + s;
        }
    }

    private void initTimeRecordListView() {
        timeItemList = new LinkedList<Map<String, Object>>();
        timeAdapter = new SimpleAdapter(this, timeItemList, R.layout.record_time_listview_time_item,
                new String[]{"num", "recordTime", "studentName", "studentNumber", "studentSex", "fullStudentNumber"},
                new int[]{R.id.tvNum, R.id.tvRecordTime, R.id.tvStudentName,
                        R.id.tvStudentNumber, R.id.tvStudentSex, R.id.tvFullStudentNumber});
        recordTimeListView.setAdapter(timeAdapter);
        timeAdapter.notifyDataSetChanged();
        recordTimeListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                clickedTimeItem = (Map<String, Object>) timeAdapter.getItem(position);
                studentItemList.clear();
                studentAdapter.notifyDataSetChanged();
                recordTimeMainView.setVisibility(View.GONE);
                recordTimeSearchView.setVisibility(View.VISIBLE);
                tvSearch.setText("");
                tvSearch.requestFocus();
                toggleKeyBoard();
            }
        });

        studentItemList = new LinkedList<Map<String, Object>>();
        studentAdapter = new SimpleAdapter(this, studentItemList, R.layout.record_time_listview_student_item,
                new String[]{"studentName", "studentNumber", "studentSex", "fullStudentNumber"},
                new int[]{R.id.tvStudentName,
                        R.id.tvStudentNumber, R.id.tvStudentSex, R.id.tvFullStudentNumber});
        studentListView.setAdapter(studentAdapter);
        studentAdapter.notifyDataSetChanged();
        studentListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Map<String, Object> studentItem = (Map<String, Object>) studentAdapter.getItem(position);
                toggleKeyBoard();
                recordTimeMainView.setVisibility(View.VISIBLE);
                recordTimeSearchView.setVisibility(View.GONE);
                clickedTimeItem.put("studentName", studentItem.get("studentName"));
                clickedTimeItem.put("studentSex", studentItem.get("studentSex"));
                clickedTimeItem.put("studentNumber", studentItem.get("studentNumber"));
                clickedTimeItem.put("fullStudentNumber", studentItem.get("fullStudentNumber"));
                String fullStudentNumber = (String) studentItem.get("fullStudentNumber");
                String recordTime = (String) clickedTimeItem.get("recordTime");
                for(StudentBean bean : studentList){
                    if(bean.getStudentCode().equals(fullStudentNumber)){
                        bean.setScore(testProject, recordTime);
                        dbManager.addTestFileRow(bean.getTestFileRow());
                        System.out.println(testProject+":"+recordTime);
                        refreshTable();
                        break;
                    }
                }
                timeAdapter.notifyDataSetChanged();
            }
        });
    }

    private void getStudentInfo(){
        int chosenGrade=getClass.getChoseGrade();
        int chosenClass=getClass.getChoseClass();
        String classID=getClass.findClassId(chosenGrade,chosenClass);
        allStudentList=dbManager.getStudents(classID, testFileID);
        maleStudentList.clear();
        femaleStudentList.clear();
        for(StudentBean student:allStudentList){
            if(student.getSex().equals("男生"))
                maleStudentList.add(student);
            else
                femaleStudentList.add(student);
        }
        switch (whichIsChosen){
            case 0:
                studentList = allStudentList;
                break;
            case 1:
                studentList = maleStudentList;
                break;
            case 2:
                studentList = femaleStudentList;
                break;
        }
    }

    private void initTimeList() {
        int count = 0;
        for (String time : timeList) {
            count++;
            Map<String, Object> timeItemMap = new HashMap<String, Object>();
            //"num","recordTime"
            timeItemMap.put("num", count);
            timeItemMap.put("recordTime", time);
            timeItemList.add(timeItemMap);
        }

        timeAdapter.notifyDataSetChanged();
    }

    private void initSearchText() {

        tvSearch.addTextChangedListener(
                new TextWatcher() {
                    @Override
                    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                    }

                    @Override
                    public void onTextChanged(CharSequence s, int start, int before, int count) {

                    }

                    @Override
                    public void afterTextChanged(Editable s) {
                        GB2Helper gb2Helper = GB2Helper.getInstance();
                        String num = s.toString();
                        studentItemList.clear();
                        if (!num.equals("")){
                            for (StudentBean student : studentList) {
//                                if (student.getStudentNumberLastSixNum().contains(num)) {
//                                    addSearchItem(student);
//                                }
                                String str = gb2Helper.String2Alpha(student.getName());
                                //System.out.println(str);
                                if(str.contains(num)){
                                    addSearchItem(student);
                                }
                            }
                        }
                        studentAdapter.notifyDataSetChanged();
                    }
                }
        );
    }


    private void addSearchItem(StudentBean student) {
        Map<String, Object> item = new HashMap<String, Object>();
        item.put("studentName", student.getName());
        item.put("studentSex", student.getSex());
        item.put("studentNumber", student.getStudentNumberLastSixNum());
        item.put("fullStudentNumber", student.getStudentCode());
        studentItemList.push(item);
    }

    public void attachStudentAndTime(String fullStudentNumber, String time) {
        recordMap.put(fullStudentNumber, time);
    }

    public void toggleKeyBoard() {
        inputmanger.toggleSoftInput(0, InputMethodManager.HIDE_NOT_ALWAYS);
    }

    public void showRecordTimeView(){
        initTimeRecordListView();
        initTimeList();
        initSearchText();
        listTimeListView.setVisibility(View.GONE);
        recordTimeMainView.setVisibility(View.VISIBLE);
        recordTimeSearchView.setVisibility(View.GONE);
        clockLayout.setVisibility(View.GONE);

    }

    public void hideRecordTimeView(){
        recordTimeMainView.setVisibility(View.GONE);
        recordTimeSearchView.setVisibility(View.GONE);
        clockLayout.setVisibility(View.VISIBLE);
        listTimeListView.setVisibility(View.VISIBLE);
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if(keyCode == KeyEvent.KEYCODE_BACK && event.getAction() == KeyEvent.ACTION_DOWN){
            if(recordTimeMainView.getVisibility()==View.VISIBLE){
                hideRecordTimeView();
                return true;
            }else if(recordTimeSearchView.getVisibility()==View.VISIBLE){
                recordTimeMainView.setVisibility(View.VISIBLE);
                recordTimeSearchView.setVisibility(View.GONE);
                return true;
            }else if(recordTableView.getVisibility()==View.VISIBLE){
                hideRecordTimeView();
                recordTableView.setVisibility(View.GONE);
                return true;
            }
        }
        return super.onKeyDown(keyCode, event);
    }

    @OnClick(R.id.showAll)
    public void showAll(View v){
        showAll.setTextColor(Color.WHITE);
        showAll.setBackgroundResource(R.drawable.showall_click);
        showMale.setTextColor(Color.BLACK);
        showMale.setBackgroundResource(R.drawable.showmale);
        showFemale.setTextColor(Color.BLACK);
        showFemale.setBackgroundResource(R.drawable.showfemale);
        studentList = allStudentList;
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
        studentList = maleStudentList;
        tableHelper.setTable(table, tableTitleString, studentList,testProject,null,this);
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
        studentList = femaleStudentList;
        tableHelper.setTable(table, tableTitleString, studentList,testProject,null,this);
        scrollToTop();
    }
}