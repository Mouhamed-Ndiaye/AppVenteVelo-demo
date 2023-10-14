package sn.edu.ugb.ipsl.appventevelo.facades;

import jakarta.ejb.Stateless;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.Query;
import jakarta.persistence.TypedQuery;
import jakarta.transaction.Transactional;
import sn.edu.ugb.ipsl.appventevelo.entities.Employe;

import java.util.List;

@Stateless
public class EmployeFacade {

    @PersistenceContext(name = "AppVente_PU")
    private EntityManager em;

    public void SaveEmploye(Employe employe){

        em.persist(employe);
    }

    public List<Employe> FindAll() {
        Query query = em.createQuery("SELECT em FROM Employe em");
        return query.getResultList();
    }

    public List<String> AutoCompletePrenom(String txt){
        Query query = em.createQuery("SELECT DISTINCT(e.prenom) FROM Employe e WHERE e.prenom LIKE '"+txt+"%'");
        return query.getResultList();
    }

    public List<String> AutoCompleteNom(String txt){
        Query query = em.createQuery("SELECT DISTINCT(e.nom) FROM Employe e WHERE e.nom LIKE '"+txt+"%'");
        return query.getResultList();
    }

    public List<String> AutoCompleteEmail(String txt){
        Query query = em.createQuery("SELECT DISTINCT(e.email) FROM Employe e WHERE e.email LIKE '"+txt+"%'");
        return query.getResultList();
    }

    public List<String> AutoCompleteTelephone(String txt){
        Query query = em.createQuery("SELECT DISTINCT(e.telephone) FROM Employe e WHERE e.telephone LIKE '"+txt+"%'");
        return query.getResultList();
    }

    @Transactional
    public Employe FindById(Integer id) {
        if (id != null){
            return em.find(Employe.class, id);
        }
        return null;
    }

    public List<Employe> searchEmploye(String nom){
        String JPQLquery = "SELECT e FROM Employe e WHERE e.nom like '%"+nom+"%' or e.prenom like '%"+nom+"%' or e.email like '%"+nom+"%' or e.telephone like '%"+nom+"%'";
        Query query = em.createQuery(JPQLquery);
        return query.getResultList();
    }

    public Employe findByTelephone(String telephone) {
        TypedQuery<Employe> query = em.createQuery("SELECT e FROM Employe e WHERE e.telephone = :telephone", Employe.class);
        query.setParameter("telephone", telephone);

        List<Employe> results = query.getResultList();
        if (!results.isEmpty()) {
            // Si un employé avec ce numéro de téléphone est trouvé, retournez le premier résultat
            return results.get(0);
        } else {
            // Si aucun employé avec ce numéro de téléphone n'est trouvé, retournez null
            return null;
        }
    }


    public Employe findByEmail(String email) {
        TypedQuery<Employe> query = em.createQuery("SELECT e FROM Employe e WHERE e.email = :email", Employe.class);
        query.setParameter("email", email);

        List<Employe> results = query.getResultList();
        if (!results.isEmpty()) {
            // Si un employé avec cet email de téléphone est trouvé, retourner le premier résultat
            return results.get(0);
        } else {
            // Si aucun employé avec cet email de téléphone n'est trouvé, retourner null
            return null;
        }
    }

    @Transactional
    public void UpdateEmploye(Employe employe) {
        em.merge(employe);
    }

    public void RemoveEmploye(Integer employeId) {
        Employe employe = FindById(employeId);
        if (employe != null) {
            em.remove(employe);
        }
    }

}
