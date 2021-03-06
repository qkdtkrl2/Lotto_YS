package com.example.lotto;

import android.annotation.SuppressLint;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;

public class AutoNumActive extends AppCompatActivity {

    Button btnAutoCreate;
    Button btnAutoSave;
    EditText editTboxAutoNum;

    SQLiteDatabase sqlDB;
    myDBHelper myDBHelper;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_auto);

        btnAutoCreate = findViewById(R.id.btnAutoCreate);
        btnAutoSave = findViewById(R.id.btnAutoSave);
        editTboxAutoNum = findViewById(R.id.editTboxAutoNum);

        int max_num_value = 45;
        int min_num_value = 1;

        myDBHelper = new myDBHelper(this);


        btnAutoCreate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //1~45 겹치지 않는 숫자 7개 1~6은 기본 숫자 7은 보너스 숫자
                int[] NumList = new int[7];
                Random random = new Random();
                for(int i = 0 ; i<7; i++){
                    NumList[i] = random.nextInt(max_num_value - min_num_value) + min_num_value;
                    for(int j = 0; j < i; j++){
                        if(NumList[i] == NumList[j]){
                            i--;
                        }
                    }
                }

                Arrays.sort(NumList);

                String msg = "";

                int index = random.nextInt(6);
                for(int i = 0; i < NumList.length; i++){
                    if(i == index){
                        continue;
                    }else{
                        msg += NumList[i]+ " ";
                    }
                }

                msg += "+" + NumList[index] + "\r\n";

                editTboxAutoNum.append(msg);
            }
        });

        btnAutoSave.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("WrongConstant")
            @Override
            public void onClick(View v) {
                String[] token = editTboxAutoNum.getText().toString().split("\r\n");

                sqlDB = myDBHelper.getWritableDatabase();
                for(int i =0; i<token.length-1; i++){
                    sqlDB.execSQL("INSERT INTO LOTTO VALUES ('"+token[i]+"');");
                }
                sqlDB.close();

                Toast.makeText(getApplicationContext(),"Succes Save",0).show();
            }
        });
    }
}
