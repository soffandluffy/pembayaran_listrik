package com.soffandluffy.pembayaranlistrik.models;

/**
 * Created by SoffanDLuffy on 2/11/2018.
 */

public class Pelanggan {

    private String idPelanggan;
    private int nometer;
    private String nama;
    private String alamat;
    private int daya;
    private int tarifperkwh;

    public Pelanggan(){

    }

    public Pelanggan(String idPelanggan,int nometer, String nama, String alamat, int daya, int tarifperkwh) {
        this.idPelanggan = idPelanggan;
        this.nometer = nometer;
        this.nama = nama;
        this.alamat = alamat;
        this.daya = daya;
        this.tarifperkwh = tarifperkwh;
    }

    public String getIdPelanggan() {
        return idPelanggan;
    }

    public void setIdPelanggan(String idPelanggan) {
        this.idPelanggan = idPelanggan;
    }

    public int getNometer() {
        return nometer;
    }

    public void setNometer(int nometer) {
        this.nometer = nometer;
    }

    public String getNama() {
        return nama;
    }

    public void setNama(String nama) {
        this.nama = nama;
    }

    public String getAlamat() {
        return alamat;
    }

    public void setAlamat(String alamat) {
        this.alamat = alamat;
    }

    public int getDaya() {
        return daya;
    }

    public void setDaya(int daya) {
        this.daya = daya;
    }

    public int getTarifperkwh() {
        return tarifperkwh;
    }

    public void setTarifperkwh(int tarifperkwh) {
        this.tarifperkwh = tarifperkwh;
    }
}
