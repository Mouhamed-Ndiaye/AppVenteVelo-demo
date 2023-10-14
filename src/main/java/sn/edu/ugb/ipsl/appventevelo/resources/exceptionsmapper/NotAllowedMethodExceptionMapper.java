package sn.edu.ugb.ipsl.appventevelo.resources.exceptionsmapper;

import jakarta.ws.rs.NotAllowedException;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.ext.ExceptionMapper;
import jakarta.ws.rs.ext.Provider;
import sn.edu.ugb.ipsl.appventevelo.resources.exceptions.ErrorMessage;

@Provider
public class NotAllowedMethodExceptionMapper implements ExceptionMapper<NotAllowedException> {

    @Override
    public Response toResponse(NotAllowedException exception) {
        ErrorMessage errorMessage = new ErrorMessage("La méthode utilisée est inapropriée", 505, "http://localhost:8080/AppVenteVelo-1.0-SNAPSHOT/documentations/index.html");
        return Response.status(505)
                .entity(errorMessage)
                .build();
    }
}
