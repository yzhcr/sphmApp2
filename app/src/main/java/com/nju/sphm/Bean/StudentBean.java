package com.nju.sphm.Bean;

import com.google.gson.Gson;

import org.json.JSONObject;

import java.util.HashMap;

/**
 * Created by HuangQiushuo on 2015/1/15.
 */
public class StudentBean {
    String _id;
    String organization;
    String studentCode;
    int _v;
    HashMap<String, Object> info = new HashMap<String, Object>();
    String infoJSON;

    public String getStudentCodeLastSixNum() {
        return studentCode.substring(studentCode.length() - 6, studentCode.length());
    }

    public String getInfoJSON() {
        enCode();
        return infoJSON;
    }

    public void setInfoJSON(String infoJSON) {
        this.infoJSON = infoJSON;
        deCode();
    }

    public String get_id() {
        return _id;
    }

    public void set_id(String _id) {
        this._id = _id;
    }

    public String getOrganization() {
        return organization;
    }

    public void setOrganization(String organization) {
        this.organization = organization;
    }

    public String getStudentCode() {
        return studentCode;
    }

    public void setStudentCode(String studentCode) {
        this.studentCode = studentCode;
    }

    public int get_v() {
        return _v;
    }

    public void set_v(int _v) {
        this._v = _v;
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
