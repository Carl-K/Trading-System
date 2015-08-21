package publishers;

import java.util.HashMap;
import java.util.HashSet;

import customExceptions.SubscriberException;
import client.User;

public abstract class Publisher {

	protected HashMap<String, HashSet<User>> subscribers;
	
	public synchronized void subscribe( User u, String product) throws SubscriberException
	{
		if ( subscribers.containsKey( product ) && subscribers.get( product ).contains( u ) )
		{
			throw new SubscriberException("User already subscribed");
		}
		else
		{
			if( !subscribers.containsKey( product ) )
			{
				subscribers.put( product , new HashSet<User>() );
			}
			subscribers.get( product ).add( u );
		}
	}
	
	public synchronized void unSubscribe( User u, String product ) throws SubscriberException
	{
		if( subscribers.containsKey( product ) && !subscribers.get( product ).contains( u ) )
		{
			throw new SubscriberException("User not subscribed");
		}
		else
		{
			subscribers.get( product ).remove( u );
			//maybe dealloc after product has no subscribers
		}
	}
	
}
