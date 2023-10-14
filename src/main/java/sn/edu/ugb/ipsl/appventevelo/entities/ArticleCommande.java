package sn.edu.ugb.ipsl.appventevelo.entities;

import jakarta.persistence.*;
import jakarta.xml.bind.annotation.XmlRootElement;

import java.math.BigDecimal;

@Entity
@Table(name = "article_commande")
@IdClass(ArticleCommandeId.class)
@XmlRootElement(name = "articleCommande")
public class ArticleCommande {

    @Id
    @ManyToOne
    private Commande numeroCommande;

    @Id
    private int ligne;

    @ManyToOne
    private Produit produit;

    private int quantite;

    @Column(length = 10)
    private BigDecimal prix_depart;

    @Column(length = 10)
    private BigDecimal Remise;



    public ArticleCommande() {

    }

    public Commande getNumeroCommande() {
        return numeroCommande;
    }

    public void setNumeroCommande(Commande numeroCommande) {
        this.numeroCommande = numeroCommande;
    }

    public int getLigne() {
        return ligne;
    }

    public void setLigne(int ligne) {
        this.ligne = ligne;
    }

    public Produit getProduit() {
        return produit;
    }

    public void setProduit(Produit produit) {
        this.produit = produit;
    }

    public int getQuantite() {
        return quantite;
    }

    public void setQuantite(int quantite) {
        this.quantite = quantite;
    }

    public BigDecimal getPrix_depart() {
        return prix_depart;
    }

    public void setPrix_depart(BigDecimal prix_depart) {
        this.prix_depart = prix_depart;
    }

    public BigDecimal getRemise() {
        return Remise;
    }

    public void setRemise(BigDecimal remise) {
        Remise = remise;
    }
}