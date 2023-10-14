package sn.edu.ugb.ipsl.appventevelo.facades;


import jakarta.ejb.Stateless;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.Query;
import sn.edu.ugb.ipsl.appventevelo.entities.Produit;

import java.util.List;

@Stateless
public class ProduitFacade {

    @PersistenceContext(name = "AppVente_PU")
    private EntityManager em;

    public void SaveProduit(Produit produit) {

        em.persist(produit);
    }

    public List<Produit> FindAll() {
        Query query = em.createQuery("SELECT p from Produit p");
        return query.getResultList();
    }

    public Produit FindById(Integer id) {
        return em.find(Produit.class, id);
    }

    public List<Produit> searchProduit(String nom){
        String JPQLquery = "SELECT p FROM Produit p WHERE p.nom like '%"+nom+"%' or p.categorie.nom like '%"+nom+"%' or p.marque.nom like '%"+ nom +"%'";
        Query query = em.createQuery(JPQLquery);
        return query.getResultList();
    }

    public List<String> AutoCompleteNom(String txt) {
        Query query = em.createQuery("SELECT DISTINCT(p.nom) FROM Produit p WHERE p.nom LIKE '" + txt + "%'");
        return query.getResultList();
    }

    public void UpdateProduit(Produit produit) {

        em.merge(produit);
    }

    public void RemoveProduit(Integer produitId) {
        Produit produit = FindById(produitId);
        if (produit != null) {
            em.remove(produit);
        }
    }

}
