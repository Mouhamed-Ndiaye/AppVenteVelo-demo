package sn.edu.ugb.ipsl.appventevelo.mbeans.commandembeans;

import jakarta.ejb.EJB;
import jakarta.ejb.EJBException;
import jakarta.faces.application.FacesMessage;
import jakarta.faces.context.FacesContext;
import jakarta.faces.view.ViewScoped;
import jakarta.inject.Named;
import sn.edu.ugb.ipsl.appventevelo.entities.*;
import sn.edu.ugb.ipsl.appventevelo.facades.*;

import java.io.Serializable;

@Named("ajoutCommandeBeans")
@ViewScoped
public class AjoutCommandeBeans implements Serializable {

    @EJB
    private CommandeFacade commandeFacade;

    @EJB
    private ClientFacade clientFacade;

    @EJB
    private EmployeFacade employeFacade;

    @EJB
    private MagasinFacade magasinFacade;


    private Commande commande = new Commande();

    private Integer clientId;

    private Integer magasinId;

    private Integer vendeurId;

    public Commande getCommande() {
        return commande;
    }

    public void setCommande(Commande commande) {
        this.commande = commande;
    }

    public Integer getClientId() {
        return clientId;
    }

    public void setClientId(Integer clientId) {
        this.clientId = clientId;
    }

    public Integer getMagasinId() {
        return magasinId;
    }

    public void setMagasinId(Integer magasinId) {
        this.magasinId = magasinId;
    }

    public Integer getVendeurId() {
        return vendeurId;
    }

    public void setVendeurId(Integer vendeurId) {
        this.vendeurId = vendeurId;
    }

    public String SaveCommande() {

        try {
            Client client = clientFacade.FindById(clientId);
            if (client == null) {
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR,"Erreur!" , "Ce client n'existe pas."));
                return null;

            }

            Magasin magasin = magasinFacade.FindById(magasinId);

            if (magasin == null) {
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Erreur!", "Cet magasin n'existe pas."));
                return null;
            }

            Employe vendeur = employeFacade.FindById(vendeurId);

            if (vendeur == null) {
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR,"Erreur!" , "Ce vendeur n'existe pas."));
                return null;
            }

            commande.setClient(client);
            commande.setMagasin(magasin);
            commande.setVendeur(vendeur);

            commandeFacade.SaveCommande(commande);
            commande = new Commande();
            FacesMessage msg = new FacesMessage("Succès!", "L'ajout a été effectué avec succès.");
            FacesContext.getCurrentInstance().getExternalContext().getFlash().setKeepMessages(true);
            FacesContext.getCurrentInstance().addMessage("successMessages", msg);
        } catch (EJBException e) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Erreur lors de l'enregistrement!", "Veuillez vérifier vos champs svp."));
            commande = new Commande();
            return null;
        }

        return "liste-commande.xhtml?faces-redirect=true";
    }


}