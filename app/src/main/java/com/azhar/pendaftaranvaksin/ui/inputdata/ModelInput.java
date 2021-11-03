package com.azhar.pendaftaranvaksin.ui.inputdata;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

/**
 * Created by Azhar Rivaldi on 17-10-2021
 * Youtube Channel : https://bit.ly/2PJMowZ
 * Github : https://github.com/AzharRivaldi
 * Twitter : https://twitter.com/azharrvldi_
 * Instagram : https://www.instagram.com/azhardvls_
 * LinkedIn : https://www.linkedin.com/in/azhar-rivaldi
 */

@Entity(tableName = "vaccine_table")
public class ModelInput {

    @PrimaryKey(autoGenerate = true)
    private int id;

    @ColumnInfo(name = "nik")
    private String nik;

    @ColumnInfo(name = "nama")
    private String nama;

    @ColumnInfo(name = "ttl")
    private String ttl;

    @ColumnInfo(name = "tglvaksin")
    private String tglvaksin;

    @ColumnInfo(name = "alamat")
    private String alamat;

    @ColumnInfo(name = "rumahsakit")
    private String rumahsakit;

    @ColumnInfo(name = "image", typeAffinity = ColumnInfo.BLOB)
    private byte[] image;

    public ModelInput(String nik, String nama, String ttl, String tglvaksin, String alamat, String rumahsakit, byte[] image) {
        this.nik = nik;
        this.nama = nama;
        this.ttl = ttl;
        this.tglvaksin = tglvaksin;
        this.alamat = alamat;
        this.rumahsakit = rumahsakit;
        this.image = image;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNik() {
        return nik;
    }

    public void setNik(String nik) {
        this.nik = nik;
    }

    public String getNama() {
        return nama;
    }

    public void setNama(String nama) {
        this.nama = nama;
    }

    public String getTtl() {
        return ttl;
    }

    public void setTtl(String ttl) {
        this.ttl = ttl;
    }

    public String getTglvaksin() {
        return tglvaksin;
    }

    public void setTglvaksin(String tglvaksin) {
        this.tglvaksin = tglvaksin;
    }

    public String getAlamat() {
        return alamat;
    }

    public void setAlamat(String alamat) {
        this.alamat = alamat;
    }

    public String getRumahsakit() {
        return rumahsakit;
    }

    public void setRumahsakit(String rumahsakit) {
        this.rumahsakit = rumahsakit;
    }

    public byte[] getImage() {
        return image;
    }

    public void setImage(byte[] image) {
        this.image = image;
    }
}
