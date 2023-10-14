package sn.edu.ugb.ipsl.appventevelo;

import jakarta.annotation.PostConstruct;
import jakarta.annotation.PreDestroy;
import jakarta.ejb.EJB;
import jakarta.ejb.Singleton;
import jakarta.ejb.Startup;
import sn.edu.ugb.ipsl.appventevelo.mailsessionbean.EmailSessionBean;
import sn.edu.ugb.ipsl.appventevelo.mailsessionbean.EtatStockEJB;

@Startup
@Singleton
public class  AppVenteVeloStartupApp {

    /*@EJB
    private MarqueFacade marqueFacade;*/

    @EJB
    EmailSessionBean emailSessionBean = new EmailSessionBean();

    @EJB
    EtatStockEJB etatStockEJB = new EtatStockEJB();


    public void SendInfosStocks(){
        etatStockEJB.InfosStocks();
    }


    @PostConstruct
    public void init() {
        /*Marque marque = new Marque(10, "VTT");
        marqueFacade.SaveSaveMarque(marque);*/

        emailSessionBean.SendMail("Application démarree", """

                L'application AppVenteVelo a été lancée.

                Zenitsu-MNN :-)""");
    }

    @PreDestroy
    public void destroy() {
        emailSessionBean.SendMail("Application arretee", """

                L'application AppVenteVelo est en cours d'arret.

                Zenitsu-MNN :-)""");

    }

}