package sn.edu.ugb.ipsl.appventevelo.mbeans.employembeans;

import jakarta.ejb.EJB;
import jakarta.ejb.EJBException;
import jakarta.faces.application.FacesMessage;
import jakarta.faces.context.FacesContext;
import jakarta.faces.view.ViewScoped;
import jakarta.inject.Named;
import sn.edu.ugb.ipsl.appventevelo.entities.*;
import sn.edu.ugb.ipsl.appventevelo.facades.EmployeFacade;
import sn.edu.ugb.ipsl.appventevelo.facades.MagasinFacade;

import java.io.Serializable;
import java.util.List;

@Named("modificationEmployeBeans")
@ViewScoped
public class ModificationEmployeBeans implements Serializable {

    @EJB
    private EmployeFacade employeFacade;

    @EJB
    private MagasinFacade magasinFacade;

    private Integer employeId;

    private String nouveauNom;

    private String nouveauPrenom;

    private String nouveauTelephone;

    private String nouveauEmail;

    private short nouveauActif;

    private Integer nouveauMagasinId;

    private Integer nouveauManagerId;

    public Integer getEmployeId() {
        return employeId;
    }

    public void setEmployeId(Integer employeId) {
        this.employeId = employeId;
    }

    public String getNouveauNom() {
        return nouveauNom;
    }

    public void setNouveauNom(String nouveauNom) {
        this.nouveauNom = nouveauNom;
    }

    public String getNouveauPrenom() {
        return nouveauPrenom;
    }

    public void setNouveauPrenom(String nouveauPrenom) {
        this.nouveauPrenom = nouveauPrenom;
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

    public short getNouveauActif() {
        return nouveauActif;
    }

    public void setNouveauActif(short nouveauActif) {
        this.nouveauActif = nouveauActif;
    }

    public Integer getNouveauMagasinId() {
        return nouveauMagasinId;
    }

    public void setNouveauMagasinId(Integer nouveauMagasinId) {
        this.nouveauMagasinId = nouveauMagasinId;
    }

    public Integer getNouveauManagerId() {
        return nouveauManagerId;
    }

    public void setNouveauManagerId(Integer nouveauManagerId) {
        this.nouveauManagerId = nouveauManagerId;
    }

    public List<String> searchPrenom(String txt){
        return employeFacade.AutoCompletePrenom(txt);
    }

    public List<String> searchNom(String txt){
        return employeFacade.AutoCompleteNom(txt);
    }

    public List<String> searchEmail(String txt){
        return employeFacade.AutoCompleteEmail(txt);
    }

    public void chargerEmployeParId() {
        if (employeId != null) {
            Employe employeExistant = employeFacade.FindById(employeId);

            if (employeExistant != null) {
                nouveauNom = employeExistant.getNom();
                nouveauPrenom = employeExistant.getPrenom();
                nouveauTelephone = employeExistant.getTelephone();
                nouveauEmail = employeExistant.getEmail();
                nouveauActif = employeExistant.getActif();
                Magasin magasin = employeExistant.getMagasin();
                if (magasin != null) {
                    nouveauMagasinId = magasin.getId();
                }
                Employe manager = employeExistant.getManager();
                if (manager != null) {
                    nouveauManagerId = manager.getId();
                }
            } else {
                nouveauNom = null;
                nouveauPrenom = null;
                nouveauTelephone = null;
                nouveauEmail = null;
                nouveauActif = 0;
                nouveauMagasinId = null;
                nouveauManagerId = null;
            }
        } else {
            nouveauNom = null;
            nouveauPrenom = null;
            nouveauTelephone = null;
            nouveauEmail = null;
            nouveauActif = 0;
            nouveauMagasinId = null;
            nouveauManagerId = null;
        }
    }


    public String UpdateEmploye() {
        try {
            Employe employeExistant = employeFacade.FindById(employeId);


            if (employeExistant == null) {
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Erreur!", "L'employé avec cet ID n'existe pas"));
                return null;
            }

            String ancienTelephone = employeExistant.getTelephone();

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
                // L'utilisateur a modifié le téléphone, alors effectuez la validation existante
                if (employeFacade.findByTelephone(nouveauTelephone) != null){
                    FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Erreur!", "Ce téléphone existe déjà."));
                    return null;
                }
            }

            String ancienEmail = employeExistant.getEmail();

            if (!ancienEmail.equals(nouveauEmail)) {
                // L'utilisateur a modifié l'email, alors effectuez la validation existante
                if (employeFacade.findByEmail(nouveauEmail) != null){
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
                employeExistant.setTelephone(null); // Définir la valeur du téléphone à null dans l'objet employeExistant
            } else {
                employeExistant.setTelephone(nouveauTelephone); // Définir la nouvelle valeur du téléphone
            }

            Magasin magasin = magasinFacade.FindById(nouveauMagasinId);
            Employe manager = employeFacade.FindById(nouveauManagerId);

            if (magasin == null) {
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Erreur!", "Ce magasin n'existe pas."));
                return null;
            }

            if (nouveauManagerId == null) {
                employeExistant.setManager(null);
                employeFacade.UpdateEmploye(employeExistant);
                FacesMessage msg = new FacesMessage("Succès!", "La modification a été effectuée avec succès.");
                FacesContext.getCurrentInstance().getExternalContext().getFlash().setKeepMessages(true);
                FacesContext.getCurrentInstance().addMessage("successMessages", msg);
                return "liste-employe.xhtml?faces-redirect=true";
            }

            if (manager == null) {
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR,"Erreur!" , "Ce manager n'existe pas."));
                return null;
            }

            employeExistant.setNom(nouveauNom);
            employeExistant.setPrenom(nouveauPrenom);
            employeExistant.setEmail(nouveauEmail);
            employeExistant.setTelephone(nouveauTelephone);
            employeExistant.setActif(nouveauActif);
            employeExistant.setMagasin(magasin);
            employeExistant.setManager(manager);


            employeFacade.UpdateEmploye(employeExistant);
            FacesMessage msg = new FacesMessage("Succès!", "La modification a été effectuée avec succès.");
            FacesContext.getCurrentInstance().getExternalContext().getFlash().setKeepMessages(true);
            FacesContext.getCurrentInstance().addMessage("successMessages", msg);
        } catch (EJBException e) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Erreur lors de la modification!", "Vérifiez vos champs svp."));
            return null;
        }

        return "liste-employe.xhtml?faces-redirect=true";

    }

}
