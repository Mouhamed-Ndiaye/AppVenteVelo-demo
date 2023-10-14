package sn.edu.ugb.ipsl.appventevelo.mbeans.produitmbeans;

import jakarta.annotation.PostConstruct;
import jakarta.ejb.EJB;
import jakarta.faces.view.ViewScoped;
import jakarta.inject.Named;
import org.primefaces.event.SelectEvent;
import org.primefaces.model.LazyDataModel;
import sn.edu.ugb.ipsl.appventevelo.entities.Produit;
import sn.edu.ugb.ipsl.appventevelo.facades.ProduitFacade;

import java.io.Serializable;

@Named("listeProduitsBeans")
@ViewScoped
public class ListeProduitsBeans implements Serializable {

    @EJB
    private ProduitFacade produitFacade;

    private LazyDataModel<Produit> lazyModel;

    private Produit selectedProduit;

    @PostConstruct
    public void init() {
        lazyModel = new LazyProduitDataModel(produitFacade.FindAll());
    }

    public LazyDataModel<Produit> getLazyModel() {
        return lazyModel;
    }

    public Produit getSelectedProduit() {
        return selectedProduit;
    }

    public void setSelectedProduit(Produit selectedProduit) {

        this.selectedProduit = selectedProduit;
    }

    public void setProduitFacade(ProduitFacade produitFacade) {
        this.produitFacade = produitFacade;
    }

    public void onRowSelect(SelectEvent<Produit> event) {
        selectedProduit = event.getObject();
    }


}

