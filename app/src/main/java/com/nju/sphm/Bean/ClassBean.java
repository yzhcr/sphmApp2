package com.nju.sphm.Bean;

import java.util.ArrayList;

/**
 * Created by HuangQiushuo on 2015/1/20.
 */
public class ClassBean {
    private String _id;
    private int _v;
    private String createdDate;
    private String name;
    private String path;
    private String type;
    private int schoolYear;
    private ArrayList<StudentBean> students = new ArrayList<StudentBean>();

    public String get_id() {
        return _id;
    }

    public void set_id(String _id) {
        this._id = _id;
    }

    public int get_v() {
        return _v;
    }

    public void set_v(int _v) {
        this._v = _v;
    }

    public String getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(String createdDate) {
        this.createdDate = createdDate;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public int getSchoolYear() {
        return schoolYear;
    }

    public void setSchoolYear(int schoolYear) {
        this.schoolYear = schoolYear;
    }

    public ArrayList<StudentBean> getStudents() {
        return students;
    }

    public void setStudents(ArrayList<StudentBean> students) {
        this.students = students;
    }
}
