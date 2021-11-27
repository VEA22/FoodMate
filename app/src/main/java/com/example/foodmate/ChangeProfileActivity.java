package com.example.foodmate;

import android.Manifest;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import de.hdodenhof.circleimageview.CircleImageView;

public class ChangeProfileActivity extends AppCompatActivity {

    final private static String TAG = "GILBOMI";
    final static int TAKE_PICTURE = 1;
    private final int GET_GALLERY_IMAGE = 2;

    Button button_complete;
    CircleImageView click_image;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_profile);

        button_complete = (Button) findViewById(R.id.complete);
        button_complete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), ProfileActivity.class);
                // 중복된 프래그먼트 삭제
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
            }
        });

        Button back_in_change_profile = (Button) findViewById(R.id.back_in_change_profile);
        back_in_change_profile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });

        click_image = (CircleImageView) findViewById(R.id.change_profile_image);
        // 프로필 사진 클릭 시
        click_image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //대화 상자 설정
                AlertDialog.Builder builder = new AlertDialog.Builder(ChangeProfileActivity.this);
                builder.setTitle("사진 선택");
                builder.setCancelable(false);
                final String[] select = new String[]{"카메라 촬영", "앨범에서 선택"};

                builder.setItems(select, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        // 카메라 연동
                        if (which == 0) {
                            if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                                //카메라, 미디어 저장소 권한 있는지 확인
                                if(checkSelfPermission(Manifest.permission.CAMERA) == PackageManager.PERMISSION_GRANTED &&
                                        checkSelfPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED) {
                                    Log.d(TAG, "권한 설정 완료");
                                }
                                //권한 요청
                                else {
                                    Log.d(TAG, "권한 설정 요청");
                                    ActivityCompat.requestPermissions(ChangeProfileActivity.this, new String[]{
                                            Manifest.permission.CAMERA, Manifest.permission.WRITE_EXTERNAL_STORAGE
                                    }, 1);
                                }
                            }
                            //카메라 화면으로 이동
                            Intent cameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                            startActivityForResult(cameraIntent, TAKE_PICTURE);

                        }
                        // 앨범 연동
                        else {
                            Intent albumIntent = new Intent(Intent.ACTION_PICK);
                            albumIntent.setDataAndType(MediaStore.Images.Media.EXTERNAL_CONTENT_URI,"image/*");
                            startActivityForResult(albumIntent, GET_GALLERY_IMAGE);
                        }
                    }
                });

                builder.setNegativeButton("취소", null);

                // 대화상자 생성
                AlertDialog dlg = builder.create();
                dlg.show();

            }
        });
    }

    // 권한 요청 및 설정
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        Log.d(TAG, "onRequestPermissionsResult");
        if (grantResults[0] == PackageManager.PERMISSION_GRANTED &&
                grantResults[1] == PackageManager.PERMISSION_GRANTED ) {
            Log.d(TAG, "Permission: " + permissions[0] + "was " + grantResults[0]);
        }
    } // 카메라로 촬영한 사진의 썸네일을 가져와 이미지뷰에 띄워줌

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent intent) {
        super.onActivityResult(requestCode, resultCode, intent);
        switch (requestCode) {
            //사진찍기
            case TAKE_PICTURE:
                if (resultCode == RESULT_OK && intent.hasExtra("data")) {
                    Bitmap bitmap = (Bitmap) intent.getExtras().get("data");
                    //카메라 이미지 넣기
                    if (bitmap != null) {
                        click_image.setImageBitmap(bitmap);
                    }
                }
                break;
                //갤러리에서 이미지 가져오기
            case GET_GALLERY_IMAGE:
                if (resultCode == RESULT_OK && intent != null && intent.getData() != null){
                    Uri selectedImageUri = intent.getData();
                    //갤러리 이미지 넣기기
                   click_image.setImageURI(selectedImageUri);
                }
        }
    }




}



