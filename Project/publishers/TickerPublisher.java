package publishers;

import java.util.HashMap;
import java.util.HashSet;

import price.Price;
import client.User;

public class TickerPublisher extends Publisher {

	private static TickerPublisher instance;
	
	private static HashMap<String, Price> mostRecent;
	
	private TickerPublisher()
	{
		subscribers = new HashMap<String, HashSet<User>>();
		mostRecent = new HashMap<String, Price>();
	}
	
	public static TickerPublisher getInstance()
	{
		if (instance == null)
		{
			instance = new TickerPublisher();
		}
		return instance;
	}
	
	public synchronized void publishTicker( String product, Price p )
	{
		char direction;
		if ( !mostRecent.containsKey( product ) )
		{
			direction = ' ';
		}
		else
		{
			if ( mostRecent.get( product ).greaterThan( p ) )
			{
				//direction = (char)8595;
				direction = 'D';
			}
			else if ( mostRecent.get( product ).lessThan( p ) )
			{
				//direction = (char)8593;
				direction = 'U';
			}
			else
			{
				direction = '=';
			}
		}
		
		if ( subscribers.containsKey( product ))
		{
			HashSet<User> list = subscribers.get(product );
			for ( User u : list )
			{
				u.acceptTicker(product, p, direction);
			}
		}
		mostRecent.put( product, p );
	}
	
}
