package com.nju.sphm.Bean;

import java.util.ArrayList;

/**
 * Created by HuangQiushuo on 2015/1/21.
 */
public class OrganizationBean {
    private String _id;
    private int _v;
    private String createDate;
    private String fullPath;
    private String label;
    private String name;
    private String type;

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

    public String getCreateDate() {
        return createDate;
    }

    public void setCreateDate(String createDate) {
        this.createDate = createDate;
    }

    public String getFullPath() {
        return fullPath;
    }

    public void setFullPath(String fullPath) {
        this.fullPath = fullPath;
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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

    public ArrayList<OrganizationBean> getChildren() {
        return children;
    }

    public void setChildren(ArrayList<OrganizationBean> children) {
        this.children = children;
    }

    private int schoolYear;
    private ArrayList<OrganizationBean> children;
}
