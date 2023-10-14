package sn.edu.ugb.ipsl.appventevelo.mbeans.articleCommandembeans;

import jakarta.ejb.EJB;
import jakarta.faces.view.ViewScoped;
import jakarta.inject.Named;
import sn.edu.ugb.ipsl.appventevelo.entities.ArticleCommande;
import sn.edu.ugb.ipsl.appventevelo.facades.ArticleCommandeFacade;

import java.io.Serializable;
import java.util.List;

@Named("listeArticleCommandesBeans")
@ViewScoped
public class ListeArticleCommandesBeans implements Serializable {

    @EJB
    private ArticleCommandeFacade articleCommandeFacade;

    private List<ArticleCommande> articleCommandes;

    public List<ArticleCommande> getArticleCommandes() {
        if (articleCommandes == null) {
            articleCommandes = articleCommandeFacade.FindAll();
        }
        return articleCommandes;
    }
}

