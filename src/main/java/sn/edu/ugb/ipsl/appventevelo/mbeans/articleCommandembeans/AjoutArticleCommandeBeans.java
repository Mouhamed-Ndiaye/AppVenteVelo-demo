package sn.edu.ugb.ipsl.appventevelo.mbeans.articleCommandembeans;

import jakarta.ejb.EJB;
import jakarta.faces.application.FacesMessage;
import jakarta.faces.context.FacesContext;
import jakarta.faces.view.ViewScoped;
import jakarta.inject.Named;
import sn.edu.ugb.ipsl.appventevelo.entities.*;
import sn.edu.ugb.ipsl.appventevelo.facades.ArticleCommandeFacade;
import sn.edu.ugb.ipsl.appventevelo.facades.CommandeFacade;
import sn.edu.ugb.ipsl.appventevelo.facades.ProduitFacade;

import java.io.Serializable;

@Named("ajoutArticleCommandeBeans")
@ViewScoped
public class AjoutArticleCommandeBeans implements Serializable {

    @EJB
    private ArticleCommandeFacade articleCommandeFacade;

    @EJB
    private CommandeFacade commandeFacade;

    @EJB
    private ProduitFacade produitFacade;

    private ArticleCommande articleCommande = new ArticleCommande();

    private Integer numeroCommande;

    private Integer produitId;

    public ArticleCommande getArticleCommande() {
        return articleCommande;
    }

    public void setArticleCommande(ArticleCommande articleCommande) {
        this.articleCommande = articleCommande;
    }

    public Integer getNumeroCommande() {
        return numeroCommande;
    }

    public void setNumeroCommande(Integer numeroCommande) {
        this.numeroCommande = numeroCommande;
    }

    public Integer getProduitId() {
        return produitId;
    }

    public void setProduitId(Integer produitId) {
        this.produitId = produitId;
    }

    public String SaveArticleCommande() {

        if (articleCommandeFacade.FindById(numeroCommande, articleCommande.getLigne()) != null) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Erreur!", "Cet article existe déjà."));
            return null;
        }

        try {

            Commande commande = commandeFacade.FindById(numeroCommande);
            if (commande == null) {
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Erreur!", "Cette commande n'existe pas."));
                return null;
            }

            Produit produit = produitFacade.FindById(produitId);
            if (produit == null) {
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Erreur!", "Ce produit n'existe pas."));
                return null;
            }

            articleCommande.setNumeroCommande(commande);
            articleCommande.setProduit(produit);
            articleCommandeFacade.SaveArticleCommande(articleCommande);
            articleCommande = new ArticleCommande();
            FacesMessage msg = new FacesMessage("Succès!", "L'ajout a été effectué avec succès.");
            FacesContext.getCurrentInstance().getExternalContext().getFlash().setKeepMessages(true);
            FacesContext.getCurrentInstance().addMessage("successMessages", msg);
        } catch (jakarta.ejb.EJBException e) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Erreur lors de l'enregistrement!", "Veuillez vérifier vos champs svp."));
            articleCommande = new ArticleCommande();
            return null;
        }

        return "liste-articleCommande.xhtml?faces-redirect=true";

    }


}
