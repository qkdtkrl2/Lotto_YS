package com.example.lotto;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

public class MyNumActive extends AppCompatActivity {

    SQLiteDatabase sqlDB;
    myDBHelper myDBHelper;

    EditText editTboxMyNum;
    Button btnInit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mynum);

        editTboxMyNum = findViewById(R.id.editTboxMyNum);
        btnInit = findViewById(R.id.btnInit);

        myDBHelper = new myDBHelper(this);

        sqlDB = myDBHelper.getReadableDatabase();
        Cursor cursor;
        cursor = sqlDB.rawQuery("SELECT * FROM LOTTO;",null);

        String number = "";

        while (cursor.moveToNext()){
            number += cursor.getString(0) + "\r\n";
        }

        editTboxMyNum.setText(number);

        cursor.close();
        sqlDB.close();

        btnInit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sqlDB = myDBHelper.getWritableDatabase();
                myDBHelper.onUpgrade(sqlDB,1,2);
                sqlDB.close();

                editTboxMyNum.setText("");
            }
        });
    }
}
