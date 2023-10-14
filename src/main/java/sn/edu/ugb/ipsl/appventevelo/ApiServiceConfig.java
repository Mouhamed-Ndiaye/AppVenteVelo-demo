package sn.edu.ugb.ipsl.appventevelo;


import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.servers.Server;
import io.swagger.v3.oas.annotations.servers.ServerVariable;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.ws.rs.ApplicationPath;
import jakarta.ws.rs.core.Application;


@OpenAPIDefinition(
        info = @Info(
                title = "AppVenteVelo service",
                description = "Service de la plateforme de vente de vélos.",
                version = "1.0.0",
                contact = @Contact(
                        name = "Mamadou Ndiaga Ndiaye",
                        email = "ndiaye.mamadou-ndiaga@ugb.edu.sn",
                        url = "http://ipsl.ugb.edu.sn/771367500"

                )
        ),
        servers = @Server(
                url = "{protocole}://{host}:{port}/{context}",
                description = "Serveur de l'API",
                variables = {
                        @ServerVariable(name = "protocole", defaultValue = "http"),
                        @ServerVariable(name = "host", defaultValue = "localhost"),
                        @ServerVariable(name = "port", defaultValue = "8080"),
                        @ServerVariable(name = "context", defaultValue = "AppVenteVelo-1.0-SNAPSHOT"),
                }

        ),
        tags = {
                @Tag(name = ApiServiceConfig.TAG_GESTION_ARTICLES_COMMANDES_NAME, description = "Services web permettant de gérer le CRUD (Ajout, liste, modification et suppression) sur les articles de commande."),
                @Tag(name = ApiServiceConfig.TAG_GESTION_CATEGORIES_NAME, description = "Services web permettant de gérer le CRUD (Ajout, liste, modification et suppression) sur les catégories de vélos."),
                @Tag(name = ApiServiceConfig.TAG_GESTION_CLIENTS_NAME, description = "Services web permettant de gérer le CRUD (Ajout, liste, modification et suppression) sur les clients."),
                @Tag(name = ApiServiceConfig.TAG_GESTION_COMMANDES_NAME, description = "Services web permettant de gérer le CRUD (Ajout, liste, modification et suppression sur les commandes.)"),
                @Tag(name = ApiServiceConfig.TAG_GESTION_EMPLOYES_NAME, description = "Services web permettant de gérer le CRUD (Ajout, liste, modification et suppression) sur les employés de magasins."),
                @Tag(name = ApiServiceConfig.TAG_GESTION_MAGASINS_NAME, description = "Services web permettant de gérer le CRUD (Ajout, liste, modification et suppression) sur les magasins de vélos."),
                @Tag(name = ApiServiceConfig.TAG_GESTION_MARQUES_NAME, description = "Services web permettant de gérer le CRUD (Ajout, liste, modification et suppression) sur les marques de vélos."),
                @Tag(name = ApiServiceConfig.TAG_GESTION_PRODUITS_NAME, description = "Services web permettant de gérer le CRUD (Ajout, liste, modification et suppression) sur les produits (vélos)."),
                @Tag(name = ApiServiceConfig.TAG_GESTION_STOCKS_NAME, description = "Services web permettant de gérer le CRUD (Ajout, liste, modification et suppression) sur les stocks de vélos dans les magasins."),

        }
)

@ApplicationPath("services")
public class ApiServiceConfig extends Application {
    public static final String TAG_GESTION_ARTICLES_COMMANDES_NAME = "Gestion des articles de commande";
    public static final String TAG_GESTION_CATEGORIES_NAME = "Gestion des catégories";
    public static final String TAG_GESTION_CLIENTS_NAME = "Gestion des clients";
    public static final String TAG_GESTION_COMMANDES_NAME = "Gestion des commandes";
    public static final String TAG_GESTION_EMPLOYES_NAME = "Gestion des employés";
    public static final String TAG_GESTION_MAGASINS_NAME = "Gestion des magasins";
    public static final String TAG_GESTION_MARQUES_NAME = "Gestion des marques";
    public static final String TAG_GESTION_PRODUITS_NAME = "Gestion des produits";
    public static final String TAG_GESTION_STOCKS_NAME = "Gestion des stocks";
}
