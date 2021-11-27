package com.example.foodmate;

import androidx.annotation.IdRes;
import androidx.appcompat.app.AppCompatActivity;
import androidx.annotation.NonNull;

import android.view.View;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.os.Bundle;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;


public class SignupActivity extends AppCompatActivity {
    private FirebaseAuth firebaseAuth;
    private FirebaseFirestore db;

    private boolean gender;


    //private TextView editTextID;
    private TextView editTextPW;
    private TextView editTextName;
    private TextView editTextMail;
    private TextView editTextPhone;
    private RadioButton male, female;
    private RadioGroup rGender;
    private Map<String, Object> user = new HashMap<>();

    TextView SignUp_btn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        //인스턴스 셋팅
        firebaseAuth = FirebaseAuth.getInstance(); //파이어베이스 계정 인스턴스 get
        db = FirebaseFirestore.getInstance();


        //editTextID = findViewById(R.id.textView12);
        editTextMail = findViewById(R.id.Text_SignUp_Email);
        editTextPW = findViewById(R.id.Text_SignUp_PW);
        editTextName = findViewById(R.id.Text_SignUp_NAME);
        editTextPhone = findViewById(R.id.Text_SignUp_Phone);
        SignUp_btn = findViewById(R.id.Sign_Up_Button);
        male = findViewById(R.id.gender_male_btn);
        female = findViewById(R.id.gender_female_btn);
        rGender = findViewById(R.id.radioGenderGroup);
        rGender.setOnCheckedChangeListener(radioGroupButtonChanageListener);

        //Sign UP 버튼 클릭
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

                //Toast.makeText(getApplicationContext(), pw, Toast.LENGTH_LONG).show();

                if (!editTextMail.getText().toString().equals("") && !editTextPW.getText().toString().equals("")) {
                    // 아이디, 비밀번호가 공백이 아닌 경우
                    createUser(editTextMail.getText().toString(), editTextPW.getText().toString(), editTextName.getText().toString(), editTextPhone.getText().toString(), gender);
                } else {
                    // 아이디와 비밀번호가 공백인 경우
                    Toast.makeText(SignupActivity.this, "아이디와 비밀번호를 입력하세요.", Toast.LENGTH_LONG).show();
                }
            }
        });
    }

    //라디오 그룹 클릭 리스너
    RadioGroup.OnCheckedChangeListener radioGroupButtonChanageListener = new RadioGroup.OnCheckedChangeListener() {
        @Override
        public void onCheckedChanged(RadioGroup radioGroup, @IdRes int i) {
            //남자 선택
            if(i == R.id.gender_male_btn){
                gender = false;
            }

            //여자 선택
            else if(i == R.id.gender_female_btn){
                gender = true;
            }
        }
    };

    //유저 가입 처리 함수
    private void createUser(String mail, String pw, String name, String phone, Boolean gender) {
        firebaseAuth.createUserWithEmailAndPassword(mail, pw)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        //회원가입 성공시
                        if (task.isSuccessful()) {
                            final String uid = task.getResult().getUser().getUid(); //가입에 성공한 계정의 uid 추출
                            //user 맵에 프로필 정보 저장
                            user.put("uid", uid);
                            user.put("mail", mail);
                            user.put("name", name);
                            user.put("phone", phone);
                            user.put("gender", gender);

                            //db에 정보 삽입
                            db.collection("user")
                                    .add(user)
                                    .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                                        @Override
                                        public void onSuccess(DocumentReference documentReference) {
                                            Toast.makeText(SignupActivity.this, "DB 삽입 성공", Toast.LENGTH_SHORT).show(); //DB 삽입 여부 체크
                                        }
                                    });

                            Toast.makeText(SignupActivity.this, "회원가입 성공", Toast.LENGTH_SHORT).show();
                            finish();
                        } else {
                            // 가입 실패
                            Toast.makeText(SignupActivity.this, "이메일 또는 비밀번호 형식 오류", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }
}