package com.nju.sphm.Model.Interface;

import android.app.Activity;
import android.widget.TableLayout;

import com.nju.sphm.Bean.StudentBean;

import java.util.ArrayList;

/**
 * Created by HuangQiushuo on 2015/4/13.
 */
public interface TableHelperInterface {
    public void setTable(TableLayout table,String tableTitleString,ArrayList<StudentBean> studentList, final String testName1, final String testName2, final Activity activity);

    public void setTableTitle(TableLayout tableTitle,String tableTitleString,Activity activity);

    public void lastEditTextLostFocus();

    public void setAllEditTextUnEdited();

    public void setAllEditTextEdited();

}
