package com.nju.sphm.Model.DataHelper;

import com.google.gson.Gson;
import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.DBCursor;
import com.mongodb.Mongo;
import com.nju.sphm.Bean.MyOrganization;

import java.util.ArrayList;

/**
 * Created by hcr1 on 2015/1/8.
 */
public class GetOrganizationDAO {
    public  ArrayList<MyOrganization> getOrganizations(  ){
        ArrayList<String> organStringList=new ArrayList<String>();
        ArrayList<MyOrganization> organList=new ArrayList<MyOrganization>();
        try{
            // 连接到 mongodb 服务
            Mongo mongo = new Mongo("192.168.204.128",27017);
            // 连接到数据库
            DB db = mongo.getDB( "test" );
            DBCollection users = db.getCollection("organizations");
            DBCursor cur = users.find();

            while (cur.hasNext()) {
                //System.out.println(cur.next().toString());
                organStringList.add(cur.next().toString());
            }
            for(String s:organStringList){

                Gson gson = new Gson();
                MyOrganization o=gson.fromJson(s,MyOrganization.class);
                organList.add(o);
            }

        }catch(Exception e){
           e.printStackTrace();
        }
        return organList;
    }
}
