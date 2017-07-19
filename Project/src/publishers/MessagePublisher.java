package publishers;

import java.util.HashMap;
import java.util.HashSet;

import messages.CancelMessage;
import messages.FillMessage;
import messages.MarketMessage;
import client.User;

public class MessagePublisher extends Publisher {

	private static MessagePublisher instance;
	
	private MessagePublisher()
	{
		subscribers = new HashMap<String, HashSet<User>>();
	}
	
	public static MessagePublisher getInstance()
	{
		if (instance == null)
		{
			instance = new MessagePublisher();
		}
		return instance;
	}
	
	public synchronized void publishCancel(CancelMessage cm)
	{
		if ( subscribers.containsKey( cm.getProduct() ))
		{
			HashSet<User> list = subscribers.get( cm.getProduct() );
			for ( User u : list )
			{
				if ( u.getUserName().equals( cm.getUser() ) )
				{
					u.acceptMessage( cm );
				}
			}
		}
	}
	
	public synchronized void publishFill(FillMessage fm)
	{
		if ( subscribers.containsKey( fm.getProduct() ))
		{
			HashSet<User> list = subscribers.get( fm.getProduct() );
			for ( User u : list )
			{
				if ( u.getUserName().equals( fm.getUser() ) )
				{
					u.acceptMessage( fm );
				}
			}
		}
	}
	
	public synchronized void publishMarketMessage(MarketMessage mm)
	{
		HashSet<User> temp = new HashSet<User>();
		//System.out.println( subscribers.keySet() );
		for ( String t : subscribers.keySet() )
		{
			//System.out.println( t );
			HashSet<User> list = subscribers.get( t );
			for ( User u : list )
			{
				if ( !temp.contains(u) )
				{
					u.acceptMarketMessage( mm.toString() );
					temp.add(u);
				}
			}
		}
	}
	
}
