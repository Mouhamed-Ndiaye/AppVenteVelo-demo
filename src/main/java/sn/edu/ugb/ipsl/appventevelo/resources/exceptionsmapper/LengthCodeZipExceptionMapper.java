package sn.edu.ugb.ipsl.appventevelo.resources.exceptionsmapper;

import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.ext.ExceptionMapper;
import jakarta.ws.rs.ext.Provider;
import sn.edu.ugb.ipsl.appventevelo.resources.exceptions.ErrorMessage;
import sn.edu.ugb.ipsl.appventevelo.resources.exceptions.LengthCodeZipException;

@Provider
public class LengthCodeZipExceptionMapper implements ExceptionMapper<LengthCodeZipException> {

    @Override
    public Response toResponse(LengthCodeZipException exception) {
        ErrorMessage errorMessage = new ErrorMessage(exception.getMessage(), 760, "http://localhost:8080/AppVenteVelo-1.0-SNAPSHOT/documentations/index.html");
        return Response.status(760)
                .entity(errorMessage)
                .build();
    }
}
