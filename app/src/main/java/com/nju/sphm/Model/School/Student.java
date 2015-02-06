package com.nju.sphm.Model.School;

/**
 * Created by HuangQiushuo on 2015/1/12.
 */
public class Student {
    private String name;
    private String sex;
    private String studentNumber;
    static public final String FEMALE = "女";
    static public final String MALE = "男";

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public String getStudentNumber() {
        return studentNumber;
    }

    public String getStudentNumberLastSixNum() {
        return studentNumber.substring(studentNumber.length() - 6, studentNumber.length());
    }

    public void setStudentNumber(String studentNumber) {
        this.studentNumber = studentNumber;
    }
}
