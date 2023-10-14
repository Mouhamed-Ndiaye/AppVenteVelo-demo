package sn.edu.ugb.ipsl.appventevelo.mbeans.articleCommandembeans;

import jakarta.ejb.EJB;
import jakarta.faces.application.FacesMessage;
import jakarta.faces.context.FacesContext;
import jakarta.faces.view.ViewScoped;
import jakarta.inject.Named;
import sn.edu.ugb.ipsl.appventevelo.entities.ArticleCommande;
import sn.edu.ugb.ipsl.appventevelo.facades.ArticleCommandeFacade;

import java.io.Serializable;

@Named("suppressionArticleCommandeBeans")
@ViewScoped
public class SuppressionArticleCommandeBeans implements Serializable {

    @EJB
    private ArticleCommandeFacade articleCommandeFacade;

    private Integer numeroCommande;

    private Integer ligne;

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

    public String RemoveArticleCommande(){

        ArticleCommande articleCommandeExistant = articleCommandeFacade.FindById(numeroCommande, ligne);

        if (articleCommandeExistant == null) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Erreur!", "L'article avec cet ID n'existe pas."));
            return null;
        }

        try {
            articleCommandeFacade.RemoveArticleCommande(numeroCommande, ligne);
            FacesMessage msg = new FacesMessage("Succès!", "La suppression a été effectuée avec succès.");
            FacesContext.getCurrentInstance().getExternalContext().getFlash().setKeepMessages(true);
            FacesContext.getCurrentInstance().addMessage("successMessages", msg);
        } catch (Exception e){
            FacesContext.getCurrentInstance().addMessage("messages", new FacesMessage(FacesMessage.SEVERITY_ERROR, "Erreur lors de la suppression!", "Veuillez vérifier vos champs svp."));
            return null;
        }

        return "liste-articleCommande.xhtml?faces-redirect=true";

    }
}
