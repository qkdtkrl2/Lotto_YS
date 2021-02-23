package com.example.lotto;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentValues;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.zxing.integration.android.IntentIntegrator;

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

    Integer lotto_count = 940;

    String str, receiveMsg;

    IntentIntegrator integrator;

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

        btnQr.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                integrator.initiateScan();
            }
        });

        CountTask count = new CountTask();
        count.execute();
    }

    class CountTask extends AsyncTask<Integer, Integer, Void> {
        @Override
        protected Void doInBackground(Integer... params) {
            URL url = null;
            try {
                url = new URL("https://www.dhlottery.co.kr/common.do?method=getLottoNumber&drwNo=" + lotto_count.toString());

                HttpsURLConnection conn = (HttpsURLConnection) url.openConnection();

                InputStreamReader tmp = new InputStreamReader(conn.getInputStream(), "UTF-8");
                BufferedReader reader = new BufferedReader(tmp);
                StringBuffer buffer = new StringBuffer();
                while ((str = reader.readLine()) != null) {
                    buffer.append(str);
                }
                receiveMsg = buffer.toString();

                JsonObject jsonObject = (JsonObject) JsonParser.parseString(receiveMsg);

                textDate.setText(jsonObject.get("drwNoDate").toString());
                String num = jsonObject.get("drwtNo1").toString() + "," + jsonObject.get("drwtNo2").toString() + "," + jsonObject.get("drwtNo3").toString()
                        + "," + jsonObject.get("drwtNo4").toString()+ "," + jsonObject.get("drwtNo5").toString()+ "," + jsonObject.get("drwtNo6").toString()
                        + "+" + jsonObject.get("bnusNo").toString();

                textNum.setText(num);

                reader.close();

            } catch (MalformedURLException e) {
                e.printStackTrace();
            }catch (IOException e) {
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