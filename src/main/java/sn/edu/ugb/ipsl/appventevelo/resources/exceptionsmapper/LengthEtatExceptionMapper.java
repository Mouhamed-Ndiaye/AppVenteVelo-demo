package sn.edu.ugb.ipsl.appventevelo.resources.exceptionsmapper;

import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.ext.ExceptionMapper;
import jakarta.ws.rs.ext.Provider;
import sn.edu.ugb.ipsl.appventevelo.resources.exceptions.ErrorMessage;
import sn.edu.ugb.ipsl.appventevelo.resources.exceptions.LengthEtatException;

@Provider
public class LengthEtatExceptionMapper implements ExceptionMapper<LengthEtatException> {

    @Override
    public Response toResponse(LengthEtatException exception) {
        ErrorMessage errorMessage = new ErrorMessage(exception.getMessage(), 750, "http://localhost:8080/AppVenteVelo-1.0-SNAPSHOT/documentations/index.html");
        return Response.status(750)
                .entity(errorMessage)
                .build();
    }
}
