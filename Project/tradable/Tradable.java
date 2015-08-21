package tradable;

import customExceptions.InvalidVolumeException;
import price.Price;

public interface Tradable 
{
	
	public String getProduct();
	
	public Price getPrice();
	
	public int getOriginalVolume();
	
	public int getRemainingVolume();
	
	public int getCancelledVolume();
	
	public void setCancelledVolume( int newCancelledVolume ) throws InvalidVolumeException;
	
	public void setRemainingVolume( int newRemainingVolume ) throws InvalidVolumeException;
	
	public String getUser();
	
	public String getSide();
	
	public boolean isQuote();
	
	public String getId();
	
}
