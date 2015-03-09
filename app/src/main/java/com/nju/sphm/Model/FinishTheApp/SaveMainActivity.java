package com.nju.sphm.Model.FinishTheApp;


import com.nju.sphm.Controller.LoginActivity.MainActivity;

/**
 * Created by hcr1 on 2015/3/9.
 */
public class SaveMainActivity {
    private SaveMainActivity(){
    }

    private static SaveMainActivity instance=null;

    public static SaveMainActivity getInstance(){
        if(instance==null){
            instance=new SaveMainActivity();
        }
        return instance;
    }

    private MainActivity mainActivity;

    public MainActivity getMainActivity() {
        return mainActivity;
    }

    public void setMainActivity(MainActivity mainActivity) {
        this.mainActivity = mainActivity;
    }
}
