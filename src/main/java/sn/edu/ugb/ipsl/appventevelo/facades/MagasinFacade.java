package sn.edu.ugb.ipsl.appventevelo.facades;

import jakarta.ejb.Stateless;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.Query;
import jakarta.persistence.TypedQuery;
import jakarta.transaction.Transactional;
import sn.edu.ugb.ipsl.appventevelo.entities.Magasin;

import java.util.List;

@Stateless
public class MagasinFacade {

    @PersistenceContext(name = "AppVente_PU")
    private EntityManager em;

    public void SaveMagasin(Magasin magasin){

        em.persist(magasin);
    }

    public List<Magasin> FindAll() {
        Query query = em.createQuery("SELECT m from Magasin m");
        return query.getResultList();
    }

    public Magasin FindById(Integer id) {
        if (id != null) {
            return em.find(Magasin.class, id);
        }
        return null;
    }

    public List<Magasin> searchMagasin(String nom){
        String JPQLquery = "SELECT m FROM Magasin m WHERE m.nom like '%"+nom+"%' or m.email like '%"+nom+"%' or m.telephone like '%"+nom+"%' or m.adresse.adresse like '%"+nom+"%' or m.adresse.ville like '%"+nom+"%' or m.adresse.etat like '%"+nom+"%' or m.adresse.codeZip like '%"+nom+"%'";
        Query query = em.createQuery(JPQLquery);
        return query.getResultList();
    }

    public Magasin findByTelephone(String telephone) {
        TypedQuery<Magasin> query = em.createQuery("SELECT m FROM Magasin m WHERE m.telephone = :telephone", Magasin.class);
        query.setParameter("telephone", telephone);

        List<Magasin> results = query.getResultList();
        if (!results.isEmpty()) {
            return results.get(0);
        } else {
            return null;
        }
    }


    public Magasin findByEmail(String email) {
        TypedQuery<Magasin> query = em.createQuery("SELECT m FROM Magasin m WHERE m.email = :email", Magasin.class);
        query.setParameter("email", email);

        List<Magasin> results = query.getResultList();
        if (!results.isEmpty()) {
            return results.get(0);
        } else {
            return null;
        }
    }

    public List<String> AutoCompleteNom(String txt){
        Query query = em.createQuery("SELECT DISTINCT(m.nom) FROM Magasin m WHERE m.nom LIKE '"+txt+"%'");
        return query.getResultList();
    }

    public List<String> AutoCompleteTelephone(String txt){
        Query query = em.createQuery("SELECT DISTINCT(m.telephone) FROM Magasin m WHERE m.telephone LIKE '"+txt+"%'");
        return query.getResultList();
    }

    public List<String> AutoCompleteEmail(String txt){
        Query query = em.createQuery("SELECT DISTINCT(m.email) FROM Magasin m WHERE m.email like '"+txt+"%'");
        return query.getResultList();
    }

    public List<String> AutoCompleteAdresse(String txt){
        Query query = em.createQuery("SELECT DISTINCT(m.adresse.adresse) FROM Magasin m WHERE m.adresse.adresse like '"+txt+"%'");
        return query.getResultList();
    }

    public List<String> AutoCompleteEtat(String txt){
        Query query = em.createQuery("SELECT DISTINCT(m.adresse.etat) FROM Magasin m WHERE m.adresse.etat like '"+txt+"%'");
        return query.getResultList();
    }

    public List<String> AutoCompleteVille(String txt){
        Query query = em.createQuery("SELECT DISTINCT(m.adresse.ville) FROM Magasin m WHERE m.adresse.ville like '"+txt+"%'");
        return query.getResultList();
    }

    public List<String> AutoCompleteCodeZip(String txt){
        Query query = em.createQuery("SELECT DISTINCT(m.adresse.codeZip) FROM  Magasin m WHERE m.adresse.codeZip like '"+txt+"%'");
        return query.getResultList();
    }

    @Transactional
    public void UpdateMagasin(Magasin magasin) {
        em.merge(magasin);
    }

    public void RemoveMagasin(Integer magasinId) {
       Magasin magasin = FindById(magasinId);
        if (magasin != null) {
            em.remove(magasin);
        }
    }


}
