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
import java.util.List;

@Named("modificationClientBeans")
@ViewScoped
public class ModificationClientBeans implements Serializable {

    @EJB
    private ClientFacade clientFacade;

    private Integer clientId;

    private String nouveauNom;

    private String nouveauPrenom;

    private String nouveauTelephone;

    private String nouveauEmail;

    private String nouvelleAdresseRue;

    private String nouvelleVille;

    private String nouveauEtat;

    private String nouveauCodeZip;

    public Integer getClientId() {
        return clientId;
    }

    public void setClientId(Integer clientId) {
        this.clientId = clientId;
    }

    public String getNouveauPrenom() {
        return nouveauPrenom;
    }

    public void setNouveauPrenom(String nouveauPrenom) {
        this.nouveauPrenom = nouveauPrenom;
    }

    public String getNouveauNom() {
        return nouveauNom;
    }

    public void setNouveauNom(String nouveauNom) {
        this.nouveauNom = nouveauNom;
    }

    public String getNouveauTelephone() {
        return nouveauTelephone;
    }

    public void setNouveauTelephone(String nouveauTelephone) {
        this.nouveauTelephone = nouveauTelephone;
    }

    public String getNouveauEmail() {
        return nouveauEmail;
    }

    public void setNouveauEmail(String nouveauEmail) {
        this.nouveauEmail = nouveauEmail;
    }

    public String getNouvelleAdresseRue() {
        return nouvelleAdresseRue;
    }

    public void setNouvelleAdresseRue(String nouvelleAdresseRue) {
        this.nouvelleAdresseRue = nouvelleAdresseRue;
    }

    public String getNouvelleVille() {
        return nouvelleVille;
    }

    public void setNouvelleVille(String nouvelleVille) {
        this.nouvelleVille = nouvelleVille;
    }

    public String getNouveauEtat() {

        return nouveauEtat;
    }

    public void setNouveauEtat(String nouveauEtat) {
        if (nouveauEtat != null && nouveauEtat.length() >= 26) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Erreur!", "Etat trop long"));
        }
        this.nouveauEtat = nouveauEtat;
    }

    public String getNouveauCodeZip() {
        return nouveauCodeZip;
    }

    public void setNouveauCodeZip(String nouveauCodeZip) {
        this.nouveauCodeZip = nouveauCodeZip;
    }

    public List<String> searchPrenom(String txt) {
        return clientFacade.AutoCompletePrenom(txt);
    }

    public List<String> searchNom(String txt) {
        return clientFacade.AutoCompleteNom(txt);
    }

    public List<String> searchEmail(String txt) {
        return clientFacade.AutoCompleteEmail(txt);
    }

    public void chargerClientParId() {
        if (clientId != null) {
            Client clientExistant = clientFacade.FindById(clientId);
            if (clientExistant != null) {
                nouveauNom = clientExistant.getNom();
                nouveauPrenom = clientExistant.getPrenom();
                nouveauTelephone = clientExistant.getTelephone();
                nouveauEmail = clientExistant.getEmail();
                nouvelleAdresseRue = clientExistant.getAdresse().getAdresse();
                nouvelleVille = clientExistant.getAdresse().getVille();
                nouveauEtat = clientExistant.getAdresse().getEtat();
                nouveauCodeZip = clientExistant.getAdresse().getCodeZip();
            } else {
                nouveauNom = null;
                nouveauPrenom = null;
                nouveauTelephone = null;
                nouveauEmail = null;
                nouvelleAdresseRue = null;
                nouvelleVille = null;
                nouveauEtat = null;
                nouveauCodeZip = null;
            }
        } else {
            nouveauNom = null;
            nouveauPrenom = null;
            nouveauTelephone = null;
            nouveauEmail = null;
            nouvelleAdresseRue = null;
            nouvelleVille = null;
            nouveauEtat = null;
            nouveauCodeZip = null;
        }
    }

    public List<String> searchAdresse(String txt) {
        return clientFacade.AutoCompleteAdresse(txt);
    }

    public List<String> searchVille(String txt) {
        return clientFacade.AutoCompleteVille(txt);
    }

    public List<String> searchEtat(String txt) {
        return clientFacade.AutoCompleteEtat(txt);
    }

    public List<String> searchCodeZip(String txt) {
        return clientFacade.AutoCompleteCodeZip(txt);
    }

    public String UpdateClient() {
        try {

            Client clientExistant = clientFacade.FindById(clientId);

            if (clientExistant == null) {
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Erreur!", "Le client avec cet ID n'existe pas"));
                return null;
            }

            String ancienTelephone = clientExistant.getTelephone();

            if (nouveauNom == null){
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Erreur!", "Le nom est obligatoire."));
                return null;
            }

            if (nouveauPrenom == null){
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Erreur!", "Le prénom est obligatoire."));
                return null;
            }

            if (nouveauEmail == null){
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Erreur!", "L'e-mail est obligatoire."));
                return null;
            }

            if (nouveauTelephone != null && !nouveauTelephone.equals(ancienTelephone)) {
                // L'utilisateur a modifié le téléphone, alors effectuer la validation existante
                if (clientFacade.findByTelephone(nouveauTelephone) != null){
                    FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Erreur!", "Ce téléphone existe déjà."));
                    return null;
                }
            }

            String ancienEmail = clientExistant.getEmail();

            if (!ancienEmail.equals(nouveauEmail)) {
                // L'utilisateur a modifié l'email, alors effectuer la validation existante
                if (clientFacade.findByEmail(nouveauEmail) != null){
                    FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Erreur!", "Cet email existe déjà."));
                    return null;
                }
            }

            String emailRegex = "^[a-zA-Z0-9_+&*-]+(?:\\.[a-zA-Z0-9_+&*-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,7}$";
            if (!nouveauEmail.matches(emailRegex)) {
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Erreur!", "L'email n'est pas au format valide."));
                return null;
            }

            if (nouveauTelephone == null || nouveauTelephone.equals("")) {
                nouveauTelephone = null;
                clientExistant.setTelephone(null); // Définir la valeur du téléphone à null dans l'objet employeExistant
            } else {
                clientExistant.setTelephone(nouveauTelephone); // Définir la nouvelle valeur du téléphone
            }

            clientExistant.setNom(nouveauNom);
            clientExistant.setPrenom(nouveauPrenom);
            clientExistant.setTelephone(nouveauTelephone);
            clientExistant.setEmail(nouveauEmail);

            Adresse nouvelleAdresse = clientExistant.getAdresse();

            nouvelleAdresse.setAdresse(nouvelleAdresseRue);
            nouvelleAdresse.setVille(nouvelleVille);
            nouvelleAdresse.setEtat(nouveauEtat);
            nouvelleAdresse.setCodeZip(nouveauCodeZip);

            clientExistant.setAdresse(nouvelleAdresse);

            clientFacade.UpdateClient(clientExistant);
            FacesMessage msg = new FacesMessage("Succès!", "La modification a été effectuée avec succès.");
            FacesContext.getCurrentInstance().getExternalContext().getFlash().setKeepMessages(true);
            FacesContext.getCurrentInstance().addMessage("successMessages", msg);
        } catch (EJBException e) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Erreur lors de la modification!", "Veuillez vérifier vos champs svp."));
            return null;
        }

        return "liste-client.xhtml?faces-redirect=true";

    }

}
