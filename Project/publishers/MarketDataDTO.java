package publishers;

import price.Price;

public class MarketDataDTO {
	
	public String product;
	public Price buyPrice;
	public int buyVolume;
	public Price sellPrice;
	public int sellVolume;
	
	public MarketDataDTO( String productIn, Price buyPriceIn, int buyVolumeIn, Price sellPriceIn, int sellVolumeIn )
	{
		product = productIn;
		buyPrice = buyPriceIn;
		buyVolume = buyVolumeIn;
		sellPrice = sellPriceIn;
		sellVolume = sellVolumeIn;
	}
	
	public String toString()
	{
		return "Stock: " + product + " ,Buy Price: " + ( (buyPrice == null) ? "NO_PRICE" : buyPrice.toString() ) + " ,Buy Volume: " + buyVolume + " ,Sell Price: " + ( (sellPrice == null) ? "NO_PRICE" : sellPrice.toString() ) + " ,Sell Volume: " + sellVolume ;
	}
	
}
