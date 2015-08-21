package customExceptions;

public class ProductAlreadyExistsException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	public ProductAlreadyExistsException( String s )
	{
		super( s );
	}

}
