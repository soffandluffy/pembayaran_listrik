package com.soffandluffy.pembayaranlistrik;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.soffandluffy.pembayaranlistrik.models.Pelanggan;
import com.whiteelephant.monthpicker.MonthPickerDialog;

import java.util.Calendar;
import java.util.Date;

public class MainActivity extends AppCompatActivity {

    private FirebaseDatabase database;
    private DatabaseReference mDatabase;

    private Button btnPelanggan,btnBulan;
    TextView tvBulan;
    int choosenMonth = 2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        database = FirebaseDatabase.getInstance();

        btnPelanggan = (Button) findViewById(R.id.btnPelanggan);
        btnPelanggan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainActivity.this, PelangganActivity.class));
                finish();
            }
        });

        final Calendar c = Calendar.getInstance();
        final MonthPickerDialog.Builder builder = new MonthPickerDialog.Builder(MainActivity.this, new MonthPickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(int selectedMonth, int selectedYear) {

            }
        },c.get(Calendar.YEAR),c.get(Calendar.MONTH));

        tvBulan =(TextView) findViewById(R.id.tvBulan);

        btnBulan = (Button) findViewById(R.id.btnBulan);
        btnBulan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                builder.showMonthOnly()
                        .setOnMonthChangedListener(new MonthPickerDialog.OnMonthChangedListener() {
                            @Override
                            public void onMonthChanged(int selectedMonth) {
                                choosenMonth = selectedMonth + 1;
                                tvBulan.setText(Integer.toString(choosenMonth));
                                Log.d("MainActivity", "Selected month : " + choosenMonth);
                            }
                        })
                        .build()
                        .show();
            }
        });

    }
}
