package sn.edu.ugb.ipsl.appventevelo.mbeans.clientmbeans;

import jakarta.ejb.EJB;
import jakarta.ejb.EJBException;
import jakarta.faces.application.FacesMessage;
import jakarta.faces.context.FacesContext;
import jakarta.faces.view.ViewScoped;
import jakarta.inject.Named;
import sn.edu.ugb.ipsl.appventevelo.entities.Adresse;
import sn.edu.ugb.ipsl.appventevelo.entities.Client;
import sn.edu.ugb.ipsl.appventevelo.facades.ClientFacade;

import java.io.Serializable;

@Named("ajoutClientBeans")
@ViewScoped
public class AjoutClientBeans implements Serializable {

    @EJB
    private ClientFacade clientFacade;

    private Client client = new Client();

    private String adresse;
    private String ville;
    private String etat;
    private String codeZip;

    public Client getClient() {
        return client;
    }

    public void setClient(Client client) {
        this.client = client;
    }

    public String getAdresse() {
        return adresse;
    }

    public void setAdresse(String adresse) {
        this.adresse = adresse;
    }

    public String getVille() {
        return ville;
    }

    public void setVille(String ville) {
        this.ville = ville;
    }

    public String getEtat() {
        return etat;
    }


    public void setEtat(String etat) {

        if (etat.length() >= 26) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Erreur!", "Etat trop long"));
        }
        this.etat = etat;
    }

    public String getCodeZip() {
        return codeZip;
    }

    public void setCodeZip(String codeZip) {
        this.codeZip = codeZip;
    }


    public String SaveClient() {

        try {

            if (client.getTelephone().equals("")){
                client.setTelephone(null);
            }

            String emailRegex = "^[a-zA-Z0-9_+&*-]+(?:\\.[a-zA-Z0-9_+&*-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,7}$";
            if (!client.getEmail().matches(emailRegex)) {
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Erreur!", "L'email n'est pas au format valide."));
                return null;
            }

            if (clientFacade.findByEmail(client.getEmail()) != null){
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Erreur!", "Cet email existe déjà"));
                return null;
            }

            if (clientFacade.findByTelephone(client.getTelephone()) != null){
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Erreur!", "Ce téléphone existe déjà"));
                return null;
            }

            Adresse nouvelleAdresse = new Adresse();
            nouvelleAdresse.setAdresse(adresse);
            nouvelleAdresse.setVille(ville);
            nouvelleAdresse.setEtat(etat);
            nouvelleAdresse.setCodeZip(codeZip);

            client.setAdresse(nouvelleAdresse);

            try {

                clientFacade.SaveClient(client);

                client = new Client();

                adresse = null;
                ville = null;
                etat = null;
                codeZip = null;

                FacesMessage msg = new FacesMessage("Succès!", "L'ajout a été effectué avec succès.");
                FacesContext.getCurrentInstance().getExternalContext().getFlash().setKeepMessages(true);
                FacesContext.getCurrentInstance().addMessage("successMessages", msg);


            } catch (jakarta.ejb.EJBException e) {
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Erreur lors de l'enregistrement!",  "Veuillez vérifier vos champs svp."));
                client = new Client();

                adresse = null;
                ville = null;
                etat = null;
                codeZip = null;
                return null;
            }
        } catch (EJBException e) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Erreur lors de l'enregistrement!",  "Veuillez vérifier vos champs svp."));
            return null;
        }

        return "liste-client.xhtml?faces-redirect=true";
    }


}
