package sn.edu.ugb.ipsl.appventevelo.dtoclasses;

import jakarta.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "employe")
public class MiseAJourEmployeDTO {

    private Integer id;
    private String prenom;
    private String nom;
    private String email;
    private String telephone;
    private short actif;
    private Integer magasin;
    private Integer manager;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getPrenom() {
        return prenom;
    }

    public void setPrenom(String prenom) {
        this.prenom = prenom;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getTelephone() {
        return telephone;
    }

    public void setTelephone(String telephone) {
        this.telephone = telephone;
    }

    public short getActif() {
        return actif;
    }

    public void setActif(short actif) {
        this.actif = actif;
    }

    public Integer getMagasin() {
        return magasin;
    }

    public void setMagasin(Integer magasin) {
        this.magasin = magasin;
    }

    public Integer getManager() {
        return manager;
    }

    public void setManager(Integer manager) {
        this.manager = manager;
    }
}

