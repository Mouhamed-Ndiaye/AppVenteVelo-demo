package sn.edu.ugb.ipsl.appventevelo.facades;

import jakarta.ejb.Stateless;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.Query;
import sn.edu.ugb.ipsl.appventevelo.entities.ArticleCommande;
import sn.edu.ugb.ipsl.appventevelo.entities.ArticleCommandeId;

import java.math.BigDecimal;
import java.util.List;

@Stateless
public class ArticleCommandeFacade {

    @PersistenceContext(name = "AppVente_PU")
    private EntityManager em;

    public void SaveArticleCommande(ArticleCommande articleCommande){
        em.persist(articleCommande);
    }

    public List<ArticleCommande> FindAll() {
        Query query = em.createQuery("SELECT ac FROM ArticleCommande ac");
        return query.getResultList();
    }

    public List<ArticleCommande> searchArticleCommande(BigDecimal number) {
        Query query = em.createQuery("SELECT ac FROM ArticleCommande ac WHERE ac.prix_depart = :number OR ac.Remise = :number OR ac.quantite = :number", ArticleCommande.class);
        query.setParameter("number", number);
        return query.getResultList();
    }




    public List<BigDecimal> AutoCompletePrixDepart(BigDecimal prixDepart){
        Query query = em.createQuery("SELECT DISTINCT (a.prix_depart) FROM ArticleCommande a WHERE a.prix_depart = '"+prixDepart+"%'");
        return query.getResultList();
    }

    public List<BigDecimal> AutoCompleteRemise(BigDecimal remise){
        Query query = em.createQuery("SELECT DISTINCT (a.prix_depart) FROM ArticleCommande a WHERE a.prix_depart = '"+remise+"%'");
        return query.getResultList();
    }

    public ArticleCommande FindById(Integer numeroCommande, Integer ligne) {
        ArticleCommandeId articleCommandeId = new ArticleCommandeId();
        articleCommandeId.setNumeroCommande(numeroCommande);
        articleCommandeId.setLigne(ligne);
        return em.find(ArticleCommande.class, articleCommandeId);
    }

    public void UpdateArticleCommande(ArticleCommande articleCommande){

        em.merge(articleCommande);
    }

    public void RemoveArticleCommande(Integer numeroCommande, Integer ligne) {
        ArticleCommande articleCommande = FindById(numeroCommande, ligne);
        if (articleCommande != null) {
            em.remove(articleCommande);
        }
    }
}
