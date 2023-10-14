package sn.edu.ugb.ipsl.appventevelo.resources.exceptionsmapper;

import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.ext.ExceptionMapper;
import jakarta.ws.rs.ext.Provider;
import sn.edu.ugb.ipsl.appventevelo.resources.exceptions.ChampObligatoireException;
import sn.edu.ugb.ipsl.appventevelo.resources.exceptions.ErrorMessage;

@Provider
public class ChampObligatoireExceptionMapper implements ExceptionMapper<ChampObligatoireException> {

    @Override
    public Response toResponse(ChampObligatoireException exception) {
        ErrorMessage errorMessage = new ErrorMessage(exception.getMessage(), 600, "http://localhost:8080/AppVenteVelo-1.0-SNAPSHOT/documentations/index.html");
        return Response.status(600)
                .entity(errorMessage)
                .build();
    }
}
