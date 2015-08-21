package publishers;

import java.util.HashMap;
import java.util.HashSet;

import client.User;
import price.Price;
import publishers.TickerPublisher;

public class LastSalePublisher extends Publisher {

	private static LastSalePublisher instance;
	
	private LastSalePublisher()
	{
		subscribers = new HashMap<String, HashSet<User>>();
	}
	
	public static LastSalePublisher getInstance()
	{
		if (instance == null)
		{
			instance = new LastSalePublisher();
		}
		return instance;
	}
	
	public synchronized void publishLastSale( String product, Price p, int v )
	{
		if ( subscribers.containsKey( product ))
		{
			HashSet<User> list = subscribers.get( product );
			for ( User u : list )
			{
				u.acceptLastSale( product, p, v  );
			}
			TickerPublisher.getInstance().publishTicker( product, p );
		}
	}
	
}