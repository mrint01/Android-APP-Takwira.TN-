package com.example.takwiratn.models;

public class Booking {
    String stade_name,prix_total_order,nbr_total_personne,date_order,id_directeur,id_user,id_book;

    public Booking(){

    }
    public Booking(String stade_name, String prix_total_order, String nbr_total_personne, String date_order, String id_directeur, String id_user,String id_book) {
        this.stade_name = stade_name;
        this.prix_total_order = prix_total_order;
        this.nbr_total_personne = nbr_total_personne;
        this.date_order = date_order;
        this.id_directeur = id_directeur;
        this.id_user = id_user;
        this.id_book = id_book;

    }

    public String getStade_name() {
        return stade_name;
    }

    public void setStade_name(String stade_name) {
        this.stade_name = stade_name;
    }

    public String getPrix_total_order() {
        return prix_total_order;
    }

    public void setPrix_total_order(String prix_total_order) {
        this.prix_total_order = prix_total_order;
    }

    public String getNbr_total_personne() {
        return nbr_total_personne;
    }

    public void setNbr_total_personne(String nbr_total_personne) {
        this.nbr_total_personne = nbr_total_personne;
    }

    public String getDate_order() {
        return date_order;
    }

    public void setDate_order(String date_order) {
        this.date_order = date_order;
    }

    public String getId_directeur() {
        return id_directeur;
    }

    public void setId_directeur(String id_directeur) {
        this.id_directeur = id_directeur;
    }

    public String getId_user() {
        return id_user;
    }

    public void setId_user(String id_user) {
        this.id_user = id_user;
    }

    public String getId_book() {
        return id_book;
    }

    public void setId_book(String id_book) {
        this.id_book = id_book;
    }
}
