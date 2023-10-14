package sn.edu.ugb.ipsl.appventevelo.entities;

import jakarta.persistence.*;
import jakarta.xml.bind.annotation.XmlRootElement;

import java.util.List;

@Entity
@Table(name = "employe")
@XmlRootElement(name = "employe")
public class Employe extends Personne {

    private short actif;

    @ManyToOne
    private Magasin magasin;


    @ManyToOne
    @JoinColumn(nullable = true)
    private Employe manager;

    @OneToMany(mappedBy = "manager")
    private List<Employe> employes;

    @OneToMany(mappedBy = "vendeur", cascade = CascadeType.ALL)
    private List<Commande> commandes;

    public Employe() {

    }

    public Employe(String prenom, String nom, String email, String telephone, short actif) {
        super(prenom, nom, email, telephone);
        this.actif = actif;
    }

    public Employe(short actif) {
        this.actif = actif;
    }

    public short getActif() {
        return actif;
    }

    public void setActif(short actif) {
        this.actif = actif;
    }

    public Magasin getMagasin() {
        return magasin;
    }

    public void setMagasin(Magasin magasin) {
        this.magasin = magasin;
    }

    public Employe getManager() {
        return manager;
    }

    public void setManager(Employe manager) {
        this.manager = manager;
    }

    public List<Employe> getEmployes() {
        return employes;
    }

    public void setEmployes(List<Employe> employes) {
        this.employes = employes;
    }

    public List<Commande> getCommandes() {
        return commandes;
    }

    public void setCommandes(List<Commande> commandes) {
        this.commandes = commandes;
    }

    @Override
    public String toString() {
        return "Employe { " +
                " Nom = " + getNom() +
                " Prenom = " + getPrenom() +
                " Email = " + getEmail() +
                " Telephone = " + getTelephone() +
                " actif =" + actif +
                " }";
    }
}