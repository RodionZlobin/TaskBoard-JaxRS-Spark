package se.rodion.taskjaxrs.web.exception.mapper;

import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

import se.rodion.taskdata.exception.RepositoryDataException;


@Provider
public final class RepositoryDataExceptionMapper implements ExceptionMapper<RepositoryDataException> {

	@Override
	public Response toResponse(RepositoryDataException exception) {
		return Response.status(Status.NOT_FOUND).entity("RepoInfo: " + exception.getMessage()).type("text/plain").build();
	}

}
