package client;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;

import customExceptions.InvalidPriceOperation;
import enums.BookSide;
import price.Price;
import price.PriceFactory;

public class Position {

	private HashMap<String, Integer> holdings = new HashMap<String, Integer>();
	private Price accountCosts = PriceFactory.makeLimitPrice(0);
	private HashMap<String, Price> lastSales = new HashMap<String, Price>();
	
	public Position()
	{
		//Added for clarity
	}
	
	public void updatePosition(String product, Price price, BookSide side, int volume) throws InvalidPriceOperation
	{
		int adjustedVolume = ( side.equals( BookSide.BUY ) ? volume : -volume );
		
		if ( !holdings.containsKey( product ) )
		{
			holdings.put(product, adjustedVolume);
		}
		else
		{
			int currentHolding = holdings.get( product );
			currentHolding += adjustedVolume;
			holdings.put(product, currentHolding);
		}
		
		Price totalPrice = PriceFactory.makeLimitPrice( price.getValue() * volume );
		
		if ( side.equals( BookSide.BUY ) )
		{
			accountCosts = accountCosts.subtract( totalPrice );
		}
		else
		{
			accountCosts = accountCosts.add( totalPrice );
		}
	}
	
	public void updateLastSale(String product, Price price)
	{
		lastSales.put( product, price );
	}
	
	public int getStockPositionVolume(String product)
	{
		if ( !holdings.containsKey(product) )
		{
			return 0;
		}
		return holdings.get(product);
	}
	
	public ArrayList<String> getHoldings()
	{
		ArrayList<String> h = new ArrayList<>(holdings.keySet());
		Collections.sort(h);
		return h;
	}
	
	public Price getStockPositionValue(String product)
	{
		if ( !holdings.containsKey(product) )
		{
			return PriceFactory.makeLimitPrice(0);
		}
		Price lastPrice = lastSales.get(product);
		if ( lastPrice == null )
		{
			return PriceFactory.makeLimitPrice(0);
		}
		return PriceFactory.makeLimitPrice( lastPrice.getValue() * holdings.get(product) );
	}
	
	public Price getAccountCosts()
	{
		return accountCosts;
	}
	
	public Price getAllStockValue() throws InvalidPriceOperation
	{
		Price totalValue = PriceFactory.makeLimitPrice(0);
		for ( String product : holdings.keySet() )
		{
			totalValue = totalValue.add( getStockPositionValue(product) );
		}
		return totalValue;
	}
	
	public Price getNetAccountValue() throws InvalidPriceOperation
	{
		return PriceFactory.makeLimitPrice( getAllStockValue().getValue() + accountCosts.getValue() );
	}
	
}
