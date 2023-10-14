package sn.edu.ugb.ipsl.appventevelo.mbeans.marquembeans;

import jakarta.ejb.EJB;
import jakarta.faces.application.FacesMessage;
import jakarta.faces.context.FacesContext;
import jakarta.faces.view.ViewScoped;
import jakarta.inject.Named;
import sn.edu.ugb.ipsl.appventevelo.entities.Marque;
import sn.edu.ugb.ipsl.appventevelo.facades.MarqueFacade;

import java.io.Serializable;

@Named("ajoutMarqueBeans")
@ViewScoped
public class AjoutMarqueBeans implements Serializable {

    @EJB
    private MarqueFacade marqueFacade;


    private Marque marque = new Marque();


    public Marque getMarque() {
        return marque;
    }

    public void setMarque(Marque marque) {
        this.marque = marque;
    }

    /*@PostConstruct
    public void init() {
        System.out.println("####### Enregistrement de AjoutMarqueBean" + marque);
    }

    @PreDestroy
    public void destroy() {
        System.out.println("####### Suppression de AjoutMarqueBean" + marque);
    }*/

    public String SaveMarque() {

        if (marqueFacade.FindById(marque.getId()) != null){
            marque = new Marque();
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Erreur!", "Cette marque existe déjà."));
            return null;
        }

        try {
            marqueFacade.SaveMarque(marque);
            marque = new Marque();
            FacesMessage msg = new FacesMessage("Succès!", "L'ajout a été effectué avec succès.");
            FacesContext.getCurrentInstance().getExternalContext().getFlash().setKeepMessages(true);
            FacesContext.getCurrentInstance().addMessage("successMessages", msg);
        } catch (jakarta.ejb.EJBException e) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Erreur lors de l'enregistrement!", "Veuillez vérifier vos champs svp."));
            return null;
        }

        return "liste-marque.xhtml?faces-redirect=true";

    }


}
