package sn.edu.ugb.ipsl.appventevelo.mbeans.marquembeans;

import jakarta.ejb.EJB;
import jakarta.faces.view.ViewScoped;
import jakarta.inject.Named;
import sn.edu.ugb.ipsl.appventevelo.entities.Marque;
import sn.edu.ugb.ipsl.appventevelo.facades.MarqueFacade;

import java.io.Serializable;
import java.util.List;

@Named("listeMarquesBeans")
@ViewScoped
public class ListeMarquesBeans implements Serializable {

    @EJB
    private MarqueFacade marqueFacade;

    private List<Marque> marques;

    public List<Marque> getMarques() {
        if (marques == null) {
            marques = marqueFacade.FindAll();
        }
        return marques;
    }

    public void setMarques(List<Marque> marques) {
        this.marques = marques;
    }


}

