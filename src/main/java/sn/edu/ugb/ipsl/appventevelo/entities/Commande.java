package sn.edu.ugb.ipsl.appventevelo.entities;

import jakarta.persistence.*;
import jakarta.xml.bind.annotation.XmlRootElement;

import java.util.Date;
import java.util.List;

@Entity
@Table(name = "commande")
@XmlRootElement(name = "commande")
public class Commande {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer numero;

    private short statut;

    @Temporal(TemporalType.DATE)
    private Date dateCommande;

    @Temporal(TemporalType.DATE)
    private Date dateLivraisonVoulue;

    @Temporal(TemporalType.DATE)
    private Date dateLivraison;

    @ManyToOne
    private Magasin magasin;

    @ManyToOne
    private Employe vendeur;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "numeroCommande")
    private List<ArticleCommande> articleCommandes;

    @ManyToOne
    private Client client;

    public Commande() {

    }

    public Commande(Integer numero) {
        this.numero = numero;
    }

    public Commande(Integer numero, short statut, Date dateCommande, Date dateLivraisonVoulue, Date dateLivraison) {
        this.numero = numero;
        this.statut = statut;
        this.dateCommande = dateCommande;
        this.dateLivraisonVoulue = dateLivraisonVoulue;
        this.dateLivraison = dateLivraison;
    }

    public Integer getNumero() {
        return numero;
    }

    public void setNumero(Integer numero) {
        this.numero = numero;
    }

    public short getStatut() {
        return statut;
    }

    public void setStatut(short statut) {
        this.statut = statut;
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

    public Employe getVendeur() {
        return vendeur;
    }

    public void setVendeur(Employe vendeur) {
        this.vendeur = vendeur;
    }

//    public List<ArticleCommande> getArticleCommandes() {
//        return articleCommandes;
//    }
//
//    public void setArticleCommandes(List<ArticleCommande> articleCommandes) {
//        this.articleCommandes = articleCommandes;
//    }

    public Client getClient() {
        return client;
    }

    public void setClient(Client client) {
        this.client = client;
    }
}