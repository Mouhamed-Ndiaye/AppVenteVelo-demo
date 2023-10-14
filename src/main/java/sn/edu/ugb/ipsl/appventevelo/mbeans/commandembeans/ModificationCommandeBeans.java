package sn.edu.ugb.ipsl.appventevelo.mbeans.commandembeans;

import jakarta.ejb.EJB;
import jakarta.ejb.EJBException;
import jakarta.faces.application.FacesMessage;
import jakarta.faces.context.FacesContext;
import jakarta.faces.view.ViewScoped;
import jakarta.inject.Named;
import sn.edu.ugb.ipsl.appventevelo.entities.Client;
import sn.edu.ugb.ipsl.appventevelo.entities.Commande;
import sn.edu.ugb.ipsl.appventevelo.entities.Employe;
import sn.edu.ugb.ipsl.appventevelo.entities.Magasin;
import sn.edu.ugb.ipsl.appventevelo.facades.ClientFacade;
import sn.edu.ugb.ipsl.appventevelo.facades.CommandeFacade;
import sn.edu.ugb.ipsl.appventevelo.facades.EmployeFacade;
import sn.edu.ugb.ipsl.appventevelo.facades.MagasinFacade;

import java.io.Serializable;
import java.util.Date;

@Named("modificationCommandeBeans")
@ViewScoped
public class ModificationCommandeBeans implements Serializable {

    @EJB
    private CommandeFacade commandeFacade;

    @EJB
    private ClientFacade clientFacade;

    @EJB
    private MagasinFacade magasinFacade;

    @EJB
    private EmployeFacade employeFacade;

    private Integer numero;

    private Integer clientId;

    private short statut;

    private Date dateCommande;

    private Date dateLivraisonVoulue;

    private Date dateLivraison;

    private Integer magasinId;

    private Integer vendeurId;

    public Integer getNumero() {
        return numero;
    }

    public void setNumero(Integer numero) {
        this.numero = numero;
    }

    public Integer getClientId() {
        return clientId;
    }

    public void setClientId(Integer clientId) {
        this.clientId = clientId;
    }

    public short getStatut() {
        return statut;
    }

    public void setStatut(short statut) {
        this.statut = statut;
    }

    public Date getDateCommande() {
        return dateCommande;
    }

    public void setDateCommande(Date dateCommande) {
        this.dateCommande = dateCommande;
    }

    public Date getDateLivraisonVoulue() {
        return dateLivraisonVoulue;
    }

    public void setDateLivraisonVoulue(Date dateLivraisonVoulue) {
        this.dateLivraisonVoulue = dateLivraisonVoulue;
    }

    public Date getDateLivraison() {
        return dateLivraison;
    }

    public void setDateLivraison(Date dateLivraison) {
        this.dateLivraison = dateLivraison;
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

    public void chargerCommandeParId() {
        if (numero != null) {
            Commande commandeExistante = commandeFacade.FindById(numero);
            if (commandeExistante != null) {
                clientId = commandeExistante.getClient().getId();
                statut = commandeExistante.getStatut();
                dateCommande = commandeExistante.getDateCommande();
                dateLivraisonVoulue = commandeExistante.getDateLivraisonVoulue();
                dateLivraison = commandeExistante.getDateLivraison();
                magasinId = commandeExistante.getMagasin().getId();
                vendeurId = commandeExistante.getVendeur().getId();
            } else {
                clientId = null;
                statut = 0;
                dateCommande = null;
                dateLivraisonVoulue = null;
                dateLivraison = null;
                magasinId = null;
                vendeurId = null;
            }
        } else {
            clientId = null;
            statut = 0;
            dateCommande = null;
            dateLivraisonVoulue = null;
            dateLivraison = null;
            magasinId = null;
            vendeurId = null;
        }
    }

    public String UpdateCommande() {
        try {
            Commande commandeExistante = commandeFacade.FindById(numero);

            if (commandeExistante == null) {
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Erreur!", "La commande avec cet ID n'existe pas"));
                return null;
            }

            Client client = clientFacade.FindById(clientId);

            if (client == null) {
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Erreur!", "Ce client n'existe pas."));
                return null;
            }

            Magasin magasin = magasinFacade.FindById(magasinId);

            if (magasin == null) {
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Erreur!", "Ce magasin n'existe pas."));
                return null;
            }

            Employe vendeur = employeFacade.FindById(vendeurId);

            if (vendeur == null) {
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR,"Erreur!" , "Ce vendeur n'existe pas."));
                return null;
            }

            commandeExistante.setClient(client);
            commandeExistante.setMagasin(magasin);
            commandeExistante.setVendeur(vendeur);

            commandeExistante.setStatut(statut);
            commandeExistante.setDateCommande(dateCommande);
            commandeExistante.setDateLivraisonVoulue(dateLivraisonVoulue);
            commandeExistante.setDateLivraison(dateLivraison);


            commandeFacade.UpdateCommande(commandeExistante);
            FacesMessage msg = new FacesMessage("Succès!", "La modification a été effectuée avec succès.");
            FacesContext.getCurrentInstance().getExternalContext().getFlash().setKeepMessages(true);
            FacesContext.getCurrentInstance().addMessage("successMessages", msg);
        } catch (EJBException e) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Erreur lors de la modification!", "Veuillez vérifier vos champs svp."));
            return null;
        }

        return "liste-commande.xhtml?faces-redirect=true";

    }

}
