package sn.edu.ugb.ipsl.appventevelo.dtoclasses;

import jakarta.xml.bind.annotation.XmlRootElement;
import sn.edu.ugb.ipsl.appventevelo.entities.Produit;

import java.math.BigDecimal;

@XmlRootElement(name = "articleCommande")
public class ArticleCommandeDTO {

    private Integer numeroCommande;
    private Integer ligne;
    private Produit produit;
    private Integer quantite;
    private BigDecimal prixDepart;
    private BigDecimal remise;

    public ArticleCommandeDTO() {
    }

    public ArticleCommandeDTO(Integer numeroCommande, Integer ligne, Produit produit, Integer quantite, BigDecimal prixDepart, BigDecimal remise) {
        this.numeroCommande = numeroCommande;
        this.ligne = ligne;
        this.produit = produit;
        this.quantite = quantite;
        this.prixDepart = prixDepart;
        this.remise = remise;
    }

    public Integer getNumeroCommande() {
        return numeroCommande;
    }

    public void setNumeroCommande(Integer numeroCommande) {
        this.numeroCommande = numeroCommande;
    }

    public Integer getLigne() {
        return ligne;
    }

    public void setLigne(Integer ligne) {
        this.ligne = ligne;
    }

    public Produit getProduit() {
        return produit;
    }

    public void setProduit(Produit produit) {
        this.produit = produit;
    }

    public Integer getQuantite() {
        return quantite;
    }

    public void setQuantite(Integer quantite) {
        this.quantite = quantite;
    }

    public BigDecimal getPrixDepart() {
        return prixDepart;
    }

    public void setPrixDepart(BigDecimal prixDepart) {
        this.prixDepart = prixDepart;
    }

    public BigDecimal getRemise() {
        return remise;
    }

    public void setRemise(BigDecimal remise) {
        this.remise = remise;
    }
}
