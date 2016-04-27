package se.rodion.taskjaxrs.web.exception;

import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

public final class BadRequestException extends WebApplicationException
{
	private static final long serialVersionUID = 5110216245203788814L;

	public BadRequestException(String message)
	{
		super(Response.status(Status.BAD_REQUEST).entity(message).type("text/plain").build());
	}

}