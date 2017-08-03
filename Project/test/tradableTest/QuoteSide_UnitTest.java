package tradableTest;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertNotEquals;
import customExceptions.InvalidVolumeException;
import enums.BookSide;
import price.Price;
import price.PriceFactory;
import tradable.QuoteSide;

public class QuoteSide_UnitTest {
	
	String username;
	String product;
	Price p;
	int originalVolume;
	BookSide buy;
	BookSide sell;
	
	QuoteSide buyQuoteSide;
	QuoteSide sellQuoteSide;
	
	@Before
	public void setup()
	{
		username = "REX";
		product = "AMZN";
		p = PriceFactory.makeLimitPrice(1000);
		originalVolume = 10;
		buy = BookSide.BUY;
		sell = BookSide.SELL;
		try
		{
			buyQuoteSide = new QuoteSide(username, product, p, originalVolume, buy);
			sellQuoteSide = new QuoteSide(username, product, p, originalVolume, sell);
		}
		catch (InvalidVolumeException e)
		{
			fail("setup failed");
		}
	}
	
	@Test
	public void quoteSideIsCopiedToNewQuoteSide()
	{
		QuoteSide newQuoteSide = new QuoteSide(buyQuoteSide);
		assertNotEquals("New QuoteSide is not a copy of original QuoteSide", buyQuoteSide, newQuoteSide);
	}

	@Test
	public void originalVolumeLessThan1ForConstructorThrowsException()
	{
		try
		{
			QuoteSide invalidQuoteSide = new QuoteSide(username, product, p, 0, buy);
			fail("New Order of originalVolume < 1 did not throw exception");
		}
		catch (InvalidVolumeException e)
		{
			
		}
	}
	
	@Test
	public void setCancelledVolumeToOriginalVolume()
	{
		int cancelledVolume = buyQuoteSide.getOriginalVolume();
		try
		{
			buyQuoteSide.setCancelledVolume(cancelledVolume);
		}
		catch (InvalidVolumeException e)
		{
			fail("An invalid cancel volume of " + cancelledVolume + " for original volume " + buyQuoteSide.getOriginalVolume() + "passed in");
		}
		String errorString = "Cancel volume of " + cancelledVolume + " not set correctly (" + buyQuoteSide.getCancelledVolume() + ")";
		assertEquals(errorString, cancelledVolume, buyQuoteSide.getCancelledVolume());
	}
	
	@Test
	public void settingCancelledVolumeLargerThanOriginalVolumeThrowsException()
	{
		int cancelledVolume = buyQuoteSide.getOriginalVolume() + 1;
		try
		{
			buyQuoteSide.setCancelledVolume(cancelledVolume);
			fail("A valid cancel volume of " + cancelledVolume + " for original volume " + buyQuoteSide.getOriginalVolume() + "passed in");
		}
		catch (InvalidVolumeException e)
		{
			
		}
	}
	
	@Test
	public void setRemainingVolumeTo0()
	{
		int remainingVolume = 0;
		try
		{
			buyQuoteSide.setRemainingVolume(remainingVolume);
		}
		catch (InvalidVolumeException e)
		{
			fail("An invalid remaining volume of " + remainingVolume + " for remaining volume " + buyQuoteSide.getRemainingVolume() + "passed in");
		}
		String errorString = "Cancel volume of " + remainingVolume + " not set correctly (" + buyQuoteSide.getRemainingVolume() + ")";
		assertEquals(errorString, remainingVolume, buyQuoteSide.getRemainingVolume());
	}
	
	@Test
	public void settingRemainingVolumeToLessThanZeroThrowsException()
	{
		int remainingVolume = -1;
		try
		{
			buyQuoteSide.setRemainingVolume(remainingVolume);
			fail("A valid remaining volume of " + remainingVolume + " for remaining volume " + buyQuoteSide.getRemainingVolume() + "passed in");
		}
		catch (InvalidVolumeException e)
		{
			
		}
	}
	
	@Test
	public void sideQuoteIsQuote()
	{
		assertTrue("Buy QuoteSide is supposed to be a quote", buyQuoteSide.isQuote());
		assertTrue("Sell QuoteSide is supposed to be a quote", sellQuoteSide.isQuote());
	}
}
