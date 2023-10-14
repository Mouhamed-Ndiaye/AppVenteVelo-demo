package sn.edu.ugb.ipsl.appventevelo.facades;

import jakarta.ejb.Stateless;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.Query;
import jakarta.transaction.Transactional;
import jakarta.xml.bind.annotation.XmlRootElement;
import sn.edu.ugb.ipsl.appventevelo.entities.Marque;

import java.util.List;

@Stateless
@XmlRootElement(name = "search")
public class MarqueFacade {

    @PersistenceContext(name = "AppVente_PU")
    private EntityManager em;


    public void SaveMarque(Marque marque) {
        em.merge(marque);
        em.persist(marque);
    }

    public List<Marque> FindAll() {
        Query query = em.createQuery("SELECT m from Marque m");
        return query.getResultList();
    }

    public Marque FindById(Integer id) {
        return em.find(Marque.class, id);
    }

    public List<String> AutoCompleteNom(String txt){
        Query query = em.createQuery("SELECT DISTINCT(m.nom) FROM Marque m WHERE m.nom LIKE '"+txt+"%'");
        return query.getResultList();
    }

    public List<Marque> searchMarque(String nom){
        String JPQLquery = "SELECT m FROM Marque m WHERE m.nom like '%"+nom+"%' ";
        Query query = em.createQuery(JPQLquery);
        return query.getResultList();
    }

    @Transactional
    public void UpdateMarque(Marque marque) {
        em.merge(marque);
    }

    public void RemoveMarque(Integer marqueId) {
       Marque marque = FindById(marqueId);
       if (marque != null) {
           em.remove(marque);
       }
    }
}
