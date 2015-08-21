package customExceptions;

public class InvalidMarketStateTransitionException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	public InvalidMarketStateTransitionException( String s )
	{
		super( s );
	}

}
