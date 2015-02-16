package com.nju.sphm.Model.School;

import com.nju.sphm.Bean.OrganizationBean;

import java.util.ArrayList;

/**
 * Created by hcr1 on 2015/1/13.
 */
public class GetClass {
    ArrayList<OrganizationBean> gradeList;

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
}
