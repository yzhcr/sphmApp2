package com.nju.sphm.Model.School;

import com.nju.sphm.Bean.OrganizationBean;

import java.util.ArrayList;

/**
 * Created by hcr1 on 2015/1/13.
 */
public class GetClass {
    ArrayList<OrganizationBean> gradeList;
    private int choseGrade=1;
    private int choseClass=1;
    private int GradeNumMax=0;
    private int GradeNumMin=0;
    private GetClass(){}
    private static GetClass instance=null;

    public static GetClass getInstance(){
        if(instance==null){
            instance=new GetClass();
        }
        return instance;
    }

    public ArrayList<OrganizationBean> getGradeList() {
        return gradeList;
    }

    public void setGradeList(ArrayList<OrganizationBean> gradeList) {
        this.gradeList = gradeList;
    }

    public int getGradeNum(){
        return gradeList.size();
    }

    public int getClassNum(int gradeNum){
        return gradeList.get(gradeNum-1).getChildren().size();
    }

    public int getChoseGrade() {
        return choseGrade;
    }

    public void setChoseGrade(int choseGrade) {
        this.choseGrade = choseGrade;
    }

    public int getChoseClass() {
        return choseClass;
    }

    public void setChoseClass(int choseClass) {
        this.choseClass = choseClass;
    }

    public int getGradeNumMax() {
        return GradeNumMax;
    }

    public void setGradeNumMax(int gradeNumMax) {
        GradeNumMax = gradeNumMax;
    }

    public int getGradeNumMin() {
        return GradeNumMin;
    }

    public void setGradeNumMin(int gradeNumMin) {
        GradeNumMin = gradeNumMin;
    }

    public String findClassId(int gradeNum,int classNum){
        ArrayList<OrganizationBean> classList=gradeList.get(gradeNum-1).getChildren();
        String classId=null;
        switch (gradeNum){
            case 1:
                for(OrganizationBean o:classList){
                    if(o.getName().equals("一（"+classNum+"）班")) {
                        classId = o.get_id();
                        break;
                    }
                }
                break;
            case 2:
                for(OrganizationBean o:classList){
                    if(o.getName().equals("二（"+classNum+"）班")) {
                        classId = o.get_id();
                        break;
                    }
                }
                break;
            case 3:
                for(OrganizationBean o:classList){
                    if(o.getName().equals("三（"+classNum+"）班")) {
                        classId = o.get_id();
                        break;
                    }
                }
                break;
            case 4:
                for(OrganizationBean o:classList){
                    if(o.getName().equals("四（"+classNum+"）班")) {
                        classId = o.get_id();
                        break;
                    }
                }
                break;
            case 5:
                for(OrganizationBean o:classList){
                    if(o.getName().equals("五（"+classNum+"）班")) {
                        classId = o.get_id();
                        break;
                    }
                }
                break;
            case 6:
                for(OrganizationBean o:classList){
                    if(o.getName().equals("六（"+classNum+"）班")) {
                        classId = o.get_id();
                        break;
                    }
                }
                break;
        }
        return classId;
    }

}
