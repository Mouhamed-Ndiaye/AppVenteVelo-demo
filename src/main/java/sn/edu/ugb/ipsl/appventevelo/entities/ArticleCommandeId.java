package sn.edu.ugb.ipsl.appventevelo.entities;


import jakarta.xml.bind.annotation.XmlRootElement;

import java.io.Serializable;

@XmlRootElement(name = "articleCommande")
public class ArticleCommandeId implements Serializable {


    private int numeroCommande;


    private int ligne;

    public ArticleCommandeId() {

    }

    public ArticleCommandeId(int numeroCommande, int ligne) {
        this.numeroCommande = numeroCommande;
        this.ligne = ligne;
    }

    public int getNumeroCommande() {
        return numeroCommande;
    }

    public void setNumeroCommande(int numeroCommande) {
        this.numeroCommande = numeroCommande;
    }

    public int getLigne() {
        return ligne;
    }

    public void setLigne(int ligne) {
        this.ligne = ligne;
    }
}