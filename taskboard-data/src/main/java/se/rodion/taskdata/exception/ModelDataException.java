package se.rodion.taskdata.exception;

public final class ModelDataException extends Exception
{	
	private static final long serialVersionUID = 1188789358022827921L;

	public ModelDataException(String message)
	{
		super(message);
	}
	
	public ModelDataException (String message, Throwable cause)
	{
		super(message, cause);
	}

}
