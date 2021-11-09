package com.example.foodmate;

import android.content.Intent;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;

public class MainActivity extends AppCompatActivity {
    TextView textView_btn_01;
    TextView textView_btn_02;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        textView_btn_01 = findViewById(R.id.textView3);
        textView_btn_02 = findViewById(R.id.textView4);


        textView_btn_01.setOnClickListener(new View.OnClickListener() {
            @Override
            //
            public void onClick(View view) {
                Intent intent_01 = new Intent(getApplicationContext(), FindIdActivity.class);
                startActivity(intent_01);
            }
        });

        textView_btn_02.setOnClickListener(new View.OnClickListener() {
            @Override
            //
            public void onClick(View view) {
                Intent intent_02 = new Intent(getApplicationContext(), FindPwActivity.class);
                startActivity(intent_02);
            }
        });

    }


}