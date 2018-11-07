package com.sergeybelkin.test;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DBHelper extends SQLiteOpenHelper {

    private static final String DB_NAME = "etsy_database";
    private static final int DB_VERSION = 1;

    private static final String CATEGORY_TABLE = "category_table";

    private static DBHelper instance;

    public static DBHelper getInstance(Context context){
        if (instance == null){
            instance = new DBHelper(context);
        }
        return instance;
    }

    private DBHelper(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("create table " + CATEGORY_TABLE + "(_id integer primary key autoincrement, name text)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
