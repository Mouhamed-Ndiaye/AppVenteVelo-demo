package sn.edu.ugb.ipsl.appventevelo.mbeans.clientmbeans;

import jakarta.ejb.EJB;
import jakarta.faces.view.ViewScoped;
import jakarta.inject.Named;
import sn.edu.ugb.ipsl.appventevelo.entities.Client;
import sn.edu.ugb.ipsl.appventevelo.facades.ClientFacade;

import java.io.Serializable;
import java.util.List;

@Named("listeClientsBeans")
@ViewScoped
public class ListeClientsBeans implements Serializable {

    @EJB
    private ClientFacade clientFacade;

    private List<Client> clients;

    public List<Client> getClients() {
        if (clients == null) {
            clients = clientFacade.FindAll();
        }
        return clients;
    }
}

