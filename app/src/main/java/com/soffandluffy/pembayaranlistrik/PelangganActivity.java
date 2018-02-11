package com.soffandluffy.pembayaranlistrik;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.v4.app.NavUtils;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.MenuItem;
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


        dbPelanggans = FirebaseDatabase.getInstance().getReference("pelanggans");

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

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

        lvPelanggan.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> adapterView, View view, int i, long l) {

                Pelanggan pelanggan = pelanggans.get(i);
                showUpdateDeleteDialog(pelanggan.getIdPelanggan(),Integer.valueOf(pelanggan.getNometer()),pelanggan.getNama(),pelanggan.getAlamat(),Integer.valueOf(pelanggan.getDaya()),Integer.valueOf(pelanggan.getTarifperkwh()));
                return true;
            }
        });

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

    private boolean updatePelanggan(String idPelanggan, int nometer, String nama, String alamat, int daya, int tarifperkwh){

        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("pelanggans").child(idPelanggan);

        Pelanggan pelanggan = new Pelanggan(idPelanggan, nometer, nama, alamat, daya, tarifperkwh);
        databaseReference.setValue(pelanggan);
        Toast.makeText(this, "Pelanggan Updated", Toast.LENGTH_LONG).show();

        return true;
    }

    private boolean deletePelanggan(String idPelanggan){

        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("pelanggans").child(idPelanggan);

        databaseReference.removeValue();
        DatabaseReference drTagihan = FirebaseDatabase.getInstance().getReference("tagihans").child(idPelanggan);
        drTagihan.removeValue();
        Toast.makeText(this, "Pelanggan Deleted", Toast.LENGTH_LONG).show();

        return true;
    }

    private void showUpdateDeleteDialog(final String idPelanggan, int nometer, String nama, final String alamat, int daya, int tarifperkwh){

        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(this);
        LayoutInflater inflater = getLayoutInflater();
        final View dialogView = inflater.inflate(R.layout.update_pelanggan_dialog, null);
        dialogBuilder.setView(dialogView);

        final EditText etNometer = (EditText) dialogView.findViewById(R.id.etNometer);
        final EditText etNama = (EditText) dialogView.findViewById(R.id.etNama);
        final EditText etAlamat = (EditText) dialogView.findViewById(R.id.etAlamat);
        final EditText etDaya = (EditText) dialogView.findViewById(R.id.etDaya);
        final EditText etTarifperkwh = (EditText) dialogView.findViewById(R.id.etTarif);
        final Button btnUpdate = (Button) dialogView.findViewById(R.id.btnUpdate);
        final Button btnDelete = (Button) dialogView.findViewById(R.id.btnDelete);
        etNometer.setText(String.valueOf(nometer));
        etNama.setText(nama);
        etAlamat.setText(alamat);
        etDaya.setText(String.valueOf(daya));
        etTarifperkwh.setText(String.valueOf(tarifperkwh));

        dialogBuilder.setTitle(idPelanggan);
        final AlertDialog b = dialogBuilder.create();
        b.show();

        btnUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String nometer = etNometer.getText().toString();
                String nama = etNama.getText().toString();
                String alamat = etAlamat.getText().toString();
                String daya = etDaya.getText().toString();
                String tarifperkwh = etTarifperkwh.getText().toString();
                if (!TextUtils.isEmpty(idPelanggan) || !TextUtils.isEmpty(nometer) || !TextUtils.isEmpty(nama) || !TextUtils.isEmpty(alamat) ||
                        !TextUtils.isEmpty(daya) || !TextUtils.isEmpty(tarifperkwh)){

                    updatePelanggan(idPelanggan,Integer.valueOf(nometer),nama,alamat,Integer.valueOf(daya),Integer.valueOf(tarifperkwh));
                    b.dismiss();

                } else {
                    Toast.makeText(PelangganActivity.this, "Please fill all fields", Toast.LENGTH_LONG).show();
                }
            }
        });

        btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final AlertDialog.Builder alert = new AlertDialog.Builder(PelangganActivity.this);
                alert.setTitle("Delete");
                alert.setMessage("Are you sure want to delete this?");
                alert.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        deletePelanggan(idPelanggan);
                        dialogInterface.dismiss();
                        b.dismiss();
                    }
                });
                alert.setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.dismiss();
                    }
                });
                alert.show();
            }
        });
    }
}
