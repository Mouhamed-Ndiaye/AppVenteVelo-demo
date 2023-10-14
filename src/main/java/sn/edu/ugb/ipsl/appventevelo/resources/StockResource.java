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
import sn.edu.ugb.ipsl.appventevelo.dtoclasses.MiseAJourStockDTO;
import sn.edu.ugb.ipsl.appventevelo.dtoclasses.NouveauStockDTO;
import sn.edu.ugb.ipsl.appventevelo.entities.Magasin;
import sn.edu.ugb.ipsl.appventevelo.entities.Produit;
import sn.edu.ugb.ipsl.appventevelo.entities.Stock;
import sn.edu.ugb.ipsl.appventevelo.facades.MagasinFacade;
import sn.edu.ugb.ipsl.appventevelo.facades.ProduitFacade;
import sn.edu.ugb.ipsl.appventevelo.facades.StockFacade;
import sn.edu.ugb.ipsl.appventevelo.resources.exceptions.*;

import java.util.List;


@Path("stocks")
public class StockResource {

    @EJB
    private StockFacade stockFacade;

    @EJB
    private ProduitFacade produitFacade;

    @EJB
    private MagasinFacade magasinFacade;


    @GET
    @Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
    @Operation(
            tags = ApiServiceConfig.TAG_GESTION_STOCKS_NAME,
            summary = "Obtenir la liste des stocks",
            description = "Obtient la liste des stocks avec la possibilité de filtrer par nom, email, téléphone du magasin ou nom du produit.",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = " ===> Liste des stocks récupérée avec succès.",
                            content = {
                                    @Content(
                                            mediaType = "application/json",
                                            examples = @ExampleObject("[\n" +
                                                    "  {\n" +
                                                    "    \"magasin\": {\n" +
                                                    "      \"adresse\": {\n" +
                                                    "        \"adresse\": \"3700 Portola Drive\",\n" +
                                                    "        \"codeZip\": \"95060\",\n" +
                                                    "        \"etat\": \"CA\",\n" +
                                                    "        \"ville\": \"Santa Cruz\"\n" +
                                                    "      },\n" +
                                                    "      \"email\": \"santacruz@bikes.shop\",\n" +
                                                    "      \"id\": 1,\n" +
                                                    "      \"nom\": \"Santa Cruz Bikes\",\n" +
                                                    "      \"telephone\": \"(831) 476-4321\"\n" +
                                                    "    },\n" +
                                                    "    \"produit\": {\n" +
                                                    "      \"annee_model\": 2016,\n" +
                                                    "      \"categorie\": {\n" +
                                                    "        \"id\": 6,\n" +
                                                    "        \"nom\": \"Mountain Bikes\"\n" +
                                                    "      },\n" +
                                                    "      \"id\": 1,\n" +
                                                    "      \"marque\": {\n" +
                                                    "        \"id\": 9,\n" +
                                                    "        \"nom\": \"Trek\"\n" +
                                                    "      },\n" +
                                                    "      \"nom\": \"Trek 820 - 2016\",\n" +
                                                    "      \"prix_depart\": 379.99\n" +
                                                    "    },\n" +
                                                    "    \"quantite\": 27\n" +
                                                    "  }\n" +
                                                    "]")
                                    ),
                                    @Content(
                                            mediaType = "application/xml",
                                            schema = @Schema(implementation = Stock.class)
                                    )
                            }
                    ),
                    @ApiResponse(
                            responseCode = "404",
                            description = " ===> Aucun stock trouvé."
                    ),
                    @ApiResponse(
                            responseCode = "604",
                            description = " ===> Renvoyé, si le paramétre de recherche ne correspond à aucun stock."
                    ),
                    @ApiResponse(
                            responseCode = "400",
                            description = " ===> Données fournies incorrectes ou incomplètes."
                    )
            }
    )
    public List<Stock> getStocks(
            @Parameter(name = "search", description = "Champ pour filtrer de stock(s) par nom, email, téléphone du magasin ou nom du produit.")
            @QueryParam("search") String nom) {
        if (nom != null && stockFacade.searchStock(nom).isEmpty()) {
            throw new SearchNotFoundException("Aucun stock ne correspond au critère de recherche.");
        }

        if (nom != null) {
            return stockFacade.searchStock(nom);
        }
        return stockFacade.FindAll();
    }

    @Path("/{magasinId}/{produitId}")
    @GET
    @Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
    @Operation(
            tags = ApiServiceConfig.TAG_GESTION_STOCKS_NAME,
            summary = "Obtenir un stock par ID de magasin et ID de produit",
            description = "Obtient un stock spécifique en utilisant l'ID du magasin et l'ID du produit.",
            responses = {
                    @ApiResponse(
                            responseCode = "302",
                            description = " ===> Stock récupéré avec succès.",
                            content = {
                                    @Content(
                                            mediaType = "application/json",
                                            examples = @ExampleObject("{\n" +
                                                    "  \"magasin\": {\n" +
                                                    "    \"adresse\": {\n" +
                                                    "      \"adresse\": \"3700 Portola Drive\",\n" +
                                                    "      \"codeZip\": \"95060\",\n" +
                                                    "      \"etat\": \"CA\",\n" +
                                                    "      \"ville\": \"Santa Cruz\"\n" +
                                                    "    },\n" +
                                                    "    \"email\": \"santacruz@bikes.shop\",\n" +
                                                    "    \"id\": 1,\n" +
                                                    "    \"nom\": \"Santa Cruz Bikes\",\n" +
                                                    "    \"telephone\": \"(831) 476-4321\"\n" +
                                                    "  },\n" +
                                                    "  \"produit\": {\n" +
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
                                                    "  },\n" +
                                                    "  \"quantite\": 27\n" +
                                                    "}")
                                    ),
                                    @Content(
                                            mediaType = "application/xml",
                                            schema = @Schema(implementation = Stock.class)
                                    )
                            }
                    ),
                    @ApiResponse(
                            responseCode = "404",
                            description = " ===> Stock avec l'ID de magasin et/ou l'ID de produit spécifiés non trouvé."
                    )
            }
    )
    public Response getStockById(
            @Parameter(name = "ID du magasin référencé à obtenir")
            @PathParam("magasinId") Integer magasinId,
            @Parameter(name = "ID du produit référencé à obtenir")
            @PathParam("produitId") Integer produitId) {
        Stock stockExistant = stockFacade.FindById(magasinId, produitId);
        if (stockExistant == null) {
            throw new DataNotFoundException("Stock avec ID " + magasinId + " et " + produitId + " non trouvé");
        }
        return Response.status(Response.Status.FOUND)
                .entity(stockExistant)
                .build();
    }


    @POST
    @Consumes({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
    @Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
    @Operation(
            tags = ApiServiceConfig.TAG_GESTION_STOCKS_NAME,
            summary = "Ajouter un nouveau stock",
            description = "Ajoute un nouveau stock.",
            responses = {
                    @ApiResponse(
                            responseCode = "201",
                            description = " ===> Stock ajouté avec succès.",
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
                            responseCode = "100",
                            description = " ===> Renvoyé si le(les) ID n'est(sont) pas renseigné(s)."
                    ),
                    @ApiResponse(
                            responseCode = "404",
                            description = " ===> Renvoyé si le magasin ou le produit est introuvable.",
                            content = {
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
                            description = " ===> Renvoyé si le stock existe déjà'.",
                            content = {
                                    @Content(
                                            mediaType = "application/json",
                                            examples = @ExampleObject("{\n" +
                                                    "  \"errorCode\": 509,\n" +
                                                    "  \"errorMessage\": \"Le stock avec cet ID existe déjà.\"\n" +
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
    public Response addStock(NouveauStockDTO nouveauStockDTO) {
        if (nouveauStockDTO.getMagasin() == null || nouveauStockDTO.getProduit() == null) {
            throw new IdObligatoireException("L'ID du stock est obligatoire et doit être différent de zéro.");
        }

        Magasin magasin = magasinFacade.FindById(nouveauStockDTO.getMagasin());
        if (magasin == null) {
            throw new DataNotFoundException("Le magasin avec cet ID n'existe pas.");
        }

        Produit produit = produitFacade.FindById(nouveauStockDTO.getProduit());
        if (produit == null) {
            throw new DataNotFoundException("Le produit avec cet ID n'existe pas.");
        }


        Stock stockExistant = stockFacade.FindById(nouveauStockDTO.getMagasin(), nouveauStockDTO.getProduit());
        if (stockExistant != null) {
            throw new ConflictException("Le stock avec cet ID existe déjà.");
        }

        Stock stock = new Stock();
        stock.setMagasin(magasin);
        stock.setProduit(produit);
        stock.setQuantite(nouveauStockDTO.getQuantite());

        stockFacade.SaveStock(stock);


        return Response.status(Response.Status.CREATED)
                .entity(new SuccessMessage("Stock ajouté avec succès!", 201))
                .build();
    }


    @Path("/{magasinId}/{produitId}")
    @PATCH
    @Consumes({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
    @Operation(
            tags = ApiServiceConfig.TAG_GESTION_STOCKS_NAME,
            summary = "Mettre à jour un stock",
            description = "Met à jour un stock spécifique en utilisant l'ID du magasin et l'ID du produit.",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = " ===> Stock mis à jour avec succès.",
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
                            description = " ===> Données fournies incorrectes."
                    ),
                    @ApiResponse(
                            responseCode = "404",
                            description = "  ===> Stock avec l'ID de magasin et/ou l'ID de produit spécifiés non trouvé."
                    ),
            }
    )
    public Response updateStock(
            @Parameter(name = "ID du magasin référencé", description = "ID du magasin référencé à modifier")
            @PathParam("magasinId") Integer magasinId,
            @Parameter(name = "ID du produit référencé", description = "ID du produit référencé à modifier")
            @PathParam("produitId") Integer produitId,
            MiseAJourStockDTO updatedStockDTO) {

        Stock stockExistant = stockFacade.FindById(magasinId, produitId);
        if (stockExistant == null) {
            throw new DataNotFoundException("Stock avec ID " + magasinId + " et " + produitId + " non trouvé.");
        }

        Magasin magasinExistant = magasinFacade.FindById(magasinId);
        if (magasinExistant == null) {
            throw new DataNotFoundException("Ce magasin n'existe pas.");
        }

        Produit produitExistant = produitFacade.FindById(produitId);
        if (produitExistant == null) {
            throw new DataNotFoundException("Ce produit n'existe pas.");
        }


        if (updatedStockDTO.getQuantite() != null) {
            stockExistant.setQuantite(updatedStockDTO.getQuantite());
        } else {
            stockExistant.setQuantite(0);
        }

        stockFacade.UpdateStock(stockExistant);

        return Response.status(Response.Status.OK)
                .entity(new SuccessMessage("Stock mis à jour avec succès.", 200))
                .build();

    }

    @Path("/{magasinId}/{produitId}")
    @DELETE
    @Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
    @Operation(
            tags = ApiServiceConfig.TAG_GESTION_STOCKS_NAME,
            summary = "Supprimer un stock",
            description = "Supprime un stock spécifié par l'ID du magasin et l'ID du produit.",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = " ===> Stock supprimé avec succès.",
                            content = {
                                    @Content(
                                            mediaType = "application/json",
                                            examples = @ExampleObject("{\n" +
                                                    "  \"successMessage\": \"Stock supprimé avec succés.\",\n" +
                                                    "  \"statusCode\": 200\n" +
                                                    "}")
                                    ),
                                    @Content(
                                            mediaType = "application/xml",
                                            schema = @Schema(implementation = SuccessMessage.class)
                                    )
                            }
                    ),
                    @ApiResponse(
                            responseCode = "404",
                            description = " ===> Stock avec l'ID de magasin et/ou l'ID de produit spécifiés non trouvé."
                    )
            }
    )
    public Response removeStock(
            @Parameter(name = "ID du magasin référencé", description = "ID du magasin référencé à supprimer")
            @PathParam("magasinId") Integer magasinId,
            @Parameter(name = "ID du produit référencé", description = "ID du produit référencé à supprimer")
            @PathParam("produitId") Integer produitId) {

        Magasin magasinExistant = magasinFacade.FindById(magasinId);
        if (magasinExistant == null) {
            throw new DataNotFoundException("Ce magasin n'existe pas.");
        }

        Produit produitExistant = produitFacade.FindById(produitId);
        if (produitExistant == null) {
            throw new DataNotFoundException("Ce produit n'existe pas.");
        }

        Stock stockExistant = stockFacade.FindById(magasinId, produitId);
        if (stockExistant == null) {
            throw new DataNotFoundException("Stock avec ID " + magasinId + " et " + produitId + " non trouvé.");
        }

        stockFacade.RemoveStock(magasinId, produitId);
        return Response.status(Response.Status.OK)
                .entity(new SuccessMessage("Stock supprimé avec succès.", 200))
                .build();
    }

}
