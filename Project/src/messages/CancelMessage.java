package messages;

import customExceptions.InvalidEnumException;
//import customExceptions.InvalidPriceOperation;
import customExceptions.InvalidVolumeException;
import price.Price;
import enums.BookSide;

public class CancelMessage extends Messages implements Comparable<CancelMessage> {

	public CancelMessage(String userIn, String productIn, Price priceIn, int volumeIn, String detailsIn, BookSide sideIn, String idIn) throws InvalidVolumeException, InvalidEnumException {
		
		super(userIn, productIn, priceIn, volumeIn, detailsIn, sideIn, idIn);
	
	}

	public int compareTo(CancelMessage arg0) {
		/*
		try //this is bad
		{
			return getPrice().compareTo( arg0.getPrice() ) ;
		}
		catch ( InvalidPriceOperation e )
		{
			
		}
		return 0;
		*/
		return getPrice().compareTo( arg0.getPrice() ) ;
	}

}
