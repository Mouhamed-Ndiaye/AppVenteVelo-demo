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
import sn.edu.ugb.ipsl.appventevelo.dtoclasses.CommandeDTO;
import sn.edu.ugb.ipsl.appventevelo.dtoclasses.EmployeeDTO;
import sn.edu.ugb.ipsl.appventevelo.dtoclasses.MiseAJourCommandeDTO;
import sn.edu.ugb.ipsl.appventevelo.dtoclasses.NouvelleCommandeDTO;
import sn.edu.ugb.ipsl.appventevelo.entities.Client;
import sn.edu.ugb.ipsl.appventevelo.entities.Commande;
import sn.edu.ugb.ipsl.appventevelo.entities.Employe;
import sn.edu.ugb.ipsl.appventevelo.entities.Magasin;
import sn.edu.ugb.ipsl.appventevelo.facades.ClientFacade;
import sn.edu.ugb.ipsl.appventevelo.facades.CommandeFacade;
import sn.edu.ugb.ipsl.appventevelo.facades.EmployeFacade;
import sn.edu.ugb.ipsl.appventevelo.facades.MagasinFacade;
import sn.edu.ugb.ipsl.appventevelo.resources.exceptions.CleEtrangereObligatoireException;
import sn.edu.ugb.ipsl.appventevelo.resources.exceptions.DataNotFoundException;
import sn.edu.ugb.ipsl.appventevelo.resources.exceptions.InvalidDateFormatException;
import sn.edu.ugb.ipsl.appventevelo.resources.exceptions.SearchNotFoundException;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;


@Path("commandes")
public class CommandeResource {

    @EJB
    private CommandeFacade commandeFacade;

    @EJB
    private ClientFacade clientFacade;

    @EJB
    private MagasinFacade magasinFacade;

    @EJB
    private EmployeFacade employeFacade;


