package sn.edu.ugb.ipsl.appventevelo.facades;

import jakarta.ejb.Stateless;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.Query;
import jakarta.persistence.TypedQuery;
import jakarta.transaction.Transactional;
import sn.edu.ugb.ipsl.appventevelo.entities.*;

import java.util.List;

@Stateless
public class ClientFacade {

    @PersistenceContext(name = "AppVente_PU")
    private EntityManager em;

    public void SaveClient(Client client) {

        em.persist(client);
    }

    public List<Client> FindAll() {
        Query query = em.createQuery("SELECT c from Client c");
        return query.getResultList();
    }

    public Client FindById(Integer id) {
        if (id != null) {
            return em.find(Client.class, id);
        }
        return null;
    }

    public List<Client> searchClient(String nom){
        String JPQLquery = "SELECT c FROM Client c WHERE c.nom like '%"+nom+"%' or c.prenom like '%"+nom+"%' or c.email like '%"+nom+"%' or c.telephone like '%"+nom+"%'";
        Query query = em.createQuery(JPQLquery);
        return query.getResultList();
    }

    public Client findByTelephone(String telephone) {
        TypedQuery<Client> query = em.createQuery("SELECT c FROM Client c WHERE c.telephone = :telephone", Client.class);
        query.setParameter("telephone", telephone);

        List<Client> results = query.getResultList();
        if (!results.isEmpty()) {
            return results.get(0);
        } else {
            return null;
        }
    }


    public Client findByEmail(String email) {
        TypedQuery<Client> query = em.createQuery("SELECT c FROM Client c WHERE c.email = :email", Client.class);
        query.setParameter("email", email);

        List<Client> results = query.getResultList();
        if (!results.isEmpty()) {
            return results.get(0);
        } else {
            return null;
        }
    }

    public List<String> AutoCompletePrenom(String txt) {
        Query query = em.createQuery("SELECT DISTINCT(c.prenom) FROM Client c WHERE c.prenom like '" + txt + "%'");
        return query.getResultList();
    }

    public List<String> AutoCompleteNom(String txt) {
        Query query = em.createQuery("SELECT DISTINCT(c.nom) FROM Client c WHERE c.nom like '" + txt + "%'");
        return query.getResultList();
    }

    public List<String> AutoCompleteTelephone(String txt) {
        Query query = em.createQuery("SELECT DISTINCT(c.telephone) FROM Client c WHERE c.telephone like '" + txt + "%'");
        return query.getResultList();
    }

    public List<String> AutoCompleteEmail(String txt) {
        Query query = em.createQuery("SELECT DISTINCT(c.email) FROM Client c WHERE c.email like '" + txt + "%'");
        return query.getResultList();
    }

    public List<String> AutoCompleteAdresse(String txt) {
        Query query = em.createQuery("SELECT DISTINCT(c.adresse.adresse) FROM Client c WHERE c.adresse.adresse like '" + txt + "%'");
        return query.getResultList();
    }

    public List<String> AutoCompleteEtat(String txt) {
        Query query = em.createQuery("SELECT DISTINCT(c.adresse.etat) FROM Client c WHERE c.adresse.etat like '" + txt + "%'");
        return query.getResultList();
    }

    public List<String> AutoCompleteVille(String txt) {
        Query query = em.createQuery("SELECT DISTINCT(c.adresse.ville) FROM Client c WHERE c.adresse.ville like '" + txt + "%'");
        return query.getResultList();
    }

    public List<String> AutoCompleteCodeZip(String txt) {
        Query query = em.createQuery("SELECT DISTINCT(c.adresse.codeZip) FROM Client c WHERE c.adresse.codeZip like '" + txt + "%'");
        return query.getResultList();
    }

    @Transactional
    public void UpdateClient(Client client) {

        em.merge(client);
    }

    public void RemoveClient(Integer clientId) {
        Client client = FindById(clientId);
        if (client != null) {
            em.remove(client);
        }
    }

}
