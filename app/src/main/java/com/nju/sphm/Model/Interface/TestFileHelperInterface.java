package com.nju.sphm.Model.Interface;

import android.os.Handler;

import com.nju.sphm.Bean.TestFileBean;
import com.nju.sphm.Bean.TestFileRowBean;

import java.util.ArrayList;

/**
 * Created by HuangQiushuo on 2015/4/13.
 */
public interface TestFileHelperInterface {

    public ArrayList<TestFileRowBean> getTestFileRowList(String testFileId, Handler handler);

    public ArrayList<TestFileBean> getTestFileList(int schoolYear, Handler handler);
}
