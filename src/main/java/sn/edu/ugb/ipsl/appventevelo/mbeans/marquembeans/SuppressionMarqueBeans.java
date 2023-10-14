package sn.edu.ugb.ipsl.appventevelo.mbeans.marquembeans;

import jakarta.ejb.EJB;
import jakarta.faces.application.FacesMessage;
import jakarta.faces.context.FacesContext;
import jakarta.faces.view.ViewScoped;
import jakarta.inject.Named;
import sn.edu.ugb.ipsl.appventevelo.entities.Marque;
import sn.edu.ugb.ipsl.appventevelo.facades.MarqueFacade;

import java.io.Serializable;

@Named("suppressionMarqueBeans")
@ViewScoped
public class SuppressionMarqueBeans implements Serializable {

    @EJB
    private MarqueFacade marqueFacade;

    private Integer marqueId;

    public Integer getMarqueId() {
        return marqueId;
    }

    public void setMarqueId(Integer marqueId) {
        this.marqueId = marqueId;
    }

    public String RemoveMarque(){

        try {

            Marque marqueExistante = marqueFacade.FindById(marqueId);

            if (marqueExistante == null) {
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR,"Erreur!" , "La marque avec cet ID n'existe pas."));
                return null;
            }

            marqueFacade.RemoveMarque(marqueId);
            FacesMessage msg = new FacesMessage("Succès!", "La suppression a été effectuée avec succès.");
            FacesContext.getCurrentInstance().getExternalContext().getFlash().setKeepMessages(true);
            FacesContext.getCurrentInstance().addMessage("successMessages", msg);

        } catch (Exception e){
            FacesContext.getCurrentInstance().addMessage("messages", new FacesMessage(FacesMessage.SEVERITY_ERROR, "Erreur lors de la suppression!", "Veuillez vérifier vos champs svp."));
            return null;
        }

        return "liste-marque.xhtml?faces-redirect=true";

    }
}
