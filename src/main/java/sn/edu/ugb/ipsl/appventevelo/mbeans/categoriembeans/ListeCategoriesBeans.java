package sn.edu.ugb.ipsl.appventevelo.mbeans.categoriembeans;

import jakarta.ejb.EJB;
import jakarta.faces.view.ViewScoped;
import jakarta.inject.Named;
import sn.edu.ugb.ipsl.appventevelo.entities.Categorie;
import sn.edu.ugb.ipsl.appventevelo.facades.CategorieFacade;

import java.io.Serializable;
import java.util.List;

@Named("listeCategoriesBeans")
@ViewScoped
public class ListeCategoriesBeans implements Serializable {

    @EJB
    private CategorieFacade categorieFacade;

    private List<Categorie> categories;

    public List<Categorie> getCategories() {
        if (categories == null) {
            categories = categorieFacade.FindAll();
        }
        return categories;
    }

    public void setCategories(List<Categorie> categories) {
        this.categories = categories;
    }
}

