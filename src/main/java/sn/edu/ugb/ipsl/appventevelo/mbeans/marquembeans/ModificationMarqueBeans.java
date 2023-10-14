package sn.edu.ugb.ipsl.appventevelo.mbeans.marquembeans;

import jakarta.ejb.EJB;
import jakarta.ejb.EJBException;
import jakarta.faces.application.FacesMessage;
import jakarta.faces.context.FacesContext;
import jakarta.faces.view.ViewScoped;
import jakarta.inject.Named;
import sn.edu.ugb.ipsl.appventevelo.entities.Marque;
import sn.edu.ugb.ipsl.appventevelo.facades.MarqueFacade;

import java.io.Serializable;
import java.util.List;

@Named("modificationMarqueBeans")
@ViewScoped
public class ModificationMarqueBeans implements Serializable {

    @EJB
    private MarqueFacade marqueFacade;

    private Integer marqueId;
    private String nouveauNom;

    public Integer getMarqueId() {
        return marqueId;
    }

    public void setMarqueId(Integer marqueId) {
        this.marqueId = marqueId;
    }

    public String getNouveauNom() {
        return nouveauNom;
    }

    public void setNouveauNom(String nouveauNom) {
        this.nouveauNom = nouveauNom;
    }

    public List<String> SearchNom(String txt){
        return marqueFacade.AutoCompleteNom(txt);
    }

    public void updateNomFromId() {
        if (marqueId != null) {
            Marque marqueExistante = marqueFacade.FindById(marqueId);
            if (marqueExistante != null) {
                nouveauNom = marqueExistante.getNom();
            } else {
                nouveauNom = null; // Effacer le nom si l'ID ne correspond à aucune catégorie
            }
        } else {
            nouveauNom = null; // Effacer le nom si l'ID est vide
        }
    }

    public String UpdateMarque(){

        Marque marqueExistante = marqueFacade.FindById(marqueId);

        if (marqueExistante == null) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR,"Erreur!" , "La marque avec cet ID n'existe pas."));
            return null;
        }

        try {
            marqueExistante.setNom(nouveauNom);
            marqueFacade.UpdateMarque(marqueExistante);
            FacesMessage msg = new FacesMessage("Succès!", "La modification a été effectuée avec succès.");
            FacesContext.getCurrentInstance().getExternalContext().getFlash().setKeepMessages(true);
            FacesContext.getCurrentInstance().addMessage("successMessages", msg);
        } catch (EJBException e) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Erreur lors de la modification!", "Veuillez vérifier vos champs svp."));
            return null;
        }

        return "liste-marque.xhtml?faces-redirect=true";

    }

}
