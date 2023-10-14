package sn.edu.ugb.ipsl.appventevelo.resources.exceptionsmapper;

import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.ext.ExceptionMapper;
import jakarta.ws.rs.ext.Provider;
import sn.edu.ugb.ipsl.appventevelo.resources.exceptions.ErrorMessage;
import sn.edu.ugb.ipsl.appventevelo.resources.exceptions.InvalidCodeZipFormatException;

@Provider
public class InvalidCodeZipFormatExceptionMapper implements ExceptionMapper<InvalidCodeZipFormatException> {

    @Override
    public Response toResponse(InvalidCodeZipFormatException exception) {
        ErrorMessage errorMessage = new ErrorMessage(exception.getMessage(), 850, "http://localhost:8080/AppVenteVelo-1.0-SNAPSHOT/documentations/index.html");
        return Response.status(850)
                .entity(errorMessage)
                .build();
    }
}
