package com.nju.sphm.Model.Interface;

import android.os.Handler;

/**
 * Created by HuangQiushuo on 2015/4/14.
 */
public interface DownloadHelperInterface {
    public boolean download(String path, int year, Handler handler);
}
