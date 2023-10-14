package sn.edu.ugb.ipsl.appventevelo.dtoclasses;

import jakarta.xml.bind.annotation.XmlRootElement;

import java.math.BigDecimal;

@XmlRootElement(name = "articleCommande")
public class NouvelArticleCommandeDTO {
    private Integer numeroCommande;
    private Integer ligne;
    private Integer produit;
    private Integer quantite;
    private BigDecimal prixDepart;
    private BigDecimal remise;

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

    public Integer getProduit() {
        return produit;
    }

    public void setProduit(Integer produit) {
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
