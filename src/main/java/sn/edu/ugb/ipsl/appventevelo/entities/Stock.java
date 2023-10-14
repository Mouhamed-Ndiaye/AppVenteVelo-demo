package sn.edu.ugb.ipsl.appventevelo.entities;

import jakarta.persistence.*;
import jakarta.xml.bind.annotation.XmlRootElement;

@Entity
@Table(name = "stock")
@IdClass(StockId.class)
@XmlRootElement(name = "stock")
public class Stock {

    @Id
    @ManyToOne
    private Produit produit;

    @Id
    @ManyToOne
    private Magasin magasin;


    private int quantite;

    public Stock() {

    }

    public Stock(Produit produitId, Magasin magasinId, int quantite) {
        this.produit = produitId;
        this.magasin = magasinId;
        this.quantite = quantite;
    }

    public Produit getProduit() {
        return produit;
    }

    public void setProduit(Produit produit) {
        this.produit = produit;
    }

    public Magasin getMagasin() {
        return magasin;
    }

    public void setMagasin(Magasin magasin) {
        this.magasin = magasin;
    }

    public int getQuantite() {
        return quantite;
    }

    public void setQuantite(int quantite) {
        this.quantite = quantite;
    }

}