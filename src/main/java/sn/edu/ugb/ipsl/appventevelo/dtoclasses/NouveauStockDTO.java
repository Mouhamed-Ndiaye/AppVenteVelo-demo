package sn.edu.ugb.ipsl.appventevelo.dtoclasses;

import jakarta.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "stock")
public class NouveauStockDTO {
    private Integer magasin;
    private Integer produit;
    private int quantite;

    public NouveauStockDTO() {
    }

    public NouveauStockDTO(Integer magasin, Integer produit, int quantite) {
        this.magasin = magasin;
        this.produit = produit;
        this.quantite = quantite;
    }

    public Integer getMagasin() {
        return magasin;
    }

    public void setMagasin(Integer magasin) {
        this.magasin = magasin;
    }

    public Integer getProduit() {
        return produit;
    }

    public void setProduit(Integer produit) {
        this.produit = produit;
    }

    public int getQuantite() {
        return quantite;
    }

    public void setQuantite(int quantite) {
        this.quantite = quantite;
    }
}
