package sn.edu.ugb.ipsl.appventevelo.resources.exceptionsmapper;

import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.ext.ExceptionMapper;
import jakarta.ws.rs.ext.Provider;
import sn.edu.ugb.ipsl.appventevelo.resources.exceptions.ErrorMessage;
import sn.edu.ugb.ipsl.appventevelo.resources.exceptions.InvalidDateFormatException;

@Provider
public class InvalidDateFormatExceptionMapper implements ExceptionMapper<InvalidDateFormatException> {

    @Override
    public Response toResponse(InvalidDateFormatException exception) {
        ErrorMessage errorMessage = new ErrorMessage(exception.getMessage(), 618, "http://localhost:8080/AppVenteVelo-1.0-SNAPSHOT/documentations/index.html");
        return Response.status(618)
                .entity(errorMessage)
                .build();
    }
}
