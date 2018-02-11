package com.soffandluffy.pembayaranlistrik;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.soffandluffy.pembayaranlistrik.models.Pelanggan;
import com.soffandluffy.pembayaranlistrik.models.PelangganList;
import com.soffandluffy.pembayaranlistrik.models.Tagihan;

import java.util.ArrayList;
import java.util.List;

public class PelangganActivity extends AppCompatActivity {

    private DatabaseReference mDatabase;
    private DatabaseReference pelanggan;
    private EditText etIdPelanggan,etNometer,etNama,etAlamat,etDaya,etTarif;
    private Button btnAdd;
    private ListView lvPelanggan;

    public static final String idPelanggan = "com.soffandluffy.pembayaranlistrik.idpelanggan";
    public static final String nometer = "com.soffandluffy.pembayaranlistrik.nometer";
    public static final String nama = "com.soffandluffy.pembayaranlistrik.nama";
    public static final String alamat = "com.soffandluffy.pembayaranlistrik.alamat";
    public static final String daya = "com.soffandluffy.pembayaranlistrik.daya";
    public static final String tarifperkwh = "com.soffandluffy.pembayaranlistrik.tarifperkwh";

    List<Pelanggan> pelanggans;

    DatabaseReference dbPelanggans;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pelanggan);

        FirebaseDatabase.getInstance().setPersistenceEnabled(true);
        dbPelanggans = FirebaseDatabase.getInstance().getReference("pelanggans");

        etIdPelanggan = (EditText) findViewById(R.id.etIdPelanggan);
        etNometer = (EditText) findViewById(R.id.etNometer);
        etNama = (EditText) findViewById(R.id.etNama);
        etAlamat = (EditText) findViewById(R.id.etAlamat);
        etDaya = (EditText) findViewById(R.id.etDaya);
        etTarif = (EditText) findViewById(R.id.etTarif);

        lvPelanggan = (ListView) findViewById(R.id.listPelanggan);

        btnAdd = (Button) findViewById(R.id.btnAdd);
        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addPelanggan();
            }
        });

        pelanggans = new ArrayList<>();

        lvPelanggan.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Pelanggan pelanggan = pelanggans.get(i);

                Intent intent = new Intent(getApplicationContext(), TagihanActivity.class);

                intent.putExtra(idPelanggan, pelanggan.getIdPelanggan());
                intent.putExtra(nama, pelanggan.getNama());

                startActivity(intent);
            }
        });

//        ((ListView) findViewById(R.id.listPelanggan))
//                .setAdapter(new ArrayAdapter<>(getApplicationContext(), R.layout.pelanggan_list));



    }

    @Override
    protected void onStart() {
        super.onStart();
        dbPelanggans.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                pelanggans.clear();
                for (DataSnapshot snapshotPelanggan : dataSnapshot.getChildren()){
                    Pelanggan pelanggan = snapshotPelanggan.getValue(Pelanggan.class);
                    pelanggans.add(pelanggan);
                }

                PelangganList pelangganAdapter = new PelangganList(PelangganActivity.this, pelanggans);
                lvPelanggan.setAdapter(pelangganAdapter);

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    private void addPelanggan(){
        String idPelanggan = etIdPelanggan.getText().toString();
        String nometer = etNometer.getText().toString();
        String nama = etNama.getText().toString();
        String alamat = etAlamat.getText().toString();
        String daya = etDaya.getText().toString();
        String tarifperkwh = etTarif.getText().toString();

        if (!TextUtils.isEmpty(idPelanggan) || !TextUtils.isEmpty(nometer) || !TextUtils.isEmpty(nama) || !TextUtils.isEmpty(alamat) ||
                !TextUtils.isEmpty(daya) || !TextUtils.isEmpty(tarifperkwh)){
            Pelanggan pelanggan = new Pelanggan(idPelanggan,Integer.valueOf(nometer),nama,alamat,Integer.valueOf(daya),Integer.valueOf(tarifperkwh));
            //Saving
            dbPelanggans.child(idPelanggan).setValue(pelanggan);

            etIdPelanggan.setText("");
            etNometer.setText("");
            etNama.setText("");
            etAlamat.setText("");
            etDaya.setText("");
            etTarif.setText("");

            Toast.makeText(this, "Pelanggan ditambahkan", Toast.LENGTH_LONG).show();
        } else {
            Toast.makeText(this, "Please input all fields", Toast.LENGTH_LONG).show();
        }
    }
}
