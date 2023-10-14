package sn.edu.ugb.ipsl.appventevelo.dtoclasses;


import jakarta.xml.bind.annotation.XmlRootElement;

import java.math.BigDecimal;

@XmlRootElement(name = "produit")
public class NouveauProduitDTO {

    private Integer id;
    private String nom;
    private Integer marque;
    private Integer categorie;
    private short annee_model;

    private BigDecimal prix_depart;

    public NouveauProduitDTO() {
    }

    public NouveauProduitDTO(Integer id, String nom, Integer marque, Integer categorie, short annee_model, BigDecimal prix_depart) {
        this.id = id;
        this.nom = nom;
        this.marque = marque;
        this.categorie = categorie;
        this.annee_model = annee_model;
        this.prix_depart = prix_depart;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public Integer getMarque() {
        return marque;
    }

    public void setMarque(Integer marque) {
        this.marque = marque;
    }

    public Integer getCategorie() {
        return categorie;
    }

    public void setCategorie(Integer categorie) {
        this.categorie = categorie;
    }

    public short getAnnee_model() {
        return annee_model;
    }

    public void setAnnee_model(short annee_model) {
        this.annee_model = annee_model;
    }

    public BigDecimal getPrix_depart() {
        return prix_depart;
    }

    public void setPrix_depart(BigDecimal prix_depart) {
        this.prix_depart = prix_depart;
    }

}

