package com.soffandluffy.pembayaranlistrik.models;

import android.app.Activity;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
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

public class TagihanList extends ArrayAdapter<Tagihan> {

    private Activity context;
    List<Tagihan> tagihans;

    public TagihanList(Activity context, List<Tagihan> tagihans) {
        super(context, R.layout.tagihan_list ,tagihans);
        this.context = context;
        this.tagihans = tagihans;
    }

    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        LayoutInflater inflater = context.getLayoutInflater();
        View lvItem = inflater.inflate(R.layout.tagihan_list, null, true);

        TextView tvBulan = (TextView) lvItem.findViewById(R.id.tvBulan);
        TextView tvTahun = (TextView) lvItem.findViewById(R.id.tvTahun);
        TextView tvJumlahMeter = (TextView) lvItem.findViewById(R.id.tvJumlahmeter);
        TextView tvStatus = (TextView) lvItem.findViewById(R.id.tvStatus);

        Tagihan tagihan = tagihans.get(position);
        tvBulan.setText(tagihan.getBulan());
        tvTahun.setText(tagihan.getTahun());
        tvJumlahMeter.setText(String.valueOf(tagihan.getJumlahMeter()));
        tvStatus.setText(tagihan.getStatus());

        return lvItem;
    }
}
