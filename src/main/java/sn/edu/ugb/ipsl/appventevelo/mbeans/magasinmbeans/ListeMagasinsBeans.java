package sn.edu.ugb.ipsl.appventevelo.mbeans.magasinmbeans;

import jakarta.ejb.EJB;
import jakarta.faces.view.ViewScoped;
import jakarta.inject.Named;
import sn.edu.ugb.ipsl.appventevelo.entities.Magasin;
import sn.edu.ugb.ipsl.appventevelo.facades.MagasinFacade;

import java.io.Serializable;
import java.util.List;

@Named("listeMagasinsBeans")
@ViewScoped
public class ListeMagasinsBeans implements Serializable {

    @EJB
    private MagasinFacade magasinFacade;

    private List<Magasin> magasins;

    public List<Magasin> getMagasins() {
        if (magasins == null) {
            magasins = magasinFacade.FindAll();
        }
        return magasins;
    }

    public void setMagasins(List<Magasin> magasins) {
        this.magasins = magasins;
    }
}

