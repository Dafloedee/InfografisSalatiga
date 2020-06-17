package com.dafloe.infografissalatiga.Kost;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.location.Location;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.Filter;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.dafloe.infografissalatiga.R;

import org.json.JSONObject;

import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.List;

import static androidx.constraintlayout.widget.Constraints.TAG;

public class Filter_Kost extends AppCompatActivity {
    private String sKasur = " ";
    private String sWifi = " ";
    private String sLemari = " ";
    private String sMeja = " ";
    private String sMandi, sRange,sGender;
    private Location l;
    private double lat;
    private double lon;
    private Gps gps;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_filter);

        DisplayMetrics displayMetrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);

        int width = displayMetrics.widthPixels;
        int height = displayMetrics.heightPixels;

        getWindow().setLayout((int)(width*.9),(int)(height*.8));

        Spinner spGender = findViewById(R.id.spinner_gender);
        //EditText edHargaMin = findViewById(R.id.edit_range_min);
        //EditText edHargaMax = findViewById(R.id.edit_range_max);
        CheckBox cbKasur = findViewById(R.id.check_kasur);
        CheckBox cbWifi = findViewById(R.id.check_Wifi);
        CheckBox cbMeja = findViewById(R.id.check_meja);
        CheckBox cbLemari = findViewById(R.id.check_lemari);
        Spinner spMandi = findViewById(R.id.spinner_mandi);
        Spinner spRange = findViewById(R.id.spinner_harga);
        TextView confirm = findViewById(R.id.tambahfilter);
        TextView back = findViewById(R.id.batalfilter);

        spGender.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                sGender = (String) parent.getItemAtPosition(position);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        spMandi.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                sMandi = parent.getItemAtPosition(position).toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });


        sRange = spRange.getSelectedItem().toString();

        cbKasur.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked){
                   sKasur = "true";
                }

                else if (!isChecked) {
                   sKasur = " ";
                }
            }
        });

        cbWifi.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked){
                    sWifi = "true";
                }

                else if (!isChecked) {
                    sWifi = " ";
                }
            }
        });

        cbLemari.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked){
                    sLemari = "true";
                }
                else if (!isChecked) {
                    sLemari = " ";
                }
            }
        });

        cbMeja.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked){
                    sMeja = "true";
                }

                else if (!isChecked) {
                    sMeja = " ";
                }
            }
        });

        confirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Filter_Kost.this,ActivityKost.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                intent.putExtra("filter",sGender+"_"+sKasur+"_"+sWifi+"_"+sLemari+"_"+sMeja+"_"+sMandi);
                Log.e(TAG, "onClick: " + sGender+"_"+sKasur+"_"+sWifi+"_"+sLemari+"_"+sMeja+"_"+sMandi );
                startActivity(intent);
                finish();
            }
        });

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Filter_Kost.this,ActivityKost.class);
                startActivity(intent);

            }
        });
    }


    public void startAsyncTask() {
        //https://www.youtube.com/watch?v=Xb0DTCR1H0s&t=3s
        gps = new Gps(this.getApplicationContext());
        l = gps.getLocation();
        lat = l.getLatitude();
        lon = l.getLongitude();
        Log.e(TAG, "LOKASI: "+ l );
        Filter_Kost.GetAddress kostAsyncTask = new Filter_Kost.GetAddress();

        //***************cek internet ada apa ngak*****************
        ConnectivityManager connectivityManager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        if (connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_MOBILE).getState() == NetworkInfo.State.CONNECTED
                || connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_WIFI).getState() == NetworkInfo.State.CONNECTED) {
            //we are connected to a network
            kostAsyncTask.execute(String.format("%.4f,%.4f",lat,lon));
        } else {
            Toast.makeText(getApplicationContext(), "Tidak ada koneksi internet", Toast.LENGTH_LONG).show();
        }
        //*********************************************************
    }

    private class GetAddress extends AsyncTask<String, Void, String> {

        ProgressDialog dialog = new ProgressDialog(Filter_Kost.this);

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            dialog.setMessage("Please wait...");
            dialog.setCanceledOnTouchOutside(false);
            dialog.show();
        }

        @Override
        protected String doInBackground(String... strings) {
            try {
                double lat = Double.parseDouble(strings[0].split(",")[0]);
                Log.e(TAG, "dobelLAT: "+ lat );
                double lng = Double.parseDouble(strings[0].split(",")[1]);
                Log.e(TAG, "dobelLBG: "+ lng );
                String response;
                HttpHandler http = new HttpHandler();
                String url = String.format("https://us1.locationiq.com/v1/reverse.php?key=70d40d66779458&lat="+lat +"&lon="+lng+"&format=json");
                response = http.GetHTTPData(url);
                Log.e(TAG, "RESPONSE: "+ response );
                return response;
            } catch (Exception ex) {

            }
            return null;
        }

        @Override
        protected void onPostExecute(String s) {
            try {
                List<String> alamat = new ArrayList<>();
                JSONObject jsonObject =  new JSONObject( URLDecoder.decode( s, "UTF-8" ) );
                String address =  jsonObject.getString("display_name");
                alamat.add(jsonObject.toString());
                //titik.setText(address);

            } catch (Exception e) {
                e.printStackTrace();
            }
            if (dialog.isShowing())
                dialog.dismiss();
        }

    }
}
