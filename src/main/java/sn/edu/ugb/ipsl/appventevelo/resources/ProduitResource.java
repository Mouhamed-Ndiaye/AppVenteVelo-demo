package sn.edu.ugb.ipsl.appventevelo.resources;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import jakarta.ejb.EJB;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import sn.edu.ugb.ipsl.appventevelo.ApiServiceConfig;
import sn.edu.ugb.ipsl.appventevelo.dtoclasses.MiseAJourProduitDTO;
import sn.edu.ugb.ipsl.appventevelo.dtoclasses.NouveauProduitDTO;
import sn.edu.ugb.ipsl.appventevelo.entities.Categorie;
import sn.edu.ugb.ipsl.appventevelo.entities.Marque;
import sn.edu.ugb.ipsl.appventevelo.entities.Produit;
import sn.edu.ugb.ipsl.appventevelo.facades.CategorieFacade;
import sn.edu.ugb.ipsl.appventevelo.facades.MarqueFacade;
import sn.edu.ugb.ipsl.appventevelo.facades.ProduitFacade;
import sn.edu.ugb.ipsl.appventevelo.resources.exceptions.*;

import java.util.List;


@Path("produits")
public class ProduitResource {

    @EJB
    private ProduitFacade produitFacade;

    @EJB
    private CategorieFacade categorieFacade;

    @EJB
    private MarqueFacade marqueFacade;

