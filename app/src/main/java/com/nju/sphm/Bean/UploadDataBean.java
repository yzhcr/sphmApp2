package com.nju.sphm.Bean;

import com.google.gson.Gson;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by HuangQiushuo on 2015/3/28.
 */
public class UploadDataBean {
    private String fileName = "";
    private int schoolYear = 0;
    private String type = "";
    private Map<String, Map> items = new HashMap<String, Map>();
    private String itemsJSON = "";
    private String organizationID = "";

    public void addItem(String studentCode, Map<String, Object> map){
        items.put(studentCode, map);
    }

    public String getOrganizationID() {
        return organizationID;
    }

    public void setOrganizationID(String organizationID) {
        this.organizationID = organizationID;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public int getSchoolYear() {
        return schoolYear;
    }

    public void setSchoolYear(int schoolYear) {
        this.schoolYear = schoolYear;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Map<String, Map> getItems() {
        return items;
    }

    public void setItems(Map<String, Map> items) {
        this.items = items;
    }

    public String getItemsJSON() {
        enCode();
        return itemsJSON;
    }

    public void setItemsJSON(String itemsJSON) {
        this.itemsJSON = itemsJSON;
        deCode();
    }

    public void enCode(){
        JSONObject json = new JSONObject(items);
        itemsJSON = json.toString();
    }

    public void deCode(){
        Gson gson = new Gson();
        items = gson.fromJson(itemsJSON, HashMap.class);
    }
}
