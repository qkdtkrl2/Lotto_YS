package com.example.lotto;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentValues;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;

import javax.net.ssl.HttpsURLConnection;

public class MainActivity extends AppCompatActivity {

    TextView textDate;
    Spinner spiDateNo;
    TextView textNum;

    Button btnQr;
    Button btnMyNum;
    Button btnCreateNum;
    Button btnMap;

    Integer lotto_count = 950;
    Integer lotto_real_count;
    Integer flag = 0;

    String str, receiveMsg;

    IntentIntegrator integrator;

    SQLiteDatabase sqlDB;
    myDBHelper myDBHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        JsonObject jsonObject;

        textDate = findViewById(R.id.tViewDate);
        spiDateNo = findViewById(R.id.spiDateNo);
        textNum = findViewById(R.id.tViewNum);

        btnQr = findViewById(R.id.btnQr);
        btnCreateNum = findViewById(R.id.btnCreateNum);
        btnMyNum = findViewById(R.id.btnMyNum);
        btnMap = findViewById(R.id.btnMap);

        integrator = new IntentIntegrator(this);
        //바코드 안의 텍스트
        integrator.setPrompt("바코드를 사각형 안에 비춰주세요");
        integrator.setBeepEnabled(false);
        integrator.setBarcodeImageEnabled(true);

        myDBHelper = new myDBHelper(this);
        sqlDB = myDBHelper.getWritableDatabase();
        myDBHelper.onCreate(sqlDB);
        sqlDB.close();

        btnQr.setOnClickListener(v -> integrator.initiateScan());
        btnCreateNum.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent createNum  = new Intent(getApplicationContext(), CreateNumActive.class);

                startActivity(createNum);
            }
        });
        btnMap.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent nearMap  = new Intent(getApplicationContext(), NearMapActive.class);
                startActivity(nearMap);
            }
        });
        btnMyNum.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent myNum  = new Intent(getApplicationContext(), MyNumActive.class);
                startActivity(myNum);
            }
        });

        CountTask count = new CountTask();
        count.execute();
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        IntentResult result = IntentIntegrator.parseActivityResult(requestCode, resultCode, data);
        if (result != null) {
            if (result.getContents() == null) {

            } else { //qr코드를 읽어서 EditText에 입력해줍니다.
                String address = result.getContents().toString();
                if(!address.startsWith("http://")){
                    address = "http://" + address;
                }

                Intent i = new Intent(Intent.ACTION_VIEW);
                Uri u = Uri.parse(address);
                i.setData(u);
                startActivity(i);
            }
        } else {
            super.onActivityResult(requestCode, resultCode, data);
        }
    }


    class CountTask extends AsyncTask<Integer, Integer, Void> {
        @Override
        protected Void doInBackground(Integer... params) {
            URL url = null;
            try {
                while(true){
                    if(flag.equals(0)){
                        url = new URL("https://www.dhlottery.co.kr/common.do?method=getLottoNumber&drwNo=" + lotto_count.toString());
                    }else{
                        url = new URL("https://www.dhlottery.co.kr/common.do?method=getLottoNumber&drwNo=" + lotto_real_count.toString());
                    }

                    HttpsURLConnection conn = (HttpsURLConnection) url.openConnection();

                    InputStreamReader tmp = new InputStreamReader(conn.getInputStream(), "UTF-8");
                    BufferedReader reader = new BufferedReader(tmp);
                    StringBuffer buffer = new StringBuffer();
                    while ((str = reader.readLine()) != null) {
                        buffer.append(str);
                    }
                    receiveMsg = buffer.toString();

                    JsonObject jsonObject = (JsonObject) JsonParser.parseString(receiveMsg);

                    if(jsonObject.get("returnValue").toString().equals("\"success\"") && flag.equals(0)){
                        lotto_count++;
                    }else{
                        if(jsonObject.get("returnValue").toString().equals("\"fail\"")){
                            lotto_real_count = lotto_count-1;
                            flag = 1;
                        }else if(jsonObject.get("returnValue").toString().equals("\"success\"") && flag.equals(1)){
                            textDate.setText(jsonObject.get("drwNoDate").toString());
                            String num = jsonObject.get("drwtNo1").toString() + "," + jsonObject.get("drwtNo2").toString() + "," + jsonObject.get("drwtNo3").toString()
                                    + "," + jsonObject.get("drwtNo4").toString() + "," + jsonObject.get("drwtNo5").toString() + "," + jsonObject.get("drwtNo6").toString()
                                    + "+" + jsonObject.get("bnusNo").toString();

                            textNum.setText(num);
                            reader.close();
                            break;
                        }else{
                            flag = 0;
                        }
                    }
                    reader.close();
                }

            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }

            return null;
        }

        @Override
        protected void onProgressUpdate(Integer... values) {
            super.onProgressUpdate(values);
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
        }
    }
}
