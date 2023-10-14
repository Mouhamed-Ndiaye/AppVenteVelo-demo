package sn.edu.ugb.ipsl.appventevelo.entities;

import jakarta.persistence.*;
import jakarta.xml.bind.annotation.XmlRootElement;

import java.util.List;
import java.util.Objects;

@Entity
@Table(name = "marque")
@XmlRootElement(name = "marque")
public class Marque {

    @Id
    private Integer id;

    private String nom;

    @OneToMany(mappedBy = "marque", cascade = CascadeType.ALL)
    private List<Produit> produits;

    public Marque() {

    }

    public Marque(String nom) {
        this.nom = nom;
    }

    public Marque(Integer id) {
        this.id = id;
    }

    public Marque(Integer id, String nom) {
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
        return "Marque { " +
                " id = " + id +
                ", nom = '" + nom + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Marque marque = (Marque) o;
        return id.equals(marque.id)
                && nom.equals(marque.nom);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, nom);
    }
}