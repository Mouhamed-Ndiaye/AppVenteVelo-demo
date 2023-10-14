package sn.edu.ugb.ipsl.appventevelo.mbeans.clientmbeans;

import jakarta.ejb.EJB;
import jakarta.faces.application.FacesMessage;
import jakarta.faces.context.FacesContext;
import jakarta.faces.view.ViewScoped;
import jakarta.inject.Named;
import sn.edu.ugb.ipsl.appventevelo.entities.Client;
import sn.edu.ugb.ipsl.appventevelo.facades.ClientFacade;

import java.io.Serializable;

@Named("suppressionClientBeans")
@ViewScoped
public class SuppressionClientBeans implements Serializable {

    @EJB
    private ClientFacade clientFacade;

    private Integer clientId;

    public Integer getClientId() {
        return clientId;
    }

    public void setClientId(Integer clientId) {
        this.clientId = clientId;
    }

    public String RemoveClient(){

        try {
            Client clientExistant = clientFacade.FindById(clientId);

            if (clientExistant == null) {
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR,"Erreur!" , "Le client avec cet ID n'existe pas."));
                return null;
            }

            clientFacade.RemoveClient(clientId);
            FacesMessage msg = new FacesMessage("Succès!", "La suppression a été effectuée avec succès.");
            FacesContext.getCurrentInstance().getExternalContext().getFlash().setKeepMessages(true);
            FacesContext.getCurrentInstance().addMessage("successMessages", msg);
        } catch (Exception e){
            FacesContext.getCurrentInstance().addMessage("messages", new FacesMessage(FacesMessage.SEVERITY_ERROR, "Erreur lors de la suppression!", "Veuillez vérifier vos champs svp."));
            return null;
        }

        return "liste-client.xhtml?faces-redirect=true";

    }
}
