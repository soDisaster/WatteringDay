package com.anne_sophie.watteringday;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class PlantActivity extends Activity {

    private EditText editTextNamePlant, editTextDays;
    private Plant plant;
    private Button buttonSave, buttonUpdate, buttonDelete;
    private PlantsDatabase bd;

    @Override
    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        setContentView(R.layout.activity_plant);

        editTextNamePlant = (EditText) findViewById(R.id.editTextNamePlant);
        editTextDays = (EditText) findViewById(R.id.editTextDays);
        buttonSave = (Button) findViewById(R.id.buttonOk);
        buttonUpdate = (Button) findViewById(R.id.buttonUpdate);
        buttonDelete = (Button) findViewById(R.id.buttonDelete);

        // création d'une nouvelle personne
        plant = new Plant();

        // version 1 : sans DAO
        bd = new PlantsDatabase(this);

    }

    @Override
    protected void onStart() {
        super.onStart();

        // s'il y a eu passage de paramètre, alors on peut modifier/supprimer
        Bundle donnees = getIntent().getExtras();
        if (donnees != null) {
            buttonSave.setEnabled(false);
            buttonUpdate.setEnabled(true);
            buttonDelete.setEnabled(true);
            plant = bd.getPlant(donnees.getInt("idPlant"));
            editTextNamePlant.setText(plant.getNamePlant());
            editTextDays.setText("" + plant.getWatteringPlant());
        }
        // sinon on peut ajouter une personne
        else {
            buttonSave.setEnabled(true);
            buttonUpdate.setEnabled(false);
            buttonDelete.setEnabled(false);
        }
    }

    // les boutons sont connectés par XML...

    public void save(View vue) {

        plant.setNamePlant(editTextNamePlant.getText().toString());
        plant.setWatteringPlant(Integer.parseInt(editTextDays.getText().toString()));

        bd.addPlant(plant);

        finish();
    }

    public void update(View vue) {

        plant.setNamePlant(editTextNamePlant.getText().toString());
        plant.setWatteringPlant(Integer.parseInt(editTextDays.getText().toString()));

        bd.updatePlant(plant);

        finish();
    }

    public void delete(View vue) {
        // version 1 : sans DAO
        bd.deletePlant(plant.getIdPlant());

        // version 2 : avec DAO
//		bd.supprimer(personne);

        finish();
    }

    @Override
    protected void onDestroy() {
        bd.close();
        super.onDestroy();
    }
}
