package sn.edu.ugb.ipsl.appventevelo.mbeans.stockmbeans;

import jakarta.ejb.EJB;
import jakarta.faces.view.ViewScoped;
import jakarta.inject.Named;
import sn.edu.ugb.ipsl.appventevelo.entities.Stock;
import sn.edu.ugb.ipsl.appventevelo.facades.StockFacade;

import java.io.Serializable;
import java.util.List;

@Named("listeStocksBeans")
@ViewScoped
public class ListeStocksBeans implements Serializable {

    @EJB
    private StockFacade stockFacade;

    private List<Stock> stocks;

    public List<Stock> getStocks() {
        if (stocks == null) {
            stocks = stockFacade.FindAll();
        }
        return stocks;
    }

    public void setStocks(List<Stock> stocks) {
        this.stocks = stocks;
    }
}

