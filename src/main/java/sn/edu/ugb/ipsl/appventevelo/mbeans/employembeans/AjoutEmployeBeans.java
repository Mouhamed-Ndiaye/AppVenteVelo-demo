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


@Named("ajoutEmployeBeans")
@ViewScoped
public class AjoutEmployeBeans implements Serializable {

    @EJB
    private EmployeFacade employeFacade;

    @EJB
    private MagasinFacade magasinFacade;

    private Employe employe = new Employe();

    private Integer magasinId;

    private Integer managerId;

    public Employe getEmploye() {
        return employe;
    }

    public void setEmploye(Employe employe) {
        this.employe = employe;
    }

    public Integer getMagasinId() {
        return magasinId;
    }

    public void setMagasinId(Integer magasinId) {
        this.magasinId = magasinId;
    }

    public Integer getManagerId() {
        return managerId;
    }

    public void setManagerId(Integer managerId) {
        this.managerId = managerId;
    }

    public String SaveEmploye() {

        try {

            if (employe.getTelephone().equals("")) {
                employe.setTelephone(null);
            }

            String emailRegex = "^[a-zA-Z0-9_+&*-]+(?:\\.[a-zA-Z0-9_+&*-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,7}$";
            if (!employe.getEmail().matches(emailRegex)) {
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Erreur!", "L'email n'est pas au format valide."));
                return null;
            }

            if (employeFacade.findByEmail(employe.getEmail()) != null) {
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Erreur!", "Cet email existe déjà"));
                return null;
            }

            if (employeFacade.findByTelephone(employe.getTelephone()) != null) {
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Erreur!", "Ce téléphone existe déjà"));
                return null;
            }

            Magasin magasin = magasinFacade.FindById(magasinId);
            if (magasin == null) {
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Erreur!", "Ce magasin n'existe pas."));
                return null;

            }


            employe.setMagasin(magasin);
            if (managerId == null) {
                employeFacade.SaveEmploye(employe);
                FacesMessage msg = new FacesMessage("Succès!", "L'ajout a été effectué avec succès.");
                FacesContext.getCurrentInstance().getExternalContext().getFlash().setKeepMessages(true);
                FacesContext.getCurrentInstance().addMessage("successMessages", msg);
                return "liste-employe.xhtml?faces-redirect=true";
            }


            Employe manager = employeFacade.FindById(managerId);
            if (manager == null) {
                employeFacade.SaveEmploye(employe);
                FacesMessage msg = new FacesMessage("Succès!", "L'ajout a été effectué avec succès.");
                FacesContext.getCurrentInstance().getExternalContext().getFlash().setKeepMessages(true);
                FacesContext.getCurrentInstance().addMessage("successMessages", msg);
                return "liste-employe.xhtml?faces-redirect=true";
            }
            employe.setManager(manager);


            employeFacade.SaveEmploye(employe);
            employe = new Employe();
            FacesMessage msg = new FacesMessage("Succès!", "L'ajout a été effectué avec succès.");
            FacesContext.getCurrentInstance().getExternalContext().getFlash().setKeepMessages(true);
            FacesContext.getCurrentInstance().addMessage("successMessages", msg);
        } catch (EJBException e) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Erreur lors de l'enregistrement!", "Vérifiez vos champs svp."));
            return null;
        }

        return "liste-employe.xhtml?faces-redirect=true";
    }

}
