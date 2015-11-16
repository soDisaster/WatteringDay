package com.anne_sophie.watteringday;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
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

        bd = new PlantsDatabase(this);

    }

    @Override
    protected void onStart() {
        super.onStart();

        Bundle donnees = getIntent().getExtras();
        if (donnees != null) {
            buttonSave.setEnabled(false);
            buttonUpdate.setEnabled(true);
            buttonDelete.setEnabled(true);
            plant = bd.getPlant(donnees.getInt("idPlant"));
            editTextNamePlant.setText(plant.getNamePlant());
            editTextDays.setText("" + plant.getWatteringPlant());
        }
        else {
            buttonSave.setEnabled(true);
            buttonUpdate.setEnabled(false);
            buttonDelete.setEnabled(false);
        }
    }

    // les boutons sont connectés par XML...

    public void save(View vue) {

        plant = new Plant();

        plant.setNamePlant(editTextNamePlant.getText().toString());
        plant.setWatteringPlant(Integer.parseInt(editTextDays.getText().toString()));
        plant.setWatteringDay(System.currentTimeMillis());
        Log.d("EEEE", "" + plant.getWatteringDay());
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

        bd.deletePlant(plant.getIdPlant());

        finish();
    }

    @Override
    protected void onDestroy() {
        bd.close();
        super.onDestroy();
    }
}
