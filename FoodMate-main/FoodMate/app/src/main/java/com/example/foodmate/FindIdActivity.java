package com.example.foodmate;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class FindIdActivity extends AppCompatActivity {
    Button SendCode_button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_find_id);

        SendCode_button = (Button) findViewById(R.id.button2);

        SendCode_button.setOnClickListener(new Button.OnClickListener(){
            @Override
            public void onClick(View view) {
                EditText Text_Email = (EditText) findViewById(R.id.Text_Email);

                String Email = Text_Email.getText().toString();
            }
        });
    }
}