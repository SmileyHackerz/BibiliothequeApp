# 📚 BibliothequeApp — Application Android de gestion de bibliothèque

Application Android développée dans le cadre de travaux pratiques universitaires (Labs 1 à 7).
Elle permet de gérer une bibliothèque personnelle de livres avec persistance locale des données.

---

## 🎯 Fonctionnalités

- Afficher la liste des livres enregistrés
- Ajouter un nouveau livre via un formulaire
- Modifier un livre existant (clic long ou bouton sur la fiche détail)
- Supprimer un livre avec confirmation
- Rechercher un livre par titre en temps réel
- Consulter la fiche détail d'un livre
- Persistance locale des données avec **Room (SQLite)** — les données sont conservées après fermeture de l'application
- Affichage d'un message quand la liste est vide
- Validation des champs du formulaire (titre, auteur, ISBN, année)

---

## 🛠️ Technologies utilisées

| Technologie | Rôle |
|---|---|
| Java | Langage principal |
| Android SDK | Framework mobile |
| Room (SQLite) | Persistance locale des données |
| RecyclerView | Affichage de la liste des livres |
| CardView | Design des cartes livres |
| Material Design | Composants UI (FAB, boutons, badges) |
| ExecutorService | Opérations Room en arrière-plan |
| ActivityResultLauncher | Navigation entre activités avec résultat |

---

## 🗂️ Structure du projet

```
com.example.bibliothequeapp/
│
├── Livre.java               # Entité Room — représente un livre (table SQLite)
├── LivreDao.java            # DAO — requêtes SQL (insert, update, delete, query)
├── AppDatabase.java         # Base de données Room — singleton thread-safe
├── LivreAdapter.java        # Adapter RecyclerView — affichage des livres
├── MainActivity.java        # Écran principal — liste des livres
├── AddEditActivity.java     # Formulaire ajout / modification
└── DetailActivity.java      # Fiche détail d'un livre
```

---

## 📱 Captures d'écran

| Liste des livres | Formulaire d'ajout | Fiche détail |
|---|---|---|
| Liste avec badges de disponibilité, icônes modifier/supprimer | Champs titre, auteur, ISBN, année, disponibilité | Informations complètes + bouton modifier |

---

## 🚀 Installation

1. Cloner le dépôt :
```bash
git clone https://github.com/SmileyHackerz/BibliothequeApp.git
```
2. Ouvrir le projet dans **Android Studio**
3. Cliquer sur **Sync Now** pour synchroniser les dépendances Gradle
4. Lancer l'application sur un émulateur ou un appareil physique (API 24 minimum)

---

## 📦 Dépendances principales

```toml
# gradle/libs.versions.toml
room = "2.8.4"
```

```groovy
// app/build.gradle
implementation(libs.room.runtime)
annotationProcessor(libs.room.compiler)
implementation(libs.recyclerview)
implementation(libs.cardview)
implementation(libs.material)
```

---

## 🗄️ Base de données

La base de données locale utilise **Room** par-dessus SQLite.

- Nom de la base : `bibliotheque_database`
- Version actuelle : `2`
- Table : `livres`

| Colonne | Type | Description |
|---|---|---|
| id | INTEGER (PK) | Identifiant auto-généré |
| titre | TEXT | Titre du livre |
| auteur | TEXT | Auteur du livre |
| isbn | TEXT | Numéro ISBN |
| disponible | BOOLEAN | Disponibilité du livre |
| anneePublication | INTEGER | Année de publication |

Une **migration** de la version 1 vers la version 2 est incluse pour l'ajout de la colonne `anneePublication` sans perte de données.

---

## 📋 Labs couverts

| Lab | Contenu |
|---|---|
| Labs 1-4 | Mise en place du projet, RecyclerView, CardView, DetailActivity |
| Lab 5 | Liste statique de livres, navigation |
| Lab 6 | Formulaire d'ajout et de modification, FloatingActionButton, validation |
| Lab 7 | Persistance Room, DAO, migration, recherche, suppression |

---

## 👨‍💻 Auteur

Projet réalisé dans le cadre du cours de développement Android.
