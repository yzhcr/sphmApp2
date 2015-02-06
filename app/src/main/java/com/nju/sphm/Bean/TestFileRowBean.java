package com.nju.sphm.Bean;

import com.google.gson.Gson;

import org.json.JSONObject;

import java.util.HashMap;

/**
 * Created by HuangQiushuo on 2015/1/22.
 */
public class TestFileRowBean {
    private int _v;
    private String _id;
    private String studentCode;
    private String testfile;//oid
    HashMap<String, Object> info = new HashMap<String, Object>();
    String infoJSON;

    public String getInfoJSON() {
        return infoJSON;
    }

    public void setInfoJSON(String infoJSON) {
        this.infoJSON = infoJSON;
        deCode();
    }
    public int get_v() {
        return _v;
    }

    public void set_v(int _v) {
        this._v = _v;
    }

    public String get_id() {
        return _id;
    }

    public void set_id(String _id) {
        this._id = _id;
    }

    public String getStudentCode() {
        return studentCode;
    }

    public void setStudentCode(String studentCode) {
        this.studentCode = studentCode;
    }

    public String getTestfile() {
        return testfile;
    }

    public void setTestfile(String testfile) {
        this.testfile = testfile;
    }

    public HashMap<String, Object> getInfo() {
        return info;
    }

    public void setInfo(HashMap<String, Object> info) {
        this.info = info;
    }

    public void enCode(){
        JSONObject json = new JSONObject(info);
        infoJSON = json.toString();
    }

    public void deCode(){
        Gson gson = new Gson();
        info = gson.fromJson(infoJSON, HashMap.class);
    }
}
