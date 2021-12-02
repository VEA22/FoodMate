package com.example.foodmate;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;
import com.google.android.gms.tasks.OnCompleteListener;

import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

public class MatchingActivity extends AppCompatActivity {

    private FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
    private static FirebaseFirestore db = FirebaseFirestore.getInstance();
    public String user_latitude;
    public String user_longitude;
    public String party_latitude;
    public String party_longitude;
    public String js_home_latitude = "36.6062627";
    public String js_home_longitude = "127.2925354";
    public String bj_home_latitude = "36.6081661";
    public String bj_home_longitude = "127.2916509";
    public String os_station_latitude = "36.6201593";
    public String os_station_longitude = "127.3275301";
    public double lat1, log1;
    public double lat2, log2;
    public int count = 0;
    public String pid;
    public String result[][];
    //public String Document_Id;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_matching);
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
                                                Toast.makeText(MatchingActivity.this, Double.toString(dis), Toast.LENGTH_SHORT).show();
                                                Toast.makeText(MatchingActivity.this, "범위 내 입니다.", Toast.LENGTH_SHORT).show();
                                                result[count][0] = (String)document.getData().get("partyName");
                                                result[count][1] = Double.toString(dis);
                                                count++;
                                                Toast.makeText(MatchingActivity.this, Double.toString(count), Toast.LENGTH_SHORT).show();
                                                }
                                                else{
                                                Toast.makeText(MatchingActivity.this, "범위 밖 입니다.", Toast.LENGTH_SHORT).show();
                                                Toast.makeText(MatchingActivity.this, Double.toString(dis), Toast.LENGTH_SHORT).show();
                                                }
                                            }
                                        } else {
                                            //Log.d(TAG, "Error getting documents: ", task.getException());
                                        }
/*
                                        Intent intent_Matching_To_Main = new Intent(MatchingActivity.this, MainActivity.class);

                                        Bundle bundle = new Bundle();
                                        for(int i = 0; i < count ; i++)
                                        {
                                            bundle.putStringArray("result", result[count]);
                                        }
                                        bundle.putInt("Key", count);

                                        Toast.makeText(MatchingActivity.this, "전환 성공", Toast.LENGTH_SHORT).show();

                                        startActivity(intent_Matching_To_Main);


 */
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
                            Toast.makeText(MatchingActivity.this, "가져오기 실패", Toast.LENGTH_SHORT).show();
                        }
                    }
                });


        /*DocumentReference docRef = db.collection("user").document(Document_Id);
        Toast.makeText(MatchingActivity.this, Document_Id, Toast.LENGTH_SHORT).show();

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
                        Toast.makeText(MatchingActivity.this, Document_Id, Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(MatchingActivity.this, "데이터 없음", Toast.LENGTH_SHORT).show();
                    }
                    //그렇지 않을때
                } else {
                    Toast.makeText(MatchingActivity.this, "가져오기 실패", Toast.LENGTH_SHORT).show();
                }
            }
        });*/
/*
        lat1 = Double.parseDouble(user_latitude);
        log1 = Double.parseDouble(user_longitude);
        lat2 = Double.parseDouble(party_latitude);
        log2 = Double.parseDouble(party_longitude);
        String unit = "m";
        //Toast.makeText(MatchingActivity.this, Double.toString(a), Toast.LENGTH_SHORT).show();
        double dis = distance(lat1,  log1, lat2,  log2,  unit);

        if (dis < 1000){
            Toast.makeText(MatchingActivity.this, Double.toString(dis), Toast.LENGTH_SHORT).show();
            Toast.makeText(MatchingActivity.this, "범위 내 입니다.", Toast.LENGTH_SHORT).show();
        }
        else{
            Toast.makeText(MatchingActivity.this, "범위 밖 입니다.", Toast.LENGTH_SHORT).show();
            Toast.makeText(MatchingActivity.this, Double.toString(dis), Toast.LENGTH_SHORT).show();
        }

*/
    }


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