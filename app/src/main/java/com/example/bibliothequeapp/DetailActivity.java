package com.example.bibliothequeapp;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.button.MaterialButton;

public class DetailActivity extends AppCompatActivity {

    public static final int REQUEST_EDIT_FROM_DETAIL = 200;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        Livre livre = (Livre) getIntent().getSerializableExtra("livre");

        TextView tvTitre = findViewById(R.id.tvTitreDetail);
        TextView tvAuteur = findViewById(R.id.tvAuteurDetail);
        TextView tvIsbn = findViewById(R.id.tvIsbnDetail);
        TextView tvDispo = findViewById(R.id.tvDisponibiliteDetail);
        MaterialButton btnModifier = findViewById(R.id.btnModifier);

        if (livre != null) {
            tvTitre.setText(livre.getTitre());
            tvAuteur.setText("✍️ " + livre.getAuteur());
            tvIsbn.setText("ISBN : " + livre.getIsbn());

            if (livre.isDisponible()) {
                tvDispo.setText("✅ Disponible");
                tvDispo.getBackground().mutate();
                tvDispo.setBackgroundColor(Color.parseColor("#2E7D32"));
            } else {
                tvDispo.setText("❌ Indisponible");
                tvDispo.getBackground().mutate();
                tvDispo.setBackgroundColor(Color.parseColor("#C62828"));
            }

            btnModifier.setOnClickListener(v -> {
                Intent intent = new Intent(DetailActivity.this, AddEditActivity.class);
                intent.putExtra(AddEditActivity.EXTRA_MODE, AddEditActivity.MODE_EDIT);
                intent.putExtra(AddEditActivity.EXTRA_LIVRE, livre);
                intent.putExtra(AddEditActivity.EXTRA_POSITION,
                        getIntent().getIntExtra(AddEditActivity.EXTRA_POSITION, -1));
                startActivityForResult(intent, REQUEST_EDIT_FROM_DETAIL);
            });
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_EDIT_FROM_DETAIL && resultCode == RESULT_OK) {
            setResult(RESULT_OK, data);
            finish();
        }
    }
}