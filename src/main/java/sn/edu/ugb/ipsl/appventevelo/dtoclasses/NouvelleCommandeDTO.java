package sn.edu.ugb.ipsl.appventevelo.dtoclasses;

import jakarta.xml.bind.annotation.XmlRootElement;


@XmlRootElement(name = "commande")
public class NouvelleCommandeDTO {
    private Integer client;
    private short statut;
    private String dateCommande;
    private String dateLivraisonVoulue;
    private String dateLivraison;
    private Integer magasin;
    private Integer vendeur;

    public Integer getClient() {
        return client;
    }

    public void setClient(Integer client) {
        this.client = client;
    }

    public short getStatut() {
        return statut;
    }

    public void setStatut(short statut) {
        this.statut = statut;
    }

    public String getDateCommande() {
        return dateCommande;
    }

    public void setDateCommande(String dateCommande) {
        this.dateCommande = dateCommande;
    }

    public String getDateLivraisonVoulue() {
        return dateLivraisonVoulue;
    }

    public void setDateLivraisonVoulue(String dateLivraisonVoulue) {
        this.dateLivraisonVoulue = dateLivraisonVoulue;
    }

    public String getDateLivraison() {
        return dateLivraison;
    }

    public void setDateLivraison(String dateLivraison) {
        this.dateLivraison = dateLivraison;
    }

    public Integer getMagasin() {
        return magasin;
    }

    public void setMagasin(Integer magasin) {
        this.magasin = magasin;
    }

    public Integer getVendeur() {
        return vendeur;
    }

    public void setVendeur(Integer vendeur) {
        this.vendeur = vendeur;
    }
}
