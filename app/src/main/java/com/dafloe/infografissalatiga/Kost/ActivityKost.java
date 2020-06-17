package com.dafloe.infografissalatiga.Kost;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import androidx.appcompat.widget.SearchView;
import android.widget.TextView;

import com.dafloe.infografissalatiga.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Map;


import static androidx.constraintlayout.widget.Constraints.TAG;

public class ActivityKost extends AppCompatActivity {

    private ArrayList<ProfilKost> profilKost = new ArrayList<>();
    private RecyclerView recyclerView;
    private KostAdapter adapter;

    private RecyclerView.LayoutManager layoutManager;
    String namaKost;
    String sNamaJalan;
    String sNo;
    String sKecamatan;
    String sTipe;
    String sGambar;
    String sNama;
    ProgressDialog dialog;
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_kost);
        dialog = new ProgressDialog(ActivityKost.this);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("Kost");

        TextView tambah = findViewById(R.id.tambah);
        tambah.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ActivityKost.this, Filter_Kost.class);
                startActivity(intent);
            }
        });

        SearchView searchView = findViewById(R.id.searchbarkost);
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                adapter.getFilter().filter(newText);
                return false;
            }
        });


        ActivityKost.GetKost kostAsyncTask = new ActivityKost.GetKost();

        kostAsyncTask.execute();

    }

    public void setRecyclerView() {
        dialog.dismiss();
        profilKost.add(new ProfilKost(sGambar,sNama,"Jl." +sNamaJalan+
                " No."+sNo+", "+sKecamatan));

        recyclerView = findViewById(R.id.recycle_kost);
        recyclerView.setHasFixedSize(false);
        adapter = new KostAdapter(profilKost);
        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(adapter);

        adapter.setOnItemClickListener(new KostAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(int position) {
                String pos = profilKost.get(position).getmTextKostJudul();
                Log.e(TAG, "posssss  : "+ pos );
                Intent intent = new Intent(ActivityKost.this, Detail.class);
                intent.putExtra("posisi",pos);
                startActivity(intent);
            }
        });


    }


    private class GetKost extends AsyncTask<Void, Void, Void> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();

            dialog.setMessage("Please wait...");
            dialog.setCanceledOnTouchOutside(false);
            dialog.show();
        }

        @Override
        protected Void doInBackground(Void... voids) {
            final DatabaseReference database = FirebaseDatabase.getInstance().getReference();

            Intent intent = getIntent();
            String sFilterDetail = intent.getStringExtra("filter");
            Log.e(TAG, "doInBackground: "+ sFilterDetail );
            String sFilter = sFilterDetail;
            String sKey = "Key";
            if (sFilter!=null){
                for (int i = 0; i<=14;i++){
                    Query query = database.child("Kost").orderByChild(sKey+i).equalTo(sFilter);
                    query.addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                            for (DataSnapshot childSnapshot : dataSnapshot.getChildren()) {
                                if (childSnapshot.exists() && childSnapshot.getChildrenCount() > 0) {
                                    namaKost = childSnapshot.getKey();
                                    Map<String, Object> mapKost = (Map<String, Object>) childSnapshot.getValue();
                                    sTipe = mapKost.get("Tipe").toString();
                                    sGambar  = mapKost.get("Cover").toString();
                                    sNama  = mapKost.get("Nama").toString();
                                    Map<String, Object> mapAlamat = (Map<String, Object>) childSnapshot.child("Alamat").getValue();
                                    sNamaJalan = mapAlamat.get("Nama_Jalan").toString();
                                    sNo = mapAlamat.get("No").toString();
                                    sKecamatan = mapAlamat.get("Kecamatan").toString();

                                    setRecyclerView();
                                }
                            }
                        }
                        @Override
                        public void onCancelled(@NonNull DatabaseError databaseError) {

                        }
                    });
                }

            }

            else{
                database.child("Kost").addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        //loop untuk setiap child yang ada di child
                        for (DataSnapshot childSnapshot : dataSnapshot.getChildren()) {
                            if (childSnapshot.exists() && childSnapshot.getChildrenCount() > 0) {
                                namaKost = childSnapshot.getKey();
                                Map<String, Object> mapKost = (Map<String, Object>) childSnapshot.getValue();
                                sTipe = mapKost.get("Tipe").toString();
                                //sGambar  = mapKost.get("Cover").toString();
                                sNama  = mapKost.get("Nama").toString();
                                Map<String, Object> mapGambar = (Map<String, Object>) childSnapshot.child("Gambar").getValue();
                                sGambar = mapGambar.get("Gambar"+1).toString();
                                Map<String, Object> mapAlamat = (Map<String, Object>) childSnapshot.child("Alamat").getValue();
                                sNamaJalan = mapAlamat.get("Nama_Jalan").toString();
                                sNo = mapAlamat.get("No").toString();;
                                sKecamatan = mapAlamat.get("Kecamatan").toString();

                                setRecyclerView();
                            }
                        }
                    }
                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });
            }
            return null;
        }


        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);

        }
    }

/*
     private class getKostItemPos extends AsyncTask<String, Void, String>{

         @Override
         protected void onPreExecute() {
             super.onPreExecute();
         }
         @Override
         protected String doInBackground(String... strings) {
             final DatabaseReference database = FirebaseDatabase.getInstance().getReference();
             String lat = strings[0];
             database.child("Kost").child(lat).addListenerForSingleValueEvent(new ValueEventListener() {

                 @Override
                 public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                 }

                 @Override
                 public void onCancelled(@NonNull DatabaseError databaseError) {

                 }
             });
             return null;
     }
    }*/
}
