package com.example.foodmate;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class FindPwActivity extends AppCompatActivity {
    Button SendCode_button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_find_pw);

        SendCode_button = (Button) findViewById(R.id.button4);

        SendCode_button.setOnClickListener(new Button.OnClickListener(){
            @Override
            public void onClick(View view) {
                EditText Text_id = (EditText) findViewById(R.id.Text_PW_ID);
                EditText Text_email = (EditText) findViewById(R.id.Text_PW_Email);

                String id = Text_id.getText().toString();
                String email = Text_email.getText().toString();
            }
        });
    }
}