package com.anne_sophie.watteringday;

import android.app.ListActivity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import java.sql.Timestamp;
import java.util.ArrayList;

/**
 * Created by Anne-Sophie.
 * <p>
 * Classe permettant de gérer une liste de plantes.
 */
public class ListPlantsActivity extends ListActivity {

    /**
     * Attributs d'instances
     * bd Base de données
     * plants Tableau de plantes
     * list ListView du layout
     */

    private PlantsDatabase bd;
    private ArrayList<Plant> plants = new ArrayList<Plant>();
    private ListView list;

    @Override
    public void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        // Cette classe est associée au layout activity_list_plants

        setContentView(R.layout.activity_list_plants);

        // bd correspond à la classe PlantsDatabase où est gerée la base de données

        bd = new PlantsDatabase(this);

        // Changement style bouton Ajouter

        Button buttonAdd = (Button) findViewById(R.id.buttonAdd);
        buttonAdd.setTextColor(Color.rgb(237, 127, 16));
        buttonAdd.setBackgroundColor(Color.WHITE);
    }

    @Override
    protected void onStart() {
        super.onStart();

        // Liste des plantes de la BDD dans tableau

        plants = bd.getPlants();

        // List dans ListView

        setListAdapter(new NameAndDaysAdapter(this));
    }

    @Override
    protected void onListItemClick(ListView liste, View vue, int position, long id) {

        // On récupère la plante à la position du clic

        Plant plant = plants.get(position);

        // On passe à une nouvelle activité plantActivity au clic sur un item de la liste

        Intent intention = new Intent(this, PlantActivity.class);
        intention.putExtra("idPlant", plant.getIdPlant());

        startActivity(intention);
    }

    /**
     * On passe à une nouvelle activité en cliquant sur le bouton Ajouter
     */

    public void edit(View vue) {

        startActivity(new Intent(this, PlantActivity.class));
    }

    @Override
    protected void onDestroy() {

        bd.close();
        super.onDestroy();
    }

    /**
     * Adapter pour créer une jolie liste
     */

    class NameAndDaysAdapter extends ArrayAdapter<Plant> {


        NameAndDaysAdapter(Context context) {
            super(context, R.layout.linesoflist, plants);
        }

        /**
         * Retourne le nombre de jours qui sépare deux TimeStamp
         */

        protected int compareNumberOfDays(Timestamp numberOfDays1, Timestamp numberOfDays2) {
            long nbMilliSec = numberOfDays1.getTime() - numberOfDays2.getTime();
            double numberOfDays = ((double) nbMilliSec) / (1000 * 60 * 60 * 24);
            return (int) (Math.round(numberOfDays));
        }

        // Cet adapter donne à chaque ligne de la ListView un contenu personnalisé

        public View getView(int position, View convertView, ViewGroup parent) {

            // On ne créé pas à nouveau les lignes déjà créées

            View line = convertView;
            if (line == null) {
                LayoutInflater xml = ListPlantsActivity.this.getLayoutInflater();
                line = xml.inflate(R.layout.linesoflist, null);
            }

            // On récupère les TextView présents dans le layout lineoflist

            TextView namePlant = (TextView) line.findViewById(R.id.namePlant);
            TextView daysPlant = (TextView) line.findViewById(R.id.daysPlant);
            TextView datePlant = (TextView) line.findViewById(R.id.datePlant);

            //On récupère la ListView

            list = getListView();

            /* Après un long clic sur un item de la ListView, on considère que la plante est arrosée
                On change alors sa date de dernier arrosage et la couleur de son background
             */

            list.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {

                @Override
                public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {

                    // Même procédé qu'au dessus

                    View line = view;
                    if (line == null) {
                        LayoutInflater xml = ListPlantsActivity.this.getLayoutInflater();
                        line = xml.inflate(R.layout.linesoflist, null);
                    }
                    // Plante arrosée : fond vert et mise à jour derniere date d'arrosage
                    line.setBackgroundColor(Color.rgb(58, 157, 35));
                    Plant p = plants.get(position);
                    p.setWatteringDay(System.currentTimeMillis());
                    ListPlantsActivity.this.bd.updatePlant(p);
                    return true;
                }
            });

            // Plante à une position

            Plant p = plants.get(position);

            // Fréquence d'arrosage

            int numberOfDays = p.getWatteringPlant();

            // Le dernier jour où elle a été arrosée

            Long lastday_long = p.getWatteringDay();
            Timestamp lastday = new Timestamp(lastday_long);

            // La date du jour

            Long today_long = System.currentTimeMillis();
            Timestamp today = new Timestamp(today_long);

            // On récupère le toString() de la plante et on le split

            String nameAndWatteringPlants = getListAdapter().getItem(position).toString();
            String[] data = nameAndWatteringPlants.split(",");

            /* On récupère donc son nom, sa fréquence d'arrosage en jours et la date à laquelle elle a été arrosée
                On les ajoute dans les TextView prévus à cet effet
            */

            namePlant.setText(data[0]);
            daysPlant.setText(data[1]);
            datePlant.setText(data[2]);


            // Comparaison entre la date du jour et le dernier jour d'arrosage

            /*
                Si ils sont égaux (Cas pour les plantes qui doivent être arrosées tosu les jours )
                Background vert
             */

            if (this.compareNumberOfDays(today, lastday) == 0) {
                line.setBackgroundColor(Color.rgb(58, 157, 35));
            }

            /*
                Si il reste 1 jour ou que c'est le dernier jour pour arroser la plante
                Background orange
             */

            else if (this.compareNumberOfDays(today, lastday) == numberOfDays || this.compareNumberOfDays(today, lastday) == numberOfDays - 1) {
                line.setBackgroundColor(Color.rgb(237, 127, 16));
            }

             /*
                Si la date limite pour arroser n'est pas encore arrivée
                Background vert
             */

            else if (this.compareNumberOfDays(today, lastday) < numberOfDays) {
                line.setBackgroundColor(Color.rgb(58, 157, 35));
            }

            /*
                Si la date limite pour arroser est dépassée
                Background rouge
             */

            else if (this.compareNumberOfDays(today, lastday) > numberOfDays) {
                line.setBackgroundColor(Color.rgb(219, 23, 2));
            }

            /*
               Au cas où
             */

            else {
                line.setBackgroundColor(Color.BLACK);
            }

            return line;
        }

    }
}
