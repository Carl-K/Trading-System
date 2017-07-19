package messages;

import customExceptions.InvalidEnumException;
//import customExceptions.InvalidPriceOperation;
import customExceptions.InvalidVolumeException;
import price.Price;
import enums.BookSide;

public class FillMessage extends Messages implements Comparable<FillMessage> {

	public FillMessage(String userIn, String productIn, Price priceIn, int volumeIn, String detailsIn, BookSide sideIn, String idIn) throws InvalidVolumeException, InvalidEnumException {
		
		super(userIn, productIn, priceIn, volumeIn, detailsIn, sideIn, idIn);
		
	}

	public int compareTo(FillMessage o) {
		/*
		try //this is bad
		{
			return getPrice().compareTo( o.getPrice() ) ;
		}
		catch ( InvalidPriceOperation e )
		{
			
		}
		return 0;
		*/
		return getPrice().compareTo( o.getPrice() ) ;
	}

}
