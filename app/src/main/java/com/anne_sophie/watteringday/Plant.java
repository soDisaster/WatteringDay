package com.anne_sophie.watteringday;


import android.util.Log;

import java.sql.Timestamp;
import java.text.DateFormat;
import java.util.Date;
import java.util.Locale;

/**
 * Created by Anne-Sophie.
 * Classe métier pour une Plante
 */

public class Plant {

    private int idPlant;
    private String namePlant;
    private int watteringPlant;
    private long watteringDay;

    public int getIdPlant() {
        return idPlant;
    }

    public void setIdPlant(int idPlant) {
        this.idPlant = idPlant;
    }

    public String getNamePlant() {
        return namePlant;
    }

    public void setNamePlant(String namePlant) {
        this.namePlant = namePlant;
    }

    public int getWatteringPlant() {
        return watteringPlant;
    }

    public void setWatteringPlant(int watteringPlant) {
        this.watteringPlant = watteringPlant;
    }

    public long getWatteringDay() {
        return watteringDay;
    }

    public void setWatteringDay(long watteringDay) {
        this.watteringDay = watteringDay;
    }

    @Override
    public String toString() {

        Timestamp t = new Timestamp(getWatteringDay());
        Date d = new Date(t.getTime());
        DateFormat df = DateFormat.getDateInstance(DateFormat.LONG, Locale.FRANCE);
        String date = df.format(d);
        if(getWatteringPlant() == 1 || this.getWatteringPlant() == 0 )
            return namePlant + ",Doit être arrosée tous les jours" + ", Dernière date d'arrosage : " + date;
        else{
            return namePlant + ", Doit être arrosée tous les " + watteringPlant + " jours, Dernière date d'arrosage : " + date;
        }

    }
}