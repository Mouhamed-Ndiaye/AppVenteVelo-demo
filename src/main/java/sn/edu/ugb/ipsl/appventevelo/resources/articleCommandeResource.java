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
import sn.edu.ugb.ipsl.appventevelo.dtoclasses.ArticleCommandeDTO;
import sn.edu.ugb.ipsl.appventevelo.dtoclasses.MiseAJourArticleCommandeDTO;
import sn.edu.ugb.ipsl.appventevelo.dtoclasses.NouvelArticleCommandeDTO;
import sn.edu.ugb.ipsl.appventevelo.entities.ArticleCommande;
import sn.edu.ugb.ipsl.appventevelo.entities.Commande;
import sn.edu.ugb.ipsl.appventevelo.entities.Produit;
import sn.edu.ugb.ipsl.appventevelo.facades.ArticleCommandeFacade;
import sn.edu.ugb.ipsl.appventevelo.facades.CommandeFacade;
import sn.edu.ugb.ipsl.appventevelo.facades.ProduitFacade;
import sn.edu.ugb.ipsl.appventevelo.resources.exceptions.*;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;


@Path("articleCommandes")
public class articleCommandeResource {

    @EJB
    private ArticleCommandeFacade articleCommandeFacade;

    @EJB
    private CommandeFacade commandeFacade;

    @EJB
    private ProduitFacade produitFacade;


    @GET
    @Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
    @Operation(
            tags = ApiServiceConfig.TAG_GESTION_ARTICLES_COMMANDES_NAME,
            summary = "Liste des articles de commandes",
            description = "Renvoie la liste des articles de commandes. " +
                    "Il est possible de donner un paramétre(optionnel) de filtrage pour rechercher un article spécifiqua à travers son prix, sa remise ou sa quantité.",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = " ===> Liste des articles renvoyés avec succés",
                            content = {
                                    @Content(
                                            mediaType = "application/json",
                                            examples = @ExampleObject("[{\n" +
                                                    "  \"numeroCommande\": 1,\n" +
                                                    "  \"ligne\": 1,\n" +
                                                    "  \"produit\": {\n" +
                                                    "    \"id\": 1,\n" +
                                                    "    \"nom\": \"Trek 820 - 2016\",\n" +
                                                    "    \"marque\": {\n" +
                                                    "      \"id\": 9,\n" +
                                                    "      \"nom\": \"Trek\"\n" +
                                                    "    },\n" +
                                                    "    \"categorie\": {\n" +
                                                    "      \"id\": 6,\n" +
                                                    "      \"nom\": \"Mountain Bikes\"\n" +
                                                    "    },\n" +
                                                    "    \"annee_model\": 2016,\n" +
                                                    "    \"prix_depart\": 379.99\n" +
                                                    "  },\n" +
                                                    "  \"quantite\": 1,\n" +
                                                    "  \"remise\": 0\n" +
                                                    "}\n"+
                                                    "]")
                                    ),
                                    @Content(
                                            mediaType = "application/xml",
                                            schema = @Schema(implementation = ArticleCommandeDTO.class)
                                    )
                            }
                    ),

                    @ApiResponse(
                            responseCode = "505",
                            description = " ===> Renvoyé, si La méthode utilisée est inapropriée."
                    ),

