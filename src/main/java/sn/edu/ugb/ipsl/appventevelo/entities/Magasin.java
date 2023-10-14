package sn.edu.ugb.ipsl.appventevelo.entities;

import jakarta.persistence.*;
import jakarta.xml.bind.annotation.XmlRootElement;

import java.util.List;

@Entity
@Table(name = "magasin")
@XmlRootElement(name = "magasin")
public class Magasin {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String nom;

    @Column(length = 25, unique = true, nullable = false)
    private String telephone;

    @Column(nullable = false, unique = true)
    private String email;

    @Embedded
    private Adresse adresse;

    @OneToMany(mappedBy = "magasin", cascade = CascadeType.ALL)
    private List<Stock> stocks;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "magasin")
    private List<Commande> commandes;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "magasin")
    private List<Employe> employes;

    public Magasin(){

    }

    public Magasin(Integer id, String nom, String telephone, String email, Adresse adresse) {
        this.id = id;
        this.nom = nom;
        this.telephone = telephone;
        this.email = email;
        this.adresse = adresse;
    }

    public Magasin(Integer id) {
        this.id = id;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getTelephone() {
        return telephone;
    }

    public void setTelephone(String telephone) {
        this.telephone = telephone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
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
    }

    public List<Employe> getEmployes() {
        return employes;
    }

    public void setEmployes(List<Employe> employes) {
        this.employes = employes;
    }

    public List<Stock> getStocks() {
        return stocks;
    }

    public void setStocks(List<Stock> stocks) {
        this.stocks = stocks;
    }*/

    @Override
    public String toString() {
        return " Magasin : { " +
                "id=" + id +
                ", nom='" + nom + '\'' +
                ", telephone='" + telephone + '\'' +
                ", email='" + email + '\'' +
                ", adresse = " + adresse +
                '}';
    }
}