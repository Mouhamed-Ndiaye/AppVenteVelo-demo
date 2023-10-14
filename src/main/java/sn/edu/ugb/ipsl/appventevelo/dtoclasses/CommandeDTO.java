package sn.edu.ugb.ipsl.appventevelo.dtoclasses;

import jakarta.xml.bind.annotation.XmlRootElement;
import sn.edu.ugb.ipsl.appventevelo.entities.Client;
import sn.edu.ugb.ipsl.appventevelo.entities.Magasin;

import java.util.Date;

@XmlRootElement(name = "commande")
public class CommandeDTO {
    private Integer numero;
    private short satut;
    private Date dateCommande;
    private Date dateLivraisonVoulue;
    private Date dateLivraison;
    private Magasin magasin;
    private EmployeeDTO vendeur;
    private Client client;

    // Constructeurs, getters et setters

    public CommandeDTO() {
    }

    public CommandeDTO(Integer numero, short satut, Date dateCommande, Date dateLivraisonVoulue, Date dateLivraison, Magasin magasin, EmployeeDTO vendeur, Client client) {
        this.numero = numero;
        this.satut = satut;
        this.dateCommande = dateCommande;
        this.dateLivraisonVoulue = dateLivraisonVoulue;
        this.dateLivraison = dateLivraison;
        this.magasin = magasin;
        this.vendeur = vendeur;
        this.client = client;
    }

    public Integer getNumero() {
        return numero;
    }

    public void setNumero(Integer numero) {
        this.numero = numero;
    }

    public short getSatut() {
        return satut;
    }

    public void setSatut(short satut) {
        this.satut = satut;
    }

    public Date getDateCommande() {
        return dateCommande;
    }

    public void setDateCommande(Date dateCommande) {
        this.dateCommande = dateCommande;
    }

    public Date getDateLivraisonVoulue() {
        return dateLivraisonVoulue;
    }

    public void setDateLivraisonVoulue(Date dateLivraisonVoulue) {
        this.dateLivraisonVoulue = dateLivraisonVoulue;
    }

    public Date getDateLivraison() {
        return dateLivraison;
    }

    public void setDateLivraison(Date dateLivraison) {
        this.dateLivraison = dateLivraison;
    }

    public Magasin getMagasin() {
        return magasin;
    }

    public void setMagasin(Magasin magasin) {
        this.magasin = magasin;
    }

    public EmployeeDTO getVendeur() {
        return vendeur;
    }

    public void setVendeur(EmployeeDTO vendeur) {
        this.vendeur = vendeur;
    }

    public Client getClient() {
        return client;
    }

    public void setClient(Client client) {
        this.client = client;
    }
}
