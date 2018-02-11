package com.soffandluffy.pembayaranlistrik;

import android.content.Intent;
import android.nfc.Tag;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.soffandluffy.pembayaranlistrik.models.Pelanggan;
import com.soffandluffy.pembayaranlistrik.models.PelangganList;
import com.soffandluffy.pembayaranlistrik.models.Tagihan;
import com.soffandluffy.pembayaranlistrik.models.TagihanList;
import com.whiteelephant.monthpicker.MonthPickerDialog;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class TagihanActivity extends AppCompatActivity {

    private Button btnAdd;
    private EditText etBulan,etTahun,etJumlahmeter;
    private Spinner spinnerStatus;
    private TextView tvIdPelanggan;
    private ListView lvTagihan;

    DatabaseReference dbTagihans;

    List<Tagihan> tagihans;
    int choosenMonth = 2;
    int choosenYear = 2018;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tagihan);

        Intent intent = getIntent();

        dbTagihans = FirebaseDatabase.getInstance().getReference("tagihans").child(intent.getStringExtra(PelangganActivity.idPelanggan));

        etBulan = (EditText) findViewById(R.id.etBulan);
        etTahun = (EditText) findViewById(R.id.etTahun);
        etJumlahmeter = (EditText) findViewById(R.id.etJumlahmeter);
        spinnerStatus = (Spinner) findViewById(R.id.spinStatus);
        tvIdPelanggan = (TextView) findViewById(R.id.tvIdPelanggan);
        btnAdd = (Button) findViewById(R.id.btnAdd);
        lvTagihan = (ListView) findViewById(R.id.listTagihan);

        tagihans = new ArrayList<>();

        tvIdPelanggan.setText(intent.getStringExtra(PelangganActivity.idPelanggan));

        final Calendar c = Calendar.getInstance();
        final MonthPickerDialog.Builder builder = new MonthPickerDialog.Builder(TagihanActivity.this, new MonthPickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(int selectedMonth, int selectedYear) {

            }
        },c.get(Calendar.YEAR),c.get(Calendar.MONTH));


        etBulan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                builder.showMonthOnly()
                        .setOnMonthChangedListener(new MonthPickerDialog.OnMonthChangedListener() {
                            @Override
                            public void onMonthChanged(int selectedMonth) {
                                choosenMonth = selectedMonth + 1;
                                etBulan.setText(Integer.toString(choosenMonth));
                            }
                        })
                        .build()
                        .show();
            }
        });

        etTahun.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                builder.showYearOnly()
                        .setOnYearChangedListener(new MonthPickerDialog.OnYearChangedListener() {
                            @Override
                            public void onYearChanged(int year) {
                                choosenYear = year;
                                etTahun.setText(Integer.toString(choosenYear));
                            }
                        })
                        .build()
                        .show();
            }
        });

        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                saveTagihan();
            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();

        dbTagihans.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                tagihans.clear();
                for (DataSnapshot snapshotTagihan : dataSnapshot.getChildren()){
                    Tagihan tagihan = snapshotTagihan.getValue(Tagihan.class);
                    tagihans.add(tagihan);
                }

                TagihanList tagihanAdapter = new TagihanList(TagihanActivity.this, tagihans);
                lvTagihan.setAdapter(tagihanAdapter);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    private void saveTagihan(){
        String idPelanggan = tvIdPelanggan.getText().toString();
        String bulan = etBulan.getText().toString().trim();
        String tahun = etTahun.getText().toString().trim();
        String jumlahMeter = etJumlahmeter.getText().toString();
        String status = spinnerStatus.getSelectedItem().toString();

        if (!TextUtils.isEmpty(idPelanggan) || !TextUtils.isEmpty(bulan) || !TextUtils.isEmpty(tahun) || !TextUtils.isEmpty(jumlahMeter) || !TextUtils.isEmpty(status)){
            Tagihan tagihan = new Tagihan(idPelanggan,bulan,tahun,Integer.valueOf(jumlahMeter),status);
            dbTagihans.child(idPelanggan).setValue(tagihan);
            Toast.makeText(this, "Tagihan Saved", Toast.LENGTH_LONG).show();
            tvIdPelanggan.setText("");
            etBulan.setText("");
            etTahun.setText("");
            etJumlahmeter.setText("");
        } else {
            Toast.makeText(this, "Please input all fields", Toast.LENGTH_LONG).show();
        }
    }
}
