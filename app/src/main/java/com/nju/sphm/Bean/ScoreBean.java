package com.nju.sphm.Bean;

import java.util.HashMap;

/**
 * Created by hcr1 on 2015/3/24.
 */
public class ScoreBean {
    /*private String 上等级;
    private HashMap<String, Object> 得分 = new HashMap<String, Object>();

    public HashMap<String, Object> getPoints() {
        return 得分;
    }

    public void setPoints(HashMap<String, Object> 得分) {
        this.得分 = 得分;
    }

    public String get上等级() {
        return 上等级;
    }

    public void set上等级(String 上等级) {
        this.上等级 = 上等级;
    }*/
    private HashMap<String, Object> info = new HashMap<String, Object>();

    public HashMap<String, Object> getInfo() {
        return info;
    }

    public void setInfo(HashMap<String, Object> info) {
        this.info = info;
    }
}
