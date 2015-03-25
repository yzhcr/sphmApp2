package com.nju.sphm.Model.UIHelper;

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
        return gradeList.get(gradeNum-GradeNumMin).getChildren().size();
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
        if(choseGrade<GradeNumMin){
            choseGrade=GradeNumMin;
            choseClass=1;
        }
    }

    public void addClassInfo(ArrayList<OrganizationBean> gradeList,String testName){
        ArrayList<OrganizationBean> sortGradeList=new ArrayList<OrganizationBean>();
        int gradeNum=gradeList.size();
        if(gradeNum==3){
            this.setGradeNumMax(3);
            this.setGradeNumMin(1);
            for(OrganizationBean o:gradeList){
                if(o.getName().equals("一年级")){
                    sortGradeList.add(o);
                }
            }
            for(OrganizationBean o:gradeList){
                if(o.getName().equals("二年级")){
                    sortGradeList.add(o);
                }
            }
            for(OrganizationBean o:gradeList){
                if(o.getName().equals("三年级")){
                    sortGradeList.add(o);
                }
            }
        }
        if(gradeNum==6){
            System.out.println(testName);
            if(testName.equals("BMI")||testName.equals("肺活量")||testName.equals("坐位体前屈")||testName.equals("50米跑")||testName.equals("一分钟跳绳")) {
                this.setGradeNumMax(6);
                this.setGradeNumMin(1);
                for (OrganizationBean o : gradeList) {
                    if (o.getName().equals("一年级")) {
                        sortGradeList.add(o);
                    }
                }
                for (OrganizationBean o : gradeList) {
                    if (o.getName().equals("二年级")) {
                        sortGradeList.add(o);
                    }
                }
                for (OrganizationBean o : gradeList) {
                    if (o.getName().equals("三年级")) {
                        sortGradeList.add(o);
                    }
                }
                for (OrganizationBean o : gradeList) {
                    if (o.getName().equals("四年级")) {
                        sortGradeList.add(o);
                    }
                }
                for (OrganizationBean o : gradeList) {
                    if (o.getName().equals("五年级")) {
                        sortGradeList.add(o);
                    }
                }
                for (OrganizationBean o : gradeList) {
                    if (o.getName().equals("六年级")) {
                        sortGradeList.add(o);
                    }
                }
            }else if(testName.equals("一分钟仰卧起坐")){
                this.setGradeNumMax(6);
                this.setGradeNumMin(3);
                for (OrganizationBean o : gradeList) {
                    if (o.getName().equals("三年级")) {
                        sortGradeList.add(o);
                    }
                }
                for (OrganizationBean o : gradeList) {
                    if (o.getName().equals("四年级")) {
                        sortGradeList.add(o);
                    }
                }
                for (OrganizationBean o : gradeList) {
                    if (o.getName().equals("五年级")) {
                        sortGradeList.add(o);
                    }
                }
                for (OrganizationBean o : gradeList) {
                    if (o.getName().equals("六年级")) {
                        sortGradeList.add(o);
                    }
                }
            }
            else if(testName.equals("50米×8往返跑")){
                this.setGradeNumMax(6);
                this.setGradeNumMin(5);
                for (OrganizationBean o : gradeList) {
                    if (o.getName().equals("五年级")) {
                        sortGradeList.add(o);
                    }
                }
                for (OrganizationBean o : gradeList) {
                    if (o.getName().equals("六年级")) {
                        sortGradeList.add(o);
                    }
                }
            }
        }
        this.setGradeList(sortGradeList);
    }

    //传入年级与班级，传回班级的id
    public String findClassId(int gradeNum,int classNum){
        ArrayList<OrganizationBean> classList=gradeList.get(gradeNum-GradeNumMin).getChildren();
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
