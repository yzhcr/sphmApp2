package com.nju.sphm.Model.Interface;

import android.app.Activity;
import android.os.Handler;

/**
 * Created by HuangQiushuo on 2015/4/13.
 */
public interface NetWorkHelperInterface {
    public String getIp();

    public void setIp(String ip);

    public String requestDataByGet(String url);

    public String requestDataByGet(String urlStr, Handler handler, String fileName);

    public String requestDataByPost(String accessURL, String json);

    public boolean hasWifi(Activity activity);

    public boolean hasInternet(Activity activity);
}
