package com.soffandluffy.pembayaranlistrik.models;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.soffandluffy.pembayaranlistrik.R;

import java.util.List;

/**
 * Created by SoffanDLuffy on 2/11/2018.
 */

public class PelangganList extends ArrayAdapter<Pelanggan> {
    private Activity context;
    List<Pelanggan> pelanggans;

    public PelangganList(Activity context, List<Pelanggan> pelanggans){
        super(context, R.layout.pelanggan_list, pelanggans);
        this.context = context;
        this.pelanggans = pelanggans;
    }

    @Override
    public View getView(int position,View convertView,ViewGroup parent) {

        LayoutInflater inflater = context.getLayoutInflater();

        View lvItem = inflater.inflate(R.layout.pelanggan_list, null, true);

        TextView tvIdPelanggan = (TextView) lvItem.findViewById(R.id.tvIdPelanggan);
        TextView tvNometer = (TextView) lvItem.findViewById(R.id.tvNometer);
        TextView tvNama = (TextView) lvItem.findViewById(R.id.tvNama);
        TextView tvAlamat = (TextView) lvItem.findViewById(R.id.tvAlamat);
        TextView tvDaya = (TextView) lvItem.findViewById(R.id.tvDaya);
        TextView tvTarif = (TextView) lvItem.findViewById(R.id.tvTarif);

        Pelanggan pelanggan = pelanggans.get(position);
        tvIdPelanggan.setText(pelanggan.getIdPelanggan());
        tvNometer.setText(String.valueOf(pelanggan.getNometer()));
        tvNama.setText(pelanggan.getNama());
        tvAlamat.setText(pelanggan.getAlamat());
        tvDaya.setText(String.valueOf(pelanggan.getDaya()));
        tvTarif.setText(String.valueOf(pelanggan.getTarifperkwh()));

        return lvItem;
    }
}
