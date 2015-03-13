package com.nju.sphm.Controller.TimerActivity;

/**
 * Created by HuangQiushuo on 2015/1/6.
 */

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;

import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.lidroid.xutils.view.annotation.event.OnClick;
import com.nju.sphm.Bean.OrganizationBean;
import com.nju.sphm.Bean.StudentBean;
import com.nju.sphm.Controller.TableActivity.ClassPickerDialog;
import com.nju.sphm.Model.DataHelper.DBManager;
import com.nju.sphm.Model.School.GetClass;
import com.nju.sphm.R;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;

public class TimerActivity extends Activity {
    private long mlCount = 0;
    private long mlTimerUnit = 10;
    @ViewInject(R.id.tvTimer)
    private TextView tvTime;
    @ViewInject(R.id.btnStart)
    private Button btnStart;
    @ViewInject(R.id.btnStop)
    private Button btnStop;
    @ViewInject(R.id.listTimeList)
    private ListView timeListView;
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
    @ViewInject(R.id.StudentList)
    ListView lv;
    @ViewInject(R.id.title)
    TextView title;
    DBManager dbManager=null;
    String schoolid=null;
    String schoolPath=null;
    String testProject=null;
    ArrayList<OrganizationBean> gradeList=null;
    ArrayList<StudentBean> studentList=null;
    GetClass getClass=GetClass.getInstance();


    /**
     * Called when the activity is first created.
     */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.timer);
        ViewUtils.inject(this);
        tvTime.setText("00:00.00");
        initHandler();
        initListView();
        Intent intent=getIntent();
        schoolid=intent.getStringExtra("schoolid");
        schoolPath=intent.getStringExtra("schoolpath");
        testProject=intent.getStringExtra("testProject");
        title.setText(testProject);
        int choseGrade=getClass.getChoseGrade();
        int choseClass=getClass.getChoseClass();
        choseclass.setText(choseGrade+"年"+choseClass+"班");

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
                System.out.println(getClass.findClassId(choseGrade,choseClass));
            }
        });
        dialog.show();
    }

    private void initListView() {
        timeItemList = new LinkedList<Map<String, Object>>();
        adapter = new SimpleAdapter(this, timeItemList, R.layout.record_time_listview_time_item,
                new String[]{"num", "recordTime"},
                new int[]{R.id.tvNum, R.id.tvRecordTime});
        timeListView.setAdapter(adapter);
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
                        totalSec = (int) (mlCount / 100);
                        msec = (int) (mlCount % 100);
                        // Set time display
                        min = (totalSec / 60);
                        sec = (totalSec % 60);
                        try {
                            tvTime.setText(String.format("%1$02d:%2$02d.%3$02d", min, sec, msec));
                        } catch (Exception e) {
                            tvTime.setText("" + min + ":" + sec + "." + msec);
                            e.printStackTrace();
                            Log.e("MyTimer onCreate", "Format string error.");
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
            //跳转到登记界面，待实现
            Intent intent = new Intent(this, TimeRecordActivity.class);

            //用Bundle携带数据
            Bundle bundle = new Bundle();
            bundle.putStringArrayList("timelist", timeList);
            intent.putExtras(bundle);
            startActivity(intent);
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
        String time = String.format("%1$02d:%2$02d.%3$02d", min, sec, msec);
        timeList.add(time);
        int count = timeList.size();
        Map<String, Object> timeItemMap = new HashMap<String, Object>();
        //"num","recordTime"
        timeItemMap.put("num", count);
        timeItemMap.put("recordTime", time);
        timeItemList.push(timeItemMap);
        adapter.notifyDataSetChanged();
    }

}