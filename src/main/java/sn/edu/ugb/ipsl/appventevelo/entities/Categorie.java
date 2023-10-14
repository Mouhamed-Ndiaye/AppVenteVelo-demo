package sn.edu.ugb.ipsl.appventevelo.entities;

import jakarta.persistence.*;
import jakarta.xml.bind.annotation.XmlRootElement;

import java.util.List;
import java.util.Objects;

@Entity
@Table(name = "categorie")
@XmlRootElement(name = "categorie")
public class Categorie {

    @Id
    private Integer id;

    @Column(nullable = false)
    private String nom;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "categorie")
    private List<Produit> produits;

    public Categorie(){

    }

    public Categorie(Integer id) {
        this.id = id;
    }

    public Categorie(Integer id, String nom) {
        this.id = id;
        this.nom = nom;
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

    /*public List<Produit> getEmployes() {
        return produits;
    }

    public void setProduits(List<Produit> produits) {
        this.produits = produits;
    }*/

    @Override
    public String toString() {
        return "Categorie { " +
                " id = " + id +
                ", nom = '" + nom + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Categorie categorie = (Categorie) o;
        return id == categorie.id
                && nom == categorie.nom;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, nom);
    }
}