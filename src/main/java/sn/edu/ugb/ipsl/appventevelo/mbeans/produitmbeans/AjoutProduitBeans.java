package sn.edu.ugb.ipsl.appventevelo.mbeans.produitmbeans;

import jakarta.ejb.EJB;
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

@Named("ajoutProduitBeans")
@ViewScoped
public class AjoutProduitBeans implements Serializable {

    @EJB
    private ProduitFacade produitFacade;

    @EJB
    private MarqueFacade marqueFacade;

    @EJB
    private CategorieFacade categorieFacade;


    private Produit produit = new Produit();

    private Integer marqueId;

    private Integer categorieId;

    public Produit getProduit() {
        return produit;
    }

    public void setProduit(Produit produit) {
        this.produit = produit;
    }

    public Integer getMarqueId() {
        return marqueId;
    }

    public void setMarqueId(Integer marqueId) {
        this.marqueId = marqueId;
    }

    public Integer getCategorieId() {
        return categorieId;
    }

    public void setCategorieId(Integer categorieId) {
        this.categorieId = categorieId;
    }

    public String SaveProduit(){

        if (produitFacade.FindById(produit.getId()) != null) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR,"Erreur!" , "Le produit avec cet ID existe déjà."));
            return null;
        }

        Categorie categorie = categorieFacade.FindById(categorieId);
        if (categorie == null) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Erreur!", "Cette catégorie n'existe pas."));
            return null;

        }

        Marque marque = marqueFacade.FindById(marqueId);

        if (marque == null) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR,"Erreur!" , "Cette marque n'existe pas."));
            return null;
        }
        produit.setMarque(marque);
        produit.setCategorie(categorie);

        try {
            produitFacade.SaveProduit(produit);
            produit = new Produit();
            FacesMessage msg = new FacesMessage("Succès!", "L'ajout a été effectué avec succès.");
            FacesContext.getCurrentInstance().getExternalContext().getFlash().setKeepMessages(true);
            FacesContext.getCurrentInstance().addMessage("successMessages", msg);
        } catch (jakarta.ejb.EJBException e) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR,"Erreur!" , "Veuillez vérifier vos champs svp."));
            return null;
        }

        return "liste-produit.xhtml?faces-redirect=true";
    }


}
