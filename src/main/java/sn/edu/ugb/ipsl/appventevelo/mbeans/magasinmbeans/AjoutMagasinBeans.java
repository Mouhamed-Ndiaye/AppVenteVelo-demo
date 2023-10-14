package sn.edu.ugb.ipsl.appventevelo.mbeans.magasinmbeans;

import jakarta.ejb.EJB;
import jakarta.faces.application.FacesMessage;
import jakarta.faces.context.FacesContext;
import jakarta.faces.view.ViewScoped;
import jakarta.inject.Named;
import sn.edu.ugb.ipsl.appventevelo.entities.Adresse;
import sn.edu.ugb.ipsl.appventevelo.entities.Magasin;
import sn.edu.ugb.ipsl.appventevelo.facades.MagasinFacade;

import java.io.Serializable;

@Named("ajoutMagasinBeans")
@ViewScoped
public class AjoutMagasinBeans implements Serializable {

    @EJB
    private MagasinFacade magasinFacade;

    private Magasin magasin = new Magasin();

    private String adresse;
    private String ville;
    private String etat;
    private String codeZip;

    public Magasin getMagasin() {
        return magasin;
    }

    public void setMagasin(Magasin magasin) {
        this.magasin = magasin;
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


    public String SaveMagasin() {

        Adresse nouvelleAdresse = new Adresse();
        nouvelleAdresse.setAdresse(adresse);
        nouvelleAdresse.setVille(ville);
        nouvelleAdresse.setEtat(etat);
        nouvelleAdresse.setCodeZip(codeZip);

        magasin.setAdresse(nouvelleAdresse);

        try {

            String emailRegex = "^[a-zA-Z0-9_+&*-]+(?:\\.[a-zA-Z0-9_+&*-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,7}$";
            if (!magasin.getEmail().matches(emailRegex)) {
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Erreur!", "L'email n'est pas au format valide."));
                return null;
            }

            if (magasinFacade.findByTelephone(magasin.getTelephone()) != null){
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Erreur!", "Ce téléphone existe déjà."));
                return null;
            }

            if (magasinFacade.findByEmail(magasin.getEmail()) != null){
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Erreur!", "Cet email existe déjà."));
                return null;
            }

            magasinFacade.SaveMagasin(magasin);

            magasin = new Magasin();

            adresse = null;
            ville = null;
            etat = null;
            codeZip = null;
            FacesMessage msg = new FacesMessage("Succès!", "L'ajout a été effectué avec succès.");
            FacesContext.getCurrentInstance().getExternalContext().getFlash().setKeepMessages(true);
            FacesContext.getCurrentInstance().addMessage("successMessages", msg);


        } catch (jakarta.ejb.EJBException e) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Erreur lors de l'enregistrement!", "Veuillez vérifier vos champs svp."));
            magasin = new Magasin();

            adresse = null;
            ville = null;
            etat = null;
            codeZip = null;
            return null;
        }

        return "liste-magasin.xhtml?faces-redirect=true";
    }


}
