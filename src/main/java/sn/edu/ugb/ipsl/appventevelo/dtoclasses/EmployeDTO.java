package sn.edu.ugb.ipsl.appventevelo.dtoclasses;

import jakarta.xml.bind.annotation.XmlRootElement;
import sn.edu.ugb.ipsl.appventevelo.entities.Magasin;

@XmlRootElement(name = "employe")
public class EmployeDTO {
    private Integer id;
    private String prenom;
    private String nom;
    private String email;
    private String telephone;
    private short actif;
    private Magasin magasin;

    private EmployeeDTO manager;

    public EmployeDTO() {
    }

    public EmployeDTO(Integer id, String prenom, String nom, String email, String telephone, short actif, Magasin magasin, EmployeeDTO manager) {
        this.id = id;
        this.prenom = prenom;
        this.nom = nom;
        this.email = email;
        this.telephone = telephone;
        this.actif = actif;
        this.magasin = magasin;
        this.manager = manager;
    }

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

    public Magasin getMagasin() {
        return magasin;
    }

    public void setMagasin(Magasin magasin) {
        this.magasin = magasin;
    }

    public EmployeeDTO getManager() {
        return manager;
    }

    public void setManager (EmployeeDTO manager) {
        this.manager = manager;
    }
}
