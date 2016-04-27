package se.rodion.taskjaxrs.web.exception.mapper;

import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

import se.rodion.taskdata.exception.ModelDataException;

@Provider
public final class ModelDataExceptionMapper implements ExceptionMapper<ModelDataException>
{

	@Override
	public Response toResponse(ModelDataException exception)
	{
		return Response.status(Status.BAD_REQUEST).entity("ModelInfo: " + exception.getMessage()).type("text/plain").build();
	}

}
