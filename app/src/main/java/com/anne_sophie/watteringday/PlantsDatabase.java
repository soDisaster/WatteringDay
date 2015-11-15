package com.anne_sophie.watteringday;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;

/**
 * Created by Anne-Sophie.
 */
public class PlantsDatabase extends SQLiteOpenHelper {

    private String TABLE_NAME = "plant";
    private SQLiteDatabase bd;

    public PlantsDatabase(Context ctx) {
        super(ctx, "plants.bd", null, 1);
        bd = getWritableDatabase();
    }

    public PlantsDatabase(Context context, String name,
              SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    // Héritage de SQLiteOpenHelper

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE " + TABLE_NAME + "("
                + "idPlant INTEGER PRIMARY KEY AUTOINCREMENT,"
                + "namePlant TEXT NOT NULL," + "watteringPlant INTEGER NOT NULL);");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int ancienneVersion, int nouvelleVersion) {
        db.execSQL("DROP TABLE " + TABLE_NAME+ ";");
        onCreate(db);
    }

    public void close() {
        bd.close();
    }

    public long addPlant(Plant plant) {

        ContentValues valeurs = new ContentValues();

        valeurs.put("namePlant", plant.getNamePlant());
        valeurs.put("watteringPlant", plant.getWatteringPlant());

        return bd.insert("plant", null, valeurs);
    }

    public int updatePlant(Plant plant) {

        ContentValues valeurs = new ContentValues();

        valeurs.put("namePlant", plant.getNamePlant());
        valeurs.put("watteringPlant", plant.getWatteringPlant());

        return bd.update("plant", valeurs, "idPlant = " + plant.getIdPlant(), null);
    }

    public int deletePlant(int idPlant) {
        return bd.delete("plant", "idPlant = " + idPlant, null);
    }

    public Plant getPlant(int idPlant) {

        Cursor cursor = bd.query("plant", null, "idPlant = " + idPlant, null, null,	null, null);

        if (cursor.getCount() == 0)
            return null;

        // théroriquement, il n'y a qu'un seul enregistrement (au plus) qui
        // répond à la requête...
        // donc pas besoin de parcourir le résultat de la requête
        cursor.moveToFirst();

        return cursorToPlant(cursor);
    }

    public ArrayList<Plant> getPlants() {

        ArrayList<Plant> liste = new ArrayList<Plant>();

        // on renvoie toute la table "personnels", triée par nom+prénom
        Cursor cursor = bd.query("plant", null, null, null, null, null, "namePlant, watteringPlant");

        if (cursor.getCount() == 0)
            return liste;

        // on parcourt le résultat de la requête et on le transforme en un
        // tableau qu'on renvoie à la ListeView
        cursor.moveToFirst();
        do {
            liste.add(cursorToPlant(cursor));
        } while (cursor.moveToNext());

        cursor.close();

        return liste;
    }

    private Plant cursorToPlant(Cursor cursor) {

        Plant plant = new Plant();

        plant.setIdPlant(cursor.getInt(0));
        plant.setNamePlant(cursor.getString(1));
        plant.setWatteringPlant(cursor.getInt(2));

        return plant;
    }
}
