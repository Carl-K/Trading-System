package publisherTest;

import price.Price;
import price.PriceFactory;
import publishers.MessagePublisher;
import client.UserImpl;
import messages.CancelMessage;
import messages.FillMessage;
import messages.MarketMessage;

import org.junit.Before;
import org.junit.Test;

import enums.BookSide;
import enums.MarketState;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

public class MessagePublisher_UnitTest {
	
	MessagePublisher publisher;
	UserImpl user;
	String product;
	
	@Before
	public void before()
	{
		publisher = MessagePublisher.getInstance();
		user = new UserImpl("TEST");
		product = "AMZN";
	}
	
	@Test
	public void publisherIsSingleton()
	{
		MessagePublisher samePublisher = MessagePublisher.getInstance();
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
		
		CancelMessage cm = null;
		
		try
		{
			cm = new CancelMessage("OTHER", product, price, 10, "CANCELLED", BookSide.BUY, "-1");
		}
		catch(Exception e)
		{
			fail("Failed to create a cancel message");
		}
		
		publisher.publishCancel(cm);
		
		FillMessage fm = null;
		
		try
		{
			fm = new FillMessage("OTHER", product, price, 10, "FILLED", BookSide.BUY, "-1");
		}
		catch(Exception e)
		{
			fail("Failed to create a fill message");
		}
		
		publisher.publishFill(fm);
		
		MarketMessage mm = null;
		
		try
		{
			mm = new MarketMessage(MarketState.CLOSED);
		}
		catch(Exception e)
		{
			fail("Failed to create a market message");
		}
		
		publisher.publishMarketMessage(mm);
	}
}
