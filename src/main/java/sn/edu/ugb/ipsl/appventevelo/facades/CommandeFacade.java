package sn.edu.ugb.ipsl.appventevelo.facades;

import jakarta.ejb.Stateless;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.Query;
import sn.edu.ugb.ipsl.appventevelo.entities.Commande;

import java.util.Date;
import java.util.List;

@Stateless
public class CommandeFacade {

    @PersistenceContext(name = "AppVente_PU")
    private EntityManager em;

    public void SaveCommande(Commande commande){

        em.persist(commande);
    }

    public List<Commande> FindAll() {
        Query query = em.createQuery("SELECT co FROM Commande co");
        return query.getResultList();
    }

    public Commande FindById(Integer id) {
        return em.find(Commande.class, id);
    }

    public List<Commande> searchCommande(Date date) {
        Query query = em.createQuery("SELECT c FROM Commande c WHERE c.dateCommande = :date OR c.dateLivraison = :date OR c.dateLivraisonVoulue = :date", Commande.class);
        query.setParameter("date", date);
        return query.getResultList();
    }

    public void UpdateCommande(Commande commande){

        em.merge(commande);
    }

    public void RemoveCommande(Integer numero) {
        Commande commande = FindById(numero);
        if (commande != null) {
            em.remove(commande);
        }
    }

}
