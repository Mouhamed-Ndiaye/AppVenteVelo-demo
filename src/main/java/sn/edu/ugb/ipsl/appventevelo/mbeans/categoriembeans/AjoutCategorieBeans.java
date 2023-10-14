package sn.edu.ugb.ipsl.appventevelo.mbeans.categoriembeans;


import jakarta.ejb.EJB;
import jakarta.faces.application.FacesMessage;
import jakarta.faces.context.FacesContext;
import jakarta.faces.view.ViewScoped;
import jakarta.inject.Named;
import sn.edu.ugb.ipsl.appventevelo.entities.Categorie;
import sn.edu.ugb.ipsl.appventevelo.facades.CategorieFacade;

import java.io.Serializable;

@Named("ajoutCategorieBeans")
@ViewScoped
public class AjoutCategorieBeans implements Serializable {

    @EJB
    private CategorieFacade categorieFacade;


    private Categorie categorie = new Categorie();


    public Categorie getCategorie() {
        return categorie;
    }

    public void setCategorie(Categorie categorie) {
        this.categorie = categorie;
    }

    public String SaveCategorie(){

        if (categorieFacade.FindById(categorie.getId()) != null) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Erreur!", "Cette catégorie existe déjà."));
            return null;
        }

        try {
            categorieFacade.SaveCategorie(categorie);
            categorie = new Categorie();
            FacesMessage msg = new FacesMessage("Succès!", "L'ajout a été effectué avec succès.");
            FacesContext.getCurrentInstance().getExternalContext().getFlash().setKeepMessages(true);
            FacesContext.getCurrentInstance().addMessage("successMessages", msg);
        } catch (jakarta.ejb.EJBException e) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Erreur lors de l'enregistrement", "Veuillez vérifier vos champs svp."));
            categorie = new Categorie();
            return null;
        }

        return "liste-categorie.xhtml?faces-redirect=true";
    }


}
