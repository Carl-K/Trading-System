package customExceptions;

public class InvalidConnectionIdException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	public InvalidConnectionIdException( String s )
	{
		super( s );
	}

}
