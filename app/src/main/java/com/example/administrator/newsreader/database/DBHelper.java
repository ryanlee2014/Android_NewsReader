package com.example.administrator.newsreader.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;


public class DBHelper extends SQLiteOpenHelper implements databaseSettings {

    public DBHelper(Context context) {
        super(context, DATABASE_NAME, null, version);
    }

    @Override
    public void onCreate(SQLiteDatabase database) {
        for (String table_name : TABLE_NAMES) {
            database.execSQL("CREATE TABLE IF NOT EXISTS " + table_name + " (" +
                    "title varchar, " +
                    "time varchar, " +
                    "url varchar primary key, " +
                    "picUrl varchar)");
        }
    }

    @Override
    public void onUpgrade(SQLiteDatabase database, int oldVersion, int newVersion) {
        for (String table_name : TABLE_NAMES) {
            database.execSQL("DROP TABLE IF EXISTS " + table_name);
        }
        this.onCreate(database);
    }
}
