package com.nju.sphm.Controller.TimerActivity;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.SimpleAdapter;

import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.nju.sphm.Model.School.Student;
import com.nju.sphm.R;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;

/**
 * Created by HuangQiushuo on 2015/1/12.
 */
public class TimeRecordActivity extends Activity {
    private ArrayList<String> timeList;
    private ArrayList<Student> studentList;
    @ViewInject(R.id.listRecordTimeList)
    private ListView timeListView;
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
    Map<String, String> recordMap;
    private LinkedList<Map<String, Object>> timeItemList;
    private SimpleAdapter timeAdapter;
    private LinkedList<Map<String, Object>> studentItemList;
    private SimpleAdapter studentAdapter;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.record_time);
        ViewUtils.inject(this);
        Bundle bundle = this.getIntent().getExtras();
        timeList = bundle.getStringArrayList("timelist");
        inputmanger = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        recordMap = new HashMap<String, String>();
        initListView();
        initTimeList();
        initStudentList("");
        initSearchText();
        recordTimeMainView.setVisibility(View.VISIBLE);
        recordTimeSearchView.setVisibility(View.GONE);
    }

    private void initListView() {
        timeItemList = new LinkedList<Map<String, Object>>();
        timeAdapter = new SimpleAdapter(this, timeItemList, R.layout.record_time_listview_time_item,
                new String[]{"num", "recordTime", "studentName", "studentNumber", "studentSex", "fullStudentNumber"},
                new int[]{R.id.tvNum, R.id.tvRecordTime, R.id.tvStudentName,
                        R.id.tvStudentNumber, R.id.tvStudentSex, R.id.tvFullStudentNumber});
        timeListView.setAdapter(timeAdapter);
        timeAdapter.notifyDataSetChanged();
        timeListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
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
                attachStudentAndTime(fullStudentNumber, recordTime);
                timeAdapter.notifyDataSetChanged();
            }
        });
    }

    private void initStudentList(String classpath) {
        studentList = new ArrayList<Student>();
        Student s1 = new Student();
        s1.setName("王立军");
        s1.setSex(Student.MALE);
        s1.setStudentNumber("111111111111111111");
        Student s2 = new Student();
        s2.setName("孙俪");
        s2.setSex(Student.FEMALE);
        s2.setStudentNumber("111111111112222222");
        Student s3 = new Student();
        s3.setName("王思聪");
        s3.setSex(Student.MALE);
        s3.setStudentNumber("111111111111333333");
        studentList.add(s1);
        studentList.add(s2);
        studentList.add(s3);
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
                        String num = s.toString();
                        studentItemList.clear();
                        if (!num.equals("")){
                            for (Student student : studentList) {
                                if (student.getStudentNumberLastSixNum().contains(num)) {
                                    addSearchItem(student);
                                }
                            }
                        }
                        studentAdapter.notifyDataSetChanged();
                    }
                }
        );
    }


    private void addSearchItem(Student student) {
        Map<String, Object> item = new HashMap<String, Object>();
        item.put("studentName", student.getName());
        item.put("studentSex", student.getSex());
        item.put("studentNumber", student.getStudentNumberLastSixNum());
        item.put("fullStudentNumber", student.getStudentNumber());
        studentItemList.push(item);
    }

    public void attachStudentAndTime(String fullStudentNumber, String time) {
        recordMap.put(fullStudentNumber, time);
    }

    public void toggleKeyBoard() {
        inputmanger.toggleSoftInput(0, InputMethodManager.HIDE_NOT_ALWAYS);
    }

}
