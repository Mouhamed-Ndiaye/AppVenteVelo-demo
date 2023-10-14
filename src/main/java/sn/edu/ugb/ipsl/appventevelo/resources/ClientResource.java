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
import sn.edu.ugb.ipsl.appventevelo.entities.Adresse;
import sn.edu.ugb.ipsl.appventevelo.entities.Client;
import sn.edu.ugb.ipsl.appventevelo.facades.ClientFacade;
import sn.edu.ugb.ipsl.appventevelo.resources.exceptions.*;

import java.util.List;


@Path("clients")
public class ClientResource {

    @EJB
    private ClientFacade clientFacade;


    @GET
    @Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
    @Operation(
            tags = ApiServiceConfig.TAG_GESTION_CLIENTS_NAME,
            summary = "Liste des clients",
            description = "Renvoie la liste des clients. Il est possible de filtrer les clients par leur nom, prénom, email ou téléphone.",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = " ===> Liste des clients renvoyée avec succès.",
                            content = {
                                    @Content(
                                            mediaType = "application/json",
                                            examples = @ExampleObject("[\n" +
                                                    "  {\n" +
                                                    "    \"email\": \"debra.burks@yahoo.com\",\n" +
                                                    "    \"id\": 1,\n" +
                                                    "    \"nom\": \"Burks\",\n" +
                                                    "    \"prenom\": \"Debra\",\n" +
                                                    "    \"adresse\": {\n" +
                                                    "      \"adresse\": \"9273 Thorne Ave. \",\n" +
                                                    "      \"codeZip\": \"14127\",\n" +
                                                    "      \"etat\": \"NY\",\n" +
                                                    "      \"ville\": \"Orchard Park\"\n" +
                                                    "    }\n" +
                                                    "  }\n" +
                                                    "]")
                                    ),
                                    @Content(
                                            mediaType = "application/xml",
                                            schema = @Schema(implementation = Client.class)
                                    )
                            }
                    ),
                    @ApiResponse(
                            responseCode = "604",
                            description = " ===> Renvoyé si aucun client ne correspond au critère de recherche."
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
    public List<Client> getClients(
            @Parameter(name = "search", description = "Rechercher un client de par son nom, prénom, email ou téléphone.")
            @QueryParam("search") String nom) {
        if (nom != null && clientFacade.searchClient(nom).isEmpty()) {
            throw new SearchNotFoundException("Aucun client ne correspond au critère de recherche.");
        }

        if (nom != null) {
            return clientFacade.searchClient(nom);
        }
        return clientFacade.FindAll();
    }

    @Path("/{id}")
    @GET
    @Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
    @Operation(
            tags = ApiServiceConfig.TAG_GESTION_CLIENTS_NAME,
            summary = "Obtenir un client par ID",
            description = "Renvoie les détails d'un client spécifié par son ID.",
            responses = {
                    @ApiResponse(
                            responseCode = "302",
                            description = " ===> Client trouvé avec succès.",
                            content = {
                                    @Content(
                                            mediaType = "application/json",
                                            examples = @ExampleObject("{\n" +
                                                    "  \"email\": \"debra.burks@yahoo.com\",\n" +
                                                    "  \"id\": 1,\n" +
                                                    "  \"nom\": \"Burks\",\n" +
                                                    "  \"prenom\": \"Debra\",\n" +
                                                    "  \"adresse\": {\n" +
                                                    "    \"adresse\": \"9273 Thorne Ave. \",\n" +
                                                    "    \"codeZip\": \"14127\",\n" +
                                                    "    \"etat\": \"NY\",\n" +
                                                    "    \"ville\": \"Orchard Park\"\n" +
                                                    "  }\n" +
                                                    "}")
                                    ),
                                    @Content(
                                            mediaType = "application/xml",
                                            schema = @Schema(implementation = Client.class)
                                    )
                            }
                    ),
                    @ApiResponse(
                            responseCode = "404",
                            description = " ===> Renvoyé si le client avec l'ID spécifié n'est pas trouvé."
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
    public Response getClientById(
            @Parameter(name = "ID du client", description = "ID du client qu'on veut obtenir.")
            @PathParam("id") Integer id) {
        Client clientExistant = clientFacade.FindById(id);
        if (clientExistant == null) {
            throw new DataNotFoundException("Client avec ID " + id + " non trouvé");
        }
        return Response.status(302)
                .entity(clientExistant)
                .build();
    }


    @POST
    @Consumes({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
    @Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
    @Operation(
            tags = ApiServiceConfig.TAG_GESTION_CLIENTS_NAME,
            summary = "Ajouter un nouveau client",
            description = "Ajoute un nouveau client.",
            responses = {
                    @ApiResponse(
                            responseCode = "201",
                            description = "Client ajouté avec succès.",
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
                            description = " ===> Données obligatoires non fournies."
                    ),
                    @ApiResponse(
                            responseCode = "615",
                            description = " ===> Format de l'email incorrect. Format correct = example@gmail.com."
                    ),
                    @ApiResponse(
                            responseCode = "630",
                            description = " ===> Format du téléphone incorrect. Format correct = (999) 999-9999"
                    ),
                    @ApiResponse(
                            responseCode = "700",
                            description = " ===> Email déjà existant."
                    ),
                    @ApiResponse(
                            responseCode = "750",
                            description = " ===> L'état est trop long."
                    ),
                    @ApiResponse(
                            responseCode = "760",
                            description = " ===> Le code zip est trop long."
                    ),
                    @ApiResponse(
                            responseCode = "800",
                            description = "===> Le téléphone est déjà existant."
                    ),
                    @ApiResponse(
                            responseCode = "850",
                            description = " ===> Le format du Code Zip est incorrect. Format correct: 99999."
                    ),
                    @ApiResponse(
                            responseCode = "400",
                            description = " ===> Données fournies incorrectes ou incomplètes."
                    )
            }
    )
    public Response addClient(Client client) {

        if (client.getNom() == null) {
            throw new ChampObligatoireException("Le nom est obligatoire.");
        }

        if (client.getPrenom() == null) {
            throw new ChampObligatoireException("Le prénom est obligatoire");
        }

        if (client.getEmail() == null) {
            throw new ChampObligatoireException("L'email est obligatoire");
        }

        String emailRegex = "^[a-zA-Z0-9_+&*-]+(?:\\.[a-zA-Z0-9_+&*-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,7}$";
        if (!client.getEmail().matches(emailRegex)) {
            throw new InvalidEmailFormatException("L'email doit être au format example@gmail.com");
        }

        if (clientFacade.findByEmail(client.getEmail()) != null) {
            throw new EmailUniqueException("Cet email existe déjà");
        }

        if (clientFacade.findByTelephone(client.getTelephone()) != null) {
            throw new TelephoneUniqueException("Ce téléphone existe déjà.");
        }

        if (client.getTelephone() != null) {
            String phoneRegex = "^\\(\\d{3}\\) \\d{3}-\\d{4}$";

            if (!client.getTelephone().matches(phoneRegex)) {
                throw new InvalidPhoneFormatException("Le numéro de téléphone doit être au format (999) 999-9999");
            }
        }

        if (client.getAdresse().getEtat().length() >= 26) {
            throw new LengthEtatException("Etat trop long, l'état doît être au maximum 25 caractères");
        }

        if (client.getAdresse().getCodeZip() != null) {
            if (client.getAdresse().getCodeZip().length() >= 6) {
                throw new LengthCodeZipException("Code Zip trop long, le Code Zip doit être au maximum 5 caractères");
            }
            String zipCodePattern = "^\\d{2}\\d{3}$";
            if (!client.getAdresse().getCodeZip().matches(zipCodePattern)) {
                throw new InvalidCodeZipFormatException("Le code Zip doit être sous format 99 999");
            }
        }


        clientFacade.SaveClient(client);
        return Response.status(Response.Status.CREATED)
                .entity(new SuccessMessage("Client ajouté avec succès!", 201))
                .build();
    }


    @Path("/{id}")
    @PATCH
    @Consumes({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
    @Operation(
            tags = ApiServiceConfig.TAG_GESTION_CLIENTS_NAME,
            summary = "Mettre à jour un client",
            description = "Met à jour les détails d'un client spécifié par son ID.",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = " ===> Client mis à jour avec succès.",
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
                            responseCode = "615",
                            description = " ===> Format de l'email incorrect. Format correct: example@gmail.com."
                    ),
                    @ApiResponse(
                            responseCode = "630",
                            description = " ===> Format du téléphone incorrect. Format correct: (999) 999-9999."
                    ),
                    @ApiResponse(
                            responseCode = "700",
                            description = " ===> Email déjà existant."
                    ),
                    @ApiResponse(
                            responseCode = "750",
                            description = " ===> L'état est trop long."
                    ),
                    @ApiResponse(
                            responseCode = "760",
                            description = " ===> Le code zip est trop long."
                    ),
                    @ApiResponse(
                            responseCode = "800",
                            description = " ===> Le téléphone est déjà existant."
                    ),
                    @ApiResponse(
                            responseCode = "404",
                            description = " ===> Client avec l'ID spécifié non trouvé."
                    ),
                    @ApiResponse(
                            responseCode = "850",
                            description = " ===> Le format du Code Zip est incorrect. Format correct: 99999."
                    ),
                    @ApiResponse(
                            responseCode = "400",
                            description = " ===> Données fournies incorrectes ou incomplètes."
                    )
            }
    )
    public Response updateClient(
            @Parameter(name = "ID du client", description = "ID du client à modifier")
            @PathParam("id") Integer id, Client updatedClient) {
        Client clientExistant = clientFacade.FindById(id);
        if (clientExistant == null) {
            throw new DataNotFoundException("Client avec ID " + id + " non trouvé.");
        }

        if (updatedClient.getNom() != null) {
            clientExistant.setNom(updatedClient.getNom());
        }

        if (updatedClient.getPrenom() != null) {
            clientExistant.setPrenom(updatedClient.getPrenom());
        }

        String ancienTelephone = clientExistant.getTelephone();


        if (updatedClient.getTelephone() != null && !updatedClient.getTelephone().equals(ancienTelephone)) {
            if (clientFacade.findByTelephone(updatedClient.getTelephone()) != null) {
                throw new TelephoneUniqueException("Ce téléphone existe déjà.");
            } else {
                String phoneRegex = "^\\(\\d{3}\\) \\d{3}-\\d{4}$";

                if (!updatedClient.getTelephone().matches(phoneRegex)) {
                    throw new InvalidPhoneFormatException("Le numéro de téléphone doit être au format (999) 999-9999");
                }
            }
            clientExistant.setTelephone(updatedClient.getTelephone());
        }

        String ancienEmail = clientExistant.getEmail();

        if (updatedClient.getEmail() != null && !updatedClient.getEmail().equals(ancienEmail)) {
            // L'utilisateur a modifié l'email, alors effectuer la validation existante
            if (clientFacade.findByEmail(updatedClient.getEmail()) != null) {
                throw new EmailUniqueException("Cet email existe déjà");
            } else {
                String emailRegex = "^[a-zA-Z0-9_+&*-]+(?:\\.[a-zA-Z0-9_+&*-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,7}$";
                if (!updatedClient.getEmail().matches(emailRegex)) {
                    throw new InvalidEmailFormatException("L'email n'est pas au format valide.");
                }
            }
            clientExistant.setEmail(updatedClient.getEmail());
        }

        Adresse nouvelleAdresse = updatedClient.getAdresse();

        if (nouvelleAdresse != null) {
            Adresse adresseExistante = clientExistant.getAdresse();
            if (nouvelleAdresse.getAdresse() != null) {
                adresseExistante.setAdresse(nouvelleAdresse.getAdresse());
            }
            if (nouvelleAdresse.getVille() != null) {
                adresseExistante.setVille(nouvelleAdresse.getVille());
            }
            if (nouvelleAdresse.getEtat() != null) {
                if (nouvelleAdresse.getEtat().length() >= 26) {
                    throw new LengthEtatException("Etat trop long, l'état doit être au maximum 25 caractères");
                }
                adresseExistante.setEtat(nouvelleAdresse.getEtat());
            }
            if (nouvelleAdresse.getCodeZip() != null) {
                if (nouvelleAdresse.getCodeZip().length() >= 6) {
                    throw new LengthCodeZipException("Code Zip trop long, le Code Zip doit être au maximum 5 caractères");
                }
                String zipCodePattern = "^\\d{2}\\d{3}$";
                if (!nouvelleAdresse.getCodeZip().matches(zipCodePattern)) {
                    throw new InvalidCodeZipFormatException("Le code Zip doit être sous format 99 999");
                }
                adresseExistante.setCodeZip(nouvelleAdresse.getCodeZip());
            }
            clientExistant.setAdresse(adresseExistante);
        }

        clientFacade.UpdateClient(clientExistant);
        return Response.status(Response.Status.OK)
                .entity(new SuccessMessage("Client mis à jour avec succès.", 200))
                .build();
    }

    @Path("/{id}")
    @DELETE
    @Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
    @Operation(
            tags = ApiServiceConfig.TAG_GESTION_CLIENTS_NAME,
            summary = "Supprimer un client",
            description = "Supprime un client spécifié par son ID.",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = " ===> Client supprimé avec succès.",
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
                            description = " ===> Renvoyé si le client avec l'ID spécifié n'est pas trouvé."
                    ),
                    @ApiResponse(
                            responseCode = "400",
                            description = " ===> Données fournies incorrectes ou incomplètes."
                    )
            }
    )
    public Response removeClient(@PathParam("id") Integer id) {
        Client clientExistant = clientFacade.FindById(id);
        if (clientExistant == null) {
            throw new DataNotFoundException("Client avec ID " + id + " non trouvé.");
        }
        clientFacade.RemoveClient(id);
        return Response.status(Response.Status.OK)
                .entity(new SuccessMessage("Client supprimé avec succès.", 200))
                .build();
    }

}
