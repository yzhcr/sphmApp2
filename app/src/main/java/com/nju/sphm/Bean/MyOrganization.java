package com.nju.sphm.Bean;

/**
 * Created by hcr1 on 2015/1/8.
 */
public class MyOrganization {
    private _id _id;
    private String name;
    private String type;
    private String schoolYear;
    private String path;

    public _id get_id() {
        return _id;
    }

    public void set_id(_id _id) {
        this._id = _id;
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

    public String getSchoolYear() {
        return schoolYear;
    }

    public void setSchoolYear(String schoolYear) {
        this.schoolYear = schoolYear;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public CreatedDate getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(CreatedDate createdDate) {
        this.createdDate = createdDate;
    }

    public String get__v() {
        return __v;
    }

    public void set__v(String __v) {
        this.__v = __v;
    }

    private CreatedDate createdDate;
    private String __v;
}
