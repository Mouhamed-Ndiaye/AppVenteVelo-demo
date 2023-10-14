package sn.edu.ugb.ipsl.appventevelo.mbeans.magasinmbeans;

import jakarta.ejb.EJB;
import jakarta.ejb.EJBException;
import jakarta.faces.application.FacesMessage;
import jakarta.faces.context.FacesContext;
import jakarta.faces.view.ViewScoped;
import jakarta.inject.Named;
import sn.edu.ugb.ipsl.appventevelo.entities.Adresse;
import sn.edu.ugb.ipsl.appventevelo.entities.Magasin;
import sn.edu.ugb.ipsl.appventevelo.facades.MagasinFacade;

import java.io.Serializable;
import java.util.List;

@Named("modificationMagasinBeans")
@ViewScoped
public class ModificationMagasinBeans implements Serializable {

    @EJB
    private MagasinFacade magasinFacade;

    private Integer magasinId;

    private String nouveauNom;

    private String nouveauTelephone;

    private String nouveauEmail;

    private String nouvelleAdresseRue;

    private String nouvelleVille;

    private String nouveauEtat;

    private String nouveauCodeZip;

    public Integer getMagasinId() {
        return magasinId;
    }

    public void setMagasinId(Integer magasinId) {
        this.magasinId = magasinId;
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

    public List<String> searchNom(String txt){
        return magasinFacade.AutoCompleteNom(txt);
    }

    public void chargerMagasinParId() {
        if (magasinId != null) {
            Magasin magasinExistant = magasinFacade.FindById(magasinId);
            if (magasinExistant != null) {
                nouveauNom = magasinExistant.getNom();
                nouveauTelephone = magasinExistant.getTelephone();
                nouveauEmail = magasinExistant.getEmail();
                nouvelleAdresseRue = magasinExistant.getAdresse().getAdresse();
                nouvelleVille = magasinExistant.getAdresse().getVille();
                nouveauEtat = magasinExistant.getAdresse().getEtat();
                nouveauCodeZip = magasinExistant.getAdresse().getCodeZip();
            } else {
                nouveauNom = null;
                nouveauTelephone = null;
                nouveauEmail = null;
                nouvelleAdresseRue = null;
                nouvelleVille = null;
                nouveauEtat = null;
                nouveauCodeZip = null;
            }
        } else {
            nouveauNom = null;
            nouveauTelephone = null;
            nouveauEmail = null;
            nouvelleAdresseRue = null;
            nouvelleVille = null;
            nouveauEtat = null;
            nouveauCodeZip = null;
        }
    }

    public List<String> searchEmail(String txt){
        return magasinFacade.AutoCompleteEmail(txt);
    }


    public List<String> searchAdresse(String txt){
        return magasinFacade.AutoCompleteAdresse(txt);
    }

    public List<String> searchVille(String txt){
        return magasinFacade.AutoCompleteVille(txt);
    }

    public List<String> searchEtat(String txt){
        return magasinFacade.AutoCompleteEtat(txt);
    }

    public List<String> searchCodeZip(String txt){
        return magasinFacade.AutoCompleteCodeZip(txt);
    }

    public String UpdateMagasin() {

        Magasin magasinExistant = magasinFacade.FindById(magasinId);

        if (magasinExistant == null) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Erreur!", "Le magasin avec cet ID n'existe pas"));
            return null;
        }

        try {

            String ancienTelephone = magasinExistant.getTelephone();

            if (nouveauNom == null){
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Erreur!", "Le nom est obligatoire."));
                return null;
            }

            if (nouveauTelephone.equals("")){
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Erreur!", "Le téléphone est obligatoire."));
                return null;
            }

            if (nouveauEmail == null){
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Erreur!", "L'e-mail est obligatoire."));
                return null;
            }

            if (!nouveauTelephone.equals(ancienTelephone)) {
                // L'utilisateur a modifié le téléphone, alors effectuez la validation existante
                if (magasinFacade.findByTelephone(nouveauTelephone) != null){
                    FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Erreur!", "Ce téléphone existe déjà."));
                    return null;
                }
            }

            String ancienEmail = magasinExistant.getEmail();

            if (!ancienEmail.equals(nouveauEmail)) {
                // L'utilisateur a modifié l'email, alors effectuez la validation existante
                if (magasinFacade.findByEmail(nouveauEmail) != null){
                    FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Erreur!", "Cet email existe déjà."));
                    return null;
                }
            }

            String emailRegex = "^[a-zA-Z0-9_+&*-]+(?:\\.[a-zA-Z0-9_+&*-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,7}$";
            if (!nouveauEmail.matches(emailRegex)) {
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Erreur!", "L'email n'est pas au format valide."));
                return null;
            }

            Adresse nouvelleAdresse = magasinExistant.getAdresse();

            nouvelleAdresse.setAdresse(nouvelleAdresseRue);
            nouvelleAdresse.setVille(nouvelleVille);
            nouvelleAdresse.setEtat(nouveauEtat);
            nouvelleAdresse.setCodeZip(nouveauCodeZip);

            magasinExistant.setAdresse(nouvelleAdresse);

            magasinFacade.UpdateMagasin(magasinExistant);
            FacesMessage msg = new FacesMessage("Succès!", "La modification a été effectuée avec succès.");
            FacesContext.getCurrentInstance().getExternalContext().getFlash().setKeepMessages(true);
            FacesContext.getCurrentInstance().addMessage("successMessages", msg);
        } catch (EJBException e) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Erreur lors de la modification!", "Veuillez vérifier vos champs svp."));
            return null;
        }
        return "liste-magasin.xhtml?faces-redirect=true";

    }

}
