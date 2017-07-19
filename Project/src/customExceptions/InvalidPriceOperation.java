package customExceptions;

public class InvalidPriceOperation extends Exception
{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public InvalidPriceOperation( String s )
	{
		super( s );
	}
}
