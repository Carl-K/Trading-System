package publishers;

import java.util.HashMap;
import java.util.HashSet;

import price.PriceFactory;
import client.User;

public class CurrentMarketPublisher extends Publisher {

	private static CurrentMarketPublisher instance;
	
	private CurrentMarketPublisher()
	{
		subscribers = new HashMap<String, HashSet<User>>();
	}
	
	public static CurrentMarketPublisher getInstance()
	{
		if (instance == null)
		{
			instance = new CurrentMarketPublisher();
		}
		return instance;
	}
	
	public synchronized void publishCurrentMarket(MarketDataDTO md)
	{
		if ( subscribers.containsKey( md.product ))
		{
			HashSet<User> list = subscribers.get( md.product );
			for ( User u : list )
			{
				u.acceptCurrentMarket( md.product, (md.buyPrice == null ? PriceFactory.makeLimitPrice(0) : md.buyPrice), md.buyVolume, (md.sellPrice == null ? PriceFactory.makeLimitPrice(0) : md.sellPrice), md.sellVolume );
			}
		}
	}
	
}
