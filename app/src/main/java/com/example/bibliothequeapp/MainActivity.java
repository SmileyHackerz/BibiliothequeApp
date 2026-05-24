package com.example.bibliothequeapp;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    // Composant d'affichage de liste
    private RecyclerView recyclerViewLivres;

    // Adapter personnalisé
    private LivreAdapter livreAdapter;

    // Liste des livres
    private ArrayList<Livre> listeLivres;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Associe le layout XML à l'activité
        setContentView(R.layout.activity_main);

        // Récupération du RecyclerView dans le layout
        recyclerViewLivres = findViewById(R.id.recyclerViewLivres);

        // Initialisation de la liste
        listeLivres = new ArrayList<>();

        // Ajout de 8 livres fictifs
        listeLivres.add(new Livre(1, "Le Petit Prince", "Antoine de Saint-Exupéry", "9780156013987", true));
        listeLivres.add(new Livre(2, "L'Étranger", "Albert Camus", "9782070360024", false));
        listeLivres.add(new Livre(3, "Les Misérables", "Victor Hugo", "9782253096344", true));
        listeLivres.add(new Livre(4, "Une si longue lettre", "Mariama Bâ", "9782841290529", true));
        listeLivres.add(new Livre(5, "Le Vieux Nègre et la Médaille", "Ferdinand Oyono", "9782264018304", false));
        listeLivres.add(new Livre(6, "Madame Bovary", "Gustave Flaubert", "9782070409228", true));
        listeLivres.add(new Livre(7, "La Peste", "Albert Camus", "9782070360420", false));
        listeLivres.add(new Livre(8, "Sous l'orage", "Seydou Badian", "9782708707691", true));

        // Le RecyclerView affichera les éléments verticalement
        recyclerViewLivres.setLayoutManager(new LinearLayoutManager(this));

        // Création de l'adapter avec la liste des livres
        livreAdapter = new LivreAdapter(listeLivres);

        // Liaison entre le RecyclerView et l'adapter
        recyclerViewLivres.setAdapter(livreAdapter);
    }
}