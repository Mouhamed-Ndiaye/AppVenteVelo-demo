package sn.edu.ugb.ipsl.appventevelo.resources.exceptionsmapper;

import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.ext.ExceptionMapper;
import jakarta.ws.rs.ext.Provider;
import sn.edu.ugb.ipsl.appventevelo.resources.exceptions.ErrorMessage;
import sn.edu.ugb.ipsl.appventevelo.resources.exceptions.TelephoneUniqueException;

@Provider
public class TelephoneUniqueExceptionMapper implements ExceptionMapper<TelephoneUniqueException> {

    @Override
    public Response toResponse(TelephoneUniqueException exception) {
        ErrorMessage errorMessage = new ErrorMessage(exception.getMessage(), 800, "http://localhost:8080/AppVenteVelo-1.0-SNAPSHOT/documentations/index.html");
        return Response.status(800)
                .entity(errorMessage)
                .build();
    }
}