    @GET
    @Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
    @Operation(
            tags = ApiServiceConfig.TAG_GESTION_COMMANDES_NAME,
            summary = "Liste des commandes",
            description = "Renvoie la liste des commandes. Il est possible de filtrer les commandes par leur date de commande, date de livraison voulue ou date de livraison.",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = " ===> Liste des commandes renvoyée avec succès.",
                            content = {
                                    @Content(
                                            mediaType = "application/json",
                                            examples = @ExampleObject("[\n" +
                                                    "  {\n" +
                                                    "    \"actif\": 1,\n" +
                                                    "    \"email\": \"mireya.copeland@bikes.shop\",\n" +
                                                    "    \"id\": 2,\n" +
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
                                                    "    \"manager\": {\n" +
                                                    "      \"id\": 1,\n" +
                                                    "      \"nom\": \"Jackson\",\n" +
                                                    "      \"prenom\": \"Fabiola\"\n" +
                                                    "    },\n" +
                                                    "    \"nom\": \"Copeland\",\n" +
                                                    "    \"prenom\": \"Mireya\",\n" +
                                                    "    \"telephone\": \"(831) 555-5555\"\n" +
                                                    "  }\n" +
                                                    "]")
                                    ),
                                    @Content(
                                            mediaType = "application/xml",
                                            schema = @Schema(implementation = CommandeDTO.class)
                                    )
                            }
                    ),
                    @ApiResponse(
                            responseCode = "604",
                            description = " ===> Aucune commande trouvée pour la date spécifiée."
                    ),
                    @ApiResponse(
                            responseCode = "400",
                            description = " ===> Données fournies incorrectes ou incomplètes."
                    ),
                    @ApiResponse(
                            responseCode = "618",
                            description = " ===> Renvoyé si la date n'est pas au format aaaa/MM/jj."
                    )
            }
    )
    public List<CommandeDTO> getCommandes(
            @Parameter(name = "search", description = "Filtrer une commande par la date de commande, date de livraison ou date de livraison voulue")
            @QueryParam("search")
            String date) {
        DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd");
        try {
            Date dateARechercher = dateFormat.parse(date);
            if (dateARechercher != null && commandeFacade.searchCommande(dateARechercher).isEmpty()) {
                throw new SearchNotFoundException("Aucune commande ne correspond au critère de recherche.");
            }
            if (date != null) {
                List<Commande> commandes = commandeFacade.searchCommande(dateARechercher);
                List<CommandeDTO> commandeDTOS = new ArrayList<>();

                for (Commande commande : commandes) {
                    CommandeDTO dto = mapToDTO(commande);
                    commandeDTOS.add(dto);
                }

                return commandeDTOS;
            }
        } catch (ParseException e) {
            throw new InvalidDateFormatException("Le format de la date doit être yyyy/MM/dd");
        }

        List<Commande> commandes = commandeFacade.FindAll();
        List<CommandeDTO> dtos = new ArrayList<>();

        for (Commande commande : commandes) {
            CommandeDTO dto = mapToDTO(commande);
            dtos.add(dto);
        }

        return dtos;
    }

    @Path("/{numero}")
    @GET
    @Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
    @Operation(
            tags = ApiServiceConfig.TAG_GESTION_COMMANDES_NAME,
            summary = "Obtenir une commande par son numéro",
            description = "Renvoie une commande spécifiée par son numéro.",
            responses = {
                    @ApiResponse(
                            responseCode = "302",
                            description = " ===> Commande renvoyée avec succès.",
                            content = {
                                    @Content(
                                            mediaType = "application/json",
                                            examples = @ExampleObject("{\n" +
                                                    "  \"actif\": 1,\n" +
                                                    "  \"email\": \"mireya.copeland@bikes.shop\",\n" +
                                                    "  \"id\": 2,\n" +
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
                                                    "  \"manager\": {\n" +
                                                    "    \"id\": 1,\n" +
                                                    "    \"nom\": \"Jackson\",\n" +
                                                    "    \"prenom\": \"Fabiola\"\n" +
                                                    "  },\n" +
                                                    "  \"nom\": \"Copeland\",\n" +
                                                    "  \"prenom\": \"Mireya\",\n" +
                                                    "  \"telephone\": \"(831) 555-5555\"\n" +
                                                    "}")
                                    ),
                                    @Content(
                                            mediaType = "application/xml",
                                            schema = @Schema(implementation = CommandeDTO.class)
                                    )
                            }
                    ),
                    @ApiResponse(
                            responseCode = "404",
                            description = " ===> Commande avec le numéro spécifié non trouvée."
                    )
            }
    )
    public Response getCommandeById(
            @Parameter(name = "Numéro de la commande", description = "Commande à obtenir")
            @PathParam("numero") Integer numero) {
        Commande commandeExistante = commandeFacade.FindById(numero);
        if (commandeExistante == null) {
            throw new DataNotFoundException("La commande avec ID " + numero + " non trouvée");
        }

        CommandeDTO commandeDTO = mapToDTO(commandeExistante);
        return Response.status(Response.Status.FOUND)
                .entity(commandeDTO)
                .build();
    }

    // Mapper l'entité vers le DTO
    private CommandeDTO mapToDTO(Commande commande) {
        CommandeDTO dto = new CommandeDTO();
        dto.setNumero(commande.getNumero());
        dto.setSatut(commande.getStatut());
        dto.setDateCommande(commande.getDateCommande());
        dto.setDateLivraisonVoulue(commande.getDateLivraisonVoulue());
        dto.setDateLivraison(commande.getDateLivraison());
        dto.setMagasin(commande.getMagasin());

        Employe vendeur = commande.getVendeur();
        EmployeeDTO vendeurDTO = new EmployeeDTO();
        vendeurDTO.setId(vendeur.getId());
        vendeurDTO.setNom(vendeur.getNom());
        vendeurDTO.setPrenom(vendeur.getPrenom());
        dto.setVendeur(vendeurDTO);


        dto.setClient(commande.getClient());
        return dto;
    }

    @POST
    @Consumes({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
    @Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
    @Operation(
            tags = ApiServiceConfig.TAG_GESTION_COMMANDES_NAME,
            summary = "Ajouter une nouvelle commande",
            description = "Ajoute une nouvelle commande.",
            responses = {
                    @ApiResponse(
                            responseCode = "201",
                            description = " ===> Commande ajoutée avec succès.",
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
                            responseCode = "618",
                            description = "  ===> Le format de la date(commande, livraison voulue ou livraison) est invlaide. Format accepté : aaaa-MM-jj."
                    ),
                    @ApiResponse(
                            responseCode = "508",
                            description = " ===> Renvoyé si la(les) clé(s) étrangère(s) n'est(sont) pas renseignée(s)."
                    ),
                    @ApiResponse(
                            responseCode = "400",
                            description = "  ===> Données fournies incorrectes ou incomplètes."
                    ),
                    @ApiResponse(
                            responseCode = "404",
                            description = " ===> Magasin, vendeur ou client avec l'ID spécifié non trouvé."
                    )
            }
    )
    public Response addCommande(NouvelleCommandeDTO nouvelleCommandeDTO) {

        Integer clientId = nouvelleCommandeDTO.getClient();
        if (clientId == null) {
            throw new CleEtrangereObligatoireException("L'ID du client est obligatoire");
        }

        Integer magasinId = nouvelleCommandeDTO.getMagasin();
        if (magasinId == null) {
            throw new CleEtrangereObligatoireException("L'ID du magasin est obligatoire.");
        }

        Integer vendeurId = nouvelleCommandeDTO.getVendeur();
        if (vendeurId == null) {
            throw new CleEtrangereObligatoireException("L'ID du vendeur est obligatoire");
        }

        Client client = clientFacade.FindById(clientId);
        if (client == null) {
            throw new DataNotFoundException("Ce client n'existe pas");
        }

        Magasin magasin = magasinFacade.FindById(magasinId);
        if (magasin == null) {
            throw new DataNotFoundException("Ce magasin n'existe pas");
        }

        Employe vendeur = employeFacade.FindById(vendeurId);
        if (vendeur == null) {
            throw new DataNotFoundException("Ce Vendeur n'existe pas");
        }


        Commande commande = new Commande();

        commande.setClient(client);
        commande.setStatut(nouvelleCommandeDTO.getStatut());

        if (nouvelleCommandeDTO.getDateCommande() != null){
            String dateCommande = nouvelleCommandeDTO.getDateCommande();
            DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
            try {
                Date date = dateFormat.parse(dateCommande);
                commande.setDateCommande(date);
            } catch (ParseException e) {
                throw new InvalidDateFormatException("Le format de la date de commande doit être yyyy/MM/dd");
            }

        }

        if (nouvelleCommandeDTO.getDateLivraisonVoulue() != null){
            String dateLivraisonVoulue = nouvelleCommandeDTO.getDateLivraisonVoulue();
            DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
            try {
                Date date = dateFormat.parse(dateLivraisonVoulue);
                commande.setDateLivraisonVoulue(date);
            } catch (ParseException e) {
                throw new InvalidDateFormatException("Le format de la date de livraison voulue doit être yyyy/MM/dd");
            }
        }

        if (nouvelleCommandeDTO.getDateLivraison() != null){
            String dateLivraison = nouvelleCommandeDTO.getDateLivraison();
            DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
            try {
                Date date = dateFormat.parse(dateLivraison);
                commande.setDateLivraison(date);
            } catch (ParseException e) {
                throw new InvalidDateFormatException("Le format de la date de livraison doit être yyyy/MM/dd");
            }
        }

        commande.setMagasin(magasin);
        commande.setVendeur(vendeur);
        commandeFacade.SaveCommande(commande);

        return Response.status(Response.Status.CREATED)
                .entity(new SuccessMessage("Commande ajoutée avec succès!", 201))
                .build();
    }


    @Path("/{numero}")
    @PATCH
    @Consumes({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
    @Operation(
            tags = ApiServiceConfig.TAG_GESTION_COMMANDES_NAME,
            summary = "Mettre à jour une commande",
            description = "Met à jour les détails d'une commande spécifiée par son numéro.",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = " ===> Commande mise à jour avec succès.",
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
                            description = "  ===> Commande avec le numéro spécifié non trouvée."
                    ),
                    @ApiResponse(
                            responseCode = "400",
                            description = "  ===> Données fournies incorrectes ou incomplètes."
                    ),

                    @ApiResponse(
                            responseCode = "618",
                            description = "  ===> Le format de la date(commande, livraison voulue ou livraison) est invlaide. Format accepté : aaaa-MM-jj."
                    ),
                    @ApiResponse(
                            responseCode = "400",
                            description = "  ===> Données fournies incorrectes ou incomplètes."
                    ),
                    @ApiResponse(
                            responseCode = "404",
                            description = " ===> Magasin, vendeur ou client avec l'ID spécifié non trouvé."
                    )
            }
    )
    public Response updateCommande(
            @Parameter(name = "Numéro de la commande", description = "Commande à modifier")
            @PathParam("numero") Integer numero,
            MiseAJourCommandeDTO updatedCommandeDTO) {

        Commande commandeExistante = commandeFacade.FindById(numero);
        if (commandeExistante == null) {
            throw new DataNotFoundException("Commande avec ID " + numero + " non trouvée.");
        }

        if (updatedCommandeDTO.getClient() != null) {
            Client client = clientFacade.FindById(updatedCommandeDTO.getClient());
            if (client == null) {
                throw new DataNotFoundException("Le client avec cet ID n'existe pas.");
            } else {
                commandeExistante.setClient(client);
            }
        }

        if (updatedCommandeDTO.getMagasin() != null) {
            Magasin magasin = magasinFacade.FindById(updatedCommandeDTO.getMagasin());
            if (magasin == null) {
                throw new DataNotFoundException("Le client avec cet ID n'existe pas.");
            } else {
                commandeExistante.setMagasin(magasin);
            }
        }

        if (updatedCommandeDTO.getVendeur() != null) {
            Employe vendeur = employeFacade.FindById(updatedCommandeDTO.getVendeur());
            if (vendeur == null) {
                throw new DataNotFoundException("Le vendeur avec cet ID n'existe pas.");
            } else {
                commandeExistante.setVendeur(vendeur);
            }
        }


        if (updatedCommandeDTO.getStatut() != 0) {
            commandeExistante.setStatut(updatedCommandeDTO.getStatut());
        }


        if (updatedCommandeDTO.getDateCommande() != null) {
                String dateCommande = updatedCommandeDTO.getDateCommande();
                DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
                try {
                    Date date = dateFormat.parse(dateCommande);
                    commandeExistante.setDateCommande(date);
                } catch (ParseException e) {
                    throw new InvalidDateFormatException("Le format de la date de commande doit être yyyy/MM/dd");
                }
        }


        if (updatedCommandeDTO.getDateLivraisonVoulue() != null) {
            String dateLivraisonVoulue = updatedCommandeDTO.getDateLivraisonVoulue();
            DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
            try {
                Date date = dateFormat.parse(dateLivraisonVoulue);
                commandeExistante.setDateLivraisonVoulue(date);
            } catch (ParseException e) {
                throw new InvalidDateFormatException("Le format de la date de livraison voulue doit être yyyy/MM/dd");
            }
        }

        if (updatedCommandeDTO.getDateLivraison() != null) {
            String dateLivraison = updatedCommandeDTO.getDateLivraison();
            DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
            try {
                Date date = dateFormat.parse(dateLivraison);
                commandeExistante.setDateLivraison(date);
            } catch (ParseException e) {
                throw new InvalidDateFormatException("Le format de la date de livraison doit être yyyy/MM/dd");
            }
        }

        commandeFacade.UpdateCommande(commandeExistante);

        return Response.status(Response.Status.OK)
                .entity(new SuccessMessage("Commande mise à jour avec succès.", 200))
                .build();

    }

    @Path("/{numero}")
    @DELETE
    @Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
    @Operation(
            tags = ApiServiceConfig.TAG_GESTION_COMMANDES_NAME,
            summary = "Supprimer une commande",
            description = "Supprime une commande spécifiée par son numéro.",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = " ===> Commande supprimée avec succès.",
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
                            description = " ===> Commande avec le numéro spécifié non trouvée."
                    )
            }
    )
    public Response removeCommande(
            @Parameter(name = "Numéro de la commande", description = "Commande à supprimer")
            @PathParam("numero") Integer numero) {
        Commande commandeExistante = commandeFacade.FindById(numero);
        if (commandeExistante == null) {
            throw new DataNotFoundException("Commande avec ID " + numero + " non trouvée.");
        }
        commandeFacade.RemoveCommande(numero);
        return Response.status(Response.Status.OK)
                .entity(new SuccessMessage("Commande supprimée avec succès.", 200))
                .build();
    }

}
