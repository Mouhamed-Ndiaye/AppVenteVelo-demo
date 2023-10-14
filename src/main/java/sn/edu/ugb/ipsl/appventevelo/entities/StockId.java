package sn.edu.ugb.ipsl.appventevelo.entities;

import jakarta.xml.bind.annotation.XmlRootElement;

import java.io.Serializable;

@XmlRootElement(name = "stock")
public class StockId implements Serializable {

    private Integer produit;

    private Integer magasin;
    public StockId() {

    }

    public StockId(int produitId, int magasinId) {
        this.produit = produitId;
        this.magasin = magasinId;
    }

    public Integer getProduit() {
        return produit;
    }

    public void setProduit(Integer produit) {
        this.produit = produit;
    }

    public Integer getMagasin() {
        return magasin;
    }

    public void setMagasin(Integer magasin) {
        this.magasin = magasin;
    }
}
