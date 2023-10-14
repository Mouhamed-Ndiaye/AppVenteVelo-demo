package sn.edu.ugb.ipsl.appventevelo.facades;

import jakarta.ejb.Stateless;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.Query;
import sn.edu.ugb.ipsl.appventevelo.entities.Stock;
import sn.edu.ugb.ipsl.appventevelo.entities.StockId;

import java.util.List;

@Stateless
public class StockFacade {

    @PersistenceContext(name = "AppVente_PU")
    private EntityManager em;

    public void SaveStock(Stock stock){

        em.persist(stock);
    }

    public List<Stock> FindAll() {
        Query query = em.createQuery("SELECT s FROM Stock s");
        return query.getResultList();
    }

    public Stock FindById(Integer magasinId, Integer produitId) {
        StockId stockId = new StockId();
        stockId.setMagasin(magasinId);
        stockId.setProduit(produitId);
        return em.find(Stock.class, stockId);
    }

    public List<Stock> searchStock(String nom){
        String JPQLquery = "SELECT s FROM Stock s WHERE s.produit.nom like '%"+nom+"%' or s.magasin.nom like '%"+nom+"%' or s.magasin.email like '%"+ nom +"%' or s.magasin.telephone like '%"+ nom +"%' ";
        Query query = em.createQuery(JPQLquery);
        return query.getResultList();
    }

    public void UpdateStock(Stock stock){

        em.merge(stock);
    }

    public void RemoveStock(Integer magasinId, Integer produitId) {
            Stock stock = FindById(magasinId, produitId);
        if (stock != null) {
            em.remove(stock);
        }
    }

}
