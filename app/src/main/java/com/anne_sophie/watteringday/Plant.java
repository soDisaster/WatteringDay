package com.anne_sophie.watteringday;

/**
 * Created by Anne-Sophie.
 * Classe métier pour une Plante
 */

public class Plant {

    private int idPlant;
    private String namePlant;
    private int watteringPlant;

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

    @Override
    public String toString() {
        return namePlant + ", " + watteringPlant;
    }
}