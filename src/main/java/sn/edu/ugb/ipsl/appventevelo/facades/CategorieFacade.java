package sn.edu.ugb.ipsl.appventevelo.facades;

import jakarta.ejb.Stateless;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.Query;
import jakarta.transaction.Transactional;
import sn.edu.ugb.ipsl.appventevelo.entities.Categorie;

import java.util.List;

@Stateless
public class CategorieFacade {

    @PersistenceContext(name = "AppVente_PU")
    private EntityManager em;

    public void SaveCategorie(Categorie categorie) {
        // System.out.println("####### Creation de categorie");
        em.persist(categorie);
    }

    public List<Categorie> FindAll() {
        Query query = em.createQuery("SELECT c from Categorie c");
        return query.getResultList();
    }

    public Categorie FindById(Integer id) {
        return em.find(Categorie.class, id);
    }

    public List<String> AutoCompleteNom(String txt){
        Query query = em.createQuery("SELECT DISTINCT(c.nom) FROM Categorie c WHERE c.nom LIKE '"+txt+"%'");
        return query.getResultList();
    }

    public List<Categorie> searchCategorie(String nom){
        String JPQLquery = "SELECT c FROM Categorie c WHERE c.nom like '%"+nom+"%' ";
        Query query = em.createQuery(JPQLquery);
        return query.getResultList();
    }

    @Transactional
    public void UpdateCategorie(Categorie categorie) {
        em.merge(categorie);
    }

    public void RemoveCategorie(Integer categorieId) {
        Categorie categorie = FindById(categorieId);
        if (categorie != null) {
            em.remove(categorie);
        }
    }

}
