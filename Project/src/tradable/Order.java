package tradable;

import customExceptions.InvalidVolumeException;
import enums.BookSide;
import price.Price;

public class Order implements Tradable {
	
	private String userName;
	private String product;
	private String id;
	private String side;
	private Price p;
	private String originalOrderVolume;
	private String remainingOrderVolume;
	private String cancelledVolume;
	
	public Order( String userNameIn, String productIn, Price orderPriceIn, int originalVolumeIn, BookSide sideIn ) throws InvalidVolumeException
	{
		if ( originalVolumeIn < 1 )
		{
			throw new InvalidVolumeException( "Cannot have orders of 0 or less" );
		}
		userName = userNameIn;
		product = productIn;
		id = userNameIn + productIn + orderPriceIn + String.valueOf( System.nanoTime() );
		side = sideIn.toString();//side.toString();
		p = orderPriceIn;
		originalOrderVolume = String.valueOf( originalVolumeIn );
		remainingOrderVolume = originalOrderVolume;
		cancelledVolume = String.valueOf( 0 );
	}

	public String getProduct()
	{
		return product;
	}
	
	public Price getPrice()
	{
		return p;
	}
	
	public int getOriginalVolume()
	{
		return Integer.parseInt( originalOrderVolume );
	}
	
	public int getRemainingVolume()
	{
		return Integer.parseInt( remainingOrderVolume );
	}
	
	public int getCancelledVolume()
	{
		return Integer.parseInt( cancelledVolume );
	}
	
	public void setCancelledVolume( int newCancelledVolume ) throws InvalidVolumeException
	{
		if ( newCancelledVolume > Integer.parseInt( originalOrderVolume ) )
		{
			throw new InvalidVolumeException("Cannot cancel more than owned");
		}
		cancelledVolume = String.valueOf( newCancelledVolume );
	}
	
	public void setRemainingVolume( int newRemainingVolume ) throws InvalidVolumeException
	{
		if ( newRemainingVolume < 0 || newRemainingVolume > Integer.parseInt(remainingOrderVolume) )
		{
			throw new InvalidVolumeException(" Cannot have less than 0 remaining or more than currently held");
		}
		remainingOrderVolume = String.valueOf( newRemainingVolume );
	}
	
	public String getUser()
	{
		return userName;
	}
	
	public String getSide()
	{
		return side;
	}
	
	public boolean isQuote()
	{
		return false;
	}
	
	public String getId()
	{
		return id;
	}
	
	public String toString()
	{
		return		( userName +
					" order: " + getSide() + " " + getRemainingVolume() + " " + getProduct() +
					" at " + p.toString() +
					" (Original Vol: " + getOriginalVolume() + ", CXL'd Vol: " + getCancelledVolume() +
					"), ID: " + getId() );
	}
	
}