                    @ApiResponse(
                            responseCode = "604",
                            description = " ===> Renvoyé, si le paramétre de recherche ne correspond à aucun article."
                    ),
                    @ApiResponse(
                            responseCode = "400",
                            description = " ===> Données fournies incorrectes ou incomplètes."
                    )
            }

    )
    public List<ArticleCommandeDTO> getArticleCommandes(
            @Parameter(name = "search", description = "Champ pour filtrer par le prix, la remise ou la quantité.")
            @QueryParam("search")
            BigDecimal parameter) {
        if (parameter != null && articleCommandeFacade.searchArticleCommande(parameter).isEmpty()) {
            throw new SearchNotFoundException("Aucun article ne correspond au critère de recherche.");
        }

        if (parameter != null) {
            List<ArticleCommande> articleCommandes = articleCommandeFacade.searchArticleCommande(parameter);
            List<ArticleCommandeDTO> articleCommandeDTOS = new ArrayList<>();

            for (ArticleCommande articleCommande : articleCommandes) {
                ArticleCommandeDTO dto = mapToDTO(articleCommande);
                articleCommandeDTOS.add(dto);
            }

            return articleCommandeDTOS;
        }
        List<ArticleCommande> articleCommandes = articleCommandeFacade.FindAll();
        List<ArticleCommandeDTO> dtos = new ArrayList<>();

        for (ArticleCommande articleCommande : articleCommandes) {
            ArticleCommandeDTO dto = mapToDTO(articleCommande);
            dtos.add(dto);
        }

        return dtos;
    }

    @Path("/{numero}/{ligne}")
    @GET
    @Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
    @Operation(
            tags = ApiServiceConfig.TAG_GESTION_ARTICLES_COMMANDES_NAME,
            summary = "Obtenir un article de commande par son ID",
            description = "Renvoie un article de commande en fonction de son numéro de commande et de sa ligne.",
            responses = {
                    @ApiResponse(
                            responseCode = "404",
                            description = " ===> Renvoyé si l'article de commande n'est pas trouvé.",
                            content = {
                                    @Content(
                                            mediaType = "application/json",
                                            examples = @ExampleObject("{\n" +
                                                    "  \"DOCUMENTATION\": \"localhost:8080/AppVenteVelo-1.0-SNAPSHOT/documentations/index.html\",\n" +
                                                    "  \"errorCode\": 404,\n" +
                                                    "  \"errorMessage\": \"L'article avec ID 1 et 1444 non trouvé\"\n" +
                                                    "}")
                                    ),
                                    @Content(
                                            mediaType = "application/xml",
                                            schema = @Schema(implementation = ErrorMessage.class)
                                    )
                            }

                    ),

                    @ApiResponse(
                            responseCode = "302",
                            description = " ===> Article de commande trouvé et renvoyé avec succès.",
                            content = {
                                    @Content(
                                            mediaType = "application/json",
                                            examples = @ExampleObject("{\n" +
                                                    "  \"ligne\": 1,\n" +
                                                    "  \"numeroCommande\": 1444,\n" +
                                                    "  \"produit\": {\n" +
                                                    "    \"annee_model\": 2016,\n" +
                                                    "    \"categorie\": {\n" +
                                                    "      \"id\": 6,\n" +
                                                    "      \"nom\": \"Mountain Bikes\"\n" +
                                                    "    },\n" +
                                                    "    \"id\": 2,\n" +
                                                    "    \"marque\": {\n" +
                                                    "      \"id\": 5,\n" +
                                                    "      \"nom\": \"Ritchey\"\n" +
                                                    "    },\n" +
                                                    "    \"nom\": \"Ritchey Timberwolf Frameset - 2016\",\n" +
                                                    "    \"prix_depart\": 749.99\n" +
                                                    "  },\n" +
                                                    "  \"quantite\": 2,\n" +
                                                    "  \"remise\": 0.05\n" +
                                                    "}")
                                    ),
                                    @Content(
                                            mediaType = "application/xml",
                                            schema = @Schema(implementation = ArticleCommandeDTO.class)
                                    )
                            }
                    ),
                    @ApiResponse(
                            responseCode = "505",
                            description = " ===> Renvoyé, si La méthode utilisée est inapropriée."
                    ),
                    @ApiResponse(
                            responseCode = "400",
                            description = " ===> Données fournies incorrectes ou incomplètes."
                    )
            }
    )
    public Response getArticleCommandeById(
            @Parameter(name = "Numéro", description = "Numéro de commande référencé qu'on veut obtenir")
            @PathParam("numero") Integer numero,
            @Parameter(name = "Ligne", description = "Ligne de l'article qu'on veut obtenir")
            @PathParam("ligne") Integer ligne) {
        ArticleCommande articleCommandeExistant = articleCommandeFacade.FindById(numero, ligne);
        if (articleCommandeExistant == null) {
            throw new DataNotFoundException("L'article avec ID " + numero + " et " + ligne + " non trouvé");
        }

        ArticleCommandeDTO articleCommandeDTO = mapToDTO(articleCommandeExistant);
        return Response.status(302)
                .entity(articleCommandeDTO)
                .build();
    }

    // Mapper l'entité vers le DTO
    private ArticleCommandeDTO mapToDTO(ArticleCommande articleCommande) {
        ArticleCommandeDTO dto = new ArticleCommandeDTO();
        dto.setNumeroCommande(articleCommande.getNumeroCommande().getNumero());
        dto.setLigne(articleCommande.getLigne());
        dto.setProduit(articleCommande.getProduit());
        dto.setQuantite(articleCommande.getQuantite());
        dto.setPrixDepart(articleCommande.getPrix_depart());
        dto.setRemise(articleCommande.getRemise());
        return dto;
    }


    @POST
    @Consumes({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
    @Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
    @Operation(
            tags = ApiServiceConfig.TAG_GESTION_ARTICLES_COMMANDES_NAME,
            summary = "Ajouter un nouvel article de commande",
            description = "Ajoute un nouvel article de commande à la commande spécifiée.",
            responses = {
                    @ApiResponse(
                            responseCode = "505",
                            description = " ===> Renvoyé, si La méthode utilisée est inapropriée."
                    ),

                    @ApiResponse(
                            responseCode = "201",
                            description = " ===> Article de commande ajouté avec succès.",
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
                            responseCode = "100",
                            description = " ===> Renvoyé si le(les) ID n'est(sont) pas renseigné(s).",
                            content = {
                                    @Content(
                                            mediaType = "application/json",
                                            examples = @ExampleObject("{\n" +
                                                    "  \"DOCUMENTATION\": \"localhost:8080/AppVenteVelo-1.0-SNAPSHOT/documentations/index.html\",\n" +
                                                    "  \"errorCode\": 404,\n" +
                                                    "  \"errorMessage\": \"L'ID est obligatoire et doit être différent de zéro.\"\n" +
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
                            description = " ===> Renvoyé si la commande ou le produit est introuvable.",
                            content = {
                                    @Content(
                                            mediaType = "application/json",
                                            examples = @ExampleObject("{\n" +
                                                    "  \"errorCode\": 404,\n" +
                                                    "  \"errorMessage\": \"La commande avec cet ID n'existe pas.\"\n" +
                                                    "}")
                                    ),
                                    @Content(
                                            mediaType = "application/json",
                                            examples = @ExampleObject("{\n" +
                                                    "  \"errorCode\": 404,\n" +
                                                    "  \"errorMessage\": \"Le produit avec cet ID n'existe pas.\"\n" +
                                                    "}")
                                    ),
                                    @Content(
                                            mediaType = "application/xml",
                                            schema = @Schema(implementation = ErrorMessage.class)
                                    )
                            }
                    ),
                    @ApiResponse(
                            responseCode = "509",
                            description = " ===> Renvoyé si l'article existe déjà'.",
                            content = {
                                    @Content(
                                            mediaType = "application/json",
                                            examples = @ExampleObject("{\n" +
                                                    "  \"errorCode\": 509,\n" +
                                                    "  \"errorMessage\": \"L'article avec cet ID existe déjà.\"\n" +
                                                    "}")
                                    ),
                                    @Content(
                                            mediaType = "application/xml",
                                            schema = @Schema(implementation = ErrorMessage.class)
                                    )
                            }
                    ),
                    @ApiResponse(
                            responseCode = "508",
                            description = " ===> Renvoyé si la(les) clé(s) étrangère(s) n'est(sont) pas renseignée(s).",
                            content = {
                                    @Content(
                                            mediaType = "application/json",
                                            examples = @ExampleObject("{\n" +
                                                    "  \"errorCode\": 404,\n" +
                                                    "  \"errorMessage\": \"L'ID du produit est obligatoire.\"\n" +
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
    public Response addArticleCommande(NouvelArticleCommandeDTO nouvelArticleDTO) {
        if (nouvelArticleDTO.getLigne() == null || nouvelArticleDTO.getNumeroCommande() == null) {
            throw new IdObligatoireException("L'ID de l'article est obligatoire et doit être différent de zéro.");
        }

        Commande commande = commandeFacade.FindById(nouvelArticleDTO.getNumeroCommande());
        if (commande == null) {
            throw new DataNotFoundException("La commande avec cet ID n'existe pas.");
        }

        ArticleCommande articleCommandeExistant = articleCommandeFacade.FindById(nouvelArticleDTO.getNumeroCommande(), nouvelArticleDTO.getLigne());
        if (articleCommandeExistant != null) {
            throw new ConflictException("L'article avec cet ID existe déjà.");
        }

        Integer produitId = nouvelArticleDTO.getProduit();
        if (produitId == null) {
            throw new CleEtrangereObligatoireException("L'Id du produit est obligatoire");
        }

        Produit produit = produitFacade.FindById(produitId);
        if (produit != null) {
            ArticleCommande articleCommande = new ArticleCommande();
            articleCommande.setNumeroCommande(new Commande(nouvelArticleDTO.getNumeroCommande()));
            articleCommande.setLigne(nouvelArticleDTO.getLigne());
            articleCommande.setProduit(produit);
            articleCommande.setQuantite(nouvelArticleDTO.getQuantite());
            articleCommande.setPrix_depart(nouvelArticleDTO.getPrixDepart());
            articleCommande.setRemise(nouvelArticleDTO.getRemise());
            articleCommandeFacade.SaveArticleCommande(articleCommande);
        } else {
            throw new DataNotFoundException("Le produit avec cet ID n'existe pas.");
        }

        return Response.status(Response.Status.CREATED)
                .entity(new SuccessMessage("Article de commande ajouté avec succès!", 201))
                .build();
    }

    @Path("/{numero}/{ligne}")
    @PATCH
    @Consumes({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
    @Operation(
            tags = ApiServiceConfig.TAG_GESTION_ARTICLES_COMMANDES_NAME,
            summary = "Mettre à jour un article de commande",
            description = "Met à jour les détails d'un article de commande spécifié par son numéro de commande et sa ligne.",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = " ===> Article de commande mis à jour avec succès.",
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
                            description = " ===> Renvoyé si l'article de commande n'est pas trouvé.",
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
                            description = " ===> Renvoyé si les données fournies sont incorrectes ou incomplètes."
                    ),
                    @ApiResponse(
                            responseCode = "404",
                            description = " ===> Renvoyé si on souhaite modifier le produit et qu'il est inexistant.",
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
                            responseCode = "505",
                            description = " ===> Renvoyé, si La méthode utilisée est inapropriée."
                    ),
                    @ApiResponse(
                            responseCode = "400",
                            description = " ===> Données fournies incorrectes ou incomplètes."
                    )
            }
    )
    public Response updateArticleCommande(
            @Parameter(name = "Numéro", description = "Numéro de commande référencé à modifier")
            @PathParam("numero") Integer numero,
            @Parameter(name = "Ligne", description = "Ligne de l'article à modifier")
            @PathParam("ligne") Integer ligne,
            MiseAJourArticleCommandeDTO updatedArticleDTO) {

        ArticleCommande articleExistant = articleCommandeFacade.FindById(numero, ligne);
        if (articleExistant == null) {
            throw new DataNotFoundException("Article avec ID " + numero + " et " + ligne + " non trouvé.");
        }

        Commande commandeExistante = commandeFacade.FindById(numero);
        if (commandeExistante == null) {
            throw new DataNotFoundException("Cette commande n'existe pas.");
        }

        if (updatedArticleDTO.getProduit() != null) {
            Produit produit = produitFacade.FindById(updatedArticleDTO.getProduit());
            if (produit == null) {
                throw new DataNotFoundException("Le produit avec cet ID n'existe pas.");
            } else {
                articleExistant.setProduit(produit);
            }
        }


        if (updatedArticleDTO.getQuantite() != null) {
            articleExistant.setQuantite(updatedArticleDTO.getQuantite());
        } else {
            articleExistant.setQuantite(0);
        }


        if (updatedArticleDTO.getPrixDepart() != null) {
            articleExistant.setPrix_depart(updatedArticleDTO.getPrixDepart());
        } else {
            articleExistant.setPrix_depart(null);
        }


        if (updatedArticleDTO.getRemise() != null) {
            articleExistant.setRemise(updatedArticleDTO.getRemise());
        } else {
            articleExistant.setRemise(null);
        }

        articleCommandeFacade.UpdateArticleCommande(articleExistant);

        return Response.status(Response.Status.OK)
                .entity(new SuccessMessage("Article mis à jour avec succès.", 200))
                .build();

    }


    @Path("/{numero}/{ligne}")
    @DELETE
    @Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
    @Operation(
            tags = ApiServiceConfig.TAG_GESTION_ARTICLES_COMMANDES_NAME,
            summary = "Supprimer un article de commande",
            description = "Supprime un article de commande spécifié par son numéro de commande et sa ligne.",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = " ===> Article de commande supprimé avec succès.",
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
                            description = " ===> Renvoyé si l'article de commande n'est pas trouvé."
                    ),
                    @ApiResponse(
                            responseCode = "404",
                            description = " ===> Renvoyé si l'article de commande n'est pas trouvé.",
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
                            responseCode = "505",
                            description = " ===> Renvoyé, si La méthode utilisée est inapropriée."
                    ),
                    @ApiResponse(
                            responseCode = "400",
                            description = " ===> Données fournies incorrectes ou incomplètes."
                    )
            }
    )
    public Response removeArticleCommande(
            @Parameter(name = "Numéro", description = "Numéro de commande référencé à supprimer")
            @PathParam("numero") Integer numero,
            @Parameter(name = "Ligne", description = "Ligne de l'article à supprimer")
            @PathParam("ligne") Integer ligne) {

        Commande commandeExistante = commandeFacade.FindById(numero);
        if (commandeExistante == null) {
            throw new DataNotFoundException("Cette commande n'existe pas.");
        }

        ArticleCommande articleCommandeExistant = articleCommandeFacade.FindById(numero, ligne);
        if (articleCommandeExistant == null) {
            throw new DataNotFoundException("Article avec ID " + numero + " et " + ligne + " non trouvé.");
        }

        articleCommandeFacade.RemoveArticleCommande(numero, ligne);
        return Response.status(Response.Status.OK)
                .entity(new SuccessMessage("Article supprimé avec succès.", 200))
                .build();
    }

}
