package com.example.foodmate;

import android.content.Intent;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Toast;

public class LogInActivity extends AppCompatActivity {
    TextView FindIdText_btn; //id 찾기 글자를 눌렀을 때 처리를 위한 변수
    TextView FindPwText_btn; //pw 찾기 글자를 눌렀을 때 처리를 위한 변수
    TextView SignUp_btn;    //회원가입 글자를 눌렀을 때 처리를 위한 변수
    Button login_button; // 로그인 버튼을 눌렀을 때 처리를 위한 변수

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        FindIdText_btn = findViewById(R.id.textView3);
        FindPwText_btn = findViewById(R.id.textView4);
        SignUp_btn = findViewById(R.id.textView6);
        login_button = (Button) findViewById(R.id.login_button);

        //로그인 버튼 클릭시 입력창에 있는 id pw 저장
        login_button.setOnClickListener(new Button.OnClickListener(){
            @Override
            public void onClick(View view) {
                EditText Text_id = (EditText) findViewById(R.id.Text_id);
                EditText Text_pw = (EditText) findViewById(R.id.Text_pw);

                String id = Text_id.getText().toString();
                String pw = Text_pw.getText().toString();



                //Toast.makeText(getApplicationContext(), pw, Toast.LENGTH_LONG).show();;

                Intent intent_04 = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(intent_04);
            }
        });

        //아이디 찾기 버튼 클릭 시 화면 전환
        FindIdText_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            //
            public void onClick(View view) {
                Intent intent_01 = new Intent(getApplicationContext(), FindIdActivity.class);
                startActivity(intent_01);
            }
        });

        //비밀번호 찾기 클릭 시 화면 전환
        FindPwText_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            //
            public void onClick(View view) {
                Intent intent_02 = new Intent(getApplicationContext(), FindPwActivity.class);
                startActivity(intent_02);
            }
        });

        SignUp_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            //
            public void onClick(View view) {
                Intent intent_03 = new Intent(getApplicationContext(), SignupActivity.class);
                startActivity(intent_03);
            }
        });

    }

}