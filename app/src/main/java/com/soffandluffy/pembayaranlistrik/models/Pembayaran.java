package com.soffandluffy.pembayaranlistrik.models;

import java.util.Date;

/**
 * Created by SoffanDLuffy on 2/11/2018.
 */

public class Pembayaran {

    private String idPembayaran;
    private String idPelanggan;
    private long tanggal;
    private String bulanbayar;
    private int biayaadmin;

    public Pembayaran(){

    }

    public Pembayaran(String idPembayaran, String idPelanggan, long tanggal, String bulanbayar, int biayaadmin) {
        this.idPembayaran = idPembayaran;
        this.idPelanggan = idPelanggan;
        this.tanggal = tanggal;
        this.bulanbayar = bulanbayar;
        this.biayaadmin = biayaadmin;
    }

    public String getIdPembayaran() {
        return idPembayaran;
    }

    public void setIdPembayaran(String idPembayaran) {
        this.idPembayaran = idPembayaran;
    }

    public String getIdPelanggan() {
        return idPelanggan;
    }

    public void setIdPelanggan(String idPelanggan) {
        this.idPelanggan = idPelanggan;
    }

    public long getTanggal() {
        return tanggal;
    }

    public void setTanggal(long tanggal) {
        this.tanggal = tanggal;
    }

    public String getBulanbayar() {
        return bulanbayar;
    }

    public void setBulanbayar(String bulanbayar) {
        this.bulanbayar = bulanbayar;
    }

    public int getBiayaadmin() {
        return biayaadmin;
    }

    public void setBiayaadmin(int biayaadmin) {
        this.biayaadmin = biayaadmin;
    }
}
