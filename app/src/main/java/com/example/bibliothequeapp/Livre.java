package com.example.bibliothequeapp;

public class Livre {

    // Identifiant unique du livre
    private int id;

    // Titre du livre
    private String titre;

    // Nom de l'auteur
    private String auteur;

    // Code ISBN du livre
    private String isbn;

    // Indique si le livre est disponible ou non
    private boolean disponible;

    // Constructeur complet
    public Livre(int id, String titre, String auteur, String isbn, boolean disponible) {
        this.id = id;
        this.titre = titre;
        this.auteur = auteur;
        this.isbn = isbn;
        this.disponible = disponible;
    }

    // Getter pour l'id
    public int getId() {
        return id;
    }

    // Setter pour l'id
    public void setId(int id) {
        this.id = id;
    }

    // Getter pour le titre
    public String getTitre() {
        return titre;
    }

    // Setter pour le titre
    public void setTitre(String titre) {
        this.titre = titre;
    }

    // Getter pour l'auteur
    public String getAuteur() {
        return auteur;
    }

    // Setter pour l'auteur
    public void setAuteur(String auteur) {
        this.auteur = auteur;
    }

    // Getter pour l'isbn
    public String getIsbn() {
        return isbn;
    }

    // Setter pour l'isbn
    public void setIsbn(String isbn) {
        this.isbn = isbn;
    }

    // Getter pour la disponibilité
    public boolean isDisponible() {
        return disponible;
    }

    // Setter pour la disponibilité
    public void setDisponible(boolean disponible) {
        this.disponible = disponible;
    }
}