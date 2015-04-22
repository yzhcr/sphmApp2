package com.nju.sphm.Model.Login;

import com.nju.sphm.Bean.OrganizationBean;
import com.nju.sphm.Controller.LoginActivities.TreeNode;
import com.nju.sphm.Model.DataHelper.OrganizationHelper;
import com.nju.sphm.Model.Interface.GetOrganizationInterface;

import java.util.ArrayList;

/**
 * Created by hcr1 on 2015/1/8.
 */
public class GetOrganization implements GetOrganizationInterface{
    ArrayList<OrganizationBean> organList=new ArrayList<OrganizationBean>();
    ArrayList<OrganizationBean> cityOrganList=new ArrayList<OrganizationBean>();
    ArrayList<OrganizationBean> areaOrganList=new ArrayList<OrganizationBean>();
    ArrayList<OrganizationBean> schoolOrganList=new ArrayList<OrganizationBean>();
    OrganizationHelper oHelper=new OrganizationHelper();
    ArrayList<TreeNode> treeList=new ArrayList<TreeNode>();
    public ArrayList<TreeNode> setTreeBean(){
        organList=oHelper.getOrganizationList("/",2014, null);
        /*for(Organization o:organList){
            System.out.println(o.getType());
            System.out.println(o.getName());
        }*/
        OrganizationBean organizationBean=organList.get(0);
        TreeNode firstNode = new TreeNode(organizationBean.getName(), TreeNode.TOP_LEVEL, organizationBean.get_id(), TreeNode.NO_PARENT, true, false,organizationBean.getFullPath());
        treeList.add(firstNode);
                //organList.remove(o);
        organList=organizationBean.getChildren();
        for(OrganizationBean o:organList) {
                //System.out.println(o.getFullPath());
                TreeNode node = new TreeNode(o.getName(), TreeNode.TOP_LEVEL + 1, o.get_id(), treeList.get(0).getId(), true, false,o.getFullPath());
                treeList.add(node);
            cityOrganList=o.getChildren();
            for(OrganizationBean cityO:cityOrganList){
                TreeNode cityNode = new TreeNode(cityO.getName(), TreeNode.TOP_LEVEL + 2, cityO.get_id(), o.get_id(), true, false,cityO.getFullPath());
                treeList.add(cityNode);
                areaOrganList=cityO.getChildren();
                for(OrganizationBean areaO:areaOrganList){
                    TreeNode areaNode = new TreeNode(areaO.getName(), TreeNode.TOP_LEVEL + 3, areaO.get_id(), cityO.get_id(), true, false,areaO.getFullPath());
                    treeList.add(areaNode);
                    schoolOrganList=areaO.getChildren();
                    for(OrganizationBean schoolO:schoolOrganList){
//                        System.out.println("```````````````");
//                        System.out.println(schoolO.getFullPath());
                        TreeNode schoolNode = new TreeNode(schoolO.getName(), TreeNode.TOP_LEVEL + 4, schoolO.get_id(), areaO.get_id(), false, false,schoolO.getFullPath());
                        //System.out.println(schoolNode.getPath());
                        treeList.add(schoolNode);
                    }
                }
            }
        }
        return treeList;
    }
}
