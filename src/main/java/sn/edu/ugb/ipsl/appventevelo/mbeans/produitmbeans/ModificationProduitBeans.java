package sn.edu.ugb.ipsl.appventevelo.mbeans.produitmbeans;

import jakarta.ejb.EJB;
import jakarta.ejb.EJBException;
import jakarta.faces.application.FacesMessage;
import jakarta.faces.context.FacesContext;
import jakarta.faces.view.ViewScoped;
import jakarta.inject.Named;
import sn.edu.ugb.ipsl.appventevelo.entities.Categorie;
import sn.edu.ugb.ipsl.appventevelo.entities.Marque;
import sn.edu.ugb.ipsl.appventevelo.entities.Produit;
import sn.edu.ugb.ipsl.appventevelo.facades.CategorieFacade;
import sn.edu.ugb.ipsl.appventevelo.facades.MarqueFacade;
import sn.edu.ugb.ipsl.appventevelo.facades.ProduitFacade;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;

@Named("modificationProduitBeans")
@ViewScoped
public class ModificationProduitBeans implements Serializable {

    @EJB
    private ProduitFacade produitFacade;

    @EJB
    private MarqueFacade marqueFacade;

    @EJB
    private CategorieFacade categorieFacade;

    private Integer produitId;

    private String nouveauNom;

    private Integer nouvelleMarqueId;

    private Integer nouvelleCategorieId;

    private short nouvelleAnneModel;

    private BigDecimal nouveauPrixDepart;

    public Integer getProduitId() {
        return produitId;
    }

    public void setProduitId(Integer produitId) {
        this.produitId = produitId;
    }

    public String getNouveauNom() {
        return nouveauNom;
    }

    public void setNouveauNom(String nouveauNom) {
        this.nouveauNom = nouveauNom;
    }

    public Integer getNouvelleMarqueId() {
        return nouvelleMarqueId;
    }

    public void setNouvelleMarqueId(Integer nouvelleMarqueId) {
        this.nouvelleMarqueId = nouvelleMarqueId;
    }

    public Integer getNouvelleCategorieId() {
        return nouvelleCategorieId;
    }

    public void setNouvelleCategorieId(Integer nouvelleCategorieId) {
        this.nouvelleCategorieId = nouvelleCategorieId;
    }

    public short getNouvelleAnneModel() {
        return nouvelleAnneModel;
    }

    public void setNouvelleAnneModel(short nouvelleAnneModel) {
        this.nouvelleAnneModel = nouvelleAnneModel;
    }

    public BigDecimal getNouveauPrixDepart() {
        return nouveauPrixDepart;
    }

    public void setNouveauPrixDepart(BigDecimal nouveauPrixDepart) {
        this.nouveauPrixDepart = nouveauPrixDepart;
    }

    public List<String> SearchNom(String txt) {
        return produitFacade.AutoCompleteNom(txt);
    }

    public void chargerProduitParId() {
        if (produitId != null) {
            Produit produitExistant = produitFacade.FindById(produitId);
            if (produitExistant != null) {
                nouveauNom = produitExistant.getNom();
                nouvelleMarqueId = produitExistant.getMarque().getId();
                nouvelleCategorieId = produitExistant.getCategorie().getId();
                nouvelleAnneModel = produitExistant.getAnnee_model();
                nouveauPrixDepart = produitExistant.getPrix_depart();
            } else {
                nouveauNom = null;
                nouvelleMarqueId = null;
                nouvelleCategorieId = null;
                nouvelleAnneModel = 0;
                nouveauPrixDepart = null;
            }
        } else {
            nouveauNom = null;
            nouvelleMarqueId = null;
            nouvelleCategorieId = null;
            nouvelleAnneModel = 0;
            nouveauPrixDepart = null;
        }
    }

    public String UpdateProduit() {

        try {
            Produit produitExistant = produitFacade.FindById(produitId);

            if (produitExistant == null) {
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Erreur!", "Le produit avec cet ID n'existe pas."));
                return null;
            }

            if (nouveauNom == null){
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Erreur!", "Le nom est obligatoire."));
                return null;
            }

            if (nouvelleMarqueId == null){
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Erreur!", "L'id de la marque est obligatoire."));
                return null;
            }

            if (nouvelleCategorieId == null){
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Erreur!", "L'id de la catégorie est obligatoire."));
                return null;
            }


            Categorie categorie = categorieFacade.FindById(nouvelleCategorieId);
            Marque marque = marqueFacade.FindById(nouvelleMarqueId);
            if (categorie == null) {
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Erreur!", "Cette catégorie n'existe pas."));
                return null;

            }

            if (marque == null) {
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Erreur!", "Cette marque n'existe pas."));
                return null;
            }

            produitExistant.setMarque(marque);
            produitExistant.setCategorie(categorie);

            produitExistant.setNom(nouveauNom);
            produitExistant.setAnnee_model(nouvelleAnneModel);
            produitExistant.setPrix_depart(nouveauPrixDepart);


            produitFacade.UpdateProduit(produitExistant);
            FacesMessage msg = new FacesMessage("Succès!", "La modification a été effectuée avec succès.");
            FacesContext.getCurrentInstance().getExternalContext().getFlash().setKeepMessages(true);
            FacesContext.getCurrentInstance().addMessage("successMessages", msg);
        } catch (EJBException e) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Erreur lors de la modification!", "Veuillez vérifier vos champs svp."));
            return null;
        }

        return "liste-produit.xhtml?faces-redirect=true";

    }

}
