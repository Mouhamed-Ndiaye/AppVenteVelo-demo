package sn.edu.ugb.ipsl.appventevelo.resources.exceptionsmapper;

import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.ext.ExceptionMapper;
import jakarta.ws.rs.ext.Provider;
import sn.edu.ugb.ipsl.appventevelo.resources.exceptions.CleEtrangereObligatoireException;
import sn.edu.ugb.ipsl.appventevelo.resources.exceptions.ErrorMessage;

@Provider
public class CleEtrangereObligatoireExceptionMapper implements ExceptionMapper<CleEtrangereObligatoireException> {

    @Override
    public Response toResponse(CleEtrangereObligatoireException exception) {
        ErrorMessage errorMessage = new ErrorMessage(exception.getMessage(), 508, "http://localhost:8080/AppVenteVelo-1.0-SNAPSHOT/documentations/index.html");
        return Response.status(508)
                .entity(errorMessage)
                .build();
    }
}
