package com.example.bibliothequeapp;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Switch;
import android.widget.TextView;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

public class AddEditActivity extends AppCompatActivity {

    public static final String EXTRA_MODE = "MODE";
    public static final String EXTRA_LIVRE = "LIVRE";

    public static final String MODE_ADD = "ADD";
    public static final String MODE_EDIT = "EDIT";

    private EditText etTitre;
    private EditText etAuteur;
    private EditText etIsbn;
    private EditText etAnnee;
    private Switch switchDisponible;
    private Button btnEnregistrer;
    private TextView tvTitreFormulaire;

    private String mode;
    private Livre livreAModifier;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_edit);

        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
        }

        tvTitreFormulaire = findViewById(R.id.tvTitreFormulaire);
        etTitre = findViewById(R.id.etTitre);
        etAuteur = findViewById(R.id.etAuteur);
        etIsbn = findViewById(R.id.etIsbn);
        etAnnee = findViewById(R.id.etAnnee);
        switchDisponible = findViewById(R.id.switchDisponible);
        btnEnregistrer = findViewById(R.id.btnEnregistrer);

        Intent intent = getIntent();
        mode = intent.getStringExtra(EXTRA_MODE);

        if (MODE_EDIT.equals(mode)) {
            tvTitreFormulaire.setText("Modifier le livre");
            livreAModifier = (Livre) intent.getSerializableExtra(EXTRA_LIVRE);

            if (livreAModifier != null) {
                etTitre.setText(livreAModifier.getTitre());
                etAuteur.setText(livreAModifier.getAuteur());
                etIsbn.setText(livreAModifier.getIsbn());
                if (livreAModifier.getAnneePublication() > 0) {
                    etAnnee.setText(String.valueOf(livreAModifier.getAnneePublication()));
                }
                switchDisponible.setChecked(livreAModifier.isDisponible());
            }
        } else {
            mode = MODE_ADD;
            tvTitreFormulaire.setText("Ajouter un livre");
        }

        btnEnregistrer.setOnClickListener(v -> enregistrerLivre());
    }

    private void enregistrerLivre() {
        String titre = etTitre.getText().toString().trim();
        String auteur = etAuteur.getText().toString().trim();
        String isbn = etIsbn.getText().toString().trim();
        String anneeStr = etAnnee.getText().toString().trim();
        boolean disponible = switchDisponible.isChecked();

        if (!validerFormulaire(titre, auteur, isbn, anneeStr)) return;

        int annee = anneeStr.isEmpty() ? 0 : Integer.parseInt(anneeStr);

        Livre livre;
        if (MODE_EDIT.equals(mode) && livreAModifier != null) {
            livre = new Livre(livreAModifier.getId(), titre, auteur, isbn, disponible, annee);
        } else {
            livre = new Livre(0, titre, auteur, isbn, disponible, annee);
        }

        Intent resultIntent = new Intent();
        resultIntent.putExtra(EXTRA_MODE, mode);
        resultIntent.putExtra(EXTRA_LIVRE, livre);

        setResult(RESULT_OK, resultIntent);
        finish();
    }

    private boolean validerFormulaire(String titre, String auteur, String isbn, String annee) {
        boolean valide = true;

        if (TextUtils.isEmpty(titre)) {
            etTitre.setError("Le titre est obligatoire");
            valide = false;
        }

        if (TextUtils.isEmpty(auteur)) {
            etAuteur.setError("L'auteur est obligatoire");
            valide = false;
        }

        if (!TextUtils.isEmpty(isbn) && isbn.length() < 10) {
            etIsbn.setError("L'ISBN doit contenir au moins 10 caractères");
            valide = false;
        }

        if (!TextUtils.isEmpty(annee)) {
            int anneeInt = Integer.parseInt(annee);
            if (anneeInt < 1000 || anneeInt > 2025) {
                etAnnee.setError("Année invalide (entre 1000 et 2025)");
                valide = false;
            }
        }

        return valide;
    }

    @Override
    public boolean onSupportNavigateUp() {
        finish();
        return true;
    }
}