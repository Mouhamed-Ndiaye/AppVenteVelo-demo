package sn.edu.ugb.ipsl.appventevelo.mbeans.produitmbeans;


import jakarta.annotation.PostConstruct;
import jakarta.ejb.EJB;
import jakarta.faces.application.FacesMessage;
import jakarta.faces.context.FacesContext;
import jakarta.faces.view.ViewScoped;
import jakarta.inject.Named;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.TypedQuery;
import jakarta.persistence.criteria.*;
import sn.edu.ugb.ipsl.appventevelo.entities.Categorie;
import sn.edu.ugb.ipsl.appventevelo.entities.Marque;
import sn.edu.ugb.ipsl.appventevelo.entities.Produit;
import sn.edu.ugb.ipsl.appventevelo.facades.ProduitFacade;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.*;

@Named("rechercheAvanceeBeans")
@ViewScoped
public class RechercheAvanceeBeans implements Serializable {

    @EJB
    private ProduitFacade produitFacade;

    @PersistenceContext(name = "AppVente_PU")
    private EntityManager em;

    private List<Produit> produits;

    private String nomRecherche;

    private short anneeRecherchee;

    private Integer categorieRecherchee;
    private Integer marqueRecherchee;
    private String prixRecherche;

    private Map<String, String> annees = new HashMap<>();

    public List<Produit> getProduits() {
        if(produits == null){
            produits = produitFacade.FindAll();
        }
        return produits;
    }

    public void setProduits(List<Produit> produits) {
        this.produits = produits;
    }

    public String getNomRecherche() {
        return nomRecherche;
    }

    public void setNomRecherche(String nomRecherche) {
        this.nomRecherche = nomRecherche;
    }

    public short getAnneeRecherchee() {
        return anneeRecherchee;
    }

    public void setAnneeRecherchee(short anneeRecherchee) {
        this.anneeRecherchee = anneeRecherchee;
    }

    public Integer getCategorieRecherchee() {
        return categorieRecherchee;
    }

    public void setCategorieRecherchee(Integer categorieRecherchee) {
        this.categorieRecherchee = categorieRecherchee;
    }

    public Integer getMarqueRecherchee() {
        return marqueRecherchee;
    }

    public void setMarqueRecherchee(Integer marqueRecherchee) {
        this.marqueRecherchee = marqueRecherchee;
    }

    public String getPrixRecherche() {
        return prixRecherche;
    }

    public void setPrixRecherche(String prixRecherche) {
        this.prixRecherche = prixRecherche;
    }

    public Map<String, String> getAnnees() {
        return annees;
    }

    public void setAnnees(Map<String, String> annees) {
        this.annees = annees;
    }

    @PostConstruct
    public void init() {

        annees = new HashMap<>();
        annees.put("2016", "2016");
        annees.put("2017", "2017");
        annees.put("2018", "2018");
        annees.put("2019", "2019");
        annees.put("2020", "2020");

    }

    public void rechercher() {
        try {
            // Initialisez la liste de produits pour stocker les résultats de la recherche
            produits = new ArrayList<>();

            // Construction de la requête de recherche en fonction des critères renseignés
            CriteriaBuilder cb = em.getCriteriaBuilder();
            CriteriaQuery<Produit> cq = cb.createQuery(Produit.class);
            Root<Produit> root = cq.from(Produit.class);
            List<Predicate> predicates = new ArrayList<>();

            if (nomRecherche != null && !nomRecherche.isEmpty()) {
                // Ajoutez un prédicat pour la recherche par nom
                predicates.add(cb.like(cb.lower(root.get("nom")), "%" + nomRecherche.toLowerCase() + "%"));
            }

            if (anneeRecherchee != 0) {
                // Ajoutez un prédicat pour la recherche par année
                predicates.add(cb.equal(root.get("annee_model"), anneeRecherchee));
            }

            if (prixRecherche != null && !prixRecherche.isEmpty()) {
                // Ajoutez un prédicat pour la recherche par prix
                String[] prixRange = prixRecherche.split("-");
                if (prixRange.length == 2) {
                    BigDecimal minPrix = new BigDecimal(prixRange[0]);
                    BigDecimal maxPrix = new BigDecimal(prixRange[1]);
                    predicates.add(cb.between(root.get("prix_depart"), minPrix, maxPrix));
                } else if (prixRange.length == 1 && "greaterThan1000".equals(prixRange[0])) {
                    predicates.add(cb.greaterThan(root.get("prix_depart"), new BigDecimal("1000.00")));
                }
            }

            if (categorieRecherchee != null) {
                // Ajoutez un prédicat pour la recherche par catégorie
                Join<Produit, Categorie> categorieJoin = root.join("categorie");
                predicates.add(cb.equal(categorieJoin.get("id"), categorieRecherchee));
            }

            if (marqueRecherchee != null) {
                // Ajoutez un prédicat pour la recherche par marque
                Join<Produit, Marque> marqueJoin = root.join("marque");
                predicates.add(cb.equal(marqueJoin.get("id"), marqueRecherchee));
            }

            // Combinez les prédicats avec un ET
            Predicate finalPredicate = cb.and(predicates.toArray(new Predicate[0]));

            // Ajoutez le prédicat à la requête
            cq.where(finalPredicate);

            // Exécutez la requête et renvoyez les résultats
            TypedQuery<Produit> query = em.createQuery(cq);
            produits = query.getResultList();

            if (produits.isEmpty()) {
                FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_WARN, "Aucun résultat trouvé", "Aucun produit ne correspond aux critères de recherche.");
                FacesContext.getCurrentInstance().addMessage(null, message);
            } else {
                FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_INFO, "Recherche réussie", produits.size() + " produit(s) trouvé(s).");
                FacesContext.getCurrentInstance().addMessage(null, message);
            }
        } catch (Exception e) {
            // Gérez les erreurs potentielles ici
            FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_ERROR, "Erreur lors de la recherche", "Une erreur s'est produite lors de la recherche.");
            FacesContext.getCurrentInstance().addMessage(null, message);
        }
    }

}

