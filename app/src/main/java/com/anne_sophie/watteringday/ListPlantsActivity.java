package com.anne_sophie.watteringday;

import android.app.ListActivity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import java.sql.Timestamp;
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
        //setListAdapter(new ArrayAdapter<Plant>(this, android.R.layout.simple_list_item_1, plants));
        setListAdapter(new NameAndDaysAdapter(this));
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

    class NameAndDaysAdapter extends ArrayAdapter<Plant> {

        NameAndDaysAdapter(Context context) {
            super(context, R.layout.linesoflist, plants);
        }

        protected int compareNumberOfDays(Timestamp numberOfDays1, Timestamp numberOfDays2) {
            long nbMilliSec = numberOfDays1.getTime() - numberOfDays2.getTime();
            double numberOfDays = ((double)nbMilliSec) / (60 * 60 * 24);
            return (int) (Math.round(numberOfDays));
        }

        public View getView(int position, View convertView, ViewGroup parent) {

            View line = convertView;
            if (line == null) {
                LayoutInflater xml = ListPlantsActivity.this.getLayoutInflater();
                line = xml.inflate(R.layout.linesoflist, null);
            }

            TextView namePlant = (TextView) line.findViewById(R.id.namePlant);
            TextView daysPlant = (TextView) line.findViewById(R.id.daysPlant);


                Plant p = plants.get(position);

                int numberOfDays = p.getWatteringPlant();

                Long lastday_long = p.getWatteringDay();
                Timestamp lastday = new Timestamp(lastday_long);

                Long today_long = System.currentTimeMillis();
                Timestamp today = new Timestamp(today_long);

                String nameAndWatteringPlants = getListAdapter().getItem(position).toString();
                String[] data = nameAndWatteringPlants.split(" ");

                namePlant.setText(data[4]);
                daysPlant.setText(data[5]);

                if (this.compareNumberOfDays(today, lastday) == numberOfDays || this.compareNumberOfDays(today, lastday) == numberOfDays - 1) {
                    line.setBackgroundColor(Color.rgb(237, 127, 16));
                }
                else if (this.compareNumberOfDays(today, lastday) < numberOfDays) {
                    line.setBackgroundColor(Color.GREEN);
                }
                else if (this.compareNumberOfDays(today, lastday) > numberOfDays) {
                    line.setBackgroundColor(Color.RED);
                }
                else {
                    line.setBackgroundColor(Color.BLACK);
                }

            return line;
        }

    }
}
