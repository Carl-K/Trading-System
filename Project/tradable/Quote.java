package tradable;

import customExceptions.InvalidVolumeException;
import enums.BookSide;
import price.Price;

public class Quote {
	
	private String userName;
	private String product;
	private QuoteSide buy;
	private QuoteSide sell;
	
	public Quote( String userNameIn, String productIn, Price buyPriceIn, int buyVolumeIn, Price sellPriceIn, int sellVolumeIn ) throws InvalidVolumeException
	{
		if ( buyVolumeIn < 1 || sellVolumeIn < 1 )
		{
			throw new InvalidVolumeException("Cannot buy or sell 0 or less");
		}
		userName = userNameIn;
		product = productIn;
		buy = new QuoteSide( userNameIn, productIn, buyPriceIn, buyVolumeIn, BookSide.BUY );
		this.sell = new QuoteSide( userNameIn, productIn, sellPriceIn, sellVolumeIn, BookSide.SELL );
	}
	
	public String getUserName()
	{
		return userName;
	}
	
	public String getProduct()
	{
		return product;
	}
	
	public QuoteSide getQuoteSide( String sideIn/*BookSide sideIn*/ )
	{
		//return ( BookSide.valueOf( sideIn ) == BookSide.BUY ? new QuoteSide( buy ) : new QuoteSide( sell ) );
		return ( BookSide.valueOf( sideIn ) == BookSide.BUY ? buy : sell );
	}
	
	public String toString()
	{
		QuoteSide buyCopy = getQuoteSide( BookSide.BUY.toString() );
		QuoteSide sellCopy= getQuoteSide( BookSide.SELL.toString() );
		return ( getUserName() + " quote: " + getProduct() + " " + buyCopy.getPrice().toString() + " x " + buyCopy.getRemainingVolume() +
				" (Original Vol: " + buyCopy.getOriginalVolume() + ", CXL'd Vol: " + buyCopy.getRemainingVolume() + ") [" + buyCopy.getId() + "] - " +
				sellCopy.getPrice().toString() + " x " + sellCopy.getRemainingVolume() +
				"(Original Vol: " + sellCopy.getOriginalVolume() + ", CXL'd Vol: " + sellCopy.getRemainingVolume() + ") [" + sellCopy.getId() + "]" );
	}

}
