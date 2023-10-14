package sn.edu.ugb.ipsl.appventevelo.mbeans.stockmbeans;

import jakarta.ejb.EJB;
import jakarta.ejb.EJBException;
import jakarta.faces.application.FacesMessage;
import jakarta.faces.context.FacesContext;
import jakarta.faces.view.ViewScoped;
import jakarta.inject.Named;
import sn.edu.ugb.ipsl.appventevelo.entities.Magasin;
import sn.edu.ugb.ipsl.appventevelo.entities.Produit;
import sn.edu.ugb.ipsl.appventevelo.entities.Stock;
import sn.edu.ugb.ipsl.appventevelo.facades.MagasinFacade;
import sn.edu.ugb.ipsl.appventevelo.facades.ProduitFacade;
import sn.edu.ugb.ipsl.appventevelo.facades.StockFacade;

import java.io.Serializable;

@Named("modificationStockBeans")
@ViewScoped
public class ModificationStockBeans implements Serializable {

    @EJB
    private StockFacade stockFacade;

    @EJB
    private MagasinFacade magasinFacade;

    @EJB
    private ProduitFacade produitFacade;

    private Integer magasinId;

    private Integer produitId;

   private int nouvelleQuantite;

    public Integer getMagasinId() {
        return magasinId;
    }

    public void setMagasinId(Integer magasinId) {
        this.magasinId = magasinId;
    }

    public Integer getProduitId() {
        return produitId;
    }

    public void setProduitId(Integer produitId) {
        this.produitId = produitId;
    }

    public int getNouvelleQuantite() {
        return nouvelleQuantite;
    }

    public void setNouvelleQuantite(int nouvelleQuantite) {
        this.nouvelleQuantite = nouvelleQuantite;
    }

    public void chargerStockParId() {
        if (produitId != null && magasinId != null) {
            Stock stockExistant = stockFacade.FindById(magasinId, produitId);
            if (stockExistant != null) {
                nouvelleQuantite = stockExistant.getQuantite();
            } else {
                nouvelleQuantite = 0;
            }
        } else {
            nouvelleQuantite = 0;
        }
    }

    public String UpdateStock() {

        if (magasinId == null){
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR,"Erreur!" , "L'ID référant au magasin est obligatoire."));
            return null;
        }

        if (produitId == null){
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR,"Erreur!" , "L'ID référant au produit est obligatoire."));
            return null;
        }

        Stock stockExistant = stockFacade.FindById(magasinId, produitId);

        if (stockExistant == null) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR,"Erreur!" , "Le stock avec cet ID n'existe pas"));
            return null;
        }

        try {
            Produit produit = produitFacade.FindById(produitId);
            Magasin magasin = magasinFacade.FindById(magasinId);
            if (produit == null) {
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR,"Erreur!" , "Ce produit n'existe pas."));
                return null;

            }

            if (magasin == null) {
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Erreur!", "Ce magasin n'existe pas."));
                return null;
            }

            stockExistant.setMagasin(magasin);
            stockExistant.setProduit(produit);
            stockExistant.setQuantite(nouvelleQuantite);

            stockFacade.UpdateStock(stockExistant);
            FacesMessage msg = new FacesMessage("Succès!", "La modification a été effectuée avec succès.");
            FacesContext.getCurrentInstance().getExternalContext().getFlash().setKeepMessages(true);
            FacesContext.getCurrentInstance().addMessage("successMessages", msg);
        } catch (EJBException e) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Erreur lors de la modification!", "Veuillez vérifier vos champs svp."));
            return null;
        }

        return "liste-stock.xhtml?faces-redirect=true";

    }

}
