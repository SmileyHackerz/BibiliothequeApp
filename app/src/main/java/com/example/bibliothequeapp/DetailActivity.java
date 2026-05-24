package com.example.bibliothequeapp;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.button.MaterialButton;

public class DetailActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        Livre livre = (Livre) getIntent().getSerializableExtra("livre");

        TextView tvTitre = findViewById(R.id.tvTitreDetail);
        TextView tvAuteur = findViewById(R.id.tvAuteurDetail);
        TextView tvIsbn = findViewById(R.id.tvIsbnDetail);
        TextView tvAnnee = findViewById(R.id.tvAnneeDetail);
        TextView tvDispo = findViewById(R.id.tvDisponibiliteDetail);
        MaterialButton btnModifier = findViewById(R.id.btnModifier);

        if (livre != null) {
            tvTitre.setText(livre.getTitre());
            tvAuteur.setText("✍️ " + livre.getAuteur());
            tvIsbn.setText("ISBN : " + livre.getIsbn());

            if (livre.getAnneePublication() > 0) {
                tvAnnee.setText("📅 " + livre.getAnneePublication());
                tvAnnee.setVisibility(View.VISIBLE);
            } else {
                tvAnnee.setVisibility(View.GONE);
            }

            if (livre.isDisponible()) {
                tvDispo.setText("✅ Disponible");
                tvDispo.setBackgroundColor(Color.parseColor("#2E7D32"));
            } else {
                tvDispo.setText("❌ Indisponible");
                tvDispo.setBackgroundColor(Color.parseColor("#C62828"));
            }

            btnModifier.setOnClickListener(v -> {
                Intent intent = new Intent(DetailActivity.this, AddEditActivity.class);
                intent.putExtra(AddEditActivity.EXTRA_MODE, AddEditActivity.MODE_EDIT);
                intent.putExtra(AddEditActivity.EXTRA_LIVRE, livre);
                startActivity(intent);
                finish();
            });
        }
    }
}