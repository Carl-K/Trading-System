package tradableTest;

import price.PriceFactory;
import price.Price;
import enums.BookSide;
import tradable.Order;

import org.junit.Before;

import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.fail;

import customExceptions.InvalidVolumeException;

public class Order_UnitTest {
	
	String username;
	String product;
	Price p;
	int originalVolume;
	BookSide buy;
	BookSide sell;
	Order buyOrder;
	Order sellOrder;
	
	@Before
	public void before()
	{
		username = "REX";
		product = "AMZN";
		p = PriceFactory.makeLimitPrice(1000);
		originalVolume = 10;
		buy = BookSide.BUY;
		sell = BookSide.SELL;
		try
		{
			buyOrder = new Order(username, product, p, originalVolume, buy);
			sellOrder = new Order(username, product, p, originalVolume, sell);
		}
		catch (InvalidVolumeException e)
		{
			fail("setup failed");
		}
	}
	
	@Test
	public void originalVolumeLessThan1ForConstructorThrowsException()
	{
		try
		{
			Order invalidOrder = new Order(username, product, p, 0, buy);
			fail("New Order of originalVolume < 1 did not throw exception");
		}
		catch (InvalidVolumeException e)
		{
			
		}
	}
	
	@Test
	public void setCancelledVolumeToOriginalVolume()
	{
		int cancelledVolume = buyOrder.getOriginalVolume();
		try
		{
			buyOrder.setCancelledVolume(cancelledVolume);
		}
		catch (InvalidVolumeException e)
		{
			fail("An invalid cancel volume of " + cancelledVolume + " for original volume " + buyOrder.getOriginalVolume() + "passed in");
		}
		String errorString = "Cancel volume of " + cancelledVolume + " not set correctly (" + buyOrder.getCancelledVolume() + ")";
		assertEquals(errorString, cancelledVolume, buyOrder.getCancelledVolume());
	}
	
	@Test
	public void settingCancelledVolumeLargerThanOriginalVolumeThrowsException()
	{
		int cancelledVolume = buyOrder.getOriginalVolume() + 1;
		try
		{
			buyOrder.setCancelledVolume(cancelledVolume);
			fail("A valid cancel volume of " + cancelledVolume + " for original volume " + buyOrder.getOriginalVolume() + "passed in");
		}
		catch (InvalidVolumeException e)
		{
			
		}
	}
	
	@Test
	public void setRemainingVolumeToOriginalVolume()
	{
		int remainingVolume = buyOrder.getOriginalVolume();
		try
		{
			buyOrder.setRemainingVolume(remainingVolume);
		}
		catch (InvalidVolumeException e)
		{
			fail("An invalid remaining volume of " + remainingVolume + " for remaining volume " + buyOrder.getRemainingVolume() + "passed in");
		}
		String errorString = "Cancel volume of " + remainingVolume + " not set correctly (" + buyOrder.getRemainingVolume() + ")";
		assertEquals(errorString, remainingVolume, buyOrder.getRemainingVolume());
	}
	
	@Test
	public void settingRemainingVolumeToLessThanZeroThrowsException()
	{
		int remainingVolume = -1;
		try
		{
			buyOrder.setRemainingVolume(remainingVolume);
			fail("A valid remaining volume of " + remainingVolume + " for remaining volume " + buyOrder.getRemainingVolume() + "passed in");
		}
		catch (InvalidVolumeException e)
		{
			
		}
	}
	
	@Test
	public void settingRemainingVolumeToGreaterThanRemainingVolumeThrowsException()
	{
		int remainingVolume = buyOrder.getRemainingVolume() + 1;
		try
		{
			buyOrder.setRemainingVolume(remainingVolume);
			fail("A valid remaining volume of " + remainingVolume + " for remaining volume " + buyOrder.getRemainingVolume() + "passed in");
		}
		catch (InvalidVolumeException e)
		{
			
		}
	}
	
	@Test
	public void orderIsNotQuote()
	{
		assertFalse("Buy order is not supposed to be a quote", buyOrder.isQuote());
		assertFalse("Sell order is not supposed to be a quote", sellOrder.isQuote());
	}
}
