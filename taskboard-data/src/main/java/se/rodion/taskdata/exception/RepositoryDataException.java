package se.rodion.taskdata.exception;

public final class RepositoryDataException extends Exception
{
	private static final long serialVersionUID = 609521475288739858L;
	
	public RepositoryDataException (String message)
	{
		super(message);
	}
	
	public RepositoryDataException (String message, Throwable cause)
	{
		super(message, cause);
	}

}
