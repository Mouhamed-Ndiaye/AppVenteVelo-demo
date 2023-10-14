package sn.edu.ugb.ipsl.appventevelo.resources.exceptionsmapper;


import jakarta.ws.rs.ProcessingException;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.ext.ExceptionMapper;
import sn.edu.ugb.ipsl.appventevelo.resources.exceptions.ErrorMessage;


public class JsonbExceptionMapper implements ExceptionMapper<ProcessingException> {

    @Override
    public Response toResponse(ProcessingException exception) {
        ErrorMessage errorMessage = new ErrorMessage(exception.getMessage(), 500,"http://localhost:8080/AppVenteVelo-1.0-SNAPSHOT/documentations/index.html");
        return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                .entity(errorMessage)
                .build();
    }
}
