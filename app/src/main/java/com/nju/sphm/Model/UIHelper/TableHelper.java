package com.nju.sphm.Model.UIHelper;

import android.app.Activity;
import android.graphics.Color;
import android.text.InputType;
import android.view.Gravity;
import android.view.View;
import android.widget.EditText;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import com.nju.sphm.Bean.StudentBean;
import com.nju.sphm.Model.DataHelper.DBManager;
import com.nju.sphm.Model.DataHelper.WebViewHelper;

import java.text.DecimalFormat;
import java.util.ArrayList;

/**
 * Created by hcr1 on 2015/3/19.
 */
public class TableHelper {
    private DBManager dbManager;
    private EditText lastEditText;
    private ArrayList<EditText> editTextList=new ArrayList<EditText>();
    public DBManager getDbManager() {
        return dbManager;
    }

    public void setDbManager(DBManager dbManager) {
        this.dbManager = dbManager;
    }

    private void saveData(StudentBean studentBean,String score,String testName){
        //dbManager.
        studentBean.setScore(testName,score);
        dbManager.addTestFileRow(studentBean.getTestFileRow());
    }

    private void saveScore(Activity activity,String score,String testName,String grade,StudentBean studentBean){
        WebViewHelper webViewHelper=new WebViewHelper(activity);
        webViewHelper.countScore(score,testName,grade,studentBean);
    }

    public void setTable(TableLayout table,String tableTitleString,ArrayList<StudentBean> studentList, final String testName1, final String testName2, final Activity activity){
        table.removeAllViews();
        table.setStretchAllColumns(true);
        final String[] titles=tableTitleString.split(":");
        for (final StudentBean student:studentList) {
            TableRow tablerow = new TableRow(activity);
            tablerow.setBackgroundColor(Color.WHITE);
            for (int i = 0; i < titles.length; i++) {
                int width = activity.getWindowManager().getDefaultDisplay().getWidth() / titles.length;
                TableRow.LayoutParams layoutParams=new TableRow.LayoutParams(width, TableRow.LayoutParams.FILL_PARENT);
                layoutParams.setMargins(0,0,1,0);

                TextView textView=new TextView(activity);
                textView.setLines(1);
                textView.setGravity(Gravity.CENTER);
                textView.setBackgroundColor(Color.TRANSPARENT);//背景黑色
                //textView.setText(student.getStudentNumberLastSixNum());
                textView.setTextColor(Color.BLACK);
                textView.setTextSize(15);
                textView.setPadding(0,10,0,10);

                final EditText editText = new EditText(activity);
                editText.setLines(1);
                editText.setGravity(Gravity.CENTER);
                editText.setBackgroundColor(Color.TRANSPARENT);//背景黑色
                //editText.setText(String.valueOf(tableCell.value));
                editText.setTextColor(Color.BLACK);
                editText.setInputType(InputType.TYPE_CLASS_NUMBER);
                editText.setTextSize(15);
                editText.setPadding(0,10,0,10);
//                editText.setTag(1,student.get_id());


                switch (i) {
                    case 0:{
                        textView.setText(student.getStudentNumberLastSixNum());
                        tablerow.addView(textView,layoutParams);
                        break;
                    }
                    case 1:{
                        textView.setText(student.getName());
                        tablerow.addView(textView,layoutParams);
                        break;
                    }
                    case 2:{
                        textView.setText(student.getSex());
                        tablerow.addView(textView,layoutParams);
                        break;
                    }
                    case 3:{
                        //System.out.println(testName1);
                        //System.out.println(student.getScore(testName1));
                        editTextList.add(editText);
                        editText.setText(student.getScore(testName1));
                        editText.setOnFocusChangeListener(new android.view.View.OnFocusChangeListener() {
                            @Override
                            public void onFocusChange(View v, boolean hasFocus) {
                                if (hasFocus) {
                                    lastEditText=editText;
                                } else {
                                    // System.out.println(editText.getTag(1));
                                    if (!student.getScore(testName1).equals(editText.getText().toString())) {
                                        saveData(student, editText.getText().toString(), testName1);
                                        if(titles.length==4&&!editText.getText().toString().equals(""))
                                            saveScore(activity,editText.getText().toString(),testName1,getGrade(),student);
                                        else {
                                            int index=editTextList.indexOf(editText);
                                            String tall=editText.getText().toString();
                                            String weight=editTextList.get(index+1).getText().toString();
                                            if(!tall.equals("")&&!weight.equals("")){
                                                String bmi=countBMI(tall,weight);
                                                saveScore(activity,bmi,"BMI",getGrade(),student);
                                            }
                                        }
                                    }
                                }
                            }
                        });
                        tablerow.addView(editText,layoutParams);
                        break;
                    }
                    case 4:{
                        editTextList.add(editText);
                        editText.setText(student.getScore(testName2));
                        editText.setOnFocusChangeListener(new android.view.View.OnFocusChangeListener() {
                            @Override
                            public void onFocusChange(View v, boolean hasFocus) {
                                if(hasFocus) {
                                    lastEditText=editText;
                                } else {
                                    // System.out.println(editText.getTag(1));
                                    if(!student.getScore(testName2).equals(editText.getText().toString())) {
                                        saveData(student, editText.getText().toString(), testName2);
                                        int index=editTextList.indexOf(editText);
                                        String weight=editText.getText().toString();
                                        String tall=editTextList.get(index-1).getText().toString();
                                        if(!tall.equals("")&&!weight.equals("")){
                                            String bmi=countBMI(tall,weight);
                                            saveScore(activity,bmi,"BMI",getGrade(),student);
                                        }
                                    }
                                }
                            }
                        });
                        tablerow.addView(editText,layoutParams);
                        break;
                    }
                }
            }
            TableLayout.LayoutParams tableParam=new TableLayout.LayoutParams(
                    TableRow.LayoutParams.FILL_PARENT, TableRow.LayoutParams.WRAP_CONTENT);
            tableParam.setMargins(0,1,0,1);
            table.addView(tablerow,tableParam);
        }
    }

