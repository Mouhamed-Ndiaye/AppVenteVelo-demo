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
import sn.edu.ugb.ipsl.appventevelo.entities.Marque;
import sn.edu.ugb.ipsl.appventevelo.facades.MarqueFacade;
import sn.edu.ugb.ipsl.appventevelo.resources.exceptions.*;

import java.util.List;


@Path("marques")
public class MarqueResource {

    @EJB
    private MarqueFacade marqueFacade;


    @GET
    @Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
    @Operation(
            tags = ApiServiceConfig.TAG_GESTION_MARQUES_NAME,
            summary = "Liste des marques",
            description = "Renvoie la liste des marques. Il est possible de filtrer les marques par leur nom.",
            responses = {
                    @ApiResponse(
                            content = {
                                    @Content(
                                            mediaType = "application/json",
                                            examples = @ExampleObject("[\n" +
                                                    "  {\n" +
                                                    "    \"id\": 5,\n" +
                                                    "    \"nom\": \"Ritchey\"\n" +
                                                    "  }\n" +
                                                    "]")
                                    ),
                                    @Content(
                                            mediaType = "application/xml",
                                            schema = @Schema(implementation = Marque.class)
                                    )
                            }
                    ),
                    @ApiResponse(
                            responseCode = "200",
                            description = " ===> Liste des marques renvoyée avec succès."
                    ),
                    @ApiResponse(
                            responseCode = "604",
                            description = " ===> Renvoyé si aucune marque ne correspond au critère de recherche."
                    ),
                    @ApiResponse(
                            responseCode = "505",
                            description = " ===> Renvoyé si la méthode utilisée est inappropriée."
                    ),
                    @ApiResponse(
                            responseCode = "400",
                            description = " ===> Données fournies incorrectes ou incomplètes."
                    )
            }
    )
    public List<Marque> getMarques(
            @Parameter(name = "search", description = "Champ pour filtrer les marques par leur nom.")
            @QueryParam("search") String nom) {
        if (nom != null && marqueFacade.searchMarque(nom).isEmpty()) {
            throw new SearchNotFoundException("Aucune marque ne correspond au critère de recherche.");
        }

        if (nom != null) {
            return marqueFacade.searchMarque(nom);
        }
        return marqueFacade.FindAll();
    }

    @Path("/{id}")
    @GET
    @Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
    @Operation(
            tags = ApiServiceConfig.TAG_GESTION_MARQUES_NAME,
            summary = "Obtenir une marque par ID",
            description = "Renvoie une marque spécifiée par son ID.",
            responses = {
                    @ApiResponse(
                            responseCode = "302",
                            description = " ===> Marque renvoyée avec succès.",
                            content = {
                                    @Content(
                                            mediaType = "application/json",
                                            examples = @ExampleObject("{\n" +
                                                    "  \"id\": 1,\n" +
                                                    "  \"nom\": \"Heller\"\n" +
                                                    "}")
                                    ),
                                    @Content(
                                            mediaType = "application/xml",
                                            schema = @Schema(implementation = ErrorMessage.class)
                                    )
                            }
                    ),
                    @ApiResponse(
                            responseCode = "404",
                            description = " ===> Marque avec l'ID spécifié non trouvée."
                    ),
                    @ApiResponse(
                            responseCode = "400",
                            description = " ===> Données fournies incorrectes ou incomplètes."
                    )
            }
    )
    public Response getMarqueById(
            @Parameter(name = "ID de la marque", description = "ID de la marque à obtenir")
            @PathParam("id") Integer id) {
        Marque marqueExistante = marqueFacade.FindById(id);
        if (marqueExistante == null) {
            throw new DataNotFoundException("Marque avec ID " + id + " non trouvée");
        }
        return Response.status(Response.Status.FOUND)
                .entity(marqueExistante)
                .build();
    }


    @POST
    @Consumes({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
    @Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
    @Operation(
            tags = ApiServiceConfig.TAG_GESTION_MARQUES_NAME,
            summary = "Ajouter une nouvelle marque",
            description = "Ajoute une nouvelle marque.",
            responses = {
                    @ApiResponse(
                            responseCode = "201",
                            description = " ===> Marque ajoutée avec succès.",
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
                            responseCode = "509",
                            description = " ===> Marque avec l'ID spécifié existe déjà."
                    ),
                    @ApiResponse(
                            responseCode = "100",
                            description = " ===> Renvoyé si l'ID n'est pas renseigné."
                    )
            }
    )
    public Response addMarque(Marque marque) {
        if (marque.getId() == null || marque.getId().toString().trim().isEmpty() || marque.getId().equals(0)) {
            throw new IdObligatoireException("L'ID de la marque est obligatoire et doit être différent de zéro.");
        }

        Marque marqueExistante = marqueFacade.FindById(marque.getId());
        if (marqueExistante != null) {
            throw new ConflictException("La marque avec cet ID existe déjà.");
        }

        marqueFacade.SaveMarque(marque);
        return Response.status(Response.Status.CREATED)
                .entity(new SuccessMessage("Marque ajoutée avec succès!", 201))
                .build();
    }

    @Path("/{id}")
    @PATCH
    @Consumes({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
    @Operation(
            tags = ApiServiceConfig.TAG_GESTION_MARQUES_NAME,
            summary = "Mettre à jour une marque",
            description = "Met à jour les détails d'une marque spécifiée par son ID.",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = " ===> Marque mise à jour avec succès.",
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
                            description = " ===> Marque avec l'ID spécifié non trouvée."
                    ),
                    @ApiResponse(
                            responseCode = "400",
                            description = " ===> Données fournies incorrectes ou incomplètes."
                    )
            }
    )
    public Response updateMarque(
            @Parameter(name = "ID de la marque", description = "Marque à modifier")
            @PathParam("id") Integer id, Marque updatedMarque) {
        Marque marqueExistante = marqueFacade.FindById(id);
        if (marqueExistante == null) {
            throw new DataNotFoundException("Marque avec ID " + id + " non trouvée.");
        }

        marqueExistante.setNom(updatedMarque.getNom());
        marqueFacade.UpdateMarque(marqueExistante);
        return Response.status(Response.Status.OK)
                .entity(new SuccessMessage("Marque mise à jour avec succès.", 200))
                .build();
    }


    @Path("/{id}")
    @DELETE
    @Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
    @Operation(
            tags = ApiServiceConfig.TAG_GESTION_MARQUES_NAME,
            summary = "Supprimer une marque",
            description = "Supprime une marque spécifiée par son ID.",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = " ===> Marque supprimée avec succès.",
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
                            description = " ===> Marque avec l'ID spécifié non trouvée."
                    ),
                    @ApiResponse(
                            responseCode = "400",
                            description = " ===> Données fournies incorrectes ou incomplètes."
                    )
            }
    )
    public Response removeMarque(
            @Parameter(name = "ID de la marque", description = "Marque à supprimer")
            @PathParam("id") Integer id) {
        Marque marqueExistante = marqueFacade.FindById(id);
        if (marqueExistante == null) {
            throw new DataNotFoundException("Marque avec ID " + id + " non trouvée.");
        }
        marqueFacade.RemoveMarque(id);
        return Response.status(Response.Status.OK)
                .entity(new SuccessMessage("Marque supprimée avec succès.", 200))
                .build();
    }

}
