package sn.edu.ugb.ipsl.appventevelo.mbeans.stockmbeans;

import jakarta.ejb.EJB;
import jakarta.faces.application.FacesMessage;
import jakarta.faces.context.FacesContext;
import jakarta.faces.view.ViewScoped;
import jakarta.inject.Named;
import sn.edu.ugb.ipsl.appventevelo.entities.Stock;
import sn.edu.ugb.ipsl.appventevelo.facades.StockFacade;

import java.io.Serializable;

@Named("suppressionStockBeans")
@ViewScoped
public class SuppressionStockBeans implements Serializable {

    @EJB
    private StockFacade stockFacade;

    private Integer produitId;

    private Integer magasinId;

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

    public String RemoveStock(){

        Stock stockExistant = stockFacade.FindById(magasinId, produitId);

        if (stockExistant == null) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Erreur!", "Le stock avec cet ID n'existe pas."));
            return null;
        }

        try {
            stockFacade.RemoveStock(magasinId, produitId);
            FacesMessage msg = new FacesMessage("Succès!", "La suppression de a été effectuée avec succès.");
            FacesContext.getCurrentInstance().getExternalContext().getFlash().setKeepMessages(true);
            FacesContext.getCurrentInstance().addMessage("successMessages", msg);
        } catch (Exception e){
            FacesContext.getCurrentInstance().addMessage("messages", new FacesMessage(FacesMessage.SEVERITY_ERROR, "Erreur lors de la suppression!", "Veuillez vérifier vos champs svp."));
            return null;
        }

        return "liste-stock.xhtml?faces-redirect=true";

    }
}
