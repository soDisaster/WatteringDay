package com.anne_sophie.watteringday;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.ArrayList;

/**
 * Created by Anne-Sophie.
 *
 * La base de données qui hérite de SQLiteOpenHelper
 */
public class PlantsDatabase extends SQLiteOpenHelper {

    // Le nom de la table : plant

    private String TABLE_NAME = "plant";

    // La base de données

    private SQLiteDatabase bd;


    public PlantsDatabase(Context ctx) {
        super(ctx, "plants.bd", null, 1);
        bd = getWritableDatabase();
    }

    public PlantsDatabase(Context context, String name,
                          SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    /* On crée la table plant avec pour attribut
    ** idPlant id plante
    * namePlant nom plante
    * watteringPlant frequence arrosage
    * watteringDay dernier joru arrosage
    */

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE " + TABLE_NAME + "("
                + "idPlant INTEGER PRIMARY KEY AUTOINCREMENT,"
                + "namePlant TEXT NOT NULL," + "watteringPlant INTEGER NOT NULL," + "watteringDay INTEGER NOT NULL);");
    }

    // Mise a jour

    @Override
    public void onUpgrade(SQLiteDatabase db, int ancienneVersion, int nouvelleVersion) {
        db.execSQL("DROP TABLE " + TABLE_NAME+ ";");
        onCreate(db);
    }

    // Fermeture de la base

    public void close() {
        bd.close();
    }

    // Ajout des valeurs nom, frequence et date dernier arrosage dans la table

    public long addPlant(Plant p) {

        ContentValues valeurs = new ContentValues();

        valeurs.put("namePlant", p.getNamePlant());
        valeurs.put("watteringPlant", p.getWatteringPlant());
        valeurs.put("watteringDay", p.getWatteringDay());

        // Insertion

        return bd.insert(TABLE_NAME, null, valeurs);
    }

    // Mise à jour des valeurs nom, frequence et date dernier arrosage dans la table

    public int updatePlant(Plant p) {

        ContentValues valeurs = new ContentValues();

        valeurs.put("namePlant", p.getNamePlant());
        valeurs.put("watteringPlant", p.getWatteringPlant());
        valeurs.put("watteringDay", p.getWatteringDay());

        // Mise à jour

        return bd.update(TABLE_NAME, valeurs, "idPlant = " + p.getIdPlant(), null);
    }

    //Suppresion d'une plante dans la table

    public int deletePlant(int idPlant) {

        // Suppression

        return bd.delete(TABLE_NAME, "idPlant = " + idPlant, null);
    }

    // Récuperer une plante en fonction de son id

    public Plant getPlant(int idPlant) {

        // Requête pour récuperer cette plante, où idPlant est égal au int passé en paramètre

        Cursor cursor = bd.query(TABLE_NAME, null, "idPlant = " + idPlant, null, null,	null, null);

        if (cursor.getCount() == 0)
            return null;

        // Un seul enregistrement - id unique

        cursor.moveToFirst();

        return cursorToPlant(cursor);
    }

    // Récuperer les plantes de la base dans une ArrayList

    public ArrayList<Plant> getPlants() {

        ArrayList<Plant> liste = new ArrayList<Plant>();

        // Requête triée par nom

        Cursor cursor = bd.query("plant", null, null, null, null, null, "namePlant");

        if (cursor.getCount() == 0)
            return liste;

        // Ajout des plantes à la liste dans l'ordre alphabétique

        cursor.moveToFirst();
        do {
            liste.add(cursorToPlant(cursor));
        } while (cursor.moveToNext());

        cursor.close();

        return liste;
    }

    // Récupérer les informations du curseur et modifier les attributs de la plante à l'aide de celles-ci

    private Plant cursorToPlant(Cursor cursor) {

        Plant plant = new Plant();

        plant.setIdPlant(cursor.getInt(0));
        plant.setNamePlant(cursor.getString(1));
        plant.setWatteringPlant(cursor.getInt(2));
        plant.setWatteringDay(cursor.getLong(3));

        return plant;
    }
}
