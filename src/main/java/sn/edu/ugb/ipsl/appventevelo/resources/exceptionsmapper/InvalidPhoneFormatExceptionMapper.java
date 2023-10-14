package sn.edu.ugb.ipsl.appventevelo.resources.exceptionsmapper;

import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.ext.ExceptionMapper;
import jakarta.ws.rs.ext.Provider;
import sn.edu.ugb.ipsl.appventevelo.resources.exceptions.ErrorMessage;
import sn.edu.ugb.ipsl.appventevelo.resources.exceptions.InvalidPhoneFormatException;

@Provider
public class InvalidPhoneFormatExceptionMapper implements ExceptionMapper<InvalidPhoneFormatException> {

    @Override
    public Response toResponse(InvalidPhoneFormatException exception) {
        ErrorMessage errorMessage = new ErrorMessage(exception.getMessage(), 630, "http://localhost:8080/AppVenteVelo-1.0-SNAPSHOT/documentations/index.html");
        return Response.status(630)
                .entity(errorMessage)
                .build();
    }
}
