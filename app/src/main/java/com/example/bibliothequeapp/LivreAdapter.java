package com.example.bibliothequeapp;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.graphics.Color;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class LivreAdapter extends RecyclerView.Adapter<LivreAdapter.LivreViewHolder> {

    // Liste des livres à afficher
    private ArrayList<Livre> listeLivres;

    // Constructeur de l'adapter
    public LivreAdapter(ArrayList<Livre> listeLivres) {
        this.listeLivres = listeLivres;
    }

    @NonNull
    @Override
    public LivreViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        // Convertit le fichier XML item_livre.xml en objet View
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_livre, parent, false);

        return new LivreViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull LivreViewHolder holder, int position) {
        // Récupère le livre à la position courante
        Livre livre = listeLivres.get(position);

        // Injecte les données du livre dans les vues
        holder.tvTitreLivre.setText(livre.getTitre());
        holder.tvAuteurLivre.setText("Auteur : " + livre.getAuteur());
        holder.tvIsbnLivre.setText("ISBN : " + livre.getIsbn());

        // Gestion du badge de disponibilité
        if (livre.isDisponible()) {
            holder.tvDisponibilite.setText("Disponible");
            holder.tvDisponibilite.setBackgroundColor(Color.parseColor("#2E7D32"));
        } else {
            holder.tvDisponibilite.setText("Indisponible");
            holder.tvDisponibilite.setBackgroundColor(Color.parseColor("#C62828"));
        }
    }

    @Override
    public int getItemCount() {
        // Retourne le nombre total d'éléments à afficher
        return listeLivres.size();
    }

    // ViewHolder statique interne
    public static class LivreViewHolder extends RecyclerView.ViewHolder {

        TextView tvTitreLivre;
        TextView tvAuteurLivre;
        TextView tvIsbnLivre;
        TextView tvDisponibilite;

        public LivreViewHolder(@NonNull View itemView) {
            super(itemView);

            // Liaison entre les composants XML et les variables Java
            tvTitreLivre = itemView.findViewById(R.id.tvTitreLivre);
            tvAuteurLivre = itemView.findViewById(R.id.tvAuteurLivre);
            tvIsbnLivre = itemView.findViewById(R.id.tvIsbnLivre);
            tvDisponibilite = itemView.findViewById(R.id.tvDisponibilite);
        }
    }
}