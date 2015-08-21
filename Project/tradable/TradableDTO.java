package tradable;

import enums.BookSide;
import price.Price;

public class TradableDTO {

	public String product;
	public Price price;
	public int originalVolume;
	public int remainingVolume;
	public int cancelledVolume;
	public String user;
	public BookSide side;
	public boolean isQuote;
	public String id;
	
	public TradableDTO( String productIn, Price priceIn, int originalVolumeIn, int remainingVolumeIn, int cancelledVolumeIn, String userNameIn, String sideIn, boolean isQuoteIn, String idIn )
	{
		product = productIn;
		price = priceIn;
		originalVolume = originalVolumeIn;
		remainingVolume = remainingVolumeIn;
		cancelledVolume = cancelledVolumeIn;
		user = userNameIn;
		side = BookSide.valueOf( sideIn );
		isQuote = isQuoteIn;
		id = idIn;
	}
	
	
	public String toString()
	{
		return ( "Product: " + product + 
				", Price: " + price.toString() + 
				", OriginalVolume: " + originalVolume +
				", RemailingVolume: " + remainingVolume +
				", CancelledVolume: " + cancelledVolume +
				", User: " + user +
				", Side: " + side +
				", IsQuote: " + isQuote +
				", Id: " + id );
	}
	
}
