package com.example.foodmate;

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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

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
                startActivity(intent_cl);
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

}