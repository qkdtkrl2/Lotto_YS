package com.example.lotto;

import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

public class HandNumActive extends AppCompatActivity {

    private Button[] btnList = new Button[45];
    private ArrayList<Integer> AddnumList = new ArrayList<>();
    private String BonusNum;

    int Count = 0;

    Button btnHandSave;
    Button btnHandCreate;

    TextView tBoxHandNum;

    SQLiteDatabase sqlDB;
    myDBHelper myDBHelper;

    String Nummsg = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hand);


        btnList[0] = findViewById(R.id.btnNo1);
        btnList[1] = findViewById(R.id.btnNo2);
        btnList[2] = findViewById(R.id.btnNo3);
        btnList[3] = findViewById(R.id.btnNo4);
        btnList[4] = findViewById(R.id.btnNo5);
        btnList[5] = findViewById(R.id.btnNo6);
        btnList[6] = findViewById(R.id.btnNo7);
        btnList[7] = findViewById(R.id.btnNo8);
        btnList[8] = findViewById(R.id.btnNo9);
        btnList[9] = findViewById(R.id.btnNo10);
        btnList[10] = findViewById(R.id.btnNo11);
        btnList[11] = findViewById(R.id.btnNo12);
        btnList[12] = findViewById(R.id.btnNo13);
        btnList[13] = findViewById(R.id.btnNo14);
        btnList[14] = findViewById(R.id.btnNo15);
        btnList[15] = findViewById(R.id.btnNo16);
        btnList[16] = findViewById(R.id.btnNo17);
        btnList[17] = findViewById(R.id.btnNo18);
        btnList[18] = findViewById(R.id.btnNo19);
        btnList[19] = findViewById(R.id.btnNo20);
        btnList[20] = findViewById(R.id.btnNo21);
        btnList[21] = findViewById(R.id.btnNo22);
        btnList[22] = findViewById(R.id.btnNo23);
        btnList[23] = findViewById(R.id.btnNo24);
        btnList[24] = findViewById(R.id.btnNo25);
        btnList[25] = findViewById(R.id.btnNo26);
        btnList[26] = findViewById(R.id.btnNo27);
        btnList[27] = findViewById(R.id.btnNo28);
        btnList[28] = findViewById(R.id.btnNo29);
        btnList[29] = findViewById(R.id.btnNo30);
        btnList[30] = findViewById(R.id.btnNo31);
        btnList[31] = findViewById(R.id.btnNo32);
        btnList[32] = findViewById(R.id.btnNo33);
        btnList[33] = findViewById(R.id.btnNo34);
        btnList[34] = findViewById(R.id.btnNo35);
        btnList[35] = findViewById(R.id.btnNo36);
        btnList[36] = findViewById(R.id.btnNo37);
        btnList[37] = findViewById(R.id.btnNo38);
        btnList[38] = findViewById(R.id.btnNo39);
        btnList[39] = findViewById(R.id.btnNo40);
        btnList[40] = findViewById(R.id.btnNo41);
        btnList[41] = findViewById(R.id.btnNo42);
        btnList[42] = findViewById(R.id.btnNo43);
        btnList[43] = findViewById(R.id.btnNo44);
        btnList[44] = findViewById(R.id.btnNo45);

        btnHandSave = findViewById(R.id.btnHandSave);
        btnHandCreate = findViewById(R.id.brnHandCreate);

        tBoxHandNum = findViewById(R.id.tBoxHandNum);

        myDBHelper = new myDBHelper(this);

        for(int i = 0; i<btnList.length; i++){
            btnList[i].setTag(i);
            btnList[i].setOnClickListener(listener);
        }

        btnHandSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(tBoxHandNum.getText() != ""){
                    sqlDB = myDBHelper.getWritableDatabase();

                    sqlDB.execSQL("INSERT INTO LOTTO VALUES ('"+tBoxHandNum.getText()+"');");
                    sqlDB.close();
                }
            }
        });

    }

    private View.OnClickListener listener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            Button btn = (Button) v;

            for (Button tempBtn : btnList){
                if(Count == 7){
                    Collections.sort(AddnumList, new Ascending());


                    for(int i = 0; i<AddnumList.size(); i++){
                        Nummsg += AddnumList.get(i).toString() + "\r";
                    }

                    tBoxHandNum.setText(Nummsg.trim() + "+\r" + BonusNum);
                    Count++;
                    break;
                }else{
                    if(Count > 7){
                        break;
                    }else{
                        if(tempBtn == btn){
                            int position = (Integer)v.getTag();

                            if(Count == 6){
                                BonusNum = btnList[position].getText().toString();
                            }else{
                                AddnumList.add(Integer.parseInt(btnList[position].getText().toString()));
                            }

                            btnList[position].setBackgroundColor(000000);
                            btnList[position].setEnabled(false);
                            btnList[position].setText("");

                            Count++;
                        }
                    }
                }
            }
        }
    };
}

class Ascending implements Comparator<Integer> {
    public int compare(Integer a, Integer b)
    {
        return a.compareTo(b);
    }
}
