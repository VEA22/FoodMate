package com.example.foodmate;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.annotation.IdRes;
import androidx.appcompat.app.AppCompatActivity;
import androidx.annotation.NonNull;

import android.content.Intent;
import android.location.Address;
import android.location.Geocoder;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
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
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.io.IOException;
import java.util.List;
import java.util.HashMap;
import java.util.Map;

public class LocationActivity extends AppCompatActivity
        implements OnMapReadyCallback {

    private FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
    private static FirebaseFirestore db = FirebaseFirestore.getInstance();
    private GoogleMap mMap;
    private Geocoder geocoder;
    private Button button;
    private ImageButton imageButton;
    private EditText editText;
    private FirebaseAuth firebaseAuth;
    private DatabaseReference mPostReference;
    private String address;
    private String latitude ;
    private String longitude;
    //private Map<String, Object> location = new HashMap<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_location);
        editText = (EditText) findViewById(R.id.et_location_search);
        button = (Button) findViewById(R.id.btn_cur_location);
        imageButton = (ImageButton) findViewById(R.id.btn_location_search);

        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        assert mapFragment != null;
        mapFragment.getMapAsync(this);
    }

    @Override
    public void onMapReady(@NonNull final GoogleMap googleMap) {
        mMap = googleMap;
        geocoder = new Geocoder(this);

        // 맵 터치 이벤트 구현 //
        mMap.setOnMapClickListener(new GoogleMap.OnMapClickListener(){
            @Override
            public void onMapClick(@NonNull LatLng point) {
                MarkerOptions mOptions = new MarkerOptions();
                // 마커 타이틀
                mOptions.title("마커 좌표");
                double latitude = point.latitude; // 위도
                double longitude = point.longitude; // 경도
                // 마커의 스니펫(간단한 텍스트) 설정
                mOptions.snippet(Double.toString(latitude) + ", " + Double.toString(longitude));
                // LatLng: 위도 경도 쌍을 나타냄
                mOptions.position(new LatLng(latitude, longitude));
                // 마커(핀) 추가
                googleMap.addMarker(mOptions);
            }
        });
        ////////////////////

        // 버튼 이벤트
        imageButton.setOnClickListener(new ImageButton.OnClickListener() {
            @Override
            public void onClick(View v){
                String str=editText.getText().toString();
                List<Address> addressList = null;
                try {
                    // editText에 입력한 텍스트(주소, 지역, 장소 등)을 지오 코딩을 이용해 변환
                    addressList = geocoder.getFromLocationName(
                            str, // 주소
                            10); // 최대 검색 결과 개수
                }
                catch (IOException e) {
                    e.printStackTrace();
                }

                assert addressList != null;
                System.out.println(addressList.get(0).toString());
                // 콤마를 기준으로 split
                String []splitStr = addressList.get(0).toString().split(",");
                address = splitStr[0].substring(splitStr[0].indexOf("\"") + 1,splitStr[0].length() - 2); // 주소
                System.out.println(address);

                latitude = splitStr[10].substring(splitStr[10].indexOf("=") + 1); // 위도
                longitude = splitStr[12].substring(splitStr[12].indexOf("=") + 1); // 경도
                System.out.println(latitude);
                System.out.println(longitude);

                // 좌표(위도, 경도) 생성
                LatLng point = new LatLng(Double.parseDouble(latitude), Double.parseDouble(longitude));
                // 마커 생성
                MarkerOptions mOptions2 = new MarkerOptions();
                mOptions2.title("search result");
                mOptions2.snippet(address);
                mOptions2.position(point);
                // 마커 추가
                mMap.addMarker(mOptions2);
                // 해당 좌표로 화면 줌
                mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(point,15));
            }
        });

        LatLng sydney = new LatLng(-34, 151);
        mMap.addMarker(new MarkerOptions().position(sydney).title("Marker in Sydney"));
        mMap.moveCamera(CameraUpdateFactory.newLatLng(sydney));



        button.setOnClickListener(new Button.OnClickListener() {
            @Override
            public void onClick(View v){
                final String uid = user.getUid();

                DocumentReference locationRef = db.collection("user")
                        .document("1OJuPqokbP6jNXzqa7sd");

                //uid를 가져 온 후 해당 uid를 가진 문서의 위도 경도값을 업데이트 함
                db.collection("user").whereEqualTo("uid", uid).get()
                        .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                            @Override
                            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                                if(task.isSuccessful()){
                                    for(QueryDocumentSnapshot document : task.getResult()){
                                        locationRef.update("address", address, "latitude", latitude,
                                                "longitude", longitude).addOnCompleteListener(new OnCompleteListener<Void>() {
                                            @Override
                                            public void onComplete(@NonNull Task<Void> task) {
                                                if(task.isSuccessful())
                                                {
                                                    Toast.makeText(LocationActivity.this, "성공", Toast.LENGTH_SHORT).show();
                                                }
                                                else
                                                {
                                                    Toast.makeText(LocationActivity.this, "실패", Toast.LENGTH_SHORT).show();
                                                }
                                            }
                                        });
                                    }
                                }
                            }
                        });
/*
                locationRef.update("address", address, "latitude", latitude,
                        "longitude", longitude).addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if(task.isSuccessful())
                        {
                            //Toast.makeText(LocationActivity.this, "성공", Toast.LENGTH_SHORT).show();
                        }
                        else
                        {
                            Toast.makeText(LocationActivity.this, "실패", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
*/

                Intent intent_01 = new Intent(getApplicationContext(), MatchingActivity.class);
                startActivity(intent_01);
            }

        });

    }

    public Map<String, Object> toMap() {
        HashMap<String, Object> location = new HashMap<>();
        location.put("address", address);
        location.put("latitude", latitude);
        location.put("longitude", longitude);

        return location;
    }

   /* private void createUser(String mail, String pw, String name, String phone, Boolean gender) {
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
                                            Toast.makeText(LocationActivity.this, "DB 삽입 성공", Toast.LENGTH_SHORT).show(); //DB 삽입 여부 체크
                                        }
                                    });

                            Toast.makeText(LocationActivity.this, "회원가입 성공", Toast.LENGTH_SHORT).show();
                            finish();
                        } else {
                            // 가입 실패
                            Toast.makeText(LocationActivity.this, "이메일 또는 비밀번호 형식 오류", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }*/

}


