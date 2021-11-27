package com.example.foodmate;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.os.Bundle;
import android.widget.Toast;

public class SignupActivity extends AppCompatActivity {
    TextView SignUp_btn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        SignUp_btn = findViewById(R.id.Sign_Up_Button);

        SignUp_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            //
            public void onClick(View view) {
                EditText Text_id = (EditText) findViewById(R.id.Text_SignUp_ID);
                EditText Text_pw = (EditText) findViewById(R.id.Text_SignUp_PW);
                EditText Text_email = (EditText) findViewById(R.id.Text_SignUp_Email);

                String id = Text_id.getText().toString();
                String pw = Text_pw.getText().toString();
                String email = Text_email.getText().toString();

                Toast.makeText(getApplicationContext(), pw, Toast.LENGTH_LONG).show();;
            }
        });


    }


}