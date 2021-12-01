package com.example.foodmate;

import android.content.Intent;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;


import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;



public class LogInActivity extends AppCompatActivity {
    private FirebaseAuth firebaseAuth;
    private FirebaseAuth.AuthStateListener firebaseAuthListener;
    private TextView editTextEmail;
    private TextView editTextPassword;

    TextView FindIdText_btn; //id 찾기 글자를 눌렀을 때 처리를 위한 변수
    TextView FindPwText_btn; //pw 찾기 글자를 눌렀을 때 처리를 위한 변수
    TextView SignUp_btn;    //회원가입 글자를 눌렀을 때 처리를 위한 변수
    Button login_button; // 로그인 버튼을 눌렀을 때 처리를 위한 변수

    // 수정
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        firebaseAuth = FirebaseAuth.getInstance(); //FirebaseAuth 객체의 공유 인스턴스 get

        editTextEmail = (TextView) findViewById(R.id.Text_id);
        editTextPassword = (TextView) findViewById(R.id.Text_pw);

        FindIdText_btn = findViewById(R.id.textView3);
        FindPwText_btn = findViewById(R.id.textView4);
        SignUp_btn = findViewById(R.id.textView6);
        login_button = (Button) findViewById(R.id.login_button);

        //로그인 버튼 클릭시 입력창에 있는 id pw를 통해 Firebase 접근
        login_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (!editTextEmail.getText().toString().equals("") && !editTextPassword.getText().toString().equals("")) {
                    loginUser(editTextEmail.getText().toString(), editTextPassword.getText().toString());
                } else {
                    Toast.makeText(LogInActivity.this, "계정과 비밀번호를 입력하세요.", Toast.LENGTH_LONG).show();
                }

                //Toast.makeText(getApplicationContext(), pw, Toast.LENGTH_LONG).show();;

                //Intent intent_04 = new Intent(getApplicationContext(), MainActivity.class);
                //startActivity(intent_04);
            }
        });

        firebaseAuthListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {

            }
        };

        //아이디 찾기 버튼 클릭 시 화면 전환
        FindIdText_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent_01 = new Intent(getApplicationContext(), FindIdActivity.class);
                startActivity(intent_01);
            }
        });

        //비밀번호 찾기 클릭 시 화면 전환
        FindPwText_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent_02 = new Intent(getApplicationContext(), FindPwActivity.class);
                startActivity(intent_02);
            }
        });

        SignUp_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent_03 = new Intent(getApplicationContext(), SignupActivity.class);
                startActivity(intent_03);
            }
        });

    }

    //로그인 처리 함수
    public void loginUser(String email, String password) {
        firebaseAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // 로그인 성공
                            //Toast.makeText(LogInActivity.this, "로그인 성공", Toast.LENGTH_SHORT).show();
                            FirebaseUser user = firebaseAuth.getCurrentUser();

                            //올바른 로그인이 이루어졌을 경우 MainActivity 호출
                            if (user != null) {
                                Intent intent = new Intent(LogInActivity.this, MainActivity.class);
                                Toast.makeText(LogInActivity.this, "로그인 성공", Toast.LENGTH_SHORT).show();
                                startActivity(intent);
                                //finish();
                            } else {
                            }
                        } else {
                            // 로그인 실패
                            //Toast.makeText(LogInActivity.this, "아이디 또는 비밀번호가 일치하지 않습니다.", Toast.LENGTH_SHORT).show();
                            Toast.makeText(LogInActivity.this, email+password, Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }

    @Override
    protected void onStart(){
        super.onStart();
    }

    @Override
    protected void onStop(){
        super.onStop();
        if(firebaseAuthListener != null){
            firebaseAuth.removeAuthStateListener(firebaseAuthListener);
        }
    }

}