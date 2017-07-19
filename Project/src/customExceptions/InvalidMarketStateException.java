package customExceptions;

public class InvalidMarketStateException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	public InvalidMarketStateException( String s )
	{
		super( s );
	}

}
