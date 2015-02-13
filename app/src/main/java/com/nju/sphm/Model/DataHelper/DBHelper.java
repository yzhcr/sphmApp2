package com.nju.sphm.Model.DataHelper;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by HuangQiushuo on 2015/1/15.
 */
public class DBHelper extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "tt.db";
    private static final int DATABASE_VERSION = 1;

    public DBHelper(Context context) {
        //CursorFactory设置为null,使用默认值
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    //数据库第一次被创建时onCreate会被调用
    @Override
    public void onCreate(SQLiteDatabase db) {
//        db.execSQL("DROP TABLE t.organizations");
//        db.execSQL("DROP TABLE t.students");
//        db.execSQL("DROP TABLE t.testfiles");
//        db.execSQL("DROP TABLE t.testfilerows");

        db.execSQL("CREATE TABLE IF NOT EXISTS students" +
                "(_id VARCHAR PRIMARY KEY, _v INTEGER, organizationID VARCHAR, studentCode VARCHAR, info NTEXT)");
        db.execSQL("CREATE TABLE IF NOT EXISTS organizations" +
                "(_id VARCHAR PRIMARY KEY, _v INTEGER, createDate VARCHAR, fullPath NVARCHAR, " +
                "label NVARCHAR, name NVARCHAR, type NVARCHAR, childrens TEXT, schoolYear INTEGER)");
        db.execSQL("CREATE TABLE IF NOT EXISTS testfiles" +
                "(_id VARCHAR PRIMARY KEY, _v INTEGER, fileName NVARCHAR, userID VARCHAR, " +
                "organizationID VARCHAR, type NVARCHAR, schoolYear INTEGER)");
        db.execSQL("CREATE TABLE IF NOT EXISTS testfilerows" +
                "(_id VARCHAR PRIMARY KEY, _v INTEGER, testfileID VARCHAR, studentCode VARCHAR, info NTEXT)");

    }

    //如果DATABASE_VERSION值被改为2,系统发现现有数据库版本不同,即会调用onUpgrade
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
//        db.execSQL("ALTER TABLE person ADD COLUMN other STRING");
    }
}
