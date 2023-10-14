package sn.edu.ugb.ipsl.appventevelo.resources.exceptionsmapper;

import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.ext.ExceptionMapper;
import jakarta.ws.rs.ext.Provider;
import sn.edu.ugb.ipsl.appventevelo.resources.exceptions.ErrorMessage;
import sn.edu.ugb.ipsl.appventevelo.resources.exceptions.InvalidEmailFormatException;

@Provider
public class InvalidEmailFormatExceptionMapper implements ExceptionMapper<InvalidEmailFormatException> {

    @Override
    public Response toResponse(InvalidEmailFormatException exception) {
        ErrorMessage errorMessage = new ErrorMessage(exception.getMessage(), 615, "http://localhost:8080/AppVenteVelo-1.0-SNAPSHOT/documentations/index.html");
        return Response.status(615)
                .entity(errorMessage)
                .build();
    }
}
