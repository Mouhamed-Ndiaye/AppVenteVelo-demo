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
import sn.edu.ugb.ipsl.appventevelo.dtoclasses.EmployeDTO;
import sn.edu.ugb.ipsl.appventevelo.dtoclasses.EmployeeDTO;
import sn.edu.ugb.ipsl.appventevelo.dtoclasses.MiseAJourEmployeDTO;
import sn.edu.ugb.ipsl.appventevelo.dtoclasses.NouvelEmployeDTO;
import sn.edu.ugb.ipsl.appventevelo.entities.Employe;
import sn.edu.ugb.ipsl.appventevelo.entities.Magasin;
import sn.edu.ugb.ipsl.appventevelo.facades.EmployeFacade;
import sn.edu.ugb.ipsl.appventevelo.facades.MagasinFacade;
import sn.edu.ugb.ipsl.appventevelo.resources.exceptions.*;

import java.util.ArrayList;
import java.util.List;


@Path("employes")
public class EmployeResource {

    @EJB
    private EmployeFacade employeFacade;

    @EJB
    private MagasinFacade magasinFacade;


    @GET
    @Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
    @Operation(
            tags = ApiServiceConfig.TAG_GESTION_EMPLOYES_NAME,
            summary = "Liste des employés",
            description = "Renvoie la liste des employés. Il est possible de filtrer les employés par leur nom, prénom, email ou téléphone.",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = " ===> Liste des employés renvoyée avec succès.",
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
                                            schema = @Schema(implementation = EmployeDTO.class)
                                    )
                            }
                    ),
                    @ApiResponse(
                            responseCode = "604",
                            description = " ===> Aucun employé trouvé en fonction du critère de recherche."
                    ),
                    @ApiResponse(
                            responseCode = "400",
                            description = " ===> Données fournies incorrectes ou incomplètes."
                    )
            }
    )
    public List<EmployeDTO> getEmployes(
            @Parameter(name = "search", description = "Rechercher un employé par son nom, prenom, email ou téléphone.")
            @QueryParam("search")
            String nom) {
        if (nom != null && employeFacade.searchEmploye(nom).isEmpty()) {
            throw new SearchNotFoundException("Aucun catégorie ne correspond au critère de recherche.");
        }

        if (nom != null) {
            List<Employe> employes = employeFacade.searchEmploye(nom);
            List<EmployeDTO> employeDTOS = new ArrayList<>();

            for (Employe employe : employes) {
                EmployeDTO dto = mapToDTO(employe);
                employeDTOS.add(dto);
            }

            return employeDTOS;
        }
        List<Employe> employes = employeFacade.FindAll();
        List<EmployeDTO> dtos = new ArrayList<>();

        for (Employe employe : employes) {
            EmployeDTO dto = mapToDTO(employe);
            dtos.add(dto);
        }

        return dtos;
    }

    @Path("/{id}")
    @GET
    @Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
    public Response getEmployeById(@PathParam("id") Integer id) {
        Employe employeExistant = employeFacade.FindById(id);
        if (employeExistant == null) {
            throw new DataNotFoundException("L'employé avec ID " + id + " non trouvé");
        }

        EmployeDTO employeDTO = mapToDTO(employeExistant);
        return Response.status(Response.Status.FOUND)
                .entity(employeDTO)
                .build();
    }

    // Mapper l'entité vers le DTO
    private EmployeDTO mapToDTO(Employe employe) {
        EmployeDTO dto = new EmployeDTO();
        dto.setId(employe.getId());
        dto.setPrenom(employe.getPrenom());
        dto.setNom(employe.getNom());
        dto.setEmail(employe.getEmail());
        dto.setTelephone(employe.getTelephone());
        dto.setActif(employe.getActif());
        dto.setMagasin(employe.getMagasin());


        Employe manager = employe.getManager();
        if (manager != null) {
            EmployeeDTO managerDTO = new EmployeeDTO();
            managerDTO.setId(manager.getId());
            managerDTO.setNom(manager.getNom());
            managerDTO.setPrenom(manager.getPrenom());
            dto.setManager(managerDTO);
        }

        return dto;
    }


    @POST
    @Consumes({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
    @Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
    @Operation(
            tags = ApiServiceConfig.TAG_GESTION_EMPLOYES_NAME,
            summary = "Ajouter un nouvel employé",
            description = "Ajoute un nouvel employé.",
            responses = {
                    @ApiResponse(
                            responseCode = "201",
                            description = " ===> Employé ajouté avec succès.",
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
                            responseCode = "508",
                            description = " ===> Renvoyé si la(les) clé(s) étrangère(s) n'est(sont) pas renseignée(s)."
                    ),
                    @ApiResponse(
                            responseCode = "600",
                            description = " ===> Données obligatoires non fournies(nom, prénom ou email)."
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
                            responseCode = "800",
                            description = " ===> Le téléphone est déjà existant."
                    )
            }
    )
    public Response addEmploye(NouvelEmployeDTO nouvelEmployeDTO) {

        if (nouvelEmployeDTO.getNom() == null){
            throw new ChampObligatoireException("Le nom de l'employé est obligatoire.");
        }

        if (nouvelEmployeDTO.getPrenom() == null){
            throw new ChampObligatoireException("Le prénom de l'employé est obligatoire.");
        }

        if (nouvelEmployeDTO.getEmail() == null){
            throw new ChampObligatoireException("L'email'de l'employé est obligatoire.");
        }

        if (nouvelEmployeDTO.getMagasin() == null){
            throw new CleEtrangereObligatoireException("L'ID du magasin est obligatoire");
        }

        Magasin magasin = magasinFacade.FindById(nouvelEmployeDTO.getMagasin());
        if (magasin == null) {
            throw new DataNotFoundException("Ce magasin n'existe pas");
        }



        Employe employe = new Employe();
        String emailRegex = "^[a-zA-Z0-9_+&*-]+(?:\\.[a-zA-Z0-9_+&*-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,7}$";
        if (!nouvelEmployeDTO.getEmail().matches(emailRegex)) {
            throw new InvalidEmailFormatException("L'email doit être au format example@gmail.com");
        }

        if (employeFacade.findByEmail(nouvelEmployeDTO.getEmail()) != null) {
            throw new EmailUniqueException("Cet email existe déjà");
        }

        if (employeFacade.findByTelephone(nouvelEmployeDTO.getTelephone()) != null) {
            throw new TelephoneUniqueException("Ce téléphone existe déjà.");
        }

        if (nouvelEmployeDTO.getTelephone() != null) {
            String phoneRegex = "^\\(\\d{3}\\) \\d{3}-\\d{4}$";

            if (!nouvelEmployeDTO.getTelephone().matches(phoneRegex)) {
                throw new InvalidPhoneFormatException("Le numéro de téléphone doit être au format (999) 999-9999");
            }
        }

        employe.setNom(nouvelEmployeDTO.getNom());
        employe.setPrenom(nouvelEmployeDTO.getPrenom());
        employe.setEmail(nouvelEmployeDTO.getEmail());
        employe.setTelephone(nouvelEmployeDTO.getTelephone());
        employe.setActif(nouvelEmployeDTO.getActif());
        employe.setMagasin(magasin);

        if (nouvelEmployeDTO.getManager() == null){
            employe.setManager(null);
        }

        if (nouvelEmployeDTO.getManager() != null){
            Employe manager = employeFacade.FindById(nouvelEmployeDTO.getManager());
            if (manager == null) {
                throw new DataNotFoundException("Ce manager n'existe pas");
            }
            employe.setManager(manager);
        }

        employeFacade.SaveEmploye(employe);


        return Response.status(Response.Status.CREATED)
                .entity(new SuccessMessage("Employé ajouté avec succès!", 201))
                .build();
    }

    @Path("/{id}")
    @PATCH
    @Consumes({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
    @Operation(
            tags = ApiServiceConfig.TAG_GESTION_EMPLOYES_NAME,
            summary = "Mettre à jour un employé",
            description = "Met à jour les détails d'un employé spécifié par son ID.",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = " ===> Employé mis à jour avec succès.",
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
                            description = " ===> Format de l'email incorrect. Format correct = example@gmail.com."
                    ),
                    @ApiResponse(
                            responseCode = "630",
                            description = " ===> Format du téléphone incorrect. Format correct = (999) 999-9999."
                    ),
                    @ApiResponse(
                            responseCode = "700",
                            description = " ===> Email déjà existant."
                    ),
                    @ApiResponse(
                            responseCode = "800",
                            description = " ===> Le téléphone est déjà existant."
                    ),
                    @ApiResponse(
                            responseCode = "404",
                            description = " ===> Employé, manager ou magasin avec l'ID spécifié non trouvé."
                    )
            }
    )
    public Response updateEmploye(
            @PathParam("id") Integer id,
            MiseAJourEmployeDTO updatedEmployeDTO) {

        Employe employeExistant = employeFacade.FindById(id);
        if (employeExistant == null) {
            throw new DataNotFoundException("Employé avec ID " + id + " non trouvé.");
        }

        if (updatedEmployeDTO.getNom() != null){
            employeExistant.setNom(updatedEmployeDTO.getNom());
        }

        if (updatedEmployeDTO.getPrenom() != null){
            employeExistant.setPrenom(updatedEmployeDTO.getPrenom());
        }

        String ancienTelephone = employeExistant.getTelephone();


        if (updatedEmployeDTO.getTelephone() != null && !updatedEmployeDTO.getTelephone().equals(ancienTelephone)) {
            if (employeFacade.findByTelephone(updatedEmployeDTO.getTelephone()) != null) {
                throw new TelephoneUniqueException("Ce téléphone existe déjà.");
            } else {
                String phoneRegex = "^\\(\\d{3}\\) \\d{3}-\\d{4}$";

                if (!updatedEmployeDTO.getTelephone().matches(phoneRegex)) {
                    throw new InvalidPhoneFormatException("Le numéro de téléphone doit être au format (999) 999-9999");
                }
            }
            employeExistant.setTelephone(updatedEmployeDTO.getTelephone());
        }

        String ancienEmail = employeExistant.getEmail();

        if (updatedEmployeDTO.getEmail() != null && !updatedEmployeDTO.getEmail().equals(ancienEmail)) {
            // L'utilisateur a modifié l'email, alors effectuer la validation existante
            if (employeFacade.findByEmail(updatedEmployeDTO.getEmail()) != null) {
                throw new EmailUniqueException("Cet email existe déjà");
            } else {
                String emailRegex = "^[a-zA-Z0-9_+&*-]+(?:\\.[a-zA-Z0-9_+&*-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,7}$";
                if (!updatedEmployeDTO.getEmail().matches(emailRegex)) {
                    throw new InvalidEmailFormatException("L'email n'est pas au format valide.");
                }
            }
            employeExistant.setEmail(updatedEmployeDTO.getEmail());
        }

        if (updatedEmployeDTO.getActif() != 0){
            employeExistant.setActif(updatedEmployeDTO.getActif());
        }

        if (updatedEmployeDTO.getPrenom() != null){
            employeExistant.setPrenom(updatedEmployeDTO.getPrenom());
        }

        if (updatedEmployeDTO.getMagasin() != null) {
            Magasin magasin = magasinFacade.FindById(updatedEmployeDTO.getMagasin());
            if (magasin == null) {
                throw new DataNotFoundException("Le magasin avec cet ID n'existe pas.");
            } else {
                employeExistant.setMagasin(magasin);
            }
        }

        if (updatedEmployeDTO.getManager() != null){
            Employe manager = employeFacade.FindById(updatedEmployeDTO.getManager());
            if (manager == null) {
                throw new DataNotFoundException("Le manager avec cet ID n'existe pas.");
            } else {
                employeExistant.setManager(manager);
            }
        } else {
            employeExistant.setManager(null);
        }

        employeFacade.UpdateEmploye(employeExistant);

        return Response.status(Response.Status.OK)
                .entity(new SuccessMessage("Employé mis à jour avec succès.", 200))
                .build();

    }

    @Path("/{id}")
    @DELETE
    @Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
    @Operation(
            tags = ApiServiceConfig.TAG_GESTION_EMPLOYES_NAME,
            summary = "Supprimer un employé",
            description = "Supprime un employé spécifié par son ID.",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = " ===> Employé supprimé avec succès.",
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
                            description = " ===> Employé avec l'ID spécifié non trouvé."
                    )
            }
    )
    public Response removeEmploye(@PathParam("id") Integer id) {
        Employe employeExistant = employeFacade.FindById(id);
        if (employeExistant == null) {
            throw new DataNotFoundException("Commande avec ID " + id + " non trouvée.");
        }
        employeFacade.RemoveEmploye(id);
        return Response.status(Response.Status.OK)
                .entity(new SuccessMessage("Employé supprimé avec succès.", 200))
                .build();
    }

}
