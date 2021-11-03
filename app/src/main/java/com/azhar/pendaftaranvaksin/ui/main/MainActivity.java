package com.azhar.pendaftaranvaksin.ui.main;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.location.Address;
import android.location.Geocoder;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.common.Priority;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.JSONObjectRequestListener;
import com.azhar.pendaftaranvaksin.R;
import com.azhar.pendaftaranvaksin.networking.ApiClient;
import com.azhar.pendaftaranvaksin.ui.profile.ProfileActivity;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import im.delight.android.location.SimpleLocation;

public class MainActivity extends AppCompatActivity {

    int REQ_PERMISSION = 100;
    double strCurrentLatitude;
    double strCurrentLongitude;
    String strCurrentLatLong, strImage;
    SimpleLocation simpleLocation;
    ProgressBar pbLoading;
    MainAdapter mainAdapter;
    RecyclerView rvListHospital;
    TextView tvCurrentLocation;
    ImageView imageProfile;
    List<ModelMain> modelMainList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        imageProfile = findViewById(R.id.imageProfile);
        tvCurrentLocation = findViewById(R.id.tvCurrentLocation);
        pbLoading = findViewById(R.id.pbLoading);
        rvListHospital = findViewById(R.id.rvListHospital);

        setPermission();
        setStatusBar();
        setLocation();
        setInitLayout();

        //get data rumah sakit
        getRumahSakit();

        //get nama daerah
        getCurrentLocation();
    }

    private void setLocation() {
        simpleLocation = new SimpleLocation(this);

        if (!simpleLocation.hasLocationEnabled()) {
            SimpleLocation.openSettings(this);
        }

        //get location
        strCurrentLatitude = simpleLocation.getLatitude();
        strCurrentLongitude = simpleLocation.getLongitude();

        //set location lat long
        strCurrentLatLong = strCurrentLatitude + "," + strCurrentLongitude;
    }

    private void setInitLayout() {
        mainAdapter = new MainAdapter(MainActivity.this, modelMainList);
        rvListHospital.setHasFixedSize(true);
        rvListHospital.setLayoutManager(new LinearLayoutManager(this));
        rvListHospital.setAdapter(mainAdapter);

        imageProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, ProfileActivity.class);
                startActivity(intent);
            }
        });
    }

    private void getCurrentLocation() {
        Geocoder geocoder = new Geocoder(this, Locale.getDefault());
        try {
            List<Address> addressList = geocoder.getFromLocation(strCurrentLatitude, strCurrentLongitude, 1);
            if (addressList != null && addressList.size() > 0) {
                String strCurrentLocation = addressList.get(0).getLocality();
                tvCurrentLocation.setText(strCurrentLocation);
                tvCurrentLocation.setSelected(true);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void setPermission() {
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED
                && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, REQ_PERMISSION);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        for (int grantResult : grantResults) {
            if (grantResult == PackageManager.PERMISSION_GRANTED) {
                Intent intent = getIntent();
                finish();
                startActivity(intent);
            }
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQ_PERMISSION && resultCode == RESULT_OK) {

            //load data rumah sakit
            getRumahSakit();
        }
    }

    private void getRumahSakit() {
        pbLoading.setVisibility(View.VISIBLE);
        AndroidNetworking.get(ApiClient.BASE_URL + strCurrentLatLong + ApiClient.TYPE + ApiClient.API_KEY)
                .setPriority(Priority.MEDIUM)
                .build()
                .getAsJSONObject(new JSONObjectRequestListener() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            pbLoading.setVisibility(View.GONE);
                            JSONArray jsonArrayResult = response.getJSONArray("results");
                            for (int i = 0; i < jsonArrayResult.length(); i++) {
                                JSONObject jsonObjectResult = jsonArrayResult.getJSONObject(i);

                                ModelMain modelMain = new ModelMain();
                                modelMain.setStrName(jsonObjectResult.getString("name"));
                                modelMain.setStrVicinity(jsonObjectResult.getString("vicinity"));

                                //get lat long
                                JSONObject jsonObjectGeo = jsonObjectResult.getJSONObject("geometry");
                                JSONObject jsonObjectLoc = jsonObjectGeo.getJSONObject("location");

                                modelMain.setLatLoc(jsonObjectLoc.getDouble("lat"));
                                modelMain.setLongLoc(jsonObjectLoc.getDouble("lng"));

                                //handle photo result
                                try {
                                    JSONArray jsonArrayImage = jsonObjectResult.getJSONArray("photos");
                                    for (int x = 0; x < jsonArrayImage.length(); x++) {
                                        JSONObject jsonObjectData = jsonArrayImage.getJSONObject(x);
                                        strImage = jsonObjectData.getString("photo_reference");
                                        modelMain.setStrPhoto(strImage);
                                    }
                                } catch (Exception e) {
                                    e.printStackTrace();
                                    modelMain.setStrPhoto(null);
                                }
                                modelMainList.add(modelMain);
                            }
                            mainAdapter.notifyDataSetChanged();
                        } catch (JSONException e) {
                            e.printStackTrace();
                            Toast.makeText(MainActivity.this, "Gagal menampilkan data!", Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onError(ANError anError) {
                        pbLoading.setVisibility(View.GONE);
                        Toast.makeText(MainActivity.this, "Tidak ada jaringan internet!", Toast.LENGTH_SHORT).show();
                    }
                });
    }

    private void setStatusBar() {
        if (Build.VERSION.SDK_INT < 21) {
            setWindowFlag(this, WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS, true);
        }
        if (Build.VERSION.SDK_INT >= 19) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_STABLE | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN);
            }
        }
        if (Build.VERSION.SDK_INT >= 21) {
            setWindowFlag(this, WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS, false);
            getWindow().setStatusBarColor(Color.TRANSPARENT);
        }
    }

    public static void setWindowFlag(Activity activity, final int bits, boolean on) {
        Window window = activity.getWindow();
        WindowManager.LayoutParams layoutParams = window.getAttributes();
        if (on) {
            layoutParams.flags |= bits;
        } else {
            layoutParams.flags &= ~bits;
        }
        window.setAttributes(layoutParams);
    }

}