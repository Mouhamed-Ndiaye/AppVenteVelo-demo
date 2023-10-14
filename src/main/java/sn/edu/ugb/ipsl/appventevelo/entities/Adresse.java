package sn.edu.ugb.ipsl.appventevelo.entities;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import jakarta.xml.bind.annotation.XmlRootElement;

import java.io.Serializable;

@Embeddable
@XmlRootElement(name = "adresse")
public class Adresse implements Serializable {

    private String adresse;

    @Column(length = 50)
    private String ville;

    @Column(length = 25)
    private String etat;

    @Column(length = 5)
    private String codeZip;

    public String getAdresse() {
        return adresse;
    }

    public void setAdresse(String adresse) {
        this.adresse = adresse;
    }

    public String getVille() {
        return ville;
    }

    public void setVille(String ville) {
        this.ville = ville;
    }

    public String getEtat() {
        return etat;
    }

    public void setEtat(String etat) {
        this.etat = etat;
    }
    public String getCodeZip() {
        return codeZip;
    }

    public void setCodeZip(String codeZip) {
        this.codeZip = codeZip;
    }
}
