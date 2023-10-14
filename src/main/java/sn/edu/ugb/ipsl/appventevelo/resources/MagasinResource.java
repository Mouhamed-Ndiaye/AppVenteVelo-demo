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
import sn.edu.ugb.ipsl.appventevelo.entities.Magasin;
import sn.edu.ugb.ipsl.appventevelo.facades.MagasinFacade;
import sn.edu.ugb.ipsl.appventevelo.resources.exceptions.*;

import java.util.List;


@Path("magasins")
public class MagasinResource {

    @EJB
    private MagasinFacade magasinFacade;


    @GET
    @Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
    @Operation(
            tags = ApiServiceConfig.TAG_GESTION_MAGASINS_NAME,
            summary = "Liste des magasins",
            description = "Renvoie la liste des magasins. La liste peut être filtrée par nom, prénom, email, téléphone, adresse, etat ou Code Zip",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = " ===> Liste des magasins renvoyée avec succès.",
                            content = {
                                    @Content(
                                            mediaType = "application/json",
                                            examples = @ExampleObject("[\n" +
                                                    "  {\n" +
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
                                                    "  {\n" +
                                                    "    \"adresse\": {\n" +
                                                    "      \"adresse\": \"4200 Chestnut Lane\",\n" +
                                                    "      \"codeZip\": \"11432\",\n" +
                                                    "      \"etat\": \"NY\",\n" +
                                                    "      \"ville\": \"Baldwin\"\n" +
                                                    "    },\n" +
                                                    "    \"email\": \"baldwin@bikes.shop\",\n" +
                                                    "    \"id\": 2,\n" +
                                                    "    \"nom\": \"Baldwin Bikes\",\n" +
                                                    "    \"telephone\": \"(516) 379-8888\"\n" +
                                                    "  }\n" +
                                                    "]")
                                    ),
                                    @Content(
                                            mediaType = "application/xml",
                                            schema = @Schema(implementation = Magasin.class)
                                    )
                            }
                    ),
                    @ApiResponse(
                            responseCode = "604",
                            description = " ===> Aucun magasin trouvé avec le paramètre spécifié."
                    ),
                    @ApiResponse(
                            responseCode = "400",
                            description = " ===> Données fournies incorrectes ou incomplètes."
                    )
            }
    )
    public List<Magasin> getMagasins(
            @Parameter(name = "search", description = "Nom du magasin à rechercher")
            @QueryParam("search") String nom) {
        if (nom != null && magasinFacade.searchMagasin(nom).isEmpty()) {
            throw new SearchNotFoundException("Aucun magasin ne correspond au critère de recherche.");
        }

        if (nom != null) {
            return magasinFacade.searchMagasin(nom);
        }
        return magasinFacade.FindAll();
    }

    @Path("/{id}")
    @GET
    @Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
    @Operation(
            tags = ApiServiceConfig.TAG_GESTION_MAGASINS_NAME,
            summary = "Détails du magasin",
            description = "Renvoie les détails du magasin spécifié par son ID.",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = " ===> Détails du magasin renvoyés avec succès.",
                            content = {
                                    @Content(
                                            mediaType = "application/json",
                                            examples = @ExampleObject("{\n" +
                                                    "  \"adresse\": {\n" +
                                                    "    \"adresse\": \"3700 Portola Drive\",\n" +
                                                    "    \"codeZip\": \"95060\",\n" +
                                                    "    \"etat\": \"CA\",\n" +
                                                    "    \"ville\": \"Santa Cruz\"\n" +
                                                    "  },\n" +
                                                    "  \"email\": \"santacruz@bikes.shop\",\n" +
                                                    "  \"id\": 1,\n" +
                                                    "  \"nom\": \"Santa Cruz Bikes\",\n" +
                                                    "  \"telephone\": \"(831) 476-4321\"\n" +
                                                    "}")
                                    ),
                                    @Content(
                                            mediaType = "application/xml",
                                            schema = @Schema(implementation = Magasin.class)
                                    )
                            }
                    ),
                    @ApiResponse(
                            responseCode = "404",
                            description = " ===> Magasin avec l'ID spécifié non trouvé."
                    ),
                    @ApiResponse(
                            responseCode = "400",
                            description = " ===> Données fournies incorrectes ou incomplètes."
                    )
            }
    )
    public Response getMagasinById(
            @Parameter(name = "ID du magasin", description = "ID du magasin à obtenir")
            @PathParam("id") Integer id) {
        Magasin magasinExistant = magasinFacade.FindById(id);
        if (magasinExistant == null) {
            throw new DataNotFoundException("Magasin avec ID "+ id +" non trouvé");
        }
        return Response.status(Response.Status.FOUND)
                .entity(magasinExistant)
                .build();
    }


    @POST
    @Consumes({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
    @Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
    @Operation(
            tags = ApiServiceConfig.TAG_GESTION_MAGASINS_NAME,
            summary = "Ajouter un magasin",
            description = "Ajoute un nouveau magasin avec les détails fournis.",
            responses = {
                    @ApiResponse(
                            responseCode = "201",
                            description = "  ===> Magasin ajouté avec succès.",
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
                            description = " ===> Données obligatoire non fournies.",
                            content = {
                                    @Content(
                                            mediaType = "application/json",
                                            examples = @ExampleObject(value = "{\n" +
                                                    "  \"errorMessage\": \"Le nom du magasin est obligatoire.\",\n" +
                                                    "  \"errorCode\": 600\n" +
                                                    "}")
                                    ),
                                    @Content(
                                            mediaType = "application/xml",
                                            schema = @Schema(implementation = ErrorMessage.class)
                                    )
                            }
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
                            description = " ===> Le téléphone est déjà existant."
                    ),
                    @ApiResponse(
                            responseCode = "850",
                            description = " ===> Le format du Code Zip est incorrect. Format correct: 99999."
                    )
            }
    )
    public Response addMagasin(Magasin magasin) {

        if (magasin.getNom() == null) {
            throw new ChampObligatoireException("Le nom est obligatoire.");
        }

        String emailRegex = "^[a-zA-Z0-9_+&*-]+(?:\\.[a-zA-Z0-9_+&*-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,7}$";
        if (!magasin.getEmail().matches(emailRegex)) {
            throw new InvalidEmailFormatException("L'email doit être au format example@gmail.com");
        }

        if (magasinFacade.findByEmail(magasin.getEmail()) != null) {
            throw new EmailUniqueException("Cet email existe déjà");
        }

        if (magasinFacade.findByTelephone(magasin.getTelephone()) != null) {
            throw new TelephoneUniqueException("Ce téléphone existe déjà.");
        }

        if (magasin.getTelephone() != null) {
            String phoneRegex = "^\\(\\d{3}\\) \\d{3}-\\d{4}$";

            if (!magasin.getTelephone().matches(phoneRegex)) {
                throw new InvalidPhoneFormatException("Le numéro de téléphone doit être au format (999) 999-9999");
            }
        }

        if (magasin.getAdresse().getEtat() != null){
            if (magasin.getAdresse().getEtat().length() >= 26){
                throw new LengthEtatException("Etat trop long, l'état doit être au maximum 25 caractères");
            }
        }

        if (magasin.getAdresse().getCodeZip() != null){
            if (magasin.getAdresse().getCodeZip().length() >= 6){
                throw new LengthCodeZipException("Code Zip trop long, le Code Zip doit être au maximum 5 caractères");
            }
            String zipCodePattern = "^\\d{2}\\d{3}$";
            if (!magasin.getAdresse().getCodeZip().matches(zipCodePattern)){
                throw new InvalidCodeZipFormatException("Le code Zip doit être sous format 99 999");
            }
        }


        magasinFacade.SaveMagasin(magasin);
        return Response.status(Response.Status.CREATED)
                .entity(new SuccessMessage("Magasin ajouté avec succès!", 201))
                .build();
    }

    @Path("/{id}")
    @PATCH
    @Consumes({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
    @Operation(
            tags = ApiServiceConfig.TAG_GESTION_MAGASINS_NAME,
            summary = "Mettre à jour un magasin",
            description = "Met à jour les détails d'un magasin spécifié par son ID.",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = " ===> Magasin mis à jour avec succès.",
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
                            description = " ===> Magasin avec l'ID spécifié non trouvé.",
                            content = {
                                    @Content(
                                            mediaType = "application/json",
                                            examples = @ExampleObject(value = "{\n" +
                                                    "  \"error\": \"Magasin avec l'ID spécifié non trouvé.\",\n" +
                                                    "  \"errorCode\": 615\n" +
                                                    "}")
                                    ),
                                    @Content(
                                            mediaType = "application/xml",
                                            schema = @Schema(implementation = ErrorMessage.class)
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
                            responseCode = "620",
                            description = " ===> Le format du Code Zip est incorrect. Format correct: 99999."
                    ),
                    @ApiResponse(
                            responseCode = "400",
                            description = " ===> Données fournies incorrectes ou incomplètes."
                    )
            }
    )
    public Response updateMagasin(
            @Parameter(name = "ID du magasin", description = "ID du magasin à modifier")
            @PathParam("id") Integer id,
            Magasin updatedMagasin) {
        Magasin magasinExistant = magasinFacade.FindById(id);
        if (magasinExistant == null) {
            throw new DataNotFoundException("Magasin avec ID " + id + " non trouvé.");
        }

        if (updatedMagasin.getNom() != null) {
            magasinExistant.setNom(updatedMagasin.getNom());
        }

        String ancienTelephone = magasinExistant.getTelephone();


        if (updatedMagasin.getTelephone() != null && !updatedMagasin.getTelephone().equals(ancienTelephone)) {
            if (magasinFacade.findByTelephone(updatedMagasin.getTelephone()) != null) {
                throw new TelephoneUniqueException("Ce téléphone existe déjà.");
            } else {
                String phoneRegex = "^\\(\\d{3}\\) \\d{3}-\\d{4}$";

                if (!updatedMagasin.getTelephone().matches(phoneRegex)) {
                    throw new InvalidPhoneFormatException("Le numéro de téléphone doit être au format (999) 999-9999");
                }
            }
            magasinExistant.setTelephone(updatedMagasin.getTelephone());
        }

        String ancienEmail = magasinExistant.getEmail();

        if (updatedMagasin.getEmail() != null && !updatedMagasin.getEmail().equals(ancienEmail)) {
            // L'utilisateur a modifié l'email, alors effectuer la validation existante
            if (magasinFacade.findByEmail(updatedMagasin.getEmail()) != null) {
                throw new EmailUniqueException("Cet email existe déjà");
            } else {
                String emailRegex = "^[a-zA-Z0-9_+&*-]+(?:\\.[a-zA-Z0-9_+&*-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,7}$";
                if (!updatedMagasin.getEmail().matches(emailRegex)) {
                    throw new InvalidEmailFormatException("L'email n'est pas au format valide.");
                }
            }
            magasinExistant.setEmail(updatedMagasin.getEmail());
        }

        Adresse nouvelleAdresse = updatedMagasin.getAdresse();

        if (nouvelleAdresse != null) {
            Adresse adresseExistante = magasinExistant.getAdresse();
            if (nouvelleAdresse.getAdresse() != null) {
                adresseExistante.setAdresse(nouvelleAdresse.getAdresse());
            }
            if (nouvelleAdresse.getVille() != null) {
                adresseExistante.setVille(nouvelleAdresse.getVille());
            }
            if (nouvelleAdresse.getEtat() != null) {
                if (nouvelleAdresse.getEtat().length() >= 26){
                    throw new LengthEtatException("Etat trop long, l'état doit être au maximum 25 caractères");
                }
                adresseExistante.setEtat(nouvelleAdresse.getEtat());
            }
            if (nouvelleAdresse.getCodeZip() != null) {
                if (nouvelleAdresse.getCodeZip().length() >= 6){
                    throw new LengthCodeZipException("Code Zip trop long, le Code Zip doit être au maximum 5 caractères");
                }
                String zipCodePattern = "^\\d{2}\\d{3}$";
                if (!nouvelleAdresse.getCodeZip().matches(zipCodePattern)){
                    throw new InvalidCodeZipFormatException("Le code Zip doit être sous format 99 999");
                }
                adresseExistante.setCodeZip(nouvelleAdresse.getCodeZip());
            }
            magasinExistant.setAdresse(adresseExistante);
        }

        magasinFacade.UpdateMagasin(magasinExistant);
        return Response.status(Response.Status.OK)
                .entity(new SuccessMessage("Magasin mis à jour avec succès.", 200))
                .build();
    }

    @Path("/{id}")
    @DELETE
    @Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
    @Operation(
            tags = ApiServiceConfig.TAG_GESTION_MAGASINS_NAME,
            summary = "Supprimer un magasin",
            description = "Supprime un magasin spécifié par son ID.",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = " ===> Magasin supprimé avec succès.",
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
                            description = " ===> Renvoyé si le magasin avec l'ID spécifié n'est pas trouvé."
                    ),
                    @ApiResponse(
                            responseCode = "400",
                            description = " ===> Données fournies incorrectes ou incomplètes."
                    )
            }
    )
    public Response removeMagasin(
            @Parameter(name = "ID du magasin", description = "ID du magasin à supprimer")
            @PathParam("id") Integer id) {
        Magasin magasinExistant = magasinFacade.FindById(id);
        if (magasinExistant == null) {
            throw new DataNotFoundException("Magasin avec ID " + id + " non trouvé.");
        }
        magasinFacade.RemoveMagasin(id);
        return Response.status(Response.Status.OK)
                .entity(new SuccessMessage("Magasin supprimé avec succès.", 200))
                .build();
    }

}
