package sn.edu.ugb.ipsl.appventevelo.entities;

import jakarta.persistence.*;
import jakarta.xml.bind.annotation.XmlRootElement;

import java.util.List;

@Entity
@Table(name = "client")
@XmlRootElement(name = "client")
public class Client extends Personne {

    @Embedded
    private Adresse adresse;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "client")
    private List<Commande> commandes;


    public Client() {

    }

    public Client(String prenom, String nom, String email, String telephone, Adresse adresse) {
        super(prenom, nom, email, telephone);
        this.adresse = adresse;
    }

    public Adresse getAdresse() {
        return adresse;
    }

    public void setAdresse(Adresse adresse) {
        this.adresse = adresse;
    }

    /*public List<Commande> getCommandes() {
        return commandes;
    }

    public void setCommandes(List<Commande> commandes) {
        this.commandes = commandes;
    }*/
}