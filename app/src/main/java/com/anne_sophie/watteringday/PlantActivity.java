package com.anne_sophie.watteringday;

import android.app.Activity;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

/**
 * Created by Anne-Sophie
 *
 * Permet de gérer les activités lié à une plante
 */

public class PlantActivity extends Activity {

    // Les EditText correspondant au nom et à la fréquence d'arrosage de la plante

    private EditText editTextNamePlant, editTextDays;
    private Plant plant;

    // Les boutons permettant de supprimer, modifier ou valider

    private Button buttonSave, buttonUpdate, buttonDelete;

    // La base de données

    private PlantsDatabase bd;

    @Override
    public void onCreate(Bundle bundle) {

        super.onCreate(bundle);

        // Layout activity_plant

        setContentView(R.layout.activity_plant);

        // Récupérer id des EditText correspondant au nom et à la fréquence d'arrosage de la plante

        editTextNamePlant = (EditText) findViewById(R.id.editTextNamePlant);
        editTextDays = (EditText) findViewById(R.id.editTextDays);

        // Récupérer id des boutons permettant de supprimer, modifier ou valider

        buttonSave = (Button) findViewById(R.id.buttonOk);
        buttonUpdate = (Button) findViewById(R.id.buttonUpdate);
        buttonDelete = (Button) findViewById(R.id.buttonDelete);

        // Style des boutons

        buttonSave.setTextColor(Color.rgb(237, 127, 16));
        buttonSave.setBackgroundColor(Color.WHITE);

        buttonUpdate.setTextColor(Color.rgb(237, 127, 16));
        buttonUpdate.setBackgroundColor(Color.WHITE);

        buttonDelete.setTextColor(Color.rgb(237, 127, 16));
        buttonDelete.setBackgroundColor(Color.WHITE);

        // Nouvelle base de donnees dans bd

        bd = new PlantsDatabase(this);

    }

    @Override
    protected void onStart() {
        super.onStart();

        Bundle donnees = getIntent().getExtras();

        // Si il y a des données ou non. Edition ou Ajout

        // Si on édite une plante, le bouton valider n'est pas disponible

        if (donnees != null) {
            buttonSave.setEnabled(false);
            buttonUpdate.setEnabled(true);
            buttonDelete.setEnabled(true);
            plant = bd.getPlant(donnees.getInt("idPlant"));
            editTextNamePlant.setText(plant.getNamePlant());
            editTextDays.setText("" + plant.getWatteringPlant());
        }
        // Si on ajoute une plante, Seul le bouton valider est disponible

        else {
            buttonSave.setEnabled(true);
            buttonUpdate.setEnabled(false);
            buttonDelete.setEnabled(false);
        }
    }

     /*
      * Les boutons sont connectés dans le XML.
      * buttonSave avec save()
      */

    public void save(View vue) {

        plant = new Plant();

        plant.setNamePlant(editTextNamePlant.getText().toString());
        plant.setWatteringPlant(Integer.parseInt(editTextDays.getText().toString()));
        plant.setWatteringDay(System.currentTimeMillis());
        Log.d("EEEE", "" + plant.getWatteringDay());
        bd.addPlant(plant);

        finish();
    }

    /*
      * Les boutons sont connectés dans le XML.
      * buttonUpdate avec update()
      */

    public void update(View vue) {

        plant.setNamePlant(editTextNamePlant.getText().toString());
        plant.setWatteringPlant(Integer.parseInt(editTextDays.getText().toString()));
        bd.updatePlant(plant);

        finish();
    }

    /*
      * Les boutons sont connectés dans le XML.
      * buttonDelete avec delete()
      */

    public void delete(View vue) {

        bd.deletePlant(plant.getIdPlant());

        finish();
    }

    @Override
    protected void onDestroy() {
        bd.close();
        super.onDestroy();
    }
}
