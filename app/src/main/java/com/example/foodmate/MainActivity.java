package com.example.foodmate;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.Objects;

public class MainActivity extends AppCompatActivity {

    private FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
    private static FirebaseFirestore db = FirebaseFirestore.getInstance();
    public String user_latitude;
    public String user_longitude;
    public String party_latitude;
    public String party_longitude;
    public double lat1, log1;
    public double lat2, log2;
    public int count = 0;
    public String result[][] = new String[2][2];
    public static Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        context = this;

        final String uid = user.getUid();
        final TextView text_current_location = (TextView)findViewById(R.id.Text_current_location);

        db.collection("user").whereEqualTo("uid", uid).get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if(task.isSuccessful()){
                            for(QueryDocumentSnapshot document : task.getResult()){
                                String address = (String)document.getData().get("address");
                                if(address == "")
                                {
                                    text_current_location.setText("위치를 설정해주세요");
                                }
                                else
                                {
                                    text_current_location.setText(address);
                                }
                            }
                        }
                    }
                });




        ImageButton location_button = (ImageButton) findViewById(R.id.btn_location);
        ImageButton btn_koreanfood = (ImageButton) findViewById(R.id.koreanfood);
        ImageButton btn_boonsik = (ImageButton) findViewById(R.id.boonsik);
        ImageButton btn_sushi = (ImageButton) findViewById(R.id.sushi);
        ImageButton btn_chicken = (ImageButton) findViewById(R.id.chicken);
        ImageButton btn_pizza = (ImageButton) findViewById(R.id.pizza);
        ImageButton btn_jjajang = (ImageButton) findViewById(R.id.jjajang);
        ImageButton btn_zokbal = (ImageButton) findViewById(R.id.zokbal);
        ImageButton btn_fastfood = (ImageButton) findViewById(R.id.fastfood);
        ImageButton btn_italian = (ImageButton) findViewById(R.id.italian);

        Intent intent_cl = new Intent(getApplicationContext(), ChatListActivity.class);

        // Toolbar 설정
        Toolbar toolbar = (Toolbar) findViewById(R.id.main_toolbar);
        setSupportActionBar(toolbar);
        Objects.requireNonNull(getSupportActionBar()).setDisplayShowTitleEnabled(false);

        location_button.setOnClickListener(new Button.OnClickListener(){
            @Override
            public void onClick(View view) {


                Intent intent = new Intent(getApplicationContext(), LocationActivity.class);
                startActivity(intent);
            }
        });


        btn_koreanfood.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                final String uid = user.getUid();
                //Toast.makeText(MatchingActivity.this, Document_Id, Toast.LENGTH_SHORT).show();

                db.collection("user").whereEqualTo("uid", uid).get()
                        .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                            @Override
                            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                                if(task.isSuccessful()){
                                    for(QueryDocumentSnapshot document : task.getResult()){
                                        user_latitude = (String)document.getData().get("latitude");
                                        user_longitude = (String)document.getData().get("longitude");


                                        db.collection("party").get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                                            @Override
                                            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                                                if (task.isSuccessful()) {
                                                    for (QueryDocumentSnapshot document : task.getResult()) {
                                                        party_latitude = (String)document.getData().get("latitude");
                                                        party_longitude = (String)document.getData().get("longitude");


                                                        lat1 = Double.parseDouble(user_latitude);
                                                        log1 = Double.parseDouble(user_longitude);
                                                        lat2 = Double.parseDouble(party_latitude);
                                                        log2 = Double.parseDouble(party_longitude);
                                                        String unit = "m";

                                                        double dis = distance(lat1,  log1, lat2,  log2,  unit);

                                                        if (dis < 1000){
                                                            //Toast.makeText(MatchingActivity.this, Double.toString(dis), Toast.LENGTH_SHORT).show();
                                                            //Toast.makeText(MatchingActivity.this, "범위 내 입니다.", Toast.LENGTH_SHORT).show();
                                                            result[count][0] = (String)document.getData().get("partyName");
                                                            result[count][1] = Double.toString(dis);
                                                            count++;
                                                            //Toast.makeText(MatchingActivity.this, Double.toString(count), Toast.LENGTH_SHORT).show();
                                                        }
                                                        else{
                                                            //Toast.makeText(MatchingActivity.this, "범위 밖 입니다.", Toast.LENGTH_SHORT).show();
                                                            //Toast.makeText(MatchingActivity.this, Double.toString(dis), Toast.LENGTH_SHORT).show();
                                                        }
                                                    }
                                                } else {
                                                    //Log.d(TAG, "Error getting documents: ", task.getException());
                                                }



                                                Intent intent = new Intent(getApplicationContext(), ChatListActivity.class);

                                                //Bundle bundle = new Bundle();

                                                //.putString("result", result);


                                                startActivity(intent);

                                            }
                                        });

                                        //Toast.makeText(MatchingActivity.this, pid, Toast.LENGTH_SHORT).show();

                                        //double dis = distance(a,  b, c,  d,  unit);

                                        //if (dis < 1000){
                                        //Toast.makeText(MatchingActivity.this, Double.toString(dis), Toast.LENGTH_SHORT).show();
                                        //Toast.makeText(MatchingActivity.this, "범위 내 입니다.", Toast.LENGTH_SHORT).show();
                                        //}
                                        //else{
                                        //Toast.makeText(MatchingActivity.this, "범위 밖 입니다.", Toast.LENGTH_SHORT).show();
                                        //Toast.makeText(MatchingActivity.this, Double.toString(dis), Toast.LENGTH_SHORT).show();
                                        //}
                                    }
                                }
                                else {
                                    //Toast.makeText(MatchingActivity.this, "가져오기 실패", Toast.LENGTH_SHORT).show();
                                }
                            }
                        });
                //startActivity(intent_cl);
            }
        });
        btn_boonsik.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                startActivity(intent_cl);
            }
        });
        btn_sushi.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                startActivity(intent_cl);
            }
        });
        btn_chicken.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                startActivity(intent_cl);
            }
        });
        btn_pizza.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                startActivity(intent_cl);
            }
        });
        btn_jjajang.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                startActivity(intent_cl);
            }
        });
        btn_zokbal.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                startActivity(intent_cl);
            }
        });
        btn_fastfood.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                startActivity(intent_cl);
            }
        });
        btn_italian.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                startActivity(intent_cl);
            }
        });
    }

    // Toolbar에 appbar_action menu 추가
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.appbar_action, menu);
        return true;
    }

    // Toolbar의 profile button 설정(누르면 Profile activity로 이동)
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == R.id.action_profile) {
            Intent intent = new Intent(MainActivity.this, ProfileActivity.class);
            startActivity(intent);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    //뒤로가기 방지
    @Override public void onBackPressed() {
        //super.onBackPressed();
    }

    // 거리계산
    private static double distance(double lat1, double lon1, double lat2, double lon2, String unit) {

        double theta = lon1 - lon2;
        double dist = Math.sin(deg2rad(lat1)) * Math.sin(deg2rad(lat2)) + Math.cos(deg2rad(lat1)) * Math.cos(deg2rad(lat2)) * Math.cos(deg2rad(theta));

        dist = Math.acos(dist);
        dist = rad2deg(dist);
        dist = dist * 60 * 1.1515;

        if (unit == "kilometer") {
            dist = dist * 1.609344;
        } else if(unit == "m"){
            dist = dist * 1609.344;
        }

        return (dist);
    }

    // This function converts decimal degrees to radians
    private static double deg2rad(double deg) {
        return (deg * Math.PI / 180.0);
    }

    // This function converts radians to decimal degrees
    private static double rad2deg(double rad) {
        return (rad * 180 / Math.PI);
    }


}