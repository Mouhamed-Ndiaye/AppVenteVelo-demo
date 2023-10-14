package sn.edu.ugb.ipsl.appventevelo.mbeans.employembeans;

import jakarta.ejb.EJB;
import jakarta.faces.application.FacesMessage;
import jakarta.faces.context.FacesContext;
import jakarta.faces.view.ViewScoped;
import jakarta.inject.Named;
import sn.edu.ugb.ipsl.appventevelo.entities.Employe;
import sn.edu.ugb.ipsl.appventevelo.facades.EmployeFacade;

import java.io.Serializable;

@Named("suppressionEmployeBeans")
@ViewScoped
public class SuppressionEmployeBeans implements Serializable {

    @EJB
    private EmployeFacade employeFacade;

    private Integer employeId;

    public Integer getEmployeId() {
        return employeId;
    }

    public void setEmployeId(Integer employeId) {
        this.employeId = employeId;
    }

    public String RemoveMagasin() {

        try {
            Employe employeExistant = employeFacade.FindById(employeId);

            if (employeExistant == null) {
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Erreur!", "L'employé avec cet ID n'existe pas."));
                return null;
            }

            employeFacade.RemoveEmploye(employeId);
            FacesMessage msg = new FacesMessage("Succès!", "La suppression a été effectuée avec succès.");
            FacesContext.getCurrentInstance().getExternalContext().getFlash().setKeepMessages(true);
            FacesContext.getCurrentInstance().addMessage("successMessages", msg);
        } catch (Exception e) {
            FacesContext.getCurrentInstance().addMessage("messages", new FacesMessage(FacesMessage.SEVERITY_ERROR, "Erreur lors de la suppression!", "Veuillez vérifier vos champs svp."));
            return null;
        }

        return "liste-employe.xhtml?faces-redirect=true";

    }
}
