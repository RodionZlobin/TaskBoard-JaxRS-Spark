package se.rodion.taskjaxrs.web.exception.mapper;

import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

import se.rodion.taskdata.exception.ServiceDataException;


@Provider
public final class ServiceDataExceptionMapper implements ExceptionMapper<ServiceDataException> {

	@Override
	public Response toResponse(ServiceDataException exception) {
		return Response.status(Status.BAD_REQUEST).entity("ServiceInfo: " + exception.getMessage()).type("text/plain").build();
	}

}
