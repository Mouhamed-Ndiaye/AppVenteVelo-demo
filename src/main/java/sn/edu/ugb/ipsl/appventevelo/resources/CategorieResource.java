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
import sn.edu.ugb.ipsl.appventevelo.entities.Categorie;
import sn.edu.ugb.ipsl.appventevelo.facades.CategorieFacade;
import sn.edu.ugb.ipsl.appventevelo.resources.exceptions.*;

import java.util.List;


@Path("categories")
public class CategorieResource {

    @EJB
    private CategorieFacade categorieFacade;


    @GET
    @Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
    @Operation(
            tags = ApiServiceConfig.TAG_GESTION_CATEGORIES_NAME,
            summary = "Liste des catégories",
            description = "Renvoie la liste des catégories. Il est possible de filtrer les catégories par leur nom.",
            responses = {
                    @ApiResponse(
                            content = {
                                    @Content(
                                            mediaType = "application/json",
                                            examples = @ExampleObject("[\n" +
                                                    "  {\n" +
                                                    "    \"id\": 2,\n" +
                                                    "    \"nom\": \"Comfort Bicycles\"\n" +
                                                    "  }\n" +
                                                    "]")
                                    ),
                                    @Content(
                                            mediaType = "application/xml",
                                            schema = @Schema(implementation = Categorie.class)
                                    )
                            }
                    ),
                    @ApiResponse(
                            responseCode = "200",
                            description = " ===> Liste des catégories renvoyée avec succés."
                    ),
                    @ApiResponse(
                            responseCode = "604",
                            description = " ===> Renvoyé si aucune catégorie ne correspond au critère de recherche."
                    ),
                    @ApiResponse(
                            responseCode = "505",
                            description = " ===> Renvoyé, si La méthode utilisée est inapropriée."
                    )
            }
    )
    public List<Categorie> getCategories(
            @Parameter(name = "search", description = "Champ pour filtrer les catégories par leur nom.")
            @QueryParam("search") String nom) {
        if (nom != null && categorieFacade.searchCategorie(nom).isEmpty()) {
            throw new SearchNotFoundException("Aucune catégorie ne correspond au critère de recherche.");
        }

        if (nom != null) {
            return categorieFacade.searchCategorie(nom);
        }
        return categorieFacade.FindAll();
    }


    @Path("/{id}")
    @GET
    @Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
    @Operation(
            tags = ApiServiceConfig.TAG_GESTION_CATEGORIES_NAME,
            summary = "Obtenir une catégorie par ID",
            description = "Renvoie une catégorie spécifiée par son ID.",
            responses = {
                    @ApiResponse(
                            responseCode = "302",
                            description = " ===> Résultat de recherche. Catégorie renvoyée avec succès.",
                            content = {
                                    @Content(
                                            mediaType = "application/json",
                                            examples = @ExampleObject("{\n" +
                                                    "  \"id\": 5,\n" +
                                                    "  \"nom\": \"Electric Bikes\"\n" +
                                                    "}")
                                    ),
                                    @Content(
                                            mediaType = "application/xml",
                                            schema = @Schema(implementation = Categorie.class)
                                    )
                            }
                    ),
                    @ApiResponse(
                            responseCode = "404",
                            description = " ===> Renvoyé si la catégorie avec l'ID spécifié n'est pas trouvée."
                    ),
                    @ApiResponse(
                            responseCode = "505",
                            description = " ===> Renvoyé si la méthode utilisée est inapropriée."
                    )
            }
    )
    public Response getCategorieById(
            @Parameter(name = "ID de la catégorie", description = "ID de la catégorie qu'on veut obtenir.")
            @PathParam("id") Integer id) {
        Categorie categorieExistante = categorieFacade.FindById(id);
        if (categorieExistante == null) {
            throw new DataNotFoundException("Catégorie avec ID " + id + " non trouvée");
        }
        return Response.status(302)
                .entity(categorieExistante)
                .build();

    }


