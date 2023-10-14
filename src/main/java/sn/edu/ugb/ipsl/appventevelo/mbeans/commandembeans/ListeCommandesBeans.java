package sn.edu.ugb.ipsl.appventevelo.mbeans.commandembeans;

import jakarta.ejb.EJB;
import jakarta.faces.view.ViewScoped;
import jakarta.inject.Named;
import sn.edu.ugb.ipsl.appventevelo.entities.Commande;
import sn.edu.ugb.ipsl.appventevelo.facades.CommandeFacade;

import java.io.Serializable;
import java.util.List;

@Named("listeCommandesBeans")
@ViewScoped
public class ListeCommandesBeans implements Serializable {

    @EJB
    private CommandeFacade commandeFacade;

    private List<Commande> commandes;

    public List<Commande> getCommandes() {
        if (commandes == null) {
            commandes = commandeFacade.FindAll();
        }
        return commandes;
    }
}

