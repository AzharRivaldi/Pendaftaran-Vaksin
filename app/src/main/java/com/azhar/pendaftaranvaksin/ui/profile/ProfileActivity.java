package com.azhar.pendaftaranvaksin.ui.profile;

import android.app.Activity;
import android.content.DialogInterface;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.azhar.pendaftaranvaksin.R;
import com.azhar.pendaftaranvaksin.ui.inputdata.DataAdapter;
import com.azhar.pendaftaranvaksin.ui.inputdata.ModelInput;
import com.azhar.pendaftaranvaksin.viewmodel.InputViewModel;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.List;

public class ProfileActivity extends AppCompatActivity {

    DataAdapter dataAdapter;
    InputViewModel inputViewModel;
    String strNama, strNik, strLokasiVaksin;
    Toolbar toolbar;
    TextView tvNama, tvNik, tvNotFound, tvLokasiVaksin;
    LinearLayout linearData;
    RecyclerView rvListData;
    FloatingActionButton fabDelete;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        toolbar = findViewById(R.id.toolbar);
        tvNama = findViewById(R.id.tvNama);
        tvNik = findViewById(R.id.tvNik);
        tvNotFound = findViewById(R.id.tvNotFound);
        tvLokasiVaksin = findViewById(R.id.tvLokasiVaksin);
        linearData = findViewById(R.id.linearData);
        rvListData = findViewById(R.id.rvListData);
        fabDelete = findViewById(R.id.fabDelete);

        setStatusBar();
        setToolbar();
        setInitLayout();
        setViewModel();
    }

    private void setToolbar() {
        setSupportActionBar(toolbar);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowTitleEnabled(false);
        }
    }

    private void setInitLayout() {
        tvNotFound.setVisibility(View.GONE);
        fabDelete.setVisibility(View.GONE);

        //set adapter recyclerview
        dataAdapter = new DataAdapter(ProfileActivity.this);
        rvListData.setHasFixedSize(true);
        rvListData.setLayoutManager(new LinearLayoutManager(this));
        rvListData.setAdapter(dataAdapter);
    }

    private void setViewModel() {
        inputViewModel = new ViewModelProvider(this, ViewModelProvider.AndroidViewModelFactory.getInstance(this.getApplication())).get(InputViewModel.class);
        inputViewModel.getAllData().observe(this, modelInput -> {
            if (modelInput.size() != 0) {

                //hanya get data pertama, silahkan dikembangkan lagi
                strNama = modelInput.get(0).getNama();
                strNik = modelInput.get(0).getNik();
                strLokasiVaksin = modelInput.get(0).getRumahsakit();
                tvNama.setText(strNama);
                tvNik.setText(strNik);
                tvLokasiVaksin.setText(strLokasiVaksin);

                dataAdapter.setDataAdapter((ArrayList<ModelInput>) modelInput);

                fabDelete.setVisibility(View.VISIBLE);
                fabDelete.setOnClickListener(view -> {
                    AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(ProfileActivity.this);
                    alertDialogBuilder.setMessage("Apa kamu yakin ingin menghapus data ini?");
                    alertDialogBuilder.setPositiveButton("Ya", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            inputViewModel.deleteAllData();
                            Toast.makeText(ProfileActivity.this, "Semua data terhapus", Toast.LENGTH_SHORT).show();
                            fabDelete.setVisibility(View.GONE);
                        }
                    });

                    alertDialogBuilder.setNegativeButton("Batal", (dialogInterface, i) -> dialogInterface.cancel());

                    AlertDialog alertDialog = alertDialogBuilder.create();
                    alertDialog.show();
                });
            } else {
                tvNama.setText("");
                tvNik.setText("");
                tvLokasiVaksin.setText("");
                tvNotFound.setVisibility(View.VISIBLE);
                linearData.setVisibility(View.GONE);
            }
        });
    }

    private void setStatusBar() {
        if (Build.VERSION.SDK_INT < 21) {
            setWindowFlag(this, WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS, true);
        }
        if (Build.VERSION.SDK_INT >= 19) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_STABLE | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN | View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
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

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onResume() {
        super.onResume();
        setViewModel();
    }

}