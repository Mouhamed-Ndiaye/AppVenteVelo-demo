package sn.edu.ugb.ipsl.appventevelo.mbeans.stockmbeans;

import jakarta.ejb.EJB;
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

@Named("ajoutStockBeans")
@ViewScoped
public class AjoutStockBeans implements Serializable {

    @EJB
    private StockFacade stockFacade;

    @EJB
    private MagasinFacade magasinFacade;

    @EJB
    private ProduitFacade produitFacade;

    private Stock stock = new Stock();

    private Integer magasinId;

    private Integer produitId;

    public Stock getStock() {
        return stock;
    }

    public void setStock(Stock stock) {
        this.stock = stock;
    }

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

    public String SaveStock() {

        try {

            Magasin magasin = magasinFacade.FindById(magasinId);
            if (magasin == null) {
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Ce magasin n'existe pas.", null));
                return null;
            }

            Produit produit = produitFacade.FindById(produitId);

            if (produit == null) {
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Ce produit n'existe pas.", null));
                return null;
            }

            stock.setProduit(produit);
            stock.setMagasin(magasin);

            if (stockFacade.FindById(magasinId, produitId) != null){
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR,"Erreur!" , "Ce stock existe déjà."));
                return null;
            }

            stockFacade.SaveStock(stock);
            stock = new Stock();

            FacesMessage msg = new FacesMessage("Succès!", "L'ajout a été effectué avec succès.");
            FacesContext.getCurrentInstance().getExternalContext().getFlash().setKeepMessages(true);
            FacesContext.getCurrentInstance().addMessage("successMessages", msg);
        } catch (jakarta.ejb.EJBException e) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Erreur!", "Veuillez vérifiez vos champs svp."));
            stock = new Stock();
            return null;
        }

        return "liste-stock.xhtml?faces-redirect=true";

    }


}
