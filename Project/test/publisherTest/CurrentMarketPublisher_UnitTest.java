package publisherTest;

import price.Price;
import price.PriceFactory;
import publishers.MarketDataDTO;
import publishers.CurrentMarketPublisher;
import client.UserImpl;
import customExceptions.SubscriberException;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

public class CurrentMarketPublisher_UnitTest {
	
	CurrentMarketPublisher publisher;
	UserImpl user;
	String product;
	
	@Before
	public void before()
	{
		publisher = CurrentMarketPublisher.getInstance();
		user = new UserImpl("TEST");
		product = "AMZN";
	}
	
	@Test
	public void publisherIsSingleton()
	{
		CurrentMarketPublisher samePublisher = CurrentMarketPublisher.getInstance();
		assertEquals("Instances are not the same", publisher, samePublisher);
	}
	
	@Test
	public void userSubscribesSuccessfully()
	{
		try
		{
			publisher.subscribe(user, product);
		}
		catch (Exception e)
		{
			fail("User could not subscribe successfully");
		}
	}
	
	@Test
	public void userThatSubscribesOnceAlreadySubscribedThrowsException()
	{
		try
		{
			publisher.subscribe(user, product);
		}
		catch (Exception e)
		{
			fail("User could not subscribe successfully");
		}
		
		try
		{
			publisher.subscribe(user, product);
			fail("User subscribed twice");
		}
		catch (SubscriberException e)
		{
			
		}
	}
	
	@Test
	public void userUnsubscribesSuccessfully()
	{
		try
		{
			publisher.subscribe(user, product);
		}
		catch (Exception e)
		{
			fail("User could not subscribe successfully");
		}
		
		try
		{
			publisher.unSubscribe(user, product);
		}
		catch (Exception e)
		{
			fail("User could not unsubscribe successfully");
		}
	}
	
	@Test
	public void unsubscribedUserThatUnsubscribesThrowsException()
	{
		try
		{
			publisher.unSubscribe(user, product);
			fail("Unsubscribed user unsubscribed successfully");
		}
		catch (SubscriberException e)
		{
			
		}
	}

	@Test
	public void userRecievesPublication()
	{
		try
		{
			publisher.subscribe(user, product);
		}
		catch (Exception e)
		{
			fail("User could not subscribe successfully");
		}
		
		try
		{
			user.connect();
		}
		catch (Exception e)
		{
			fail("User failed to connect");
		}
		
		Price buyPrice = PriceFactory.makeLimitPrice(1000);
		Price sellPrice = PriceFactory.makeLimitPrice(950);
		
		MarketDataDTO dto = new MarketDataDTO(product, buyPrice, 20, sellPrice, 10);
		publisher.publishCurrentMarket(dto);
	}
}
