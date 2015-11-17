package com.anne_sophie.watteringday;

import java.sql.Timestamp;
import java.text.DateFormat;
import java.util.Date;
import java.util.Locale;

/**
 * Created by Anne-Sophie.
 *
 * Classe Plante
 */

public class Plant {

    /**
     * idPlant id de la plante
     * namePlant nom de la plante
     * watteringPlant frequence arrosage
     * watteringDay derniere date arrosage
     */

    private int idPlant;
    private String namePlant;
    private int watteringPlant;
    private long watteringDay;

    /**
     * Retourne id de la plante
     */

    public int getIdPlant() {
        return idPlant;
    }

    /**
     * Modifie id de la plante
     */

    public void setIdPlant(int idPlant) {
        this.idPlant = idPlant;
    }

    /**
     * Retourne le nom de la plante
     */

    public String getNamePlant() {
        return namePlant;
    }

    /**
     * Modifie le nom de la plante
     */

    public void setNamePlant(String namePlant) {
        this.namePlant = namePlant;
    }

    /**
     *  Retourne la fréquence d'arrosage
     */

    public int getWatteringPlant() {
        return watteringPlant;
    }

    /**
     * Modifie la préquence d'arrosage
     */

    public void setWatteringPlant(int watteringPlant) {
        this.watteringPlant = watteringPlant;
    }

    /**
     * Retourne la date du dernier arrosage
     * long qui sera transformé en TimeStamp
     */

    public long getWatteringDay() {
        return watteringDay;
    }

    /**
     * Modifie la date du dernier arrosage
     */

    public void setWatteringDay(long watteringDay) {
        this.watteringDay = watteringDay;
    }

    @Override
    public String toString() {

        /*
         * Date du dernier arrosage
         * Formatter en String
         */

        Timestamp t = new Timestamp(getWatteringDay());
        Date d = new Date(t.getTime());
        DateFormat df = DateFormat.getDateInstance(DateFormat.LONG, Locale.FRANCE);
        String date = df.format(d);

        // String qu'on utilisera pour modifier le contenu de la liste à l'aide du linesoflist

        // Cas où jour ne prend pas de "s"

        if(getWatteringPlant() == 1 || this.getWatteringPlant() == 0 )
            return namePlant + ",Doit être arrosée tous les jours" + ", Dernière date d'arrosage : " + date;
        else{
            return namePlant + ", Doit être arrosée tous les " + watteringPlant + " jours, Dernière date d'arrosage : " + date;
        }

    }
}