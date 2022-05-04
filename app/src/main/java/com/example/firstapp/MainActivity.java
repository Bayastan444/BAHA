package com.example.firstapp;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class MainActivity extends AppCompatActivity {
    private EditText etValue;
    private Button btnNextScreen;
    private Button btnWhatsapp;
    private Button btnSearch;
    private Button btnCall;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initViews();
    }

    private void initViews() {
        etValue = findViewById(R.id.et_value);
        btnWhatsapp = findViewById(R.id.btn_whatsapp);
        btnNextScreen = findViewById(R.id.btn_open_next_screen);
        btnCall = findViewById(R.id.btn_call);
        btnSearch = findViewById(R.id.btn_search);

        btnNextScreen.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent nextScreenIntent = new Intent(MainActivity.this, SecondActivity.class);
                String valueFromEdittext = etValue.getText().toString();
                nextScreenIntent.putExtra("value", valueFromEdittext);
                startActivity(nextScreenIntent);

                btnCall.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        int code = ActivityCompat.checkSelfPermission(MainActivity.this, Manifest.permission.CALL_PHONE);
                        boolean isPermissionGranted = code == PackageManager.PERMISSION_GRANTED;
                        if (isPermissionGranted) {
                            call();
                        } else {
                            requestCallPermission();
                        }
                    }
                });
            }

           private void requestCallPermission(){
                String[] permissions = new String[]{Manifest.permission.CALL_PHONE};

               ActivityCompat.requestPermissions(MainActivity.this, permissions, 1);
           }

            private void call(){
                String phone = etValue.getText().toString();
                Intent dialIntent = new Intent(Intent.ACTION_DIAL,Uri.fromParts("tel",phone,null));
                startActivity(dialIntent);
            }

            public void onRequestPermissionsResult(int requestCode,@NonNull String[] permissions,@NonNull int[] grantResults){
                if (requestCode ==1) {
                    boolean isPermissionGranted = grantResults[0] == PackageManager.PERMISSION_GRANTED;
                    if (isPermissionGranted) {
                        call();
                    }
                }else{
                    MainActivity.super.onRequestPermissionsResult(requestCode,permissions,grantResults);
                }
            }
        });
    }



}









