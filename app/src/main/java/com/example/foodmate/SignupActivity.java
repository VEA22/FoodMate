package com.example.foodmate;

import androidx.appcompat.app.AppCompatActivity;
import androidx.annotation.NonNull;

import android.content.Intent;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Button;
import android.os.Bundle;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;


public class SignupActivity extends AppCompatActivity {
    private FirebaseAuth firebaseAuth;
    private TextView editTextID;
    private TextView editTextPW;
    private TextView editTextName;
    private TextView editTextMail;
    private TextView editTextPhone;

    TextView SignUp_btn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        firebaseAuth = FirebaseAuth.getInstance(); //파이어베이스 계정 인스턴스 get

        //editTextID = findViewById(R.id.textView12);
        editTextPW = findViewById(R.id.textView17);
        editTextMail = findViewById(R.id.Text_SignUp_Email);
        editTextPhone = findViewById(R.id.textView18);
        SignUp_btn = findViewById(R.id.Sign_Up_Button);

        SignUp_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            //
            public void onClick(View view) {
                //EditText Text_id = (EditText) findViewById(R.id.Text_SignUp_ID);
                EditText Text_pw = (EditText) findViewById(R.id.Text_SignUp_PW);
                EditText Text_email = (EditText) findViewById(R.id.Text_SignUp_Email);

                //String id = Text_id.getText().toString();
                String pw = Text_pw.getText().toString();
                String email = Text_email.getText().toString();

                Toast.makeText(getApplicationContext(), pw, Toast.LENGTH_LONG).show();

                if (!editTextMail.getText().toString().equals("") && !editTextPW.getText().toString().equals("")) {
                    // 아이디, 비밀번호가 공백이 아닌 경우
                    createUser(editTextID.getText().toString(), editTextPW.getText().toString(), editTextName.getText().toString(), editTextMail.getText().toString(), editTextPhone.getText().toString());
                } else {
                    // 아이디와 비밀번호가 공백인 경우
                    Toast.makeText(SignupActivity.this, "아이디와 비밀번호를 입력하세요.", Toast.LENGTH_LONG).show();
                }
            }
        });
    }

    private void createUser(String id, String pw, String name, String mail, String phone) {
        firebaseAuth.createUserWithEmailAndPassword(mail, pw)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // 회원가입 성공시
                            Toast.makeText(SignupActivity.this, "회원가입 성공", Toast.LENGTH_SHORT).show();
                            finish();
                        } else {
                            // 계정이 중복된 경우
                            Toast.makeText(SignupActivity.this, "이미 존재하는 계정입니다.", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }
}