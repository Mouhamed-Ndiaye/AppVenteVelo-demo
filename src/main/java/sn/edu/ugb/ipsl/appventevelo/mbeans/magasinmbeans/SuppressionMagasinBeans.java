package sn.edu.ugb.ipsl.appventevelo.mbeans.magasinmbeans;

import jakarta.ejb.EJB;
import jakarta.faces.application.FacesMessage;
import jakarta.faces.context.FacesContext;
import jakarta.faces.view.ViewScoped;
import jakarta.inject.Named;
import sn.edu.ugb.ipsl.appventevelo.entities.Magasin;
import sn.edu.ugb.ipsl.appventevelo.facades.MagasinFacade;

import java.io.Serializable;

@Named("suppressionMagasinBeans")
@ViewScoped
public class SuppressionMagasinBeans implements Serializable {

    @EJB
    private MagasinFacade magasinFacade;

    private Integer magasinId;

    public Integer getMagasinId() {
        return magasinId;
    }

    public void setMagasinId(Integer magasinId) {
        this.magasinId = magasinId;
    }

    public String RemoveMagasin(){

        try {

            Magasin magasinExistant = magasinFacade.FindById(magasinId);

            if (magasinExistant == null) {
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Erreur!", "Le magasin avec cet ID n'existe pas."));
                return null;
            }

            magasinFacade.RemoveMagasin(magasinId);
            FacesMessage msg = new FacesMessage("Succès!", "La suppression a été effectuée avec succès.");
            FacesContext.getCurrentInstance().getExternalContext().getFlash().setKeepMessages(true);
            FacesContext.getCurrentInstance().addMessage("successMessages", msg);

        } catch (Exception e){
            FacesContext.getCurrentInstance().addMessage("messages", new FacesMessage(FacesMessage.SEVERITY_ERROR, "Erreur lors de la suppression!", "Veuillez vérifier vos champs svp."));
            return null;
        }

        return "liste-magasin.xhtml?faces-redirect=true";

    }
}
