package com.anne_sophie.watteringday;

import android.app.ListActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;

/**
 * Created by Anne-Sophie.
 */
public class ListPlantsActivity extends ListActivity {

    private PlantsDatabase bd;
    private ArrayList<Plant> plants = new ArrayList<Plant>();
    private ListView list;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_plants);

        bd = new PlantsDatabase(this);
    }

    @Override
    protected void onStart() {
        super.onStart();

        // on récupère la liste des Personnes dans la BDD, sous forme de ArrayList
        plants = bd.getPlants();
        // on affiche la liste dans une ListView
        setListAdapter(new ArrayAdapter<Plant>(this, android.R.layout.simple_list_item_1, plants));
    }

    @Override
    protected void onListItemClick(ListView liste, View vue, int position, long id) {
        Plant plant = plants.get(position);

        Intent intention = new Intent(this, PlantActivity.class);
        intention.putExtra("idPlant", plant.getIdPlant());

        startActivity(intention);
    }

    public void edit(View vue) {
        startActivity(new Intent(this, PlantActivity.class));
    }

    @Override
    protected void onDestroy() {

        bd.close();

        super.onDestroy();
    }
}
