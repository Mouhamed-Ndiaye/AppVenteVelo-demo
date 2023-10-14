package sn.edu.ugb.ipsl.appventevelo.mbeans.employembeans;

import jakarta.ejb.EJB;
import jakarta.faces.view.ViewScoped;
import jakarta.inject.Named;
import sn.edu.ugb.ipsl.appventevelo.entities.Employe;
import sn.edu.ugb.ipsl.appventevelo.facades.EmployeFacade;

import java.io.Serializable;
import java.util.List;

@Named("listeEmployesBeans")
@ViewScoped
public class ListeEmployesBeans implements Serializable {

    @EJB
    private EmployeFacade employeFacade;

    private List<Employe> employes;

    public List<Employe> getEmployes() {
        if (employes == null) {
            employes = employeFacade.FindAll();
        }
        return employes;
    }
}

