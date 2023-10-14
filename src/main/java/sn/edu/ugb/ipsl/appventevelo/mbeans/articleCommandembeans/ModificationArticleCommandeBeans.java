package sn.edu.ugb.ipsl.appventevelo.mbeans.articleCommandembeans;

import jakarta.ejb.EJB;
import jakarta.ejb.EJBException;
import jakarta.faces.application.FacesMessage;
import jakarta.faces.context.FacesContext;
import jakarta.faces.view.ViewScoped;
import jakarta.inject.Named;
import sn.edu.ugb.ipsl.appventevelo.entities.*;
import sn.edu.ugb.ipsl.appventevelo.facades.*;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;

@Named("modificationArticleCommandeBeans")
@ViewScoped
public class ModificationArticleCommandeBeans implements Serializable {

    @EJB
    private ArticleCommandeFacade articleCommandeFacade;

    @EJB
    private CommandeFacade commandeFacade;

    @EJB
    private ProduitFacade produitFacade;

    private Integer numeroCommande;

    private Integer ligne;

    private Integer produitId;

    private int quantite;

    private BigDecimal prixDepart;

    private BigDecimal remise;

    public Integer getNumeroCommande() {
        return numeroCommande;
    }

    public void setNumeroCommande(Integer numeroCommande) {
        this.numeroCommande = numeroCommande;
    }

    public Integer getLigne() {
        return ligne;
    }

    public void setLigne(Integer ligne) {
        this.ligne = ligne;
    }

    public Integer getProduitId() {
        return produitId;
    }

    public void setProduitId(Integer produitId) {
        this.produitId = produitId;
    }

    public int getQuantite() {
        return quantite;
    }

    public void setQuantite(int quantite) {
        this.quantite = quantite;
    }

    public BigDecimal getPrixDepart() {
        return prixDepart;
    }

    public void setPrixDepart(BigDecimal prixDepart) {
        this.prixDepart = prixDepart;
    }

    public BigDecimal getRemise() {
        return remise;
    }

    public void setRemise(BigDecimal remise) {
        this.remise = remise;
    }

    public List<BigDecimal> SearchPrixDepart(BigDecimal prixDepart) {
        return articleCommandeFacade.AutoCompletePrixDepart(prixDepart);
    }

    public List<BigDecimal> SearchRemise(BigDecimal remise) {
        return articleCommandeFacade.AutoCompletePrixDepart(remise);
    }

    public void chargerArticleCommandeParId() {
        if (numeroCommande != null && ligne != null) {
            ArticleCommande articleCommandeExistant = articleCommandeFacade.FindById(numeroCommande, ligne);
            if (articleCommandeExistant != null) {
                produitId = articleCommandeExistant.getProduit().getId();
                quantite = articleCommandeExistant.getQuantite();
                prixDepart = articleCommandeExistant.getPrix_depart();
                remise = articleCommandeExistant.getRemise();
            } else {
                produitId = null;
                quantite = 0;
                prixDepart = null;
                remise = null;
            }
        } else {
            produitId = null;
            quantite = 0;
            prixDepart = null;
            remise = null;
        }
    }

    public String UpdateArticleCommande() {

        if (numeroCommande == null) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Erreur!", "L'ID référant à la commande est obligatoire."));
            return null;
        }

        if (ligne == null) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Erreur!", "Le numéro de ligne est obligatoire."));
            return null;
        }

        try {

            ArticleCommande articleCommandeExistant = articleCommandeFacade.FindById(numeroCommande, ligne);

            if (articleCommandeExistant == null) {
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Erreur!", "L'article avec cet ID n'existe pas"));
                return null;
            }

            if (produitId == null) {
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Erreur!", "L'ID du produit est obligatoire."));
                return null;
            }

            Commande commande = commandeFacade.FindById(numeroCommande);
            Produit produit = produitFacade.FindById(produitId);

            if (numeroCommande == null) {
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Erreur!", "Cet article n'existe pas."));
                return null;
            }

            if (produit == null) {
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Erreur!", "Ce produit n'existe pas."));
                return null;

            }

            articleCommandeExistant.setNumeroCommande(commande);
            articleCommandeExistant.setProduit(produit);
            articleCommandeExistant.setLigne(ligne);

            articleCommandeExistant.setQuantite(quantite);
            articleCommandeExistant.setPrix_depart(prixDepart);
            articleCommandeExistant.setRemise(remise);


            articleCommandeFacade.UpdateArticleCommande(articleCommandeExistant);
            FacesMessage msg = new FacesMessage("Succès!", "La modification a été effectuée avec succès.");
            FacesContext.getCurrentInstance().getExternalContext().getFlash().setKeepMessages(true);
            FacesContext.getCurrentInstance().addMessage("successMessages", msg);
        } catch (EJBException e) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Erreur lors de la modification!", "Veuillez vérifier vos champs svp."));
            return null;
        }

        return "liste-articleCommande.xhtml?faces-redirect=true";

    }

}
