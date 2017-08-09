package publisherTest;

import price.Price;
import price.PriceFactory;
import publishers.TickerPublisher;
import client.UserImpl;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

public class TickerPublisher_UnitTest {
	
	TickerPublisher publisher;
	UserImpl user;
	String product;
	
	@Before
	public void before()
	{
		publisher = TickerPublisher.getInstance();
		user = new UserImpl("TEST");
		product = "AMZN";
	}
	
	@Test
	public void publisherIsSingleton()
	{
		TickerPublisher samePublisher = TickerPublisher.getInstance();
		assertEquals("Instances are not the same", publisher, samePublisher);
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
		
		Price price = PriceFactory.makeLimitPrice(1000);
		
		publisher.publishTicker(product, price);
	}
}
