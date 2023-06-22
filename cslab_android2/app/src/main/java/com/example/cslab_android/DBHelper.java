package com.example.cslab_android;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.ArrayList;
import java.util.Collections;

public class DBHelper extends SQLiteOpenHelper {
    static final String DB_NAME = "Log.db";
    static final int VERSION = 1;
    public DBHelper(Context context){
        super(context,DB_NAME,null,VERSION);
    }
    @Override
    public void onCreate(SQLiteDatabase db) {
        //Create 문 만들기
        db.execSQL("CREATE TABLE if not EXISTS log(example Text,LSTM_PRO double, LSTM_TIME double, DNN_PRO double, " +
                "DNN_TIME double," +
                "BERT_PRO double, BERT_TIME double )");
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }

    public void insert(String example, double LSTM_PRO, double LSTM_TIME,
                       double DNN_PRO, double DNN_TIME,
                       double BERT_PRO, double BERT_TIME){

        SQLiteDatabase db = getWritableDatabase();
        String sql = "INSERT INTO log VALUES('" + example + "', '" + LSTM_PRO +
                "', '" + LSTM_TIME +
                "', '" + DNN_PRO +
                "', '" + DNN_TIME +
                "', '" + BERT_PRO +
                "', '" + BERT_TIME + "')";
        Log.d("SQL INSERT",sql);
        db.execSQL(sql);
        db.close();
    }

    public ArrayList<DBData> select(){
        String sql = "select * from log";
        SQLiteDatabase db = getReadableDatabase();
        Cursor cursor = db.rawQuery(sql,null);
        ArrayList<DBData> list = new ArrayList<>();
        while(cursor.moveToNext()) {
            DBData dbData = new DBData(cursor.getString(0) , cursor.getDouble(1) ,cursor.getDouble(2) ,
                    cursor.getDouble(3) ,
                    cursor.getDouble(4),
                    cursor.getDouble(5) ,
                    cursor.getDouble(6));
          list.add(dbData);
        }

        Collections.reverse(list);
        return list;
    }

    public void reset(){
        String sql = "delete from log";
        getWritableDatabase().execSQL(sql);
    }
}
