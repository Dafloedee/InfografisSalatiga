package com.dafloe.infografissalatiga.InsertKost;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.dafloe.infografissalatiga.R;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;
import java.util.Map;

import kotlin.reflect.KAnnotatedElement;

import static androidx.constraintlayout.widget.Constraints.TAG;

public class insert extends AppCompatActivity {

    private DatabaseReference mDatabase;
    KostInsertSetterGetter kostInsert;
    KostInsertAlamatSetter AlamatInsert;
    KostInsertKontak kotakInsert;
    KostInsertFasilitas FasilitasInsert;
    private  EditText eNama,eHarga,eNamaJalan,eNomor,eKecamatan,eNoHp,eWakil,eLain;
    private Spinner spTipe,spKamarMandi;
    private String sNama,sTipe,sharga,sNamaJalan,sNomor,sKecamatan,sNohp,sWakil,sKamarMandi,sKasur,sWifi,sMeja,sLemari;
    private CheckBox cbkasur,cbWifi,cbmeja,cbLemari;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_insert);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("Insert Data Kost");

        eNama= findViewById(R.id.editTextNama);
        eHarga = findViewById(R.id.editTextHargaInsert);
        eNamaJalan = findViewById(R.id.editTextJalanInsert);
        eNomor = findViewById(R.id.editTextNoInsert);
        eKecamatan = findViewById(R.id.editTextKecamatanInsert);
        eNoHp = findViewById(R.id.editTextnohpInsert);
        eWakil = findViewById(R.id.editTextwakilInsert);
        Button simpan = findViewById(R.id.idSimpanInsert);
        spTipe = findViewById(R.id.spinner_gender);
        spKamarMandi = findViewById(R.id.spinner_mandi);
        cbkasur = findViewById(R.id.check_kasur);
        cbWifi = findViewById(R.id.check_Wifi);
        cbmeja = findViewById(R.id.check_meja);
        cbLemari = findViewById(R.id.check_lemari);
        eNoHp = findViewById(R.id.editTextnohpInsert);
        eWakil = findViewById(R.id.editTextwakilInsert);

        sKasur = "false";
        sWifi = "false";
        sMeja = "false";
        sLemari = "false";

        spTipe.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                sTipe = parent.getItemAtPosition(position).toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        spKamarMandi.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                sKamarMandi = parent.getItemAtPosition(position).toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        cbkasur.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked){
                    sKasur = "true";
                }
            }
        });

        cbWifi.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked){
                    sWifi = "true";
                }
            }
        });

        cbmeja.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked){
                    sMeja = "true";
                }
            }
        });

        cbLemari.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked){
                    sLemari = "true";
                }
            }
        });



        simpan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                sNama = eNama.getText().toString();
                sharga = eHarga.getText().toString();
                sNamaJalan = eNamaJalan.getText().toString();
                sKecamatan = eKecamatan.getText().toString();
                sNomor = eNomor.getText().toString();
                sNohp = eNoHp.getText().toString();
                sWakil = eWakil.getText().toString();


                mDatabase = FirebaseDatabase.getInstance().getReference("Kost");

                mDatabase.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        getValue();
                        mDatabase.child(sNama).setValue(kostInsert);
                        mDatabase.child(sNama).child("Alamat").setValue(AlamatInsert);
                        mDatabase.child(sNama).child("Kontak").setValue(kotakInsert);
                        mDatabase.child(sNama).child("Fasilitas").setValue(FasilitasInsert);
                    }
                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });

                Intent intent = new Intent(insert.this,UploadGambar.class);
                intent.putExtra("nama",sNama);
                startActivity(intent);
            }
        });
    }

    public void getValue(){
        kostInsert = new KostInsertSetterGetter(sNama,sTipe,sharga,"-",
                sTipe+"_"+" "+"_"+" "+"_"+" "+"_"+" "+"_"+sKamarMandi,sTipe+"_"+sKasur+"_"+" "+"_"+" "+"_"+" "+"_"+sKamarMandi,
                sTipe+"_"+sKasur+"_"+sWifi+"_"+" "+"_"+" "+"_"+sKamarMandi,sTipe+"_"+sKasur+"_"+sWifi+"_"+sMeja+"_"+" "+"_"+sKamarMandi,
                sTipe+"_"+sKasur+"_"+sWifi+"_"+sMeja+"_"+sLemari+"_"+sKamarMandi,sTipe+"_"+" "+"_"+sWifi+"_"+" "+"_"+" "+"_"+sKamarMandi,
                sTipe+"_"+" "+"_"+sWifi+"_"+sMeja+"_"+" "+"_"+sKamarMandi,sTipe+"_"+" "+"_"+sWifi+"_"+sMeja+"_"+sLemari+"_"+sKamarMandi,
                sTipe+"_"+" "+"_"+sWifi+"_"+" "+"_"+sLemari+"_"+sKamarMandi,sTipe+"_"+" "+"_"+" "+"_"+" "+"_"+sLemari+"_"+sKamarMandi,
                sTipe+"_"+sKasur+"_"+sWifi+"_"+" "+"_"+sLemari+"_"+sKamarMandi,sTipe+"_"+sKasur+"_"+" "+"_"+" "+"_"+sLemari+"_"+sKamarMandi,
                sTipe+"_"+sKasur+"_"+" "+"_"+sMeja+"_"+sLemari+"_"+sKamarMandi, sTipe+"_"+sKasur+"_"+" "+"_"+sMeja+"_"+" "+"_"+sKamarMandi,
                sTipe+"_"+" "+"_"+" "+"_"+sMeja+"_"+" "+"_"+sKamarMandi );

        AlamatInsert = new KostInsertAlamatSetter(sNamaJalan,sKecamatan,sNomor);

        kotakInsert = new KostInsertKontak(sNohp,sWakil);

        FasilitasInsert = new KostInsertFasilitas(sKasur,sWifi,sMeja,sLemari,sKamarMandi);
        /*
        kostInsertSetterGetter.setsNama(sNama);
        kostInsertSetterGetter.setsTipe(sTipe);
        kostInsertSetterGetter.setsHarga(sharga);
        kostInsertSetterGetter.setsLain("-");
        kostInsertSetterGetter.setsKey1(sTipe+"_"+" "+"_"+" "+"_"+" "+"_"+" "+"_"+sKamarMandi);
        kostInsertSetterGetter.setsKey2(sTipe+"_"+sKasur+"_"+" "+"_"+" "+"_"+" "+"_"+sKamarMandi);
        kostInsertSetterGetter.setsKey3(sTipe+"_"+sKasur+"_"+sWifi+"_"+" "+"_"+" "+"_"+sKamarMandi);
        kostInsertSetterGetter.setsKey4(sTipe+"_"+sKasur+"_"+sWifi+"_"+sMeja+"_"+" "+"_"+sKamarMandi);
        kostInsertSetterGetter.setsKey5(sTipe+"_"+sKasur+"_"+sWifi+"_"+sMeja+"_"+sLemari+"_"+sKamarMandi);
        kostInsertSetterGetter.setsKey6(sTipe+"_"+" "+"_"+sWifi+"_"+" "+"_"+" "+"_"+sKamarMandi);
        kostInsertSetterGetter.setsKey7(sTipe+"_"+" "+"_"+sWifi+"_"+sMeja+"_"+" "+"_"+sKamarMandi);
        kostInsertSetterGetter.setsKey8(sTipe+"_"+" "+"_"+sWifi+"_"+sMeja+"_"+sLemari+"_"+sKamarMandi);
        kostInsertSetterGetter.setsKey9(sTipe+"_"+" "+"_"+sWifi+"_"+" "+"_"+sLemari+"_"+sKamarMandi);
        kostInsertSetterGetter.setsKey10(sTipe+"_"+" "+"_"+" "+"_"+" "+"_"+sLemari+"_"+sKamarMandi);
        kostInsertSetterGetter.setsKey11(sTipe+"_"+sKasur+"_"+sWifi+"_"+" "+"_"+sLemari+"_"+sKamarMandi);
        kostInsertSetterGetter.setsKey12(sTipe+"_"+sKasur+"_"+" "+"_"+" "+"_"+sLemari+"_"+sKamarMandi);
        kostInsertSetterGetter.setsKey13(sTipe+"_"+sKasur+"_"+" "+"_"+sMeja+"_"+sLemari+"_"+sKamarMandi);
        kostInsertSetterGetter.setsKey14(sTipe+"_"+sKasur+"_"+" "+"_"+sMeja+"_"+" "+"_"+sKamarMandi);
        kostInsertSetterGetter.setsKey15(sTipe+"_"+" "+"_"+" "+"_"+sMeja+"_"+" "+"_"+sKamarMandi);

         */

    }
}