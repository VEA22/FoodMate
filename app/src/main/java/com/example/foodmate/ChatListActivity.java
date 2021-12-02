package com.example.foodmate;

import static android.content.ContentValues.TAG;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ChatListActivity extends AppCompatActivity {

    private FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser(); //현재 접속 중인 유저 인스턴스 get
    private FirebaseFirestore db; //데이터베이스 인스턴스 get
    private CollectionReference userRef;
    private Map<String, Object> party = new HashMap<>(); //데이터베이스 임시 저장 맵
    public String result[][] = new String[2][2];


    static String partyName;
    static String latitude;
    static String longitude;

    private RecyclerAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat_list);

        //Intent intent = getIntent();

        //Bundle bundle = intent.getExtras();
        //int count = bundle.getInt("key", 0);

        //result = bundle.getString("result");
        result = ((MainActivity) MainActivity.context).result;
        //Toast.makeText(ChatListActivity.this, result[0], Toast.LENGTH_SHORT).show();


        db = FirebaseFirestore.getInstance(); // db 인스턴스 get
        userRef = db.collection("user");

        init();
        getData();

        Button make_room = (Button) findViewById(R.id.make_chat);
        make_room.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final String uid = user.getUid(); //현재 접속 중인 유저 uid 추출

                //앞에서 추출한 uid를 기반으로 user DB에서 조회한 뒤 현재 로그인 한 유저의 이름 추출
                db.collection("user").whereEqualTo("uid", uid).get()
                        .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                            @Override
                            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                                if(task.isSuccessful()){
                                    for(QueryDocumentSnapshot document : task.getResult()){
                                        partyName = (String)document.getData().get("name") + "'s Party";
                                        latitude = (String)document.getData().get("latitude");
                                        longitude = (String)document.getData().get("longitude");


                                        //파티 맵에 정보 저장
                                        party.put("uid", uid);
                                        party.put("partyName", partyName);
                                        party.put("latitude", latitude);
                                        party.put("longitude", longitude);
                                        party.put("category", "");

                                        //party db set
                                        db.collection("party").add(party);
                                        Toast.makeText(ChatListActivity.this, "파티 DB 생성 완료", Toast.LENGTH_LONG).show();
                                    }
                                }
                            }
                        });

                Intent intent = new Intent(getApplicationContext(), temp_chat.class);
                startActivity(intent);
            }
        });
    }

    private void init() {
        RecyclerView recyclerView = findViewById(R.id.recyclerView);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(linearLayoutManager);

        adapter = new com.example.foodmate.RecyclerAdapter();
        recyclerView.setAdapter(adapter);

//        List<String> listTitle;
    }

    private void getData(){
        // 임의의 데이터입니다.
        List<String> listTitle = Arrays.asList(result[0][0] + "     " + result[0][1], result[1][0] + "      "  + result[1][1]);
        List<String> listContent = Arrays.asList(
                "이 꽃은 국화입니다.",
                "여기는 사막입니다.",
                "이 꽃은 수국입니다.",
                "이 동물은 해파리입니다.",
                "이 동물은 코알라입니다.",
                "이것은 등대입니다.",
                "이 동물은 펭귄입니다.",
                "이 꽃은 튤립입니다.",
                "이 꽃은 국화입니다.",
                "여기는 사막입니다.",
                "이 꽃은 수국입니다.",
                "이 동물은 해파리입니다.",
                "이 동물은 코알라입니다.",
                "이것은 등대입니다.",
                "이 동물은 펭귄입니다.",
                "이 꽃은 튤립입니다."
        );

        for (int i = 0; i < listTitle.size(); i++) {
            // 각 List의 값들을 data 객체에 set 해줍니다.
            Data data = new Data();
            data.setTitle(listTitle.get(i));

            // 각 값이 들어간 data를 adapter에 추가합니다.
            adapter.addItem(data);
        }

        // adapter의 값이 변경되었다는 것을 알려줍니다.
        adapter.notifyDataSetChanged();
    }
}