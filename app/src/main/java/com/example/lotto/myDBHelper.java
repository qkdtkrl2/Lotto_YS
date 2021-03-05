package com.example.lotto;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.NonNull;

public class myDBHelper extends SQLiteOpenHelper{
    public myDBHelper(Context context){
        super(context, "Lotto", null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db){
        //db.execSQL("CREATE TABLE IF NOT EXISTS Lotto  ( idx INTEGER PRIMARY KEY, num1 INTEGER, num2 INTEGER, num3 INTEGER, num4 INTEGER, num5 INTEGER, num6 INTEGER , bnum INTEGER);");
        db.execSQL("CREATE TABLE IF NOT EXISTS LOTTO  (number CHAR(50));");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS LOTTO");
        onCreate(db);

    }
}
