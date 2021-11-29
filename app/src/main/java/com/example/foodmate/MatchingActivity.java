package com.example.foodmate;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.Toast;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Tasks;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;



import java.util.HashMap;
import java.util.Map;

public class MatchingActivity extends AppCompatActivity {

    private static FirebaseFirestore db = FirebaseFirestore.getInstance();
    private String user_latitude;
    private String user_longitude;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_matching);

        DocumentReference docRef = db.collection("user").document("1OJuPqokbP6jNXzqa7sd");

        docRef.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot >() {
            //firestore에서 위도 경도값 가져옴
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot > task) {
                //작업이 성공적으로 마쳤을때
                if (task.isSuccessful()) {
                    DocumentSnapshot document = task.getResult();
                    if (document.exists()) {
                        user_latitude = document.getString("latitude");
                        user_longitude = document.getString("longitude");
                        Toast.makeText(MatchingActivity.this, user_latitude, Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(MatchingActivity.this, "데이터 없음", Toast.LENGTH_SHORT).show();
                    }
                    //그렇지 않을때
                } else {
                    Toast.makeText(MatchingActivity.this, "가져오기 실패", Toast.LENGTH_SHORT).show();
                }
            }
        });

        //Toast.makeText(MatchingActivity.this, "로그인 성공", Toast.LENGTH_SHORT).show();
    }
}