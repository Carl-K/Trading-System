package customExceptions;

public class AlreadyConnectedException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	public AlreadyConnectedException( String s )
	{
		super( s );
	}

}