    public void setTableTitle(TableLayout tableTitle,String tableTitleString,Activity activity){
        tableTitle.setStretchAllColumns(true);
        String[] titles=tableTitleString.split(":");
        TableRow tablerow = new TableRow(activity);
        tablerow.setBackgroundColor(Color.WHITE);
        int width = activity.getWindowManager().getDefaultDisplay().getWidth() / titles.length;
        TableRow.LayoutParams layoutParams=new TableRow.LayoutParams(width, TableRow.LayoutParams.FILL_PARENT);
        layoutParams.setMargins(0,0,1,0);
        for(int i=0;i<titles.length;i++){
            TextView textView=new TextView(activity);
            textView.setLines(1);
            textView.setGravity(Gravity.CENTER);
            textView.setBackgroundColor(Color.TRANSPARENT);//背景黑色
            textView.setText(titles[i]);
            textView.setTextColor(Color.BLACK);
            textView.setTextSize(15);
            textView.getPaint().setFakeBoldText(true);
            //textView.setPadding(0,10,0,10);
            tablerow.addView(textView,layoutParams);
        }
        TableLayout.LayoutParams tableParam=new TableLayout.LayoutParams(
                TableRow.LayoutParams.FILL_PARENT, TableRow.LayoutParams.WRAP_CONTENT);
        tableParam.setMargins(0,1,0,1);
        tableTitle.addView(tablerow,tableParam);
    }

    public void lastEditTextLostFocus(){
        if(lastEditText!=null)
            lastEditText.clearFocus();
    }

    public void setAllEditTextUnEdited(){
        for(EditText e:editTextList){
            e.setFocusable(false);
            e.setFocusableInTouchMode(false);
        }
    }

    public void setAllEditTextEdited(){
        for(EditText e:editTextList){
            e.setFocusable(true);
            e.setFocusableInTouchMode(true);
        }
    }

    private String getGrade(){
        GetClass getClass=GetClass.getInstance();
        int grade=getClass.getChoseGrade();
        String gradeString="";
        switch (grade){
            case 1: {
                return "一年级";
            }
            case 2: {
                return "二年级";
            }
            case 3: {
                return "三年级";
            }
            case 4: {
                return "四年级";
            }
            case 5: {
                return "五年级";
            }
            case 6: {
                return "六年级";
            }
        }
        return null;
    }

    private String countBMI(String tall,String weight){
        double tallInt=Double.parseDouble(tall)/100;
        double weightInt=Double.parseDouble(weight);
        double bmi=weightInt/(tallInt*tallInt);
        DecimalFormat df2  = new DecimalFormat("##.#");
        String returnString=String.valueOf(df2.format(bmi));
        System.out.println(returnString);
        return returnString;
    }
}
