package sn.edu.ugb.ipsl.appventevelo.entities;

import jakarta.persistence.*;
import jakarta.xml.bind.annotation.XmlRootElement;

import java.math.BigDecimal;
import java.util.List;
import java.util.Objects;

@Entity
@Table(name = "produit")
@XmlRootElement(name = "produit")
public class Produit {

    @Id
    @Column(name = "id", nullable = false)
    private Integer id;

    private String nom;

    private short annee_model;

    @Column(length = 10)
    private BigDecimal prix_depart;

    @ManyToOne(optional = false)
    private Categorie categorie;

    @ManyToOne
    private Marque marque;

    @OneToMany(mappedBy = "produit", cascade = CascadeType.ALL)
    private List<Stock> stocks;

    @OneToMany(mappedBy = "produit", cascade = CascadeType.ALL)
    private List<ArticleCommande> articleCommandes;

    public Produit() {

    }

    public Produit(Integer id, String nom, short annee_model, BigDecimal prix_depart, Categorie categorie, Marque marque) {
        this.id = id;
        this.nom = nom;
        this.annee_model = annee_model;
        this.prix_depart = prix_depart;
        this.categorie = categorie;
        this.marque = marque;
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

    public Categorie getCategorie() {
        return categorie;
    }

    public void setCategorie(Categorie categorie) {
        this.categorie = categorie;
    }

    public Marque getMarque() {
        return marque;
    }

    public void setMarque(Marque marque) {
        this.marque = marque;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Produit produit = (Produit) o;
        return annee_model == produit.annee_model
                && Objects.equals(id, produit.id)
                && Objects.equals(nom, produit.nom)
                && Objects.equals(prix_depart, produit.prix_depart)
                && Objects.equals(categorie, produit.categorie)
                && Objects.equals(marque, produit.marque);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, nom, annee_model, prix_depart, categorie, marque, stocks, articleCommandes);
    }
}