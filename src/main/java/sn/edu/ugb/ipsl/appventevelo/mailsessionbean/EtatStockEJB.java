package sn.edu.ugb.ipsl.appventevelo.mailsessionbean;


import jakarta.ejb.EJB;
import jakarta.ejb.Schedule;
import jakarta.ejb.Schedules;
import jakarta.ejb.Stateless;
import jakarta.persistence.*;

import java.util.List;

@Stateless
public class EtatStockEJB {


    @PersistenceContext(name = "AppVente_PU")
    private EntityManager em;

    @EJB
    EmailSessionBean emailSessionBean = new EmailSessionBean();

    @Schedules({
            @Schedule(second = "0", minute = "0", hour = "0", dayOfWeek = "*", dayOfMonth = "*", persistent = false),
            @Schedule(second = "0", minute = "0", hour = "8", dayOfWeek = "*", dayOfMonth = "*", persistent = false),
            @Schedule(second = "0", minute = "0", hour = "16", dayOfWeek = "*", dayOfMonth = "*", persistent = false)
    })
    public void InfosStocks() {

        StringBuilder stringBuilder = new StringBuilder();
        try {

            Query query = em.createQuery("SELECT p.nom, SUM(s.quantite) FROM Produit p, Stock s WHERE p.id = s.produit.id GROUP BY p.id ORDER BY p.nom");

            List<Object[]> stocks = query.getResultList();

            stringBuilder.append("                                Total des produits : ").append(stocks.size());

            stringBuilder.append("\n**************************************************************************************************\n");


            for (Object[] s : stocks) {
                String nomProduit = (String) s[0];
                long quantite = (long) s[1];
                stringBuilder.append("Le produit : [ ").append(nomProduit).append(" ] en a ").append(quantite).append(" en stock.");
                stringBuilder.append("\n**************************************************************************************************\n");
            }
            stringBuilder.append("""
                    
                    Zenitsu-MNN :-)""");

        } catch (Exception e) {
            System.out.println("####### Erreur a cause de => " + e);
        }
        stringBuilder.toString();
        emailSessionBean.SendMail("Etat du Stock actuel", stringBuilder.toString());
    }
}


