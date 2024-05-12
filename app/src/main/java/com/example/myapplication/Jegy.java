package com.example.myapplication;

import android.content.Context;
import android.util.AttributeSet;

import com.google.android.material.chip.Chip;

public class Jegy{

    private String id ;
    private String hely;
    private String tipus;
    private String km;
    private String ar;
    private String db;

    public Jegy(String hely, String tipus, String km, String ar) {
        this.hely = hely;
        this.tipus = tipus;
        this.km = km;
        this.ar = ar;
        this.id = "0";
        this.db = "0";
    }

    public Jegy(String id, String hely, String tipus, String km, String ar, String db) {
        this.id = id;
        this.hely = hely;
        this.tipus = tipus;
        this.km = km;
        this.ar = ar;
        this.db = db;
    }

    public String getHely() {
        return hely;
    }

    public String getTipus() {
        return tipus;
    }

    public String getKm() {
        return km;
    }

    public String getAr() {
        return ar;
    }

    public String getId() {
        return id;
    }
    public String getDb() {
        return db;
    }


}
