package com.example.lotto;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

public class CreateNumActive extends AppCompatActivity {

    Button btnAuto;
    Button btnHand;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_createnum);

        btnAuto = findViewById(R.id.btnAuto);
        btnHand = findViewById(R.id.btnHand);

        btnAuto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent auto = new Intent(getApplicationContext(), AutoNumActive.class);
                startActivity(auto);
            }
        });
        btnHand.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent hand = new Intent(getApplicationContext(), HandNumActive.class);
                startActivity(hand);
            }
        });
    }
}
