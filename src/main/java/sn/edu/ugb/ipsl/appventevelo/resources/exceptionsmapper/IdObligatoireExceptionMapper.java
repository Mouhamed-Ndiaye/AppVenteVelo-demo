package sn.edu.ugb.ipsl.appventevelo.resources.exceptionsmapper;

import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.ext.ExceptionMapper;
import jakarta.ws.rs.ext.Provider;
import sn.edu.ugb.ipsl.appventevelo.resources.exceptions.ErrorMessage;
import sn.edu.ugb.ipsl.appventevelo.resources.exceptions.IdObligatoireException;

@Provider
public class IdObligatoireExceptionMapper implements ExceptionMapper<IdObligatoireException> {

    @Override
    public Response toResponse(IdObligatoireException exception) {
        ErrorMessage errorMessage = new ErrorMessage(exception.getMessage(), 100, "http://localhost:8080/AppVenteVelo-1.0-SNAPSHOT/documentations/index.html");
        return Response.status(100)
                .entity(errorMessage)
                .build();
    }
}
