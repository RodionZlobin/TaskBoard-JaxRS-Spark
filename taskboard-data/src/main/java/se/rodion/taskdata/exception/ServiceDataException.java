package se.rodion.taskdata.exception;

public final class ServiceDataException extends Exception
{
	private static final long serialVersionUID = -4244230929363809168L;
	
	public ServiceDataException (String message)
	{
		super(message);
	}
	
	public ServiceDataException (String message, Throwable cause)
	{
		super(message, cause);
	}
}
