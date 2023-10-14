package sn.edu.ugb.ipsl.appventevelo.dtoclasses;

import jakarta.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "stock")
public class MiseAJourStockDTO {
    private Integer quantite;

    public MiseAJourStockDTO() {
    }

    public MiseAJourStockDTO(Integer quantite) {
        this.quantite = quantite;
    }

    public Integer getQuantite() {
        return quantite;
    }

    public void setQuantite(Integer quantite) {
        this.quantite = quantite;
    }
}
