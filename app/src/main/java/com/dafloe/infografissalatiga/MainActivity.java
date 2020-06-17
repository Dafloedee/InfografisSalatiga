package com.dafloe.infografissalatiga;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.dafloe.infografissalatiga.InsertKost.insert;
import com.dafloe.infografissalatiga.Kost.ActivityKost;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //inialisasi objek
        //Button test = findViewById(R.id.testInsert);
        de.hdodenhof.circleimageview.CircleImageView kost = findViewById(R.id.kost);
        getSupportActionBar().setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
        getSupportActionBar().setCustomView(R.layout.custom_toolbar);
        //getSupportActionBar().hide();

        //cek permission
        //ActivityCompat.requestPermissions(this,new String[]{Manifest.permission.ACCESS_FINE_LOCATION},123);
        //proses ketika object di klik
        kost.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               Intent intent = new Intent (MainActivity.this, ActivityKost.class);
                startActivity(intent);
            }
        });

       /* test.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, insert.class);
                startActivity(intent);
            }
        });*/

    }

}
