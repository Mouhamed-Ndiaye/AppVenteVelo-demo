package sn.edu.ugb.ipsl.appventevelo.resources.exceptionsmapper;

import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.ext.ExceptionMapper;
import jakarta.ws.rs.ext.Provider;
import sn.edu.ugb.ipsl.appventevelo.resources.exceptions.ErrorMessage;
import sn.edu.ugb.ipsl.appventevelo.resources.exceptions.SearchNotFoundException;

@Provider
public class SearchNotFoundExceptionMapper implements ExceptionMapper<SearchNotFoundException> {

    @Override
    public Response toResponse(SearchNotFoundException exception) {
        ErrorMessage errorMessage = new ErrorMessage(exception.getMessage(), 604, "http://localhost:8080/AppVenteVelo-1.0-SNAPSHOT/documentations/index.html");
        return Response.status(604)
                .entity(errorMessage)
                .build();
    }
}
