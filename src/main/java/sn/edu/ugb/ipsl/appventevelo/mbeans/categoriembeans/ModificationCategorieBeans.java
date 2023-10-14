package sn.edu.ugb.ipsl.appventevelo.mbeans.categoriembeans;

import jakarta.ejb.EJB;
import jakarta.ejb.EJBException;
import jakarta.faces.application.FacesMessage;
import jakarta.faces.context.FacesContext;
import jakarta.faces.view.ViewScoped;
import jakarta.inject.Named;
import sn.edu.ugb.ipsl.appventevelo.entities.Categorie;
import sn.edu.ugb.ipsl.appventevelo.facades.CategorieFacade;

import java.io.Serializable;
import java.util.List;

@Named("modificationCategorieBeans")
@ViewScoped
public class ModificationCategorieBeans implements Serializable {

    @EJB
    private CategorieFacade categorieFacade;

    private Integer categorieId;
    private String nouveauNom;

    public Integer getCategorieId() {
        return categorieId;
    }

    public void setCategorieId(Integer categorieId) {
        this.categorieId = categorieId;
    }

    public String getNouveauNom() {
        return nouveauNom;
    }

    public void setNouveauNom(String nouveauNom) {
        this.nouveauNom = nouveauNom;
    }

    public List<String> SearchNom(String txt){
        return categorieFacade.AutoCompleteNom(txt);
    }

    public void updateNomFromId() {
        if (categorieId != null) {
            Categorie categorieExistante = categorieFacade.FindById(categorieId);
            if (categorieExistante != null) {
                nouveauNom = categorieExistante.getNom();
            } else {
                nouveauNom = null; // Effacer le nom si l'ID ne correspond à aucune catégorie
            }
        } else {
            nouveauNom = null; // Effacer le nom si l'ID est vide
        }
    }


    public String UpdateCategorie() {

        Categorie categorieExistante = categorieFacade.FindById(categorieId);

        if (categorieExistante == null) {
            FacesContext.getCurrentInstance().addMessage("messages", new FacesMessage(FacesMessage.SEVERITY_ERROR, "Erreur!", "La catégorie avec cet ID n'existe pas."));
            return null;
        }

        try {
            categorieExistante.setNom(nouveauNom);
            categorieFacade.UpdateCategorie(categorieExistante);
            FacesMessage msg = new FacesMessage("Succès!", "La modification a été effectuée avec succès.");
            FacesContext.getCurrentInstance().getExternalContext().getFlash().setKeepMessages(true);
            FacesContext.getCurrentInstance().addMessage("successMessages", msg);
        } catch (EJBException e) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Erreur lors de la modification!", "Veuillez vérifier vos champs svp."));
            return null;
        }


        return "liste-categorie.xhtml?faces-redirect=true";

    }

}