    @GET
    @Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
    @Operation(
            tags = ApiServiceConfig.TAG_GESTION_PRODUITS_NAME,
            summary = "Obtenir la liste des produits",
            description = "Obtient la liste des produits avec la possibilité de filtrer par nom du produit, nom de la catégorie ou nom de la marque.",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = " ===> Liste des produits récupérée avec succès.",
                            content = {
                                    @Content(
                                            mediaType = "application/json",
                                            examples = @ExampleObject("[\n" +
                                                    "  {\n" +
                                                    "    \"annee_model\": 2016,\n" +
                                                    "    \"categorie\": {\n" +
                                                    "      \"id\": 6,\n" +
                                                    "      \"nom\": \"Mountain Bikes\"\n" +
                                                    "    },\n" +
                                                    "    \"id\": 1,\n" +
                                                    "    \"marque\": {\n" +
                                                    "      \"id\": 9,\n" +
                                                    "      \"nom\": \"Trek\"\n" +
                                                    "    },\n" +
                                                    "    \"nom\": \"Trek 820 - 2016\",\n" +
                                                    "    \"prix_depart\": 379.99\n" +
                                                    "  }\n" +
                                                    "]")
                                    ),
                                    @Content(
                                            mediaType = "application/xml",
                                            schema = @Schema(implementation = Produit.class)
                                    )
                            }
                    ),
                    @ApiResponse(
                            responseCode = "604",
                            description = "Aucun produit trouvé."
                    ),
            }
    )
    public List<Produit> getProduits(
            @Parameter(name = "search", description = "Champ pour filtrer les produits par leur nom, nom de catégorie ou nom de marque.")
            @QueryParam("search") String nom) {
        if (nom != null && produitFacade.searchProduit(nom).isEmpty()) {
            throw new SearchNotFoundException("Aucun produit ne correspond au critère de recherche.");
        }

        if (nom != null) {
            return produitFacade.searchProduit(nom);
        }
        return produitFacade.FindAll();
    }

    @Path("/{id}")
    @GET
    @Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
    @Operation(
            tags = ApiServiceConfig.TAG_GESTION_PRODUITS_NAME,
            summary = "Obtenir un produit par son ID",
            description = "Obtient un produit spécifique en utilisant son ID.",
            responses = {
                    @ApiResponse(
                            responseCode = "302",
                            description = " ===> Liste des produits récupérée avec succès.",
                            content = {
                                    @Content(
                                            mediaType = "application/json",
                                            examples = @ExampleObject("{\n" +
                                                    "  \"annee_model\": 2016,\n" +
                                                    "  \"categorie\": {\n" +
                                                    "    \"id\": 6,\n" +
                                                    "    \"nom\": \"Mountain Bikes\"\n" +
                                                    "  },\n" +
                                                    "  \"id\": 1,\n" +
                                                    "  \"marque\": {\n" +
                                                    "    \"id\": 9,\n" +
                                                    "    \"nom\": \"Trek\"\n" +
                                                    "  },\n" +
                                                    "  \"nom\": \"Trek 820 - 2016\",\n" +
                                                    "  \"prix_depart\": 379.99\n" +
                                                    "}")
                                    ),
                                    @Content(
                                            mediaType = "application/xml",
                                            schema = @Schema(implementation = Produit.class)
                                    )
                            }
                    ),
                    @ApiResponse(
                            responseCode = "404",
                            description = " ===> Produit avec l'ID spécifié non trouvé."
                    )
            }
    )
    public Response getProduitById(
            @Parameter(name = "ID du produit", description = "ID du produit à obtenir")
            @PathParam("id") Integer id) {
        Produit produitExistant = produitFacade.FindById(id);
        if (produitExistant == null) {
            throw new DataNotFoundException("Produit avec ID "+ id +" non trouvé");
        }
        return Response.status(Response.Status.FOUND)
                .entity(produitExistant)
                .build();
    }



    @POST
    @Consumes({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
    @Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
    @Operation(
            tags = ApiServiceConfig.TAG_GESTION_PRODUITS_NAME,
            summary = "Ajouter un nouveau produit",
            description = "Ajoute un nouveau produit.",
            responses = {
                    @ApiResponse(
                            responseCode = "201",
                            description = " ===> Produit ajouté avec succès.",
                            content = {
                                    @Content(
                                            mediaType = "application/json",
                                            schema = @Schema(implementation = SuccessMessage.class)
                                    ),
                                    @Content(
                                            mediaType = "application/xml",
                                            schema = @Schema(implementation = SuccessMessage.class)
                                    )
                            }
                    ),
                    @ApiResponse(
                            responseCode = "600",
                            description = " ===> Données obligatoires non fournies(nom du produit)."
                    ),
                    @ApiResponse(
                            responseCode = "400",
                            description = " ===> Données fournies incorrectes ou incomplètes."
                    ),
                    @ApiResponse(
                            responseCode = "100",
                            description = " ===> Renvoyé si l'ID n'est pas renseigné."
                    ),
                    @ApiResponse(
                            responseCode = "509",
                            description = " ===> Renvoyé si le produit avec l'ID spécifié existe déjà."
                    ),
                    @ApiResponse(
                            responseCode = "404",
                            description = " ===> Renvoyé si la marque ou la catégorie avec l'ID spécifié n'existe pas."
                    ),
                    @ApiResponse(
                            responseCode = "508",
                            description = " ===> Renvoyé si les ID des clés étrangères(catégorie et marque) ne sont pas sont renseignés."
                    ),
            }
    )
    public Response addProduit(NouveauProduitDTO nouveauProduitDTO) {
        Integer produitId = nouveauProduitDTO.getId();
        if (produitId == null) {
            throw new IdObligatoireException("L'ID du produit est obligatoire");
        }

        Produit produitExistant = produitFacade.FindById(produitId);
        if (produitExistant != null){
            throw new ConflictException("Ce produit existe déjà");
        }



        if (nouveauProduitDTO.getNom() == null){
            throw new ChampObligatoireException("Le nom du produit est obligatoire.");
        }


        Produit produit = new Produit();


        if (nouveauProduitDTO.getMarque() != null){
            Marque marqueExistante = marqueFacade.FindById(nouveauProduitDTO.getMarque());
            if (marqueExistante == null){
                throw new DataNotFoundException("Cette marque n'existe pas.");
            }
            produit.setMarque(marqueExistante);
        } else {
            throw new CleEtrangereObligatoireException("L'ID de la marque est obligatoire");
        }


        if (nouveauProduitDTO.getCategorie() != null){
            Categorie categorieExistante = categorieFacade.FindById(nouveauProduitDTO.getCategorie());
            if (categorieExistante == null){
                throw new DataNotFoundException("Cette catégorie n'existe pas.");
            }
            produit.setCategorie(categorieExistante);
        } else {
            throw new CleEtrangereObligatoireException("L'ID de la catégorie est obligatoire");
        }


        produit.setId(nouveauProduitDTO.getId());
        produit.setNom(nouveauProduitDTO.getNom());

        produit.setAnnee_model(nouveauProduitDTO.getAnnee_model());
        produit.setPrix_depart(nouveauProduitDTO.getPrix_depart());

        produitFacade.SaveProduit(produit);

        return Response.status(Response.Status.CREATED)
                .entity(new SuccessMessage("Produit ajouté avec succès!", 201))
                .build();
    }

    @Path("/{id}")
    @PATCH
    @Consumes({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
    @Operation(
            tags = ApiServiceConfig.TAG_GESTION_PRODUITS_NAME,
            summary = "Mettre à jour un produit",
            description = "Met à jour un produit spécifique en utilisant son ID.",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = " ===> Produit mis à jour avec succès.",
                            content = {
                                    @Content(
                                            mediaType = "application/json",
                                            schema = @Schema(implementation = SuccessMessage.class)
                                    ),
                                    @Content(
                                            mediaType = "application/xml",
                                            schema = @Schema(implementation = SuccessMessage.class)
                                    )
                            }
                    ),
                    @ApiResponse(
                            responseCode = "400",
                            description = " ===> Données fournies incorrectes ou incomplètes."
                    ),
                    @ApiResponse(
                            responseCode = "404",
                            description = " ===> Produit, Marque ou Catégorie avec l'ID spécifié non trouvé."
                    )
            }
    )
    public Response updateProduit(
            @Parameter(name = "ID du produit", description = "Produit à modifier")
            @PathParam("id") Integer id,
            MiseAJourProduitDTO updatedProduitDTO) {

        Produit produitExistant = produitFacade.FindById(id);
        if (produitExistant == null) {
            throw new DataNotFoundException("Produit avec ID " + id + " non trouvé.");
        }

        if (updatedProduitDTO.getNom() != null){
            produitExistant.setNom(updatedProduitDTO.getNom());
        }

        if (updatedProduitDTO.getMarque() != null) {
            Marque marque = marqueFacade.FindById(updatedProduitDTO.getMarque());
            if (marque == null) {
                throw new DataNotFoundException("La marque avec cet ID n'existe pas.");
            } else {
                produitExistant.setMarque(marque);
            }
        }

        if (updatedProduitDTO.getCategorie() != null) {
            Categorie categorie = categorieFacade.FindById(updatedProduitDTO.getCategorie());
            if (categorie == null) {
                throw new DataNotFoundException("La catégorie avec cet ID n'existe pas.");
            } else {
                produitExistant.setCategorie(categorie);
            }
        }

       if (updatedProduitDTO.getAnnee_model() != 0){
           produitExistant.setAnnee_model(updatedProduitDTO.getAnnee_model());
       }

       if (updatedProduitDTO.getPrix_depart() != null){
           produitExistant.setPrix_depart(updatedProduitDTO.getPrix_depart());
       }

        produitFacade.UpdateProduit(produitExistant);

        return Response.status(Response.Status.OK)
                .entity(new SuccessMessage("Produit mis à jour avec succès.", 200))
                .build();

    }

    @Path("/{id}")
    @DELETE
    @Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
    @Operation(
            tags = ApiServiceConfig.TAG_GESTION_PRODUITS_NAME,
            summary = "Supprimer un produit",
            description = "Supprime un produit spécifié par son ID.",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = " ===> Produit supprimé avec succès.",
                            content = {
                                    @Content(
                                            mediaType = "application/json",
                                            schema = @Schema(implementation = SuccessMessage.class)
                                    ),
                                    @Content(
                                            mediaType = "application/xml",
                                            schema = @Schema(implementation = SuccessMessage.class)
                                    )
                            }
                    ),
                    @ApiResponse(
                            responseCode = "404",
                            description = " ===> Produit avec l'ID spécifié non trouvé."
                    )
            }
    )
    public Response removeProduit(
            @Parameter(name = "ID du produit", description = "Produit à supprimer")
            @PathParam("id") Integer id) {
        Produit produitExistant = produitFacade.FindById(id);
        if (produitExistant == null) {
            throw new DataNotFoundException("Produit avec ID " + id + " non trouvé.");
        }
        produitFacade.RemoveProduit(id);
        return Response.status(Response.Status.OK)
                .entity(new SuccessMessage("Produit supprimé avec succès.", 200))
                .build();
    }

}
