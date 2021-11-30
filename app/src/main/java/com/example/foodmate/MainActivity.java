package com.example.foodmate;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import java.util.Objects;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

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