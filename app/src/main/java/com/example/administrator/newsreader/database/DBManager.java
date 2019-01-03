package com.example.administrator.newsreader.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteConstraintException;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;
import android.widget.Toast;

import com.example.administrator.newsreader.bean.NewsGson;
import com.example.administrator.newsreader.ui.news.contrant.NewsContract;

import java.util.ArrayList;
import java.util.List;

public class DBManager implements databaseSettings {
    private DBHelper helper;
    private SQLiteDatabase database;

    public DBManager(Context context) {
        this.helper = new DBHelper(context);
        this.database = helper.getWritableDatabase();
    }

    public void insert(List<NewsGson.NewslistBean> allData, int table_index, Context context, NewsContract.Callback readyToReload) {
        database.beginTransaction();
        try{
            for (NewsGson.NewslistBean data : allData) {
                try {
                    ContentValues contentValues = new ContentValues();
                    contentValues.put("hash", data.getUrl().hashCode());
                    contentValues.put("title", data.getTitle());
                    contentValues.put("time", data.getCtime());
                    contentValues.put("url", data.getUrl());
                    contentValues.put("picUrl", data.getPicUrl());
                    database.insertWithOnConflict(TABLE_NAMES[table_index], null, contentValues, SQLiteDatabase.CONFLICT_FAIL);
                    database.setTransactionSuccessful();
                } catch (SQLiteConstraintException e) {
                    if (readyToReload == null)
                        Toast.makeText(context, "已在收藏栏中", Toast.LENGTH_SHORT).show();
                } catch (Exception e) {
                    if (readyToReload == null) {
                        Toast.makeText(context, "收藏失败，数据异常", Toast.LENGTH_SHORT).show();
                        Log.d("DBManager :", "收藏失败 : " + e.getMessage());
                    }
                }
            }

        } finally {
            database.endTransaction();
            if (readyToReload != null) {
                readyToReload.reloadFromDB();
            }
        }
    }

    public List<NewsGson.NewslistBean> getAllFromTable(int table_index, Context context) {
        List<NewsGson.NewslistBean> result = new ArrayList<>();
        Cursor cursor = database.rawQuery("SELECT ALL title,time,url,picUrl FROM "
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

    public void closeDB() {
        database.close();
    }

}
