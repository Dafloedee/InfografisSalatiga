package com.dafloe.infografissalatiga.Kost;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;

import com.dafloe.infografissalatiga.R;
import com.google.android.material.tabs.TabLayout;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static androidx.constraintlayout.widget.Constraints.TAG;

public class Detail extends AppCompatActivity {

    String sNamaJalan,sNo,sKecamatan,sTipe,sGambar,sNama,sharga,sLain,sWifi,sMeja,sLemari,sKasur,skamarMandi,sNohp,sWakil,pos,s1,s2,s3,s4,s5;
    private DetailKostAdapter adapter;
    private RecyclerView recyclerView;
    private RecyclerView.LayoutManager layoutManager;
    ArrayList<ProfilKostDetail> test =  new ArrayList<ProfilKostDetail>();
    Detail.getKostItemPos kostDetailAsyncTask = new Detail.getKostItemPos();

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.detail_kost);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("");

        Intent intent = getIntent();
        String post = intent.getStringExtra("posisi");
        kostDetailAsyncTask.execute(post);

        recyclerView = findViewById(R.id.viewpager);
        recyclerView.setHasFixedSize(false);
        adapter = new DetailKostAdapter(test);
        layoutManager = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
    }

    public void ViewPagerImage(){

        TextView tNamaKost   = findViewById(R.id.nama_kost_detail);
        TextView tGender     = findViewById(R.id.gender_kost_detail);
        final TextView tAlamat     = findViewById(R.id.idalamat);
        TextView tNoHp       = findViewById(R.id.idnohp);
        TextView tWakil      = findViewById(R.id.idnamapemilik);
        TextView tHarga      = findViewById(R.id.idharga);
        TextView tKasur      = findViewById(R.id.idkasur);
        TextView tWifi       = findViewById(R.id.idwifi);
        TextView tMeja       = findViewById(R.id.idmeja);
        TextView tLemari     = findViewById(R.id.idlemari);
        TextView tKamarMandi = findViewById(R.id.idmandi);
        TextView tlain       = findViewById(R.id.idlaintext);

        tNamaKost.setText(sNama);
        tGender.setText(sTipe);
        tAlamat.setText("Jl." +sNamaJalan+ " No."+sNo+", "+sKecamatan);

        tAlamat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Create a Uri from an intent string. Use the result to create an Intent.
                Uri gmmIntentUri = Uri.parse("geo:0,0?q="+"Jl." +sNamaJalan+ " No."+sNo+", "+sKecamatan);

                // Create an Intent from gmmIntentUri. Set the action to ACTION_VIEW
                Intent mapIntent = new Intent(Intent.ACTION_VIEW, gmmIntentUri);
                // Make the Intent explicit by setting the Google Maps package
                mapIntent.setPackage("com.google.android.apps.maps");

                // Attempt to start an activity that can handle the Intent
                startActivity(mapIntent);
            }
        });


        tNoHp.setText(sNohp);
        tNoHp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ClipboardManager clipboard = (ClipboardManager) getSystemService(Context.CLIPBOARD_SERVICE);
                ClipData clip = ClipData.newPlainText("Phone", 0+sNohp);
                clipboard.setPrimaryClip(clip);
                Toast.makeText(getApplicationContext(),"Nomor Hp sudah di copy ke clipboard",Toast.LENGTH_LONG).show();
            }
        });

        tWakil.setText(sWakil);
        tHarga.setText(sharga);
        tlain.setText(sLain);

        if (sKasur=="false"){
            tKasur.setText("Tidak Disediakan");
            tKasur.setTextColor(Color.parseColor("#FB1B1B"));
        }
        else if (sKasur=="true"){
            tKasur.setText("Disediakan");
            tKasur.setTextColor(Color.parseColor("#4CAF50"));
        }

        if(sWifi=="false"){
            tWifi.setText("Tidak Disediakan");
            tWifi.setTextColor(Color.parseColor("#FB1B1B"));
        }
        else if (sWifi=="true"){
            tWifi.setText("Disediakan");
            tWifi.setTextColor(Color.parseColor("#4CAF50"));
        }

        if(sMeja=="false"){
            tMeja.setText("Tidak Disediakan");
            tMeja.setTextColor(Color.parseColor("#FB1B1B"));
        }
        else if (sMeja=="true"){
            tMeja.setText("Disediakan");
            tMeja.setTextColor(Color.parseColor("#4CAF50"));
        }

        if(sLemari=="false"){
            tLemari.setText("Tidak Disediakan");
            tLemari.setTextColor(Color.parseColor("#FB1B1B"));
        }
        else if (sLemari=="true"){
            tLemari.setText("Disediakan");
            tLemari.setTextColor(Color.parseColor("#4CAF50"));
        }
        tKamarMandi.setText(skamarMandi);
        
    }

    private class getKostItemPos extends AsyncTask<String, Void, String> {
        @Override
        protected String doInBackground(String... strings) {
            pos = strings[0];
            final DatabaseReference database = FirebaseDatabase.getInstance().getReference("Kost").child(pos);
            database.getRef();

            database.addListenerForSingleValueEvent(new ValueEventListener() {

                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                            Map<String, Object> mapDetailKost = (Map<String, Object>) dataSnapshot.getValue();
                            sTipe   = mapDetailKost.get("Tipe").toString();
                            //sGambar = mapDetailKost.get("Cover").toString();
                            sNama   = mapDetailKost.get("Nama").toString();
                            sharga  = mapDetailKost.get("Harga").toString();
                            sLain   = mapDetailKost.get("Lain").toString();


                            Map<String, Object> mapDetailAlamat = (Map<String, Object>) dataSnapshot.child("Alamat").getValue();
                            sNamaJalan = mapDetailAlamat.get("Nama_Jalan").toString();
                            sNo        = mapDetailAlamat.get("No").toString();;
                            sKecamatan = mapDetailAlamat.get("Kecamatan").toString();

                            Map<String, Object> mapDetailFasilitas = (Map<String, Object>) dataSnapshot.child("Fasilitas").getValue();
                            sKasur       = mapDetailFasilitas.get("Kasur").toString();
                            sWifi        = mapDetailFasilitas.get("Wifi").toString();
                            sMeja        = mapDetailFasilitas.get("Meja").toString();
                            sLemari      = mapDetailFasilitas.get("Lemari").toString();
                            skamarMandi  = mapDetailFasilitas.get("Kamar_Mandi").toString();

                            Map<String, Object> mapDetailKontak = (Map<String, Object>) dataSnapshot.child("Kontak").getValue();
                            sNohp  = mapDetailKontak.get("NoHp").toString();
                            sWakil = mapDetailKontak.get("Wakil").toString();

                            Map<String, Object> mapDetailGambar = (Map<String, Object>) dataSnapshot.child("Gambar").getValue();
                                long a =  dataSnapshot.child("Gambar").getChildrenCount();

                                for (int i = 1;i<=a;i++){
                                    String as= "Gambar";
                                    s1 = mapDetailGambar.get(as+i).toString();

                                    test.add(new ProfilKostDetail(s1));

                                    recyclerView.setLayoutManager(layoutManager);
                                    recyclerView.setAdapter(adapter);
                                }
                    ViewPagerImage();
                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {

                }
            });

            return null;
        }
    }
}



