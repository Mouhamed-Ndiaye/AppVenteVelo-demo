package sn.edu.ugb.ipsl.appventevelo.mbeans.commandembeans;

import jakarta.ejb.EJB;
import jakarta.faces.application.FacesMessage;
import jakarta.faces.context.FacesContext;
import jakarta.faces.view.ViewScoped;
import jakarta.inject.Named;
import sn.edu.ugb.ipsl.appventevelo.entities.Commande;
import sn.edu.ugb.ipsl.appventevelo.facades.CommandeFacade;

import java.io.Serializable;

@Named("suppressionCommandeBeans")
@ViewScoped
public class SuppressionCommandeBeans implements Serializable {

    @EJB
    private CommandeFacade commandeFacade;

    private Integer numero;

    public Integer getNumero() {
        return numero;
    }

    public void setNumero(Integer numero) {
        this.numero = numero;
    }

    public String RemoveCommande(){

        try {
            Commande commandeExistant = commandeFacade.FindById(numero);

            if (commandeExistant == null) {
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Erreur!", "La commande avec cet ID n'existe pas."));
                return null;
            }

            commandeFacade.RemoveCommande(numero);
            FacesMessage msg = new FacesMessage("Succès!", "La suppression a été effectuée avec succès.");
            FacesContext.getCurrentInstance().getExternalContext().getFlash().setKeepMessages(true);
            FacesContext.getCurrentInstance().addMessage("successMessages", msg);
        } catch (Exception e){
            FacesContext.getCurrentInstance().addMessage("messages", new FacesMessage(FacesMessage.SEVERITY_ERROR, "Erreur lors de la suppression!", "Veuillez vérifier vos champs svp."));
            return null;
        }

        return "liste-commande.xhtml?faces-redirect=true";

    }
}
