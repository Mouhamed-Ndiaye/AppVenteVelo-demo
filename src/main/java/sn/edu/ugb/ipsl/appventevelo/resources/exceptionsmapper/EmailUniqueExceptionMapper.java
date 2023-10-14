package sn.edu.ugb.ipsl.appventevelo.resources.exceptionsmapper;

import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.ext.ExceptionMapper;
import jakarta.ws.rs.ext.Provider;
import sn.edu.ugb.ipsl.appventevelo.resources.exceptions.EmailUniqueException;
import sn.edu.ugb.ipsl.appventevelo.resources.exceptions.ErrorMessage;

@Provider
public class EmailUniqueExceptionMapper implements ExceptionMapper<EmailUniqueException> {

    @Override
    public Response toResponse(EmailUniqueException exception) {
        ErrorMessage errorMessage = new ErrorMessage(exception.getMessage(), 700, "http://localhost:8080/AppVenteVelo-1.0-SNAPSHOT/documentations/index.html");
        return Response.status(700)
                .entity(errorMessage)
                .build();
    }
}
