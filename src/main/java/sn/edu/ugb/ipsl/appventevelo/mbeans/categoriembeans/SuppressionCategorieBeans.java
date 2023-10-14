package sn.edu.ugb.ipsl.appventevelo.mbeans.categoriembeans;

import jakarta.ejb.EJB;
import jakarta.faces.application.FacesMessage;
import jakarta.faces.context.FacesContext;
import jakarta.faces.view.ViewScoped;
import jakarta.inject.Named;
import sn.edu.ugb.ipsl.appventevelo.entities.Categorie;
import sn.edu.ugb.ipsl.appventevelo.facades.CategorieFacade;

import java.io.Serializable;

@Named("suppressionCategorieBeans")
@ViewScoped
public class SuppressionCategorieBeans implements Serializable {

    @EJB
    private CategorieFacade categorieFacade;

    private Integer categorieId;

    public Integer getCategorieId() {
        return categorieId;
    }

    public void setCategorieId(Integer categorieId) {
        this.categorieId = categorieId;
    }

    public String RemoveCategorie(){

        Categorie categorieExistante = categorieFacade.FindById(categorieId);

        if (categorieExistante == null) {
            FacesContext.getCurrentInstance().addMessage("messages", new FacesMessage(FacesMessage.SEVERITY_ERROR, "Erreur!", "La catégorie avec cet ID n'existe pas."));
            return null;
        }

        try {

            categorieFacade.RemoveCategorie(categorieId);
            FacesMessage msg = new FacesMessage("Succès!", "La suppression a été effectuée avec succès.");
            FacesContext.getCurrentInstance().getExternalContext().getFlash().setKeepMessages(true);
            FacesContext.getCurrentInstance().addMessage("successMessages", msg);
        } catch (Exception e){
            FacesContext.getCurrentInstance().addMessage("messages", new FacesMessage(FacesMessage.SEVERITY_ERROR, "Erreur lors de la suppression!", "Veuillez vérifier vos champs svp."));
            return null;
        }

        return "liste-categorie.xhtml?faces-redirect=true";

    }
}
