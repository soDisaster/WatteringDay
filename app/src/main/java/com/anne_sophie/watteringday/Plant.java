package com.anne_sophie.watteringday;

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
        return "Nouvelle Plante : " + idPlant + " " + namePlant + " " + watteringPlant + " " + watteringDay;
    }
}