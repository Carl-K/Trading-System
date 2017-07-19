package messages;

import customExceptions.InvalidVolumeException;
import price.Price;
import enums.BookSide;

public class Messages {

	private String user;
	private String product;
	private Price price;
	private int volume;
	private String details;
	private BookSide side;
	public String id;
	
	public Messages(String userIn, String productIn, Price priceIn, int volumeIn, String detailsIn, BookSide sideIn, String idIn) throws InvalidVolumeException
	{
		setUser( userIn );
		setProduct( productIn );
		setPrice( priceIn );
		setVolume( volumeIn );
		setDetails( detailsIn );
		setSide( sideIn );
		setId( idIn );
	}
	
	public String getUser()
	{
		return user;
	}
	protected void setUser( String userIn )
	{
		user = userIn;
	}
	
	public String getProduct()
	{
		return product;
	}
	protected void setProduct( String productIn )
	{
		product = productIn;
	}
	
	public Price getPrice()
	{
		return price;
	}
	protected void setPrice( Price priceIn )
	{
		price = priceIn;
	}
	
	public int getVolume()
	{
		return volume;
	}
	/*
	public void setVolume( int volumeIn ) throws InvalidVolumeException
	{
		if ( volumeIn < 0 )
		{
			throw new InvalidVolumeException("Cannot have volume < 0");
		}
		volume = volumeIn;
	}
	*/
	public void setVolume( int volumeIn )
	{
		volume = volumeIn;
	}
	public String getDetails()
	{
		return details;
	}
	public void setDetails( String detailsIn )
	{
		details = detailsIn;
	}
	
	public BookSide getSide()
	{
		return side;
	}
	protected void setSide( BookSide sideIn ) //throws InvalidEnumException
	{
		side = sideIn;
		/*
		for ( BookSide b : BookSide.values() )
		{
			if ( b.name().equals( sideIn ) )
			{
				side = BookSide.valueOf( sideIn );
				return;
			}
		}
		throw new InvalidEnumException("Invalid bookside");
		*/
	}
	
	public String getId()
	{
		return id;
	}
	protected void setId( String idIn )
	{
		id = idIn;
	}
	
	public String toString()
	{
		return "User: " + getUser() + ", Product: " + getProduct() + ", Price: " + getPrice() +
				", Volume: " + getVolume() + ", Details: " + getDetails() + ", Side: " + getSide() +
				", Id: " + getId() ;
	}
	
}