    @POST
    @Consumes({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
    @Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
    @Operation(
            tags = ApiServiceConfig.TAG_GESTION_CATEGORIES_NAME,
            summary = "Ajouter une nouvelle catégorie",
            description = "Ajoute une nouvelle catégorie.",
            responses = {
                    @ApiResponse(
                            responseCode = "201",
                            description = " ===> Catégorie ajoutée avec succès.",
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
                            description = " ===> Renvoyé si les données fournies sont incorrectes ou incomplètes."
                    ),
                    @ApiResponse(
                            responseCode = "509",
                            description = " ===> Renvoyé si la catégorie avec l'ID spécifié existe déjà.",
                            content = {
                                    @Content(
                                            mediaType = "application/json",
                                            examples = @ExampleObject("{\n" +
                                                    "  \"errorCode\": 509,\n" +
                                                    "  \"errorMessage\": \"La catégorie avec cet ID existe déjà.\"\n" +
                                                    "}")
                                    ),
                                    @Content(
                                            mediaType = "application/xml",
                                            schema = @Schema(implementation = ErrorMessage.class)
                                    )
                            }
                    ),
                    @ApiResponse(
                            responseCode = "100",
                            description = " ===> Renvoyé si l'ID n'est pas renseigné."
                    )
            }
    )
    public Response addCategorie(Categorie categorie) {
        if (categorie.getId() == null || categorie.getId().toString().trim().isEmpty() || categorie.getId().equals(0)) {
            throw new IdObligatoireException("L'ID de la catégorie est obligatoire et doit être différent de zéro.");
        }

        Categorie categorieExistante = categorieFacade.FindById(categorie.getId());
        if (categorieExistante != null) {
            throw new ConflictException("La catégorie avec cet ID existe déjà.");
        }

        categorieFacade.SaveCategorie(categorie);
        return Response.status(Response.Status.CREATED)
                .entity(new SuccessMessage("Catégorie ajoutée avec succès!", 201))
                .build();
    }

    @Path("/{id}")
    @PATCH
    @Consumes({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
    @Operation(
            tags = ApiServiceConfig.TAG_GESTION_CATEGORIES_NAME,
            summary = "Mettre à jour une catégorie",
            description = "Met à jour les détails d'une catégorie spécifiée par son ID.",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = " ===> Catégorie mise à jour avec succès.",
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
                            description = " ===> Renvoyé si la catégorie avec l'ID spécifié n'est pas trouvée.",
                            content = {
                                    @Content(
                                            mediaType = "application/json",
                                            examples = @ExampleObject("{\n" +
                                                    "  \"errorCode\": 404,\n" +
                                                    "  \"errorMessage\": \"La catégorie avec ID 5 non trouvée\"\n" +
                                                    "}")
                                    ),
                                    @Content(
                                            mediaType = "application/xml",
                                            schema = @Schema(implementation = ErrorMessage.class)
                                    )
                            }
                    ),
                    @ApiResponse(
                            responseCode = "400",
                            description = " ===> Données fournies incorrectes ou incomplètes."
                    )
            }
    )
    public Response updateCategorie(
            @Parameter(name = "ID de la catégorie", description = "L'ID de la catégorie à modifier")
            @PathParam("id") Integer id, Categorie updatedCategorie) {
        Categorie categorieExistante = categorieFacade.FindById(id);
        if (categorieExistante == null) {
            throw new DataNotFoundException("Catégorie avec ID " + id + " non trouvée.");
        }

        categorieExistante.setNom(updatedCategorie.getNom());
        categorieFacade.UpdateCategorie(categorieExistante);
        return Response.status(Response.Status.OK)
                .entity(new SuccessMessage("Catégorie mise à jour avec succès.", 200))
                .build();
    }


    @Path("/{id}")
    @DELETE
    @Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
    @Operation(
            tags = ApiServiceConfig.TAG_GESTION_CATEGORIES_NAME,
            summary = "Supprimer une catégorie",
            description = "Supprime une catégorie spécifiée par son ID.",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = " ===> Catégorie supprimée avec succès.",
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
                            description = " ===> Renvoyé si la catégorie avec l'ID spécifié n'est pas trouvée.",
                            content = {
                                    @Content(
                                            mediaType = "application/json",
                                            examples = @ExampleObject("{\n" +
                                                    "  \"errorCode\": 404,\n" +
                                                    "  \"errorMessage\": \"L'article avec ID 2 et 5 non trouvé\"\n" +
                                                    "}")
                                    ),
                                    @Content(
                                            mediaType = "application/xml",
                                            schema = @Schema(implementation = ErrorMessage.class)
                                    )
                            }
                    ),
                    @ApiResponse(
                            responseCode = "400",
                            description = " ===> Données fournies incorrectes ou incomplètes."
                    )
            }
    )
    public Response removeCategorie(
            @Parameter(name = "ID de la catégorie", description = "ID de la catégorie qu'on veut supprimer")
            @PathParam("id") Integer id) {
        Categorie categorieExistante = categorieFacade.FindById(id);
        if (categorieExistante == null) {
            throw new DataNotFoundException("Catégorie avec ID " + id + " non trouvée.");
        }
        categorieFacade.RemoveCategorie(id);
        return Response.status(Response.Status.OK)
                .entity(new SuccessMessage("Catégorie supprimée avec succès.", 200))
                .build();
    }


}
