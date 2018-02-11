package com.soffandluffy.pembayaranlistrik.models;

/**
 * Created by SoffanDLuffy on 2/11/2018.
 */

public class Tagihan {

    private String bulan;
    private String tahun;
    private int jumlahMeter;
    private String status;

    public Tagihan(){

    }

    public Tagihan(String bulan, String tahun, int jumlahMeter, String status) {
        this.bulan = bulan;
        this.tahun = tahun;
        this.jumlahMeter = jumlahMeter;
        this.status = status;
    }

    public String getBulan() {
        return bulan;
    }

    public void setBulan(String bulan) {
        this.bulan = bulan;
    }

    public String getTahun() {
        return tahun;
    }

    public void setTahun(String tahun) {
        this.tahun = tahun;
    }

    public int getJumlahMeter() {
        return jumlahMeter;
    }

    public void setJumlahMeter(int jumlahMeter) {
        this.jumlahMeter = jumlahMeter;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
