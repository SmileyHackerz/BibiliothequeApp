package com.example.bibliothequeapp;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class MainActivity extends AppCompatActivity {

    private RecyclerView recyclerViewLivres;
    private FloatingActionButton fabAjouterLivre;
    private EditText etRecherche;
    private TextView tvListeVide;

    private LivreAdapter livreAdapter;
    private List<Livre> listeLivres;

    private AppDatabase database;
    private ExecutorService executorService;

    private ActivityResultLauncher<Intent> addEditLauncher;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        recyclerViewLivres = findViewById(R.id.recyclerViewLivres);
        fabAjouterLivre = findViewById(R.id.fabAjouterLivre);
        etRecherche = findViewById(R.id.etRecherche);
        tvListeVide = findViewById(R.id.tvListeVide);

        database = AppDatabase.getInstance(this);
        executorService = Executors.newSingleThreadExecutor();

        listeLivres = new ArrayList<>();

        livreAdapter = new LivreAdapter(listeLivres, new LivreAdapter.OnLivreClickListener() {
            @Override
            public void onLivreClick(Livre livre) {
                ouvrirDetailLivre(livre);
            }

            @Override
            public void onLivreLongClick(Livre livre, int position) {
                afficherOptionsLivre(livre, position);
            }

            @Override
            public void onModifierClick(Livre livre) {
                ouvrirFormulaireModification(livre);
            }

            @Override
            public void onSupprimerClick(Livre livre, int position) {
                confirmerSuppression(livre, position);
            }
        });

        recyclerViewLivres.setLayoutManager(new LinearLayoutManager(this));
        recyclerViewLivres.setAdapter(livreAdapter);

        initialiserActivityResultLauncher();

        fabAjouterLivre.setOnClickListener(v -> ouvrirFormulaireAjout());

        etRecherche.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                rechercherLivres(s.toString().trim());
            }

            @Override
            public void afterTextChanged(Editable s) {}
        });

        chargerLivresDepuisRoom();
    }

    private void initialiserActivityResultLauncher() {
        addEditLauncher = registerForActivityResult(
                new ActivityResultContracts.StartActivityForResult(),
                result -> {
                    if (result.getResultCode() == RESULT_OK && result.getData() != null) {
                        Intent data = result.getData();
                        Livre livre = (Livre) data.getSerializableExtra(AddEditActivity.EXTRA_LIVRE);
                        String mode = data.getStringExtra(AddEditActivity.EXTRA_MODE);

                        if (livre == null) return;

                        if (AddEditActivity.MODE_ADD.equals(mode)) {
                            ajouterLivreDansRoom(livre);
                        } else if (AddEditActivity.MODE_EDIT.equals(mode)) {
                            modifierLivreDansRoom(livre);
                        }
                    }
                }
        );
    }

    private void chargerLivresDepuisRoom() {
        executorService.execute(() -> {
            List<Livre> livresDepuisBase = database.livreDao().getAllLivres();
            runOnUiThread(() -> {
                livreAdapter.setLivres(livresDepuisBase);
                afficherOuMasquerListeVide();
            });
        });
    }

    private void rechercherLivres(String query) {
        executorService.execute(() -> {
            List<Livre> resultats;
            if (query.isEmpty()) {
                resultats = database.livreDao().getAllLivres();
            } else {
                resultats = database.livreDao().rechercherParTitre(query);
            }
            runOnUiThread(() -> {
                livreAdapter.setLivres(resultats);
                afficherOuMasquerListeVide();
            });
        });
    }

    private void ajouterLivreDansRoom(Livre livre) {
        executorService.execute(() -> {
            livre.setId(0);
            database.livreDao().insert(livre);
            List<Livre> livresDepuisBase = database.livreDao().getAllLivres();
            runOnUiThread(() -> {
                livreAdapter.setLivres(livresDepuisBase);
                recyclerViewLivres.scrollToPosition(0);
                afficherOuMasquerListeVide();
                Toast.makeText(this, "✅ Livre ajouté !", Toast.LENGTH_SHORT).show();
            });
        });
    }

    private void modifierLivreDansRoom(Livre livre) {
        executorService.execute(() -> {
            database.livreDao().update(livre);
            List<Livre> livresDepuisBase = database.livreDao().getAllLivres();
            runOnUiThread(() -> {
                livreAdapter.setLivres(livresDepuisBase);
                afficherOuMasquerListeVide();
                Toast.makeText(this, "✏️ Livre modifié !", Toast.LENGTH_SHORT).show();
            });
        });
    }

    private void supprimerLivreDansRoom(Livre livre, int position) {
        executorService.execute(() -> {
            database.livreDao().delete(livre);
            runOnUiThread(() -> {
                livreAdapter.supprimerLivre(position);
                afficherOuMasquerListeVide();
                Toast.makeText(this, "🗑️ Livre supprimé !", Toast.LENGTH_SHORT).show();
            });
        });
    }

    private void afficherOuMasquerListeVide() {
        if (livreAdapter.getItemCount() == 0) {
            tvListeVide.setVisibility(View.VISIBLE);
            recyclerViewLivres.setVisibility(View.GONE);
        } else {
            tvListeVide.setVisibility(View.GONE);
            recyclerViewLivres.setVisibility(View.VISIBLE);
        }
    }

    private void ouvrirFormulaireAjout() {
        Intent intent = new Intent(MainActivity.this, AddEditActivity.class);
        intent.putExtra(AddEditActivity.EXTRA_MODE, AddEditActivity.MODE_ADD);
        addEditLauncher.launch(intent);
    }

    private void ouvrirFormulaireModification(Livre livre) {
        Intent intent = new Intent(MainActivity.this, AddEditActivity.class);
        intent.putExtra(AddEditActivity.EXTRA_MODE, AddEditActivity.MODE_EDIT);
        intent.putExtra(AddEditActivity.EXTRA_LIVRE, livre);
        addEditLauncher.launch(intent);
    }

    private void ouvrirDetailLivre(Livre livre) {
        Intent intent = new Intent(MainActivity.this, DetailActivity.class);
        intent.putExtra("livre", livre);
        startActivity(intent);
    }

    private void afficherOptionsLivre(Livre livre, int position) {
        new AlertDialog.Builder(this)
                .setTitle("📖 " + livre.getTitre())
                .setMessage("Que souhaitez-vous faire ?")
                .setPositiveButton("✏️ Modifier", (dialog, which) -> ouvrirFormulaireModification(livre))
                .setNegativeButton("🗑️ Supprimer", (dialog, which) -> confirmerSuppression(livre, position))
                .setNeutralButton("Annuler", null)
                .show();
    }

    private void confirmerSuppression(Livre livre, int position) {
        new AlertDialog.Builder(this)
                .setTitle("🗑️ Supprimer")
                .setMessage("Voulez-vous vraiment supprimer \"" + livre.getTitre() + "\" ?")
                .setPositiveButton("Supprimer", (dialog, which) -> supprimerLivreDansRoom(livre, position))
                .setNegativeButton("Annuler", null)
                .show();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (executorService != null) {
            executorService.shutdown();
        }
    }
}