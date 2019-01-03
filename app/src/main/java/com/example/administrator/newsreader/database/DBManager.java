package com.example.administrator.newsreader.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.widget.Toast;

import com.example.administrator.newsreader.bean.NewsGson;

import java.util.ArrayList;
import java.util.List;

public class DBManager implements databaseSettings {
    private DBHelper helper;
    private SQLiteDatabase database;

    public DBManager(Context context) {
        this.helper = new DBHelper(context);
        this.database = helper.getWritableDatabase();
    }

    public void insert(List<NewsGson.NewslistBean> allData, int table_index, Context context) {
        database.beginTransaction();
        try{
            for (NewsGson.NewslistBean data : allData) {
                ContentValues contentValues = new ContentValues();
                contentValues.put("title", data.getTitle());
                contentValues.put("time", data.getCtime());
                contentValues.put("url", data.getUrl());
                contentValues.put("picUrl", data.getPicUrl());
                database.insert(TABLE_NAMES[table_index], null, contentValues);
            }
        } catch (Exception e) {
            Toast.makeText(context, "收藏失败，数据插入出现问题", Toast.LENGTH_SHORT).show();
        } finally {
            database.endTransaction();
        }
    }

    public List<NewsGson.NewslistBean> getAllFromTable(int table_index, Context context) {
        List<NewsGson.NewslistBean> result = new ArrayList<>();
        Cursor cursor = database.rawQuery("SELECT title,time,url,picUrl FROM "
                                          + TABLE_NAMES[table_index], null);
        for (cursor.moveToFirst(); !cursor.isAfterLast(); cursor.moveToNext()) {
            result.add(new NewsGson.NewslistBean(
                    cursor.getString(0),
                    cursor.getString(1),
                    cursor.getString(2),
                    cursor.getString(3)
            ));
        }
        cursor.close();
        return result;
    }

    public void delete(String url, Context context) {
        int table_index = TI_collection;
        database.execSQL("DELETE FROM " + TABLE_NAMES[table_index] + " WHERE url = '" + url + "'");
        Toast.makeText(context, "删除成功", Toast.LENGTH_SHORT).show();
    }
}
