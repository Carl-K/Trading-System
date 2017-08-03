package tradableTest;

import price.PriceFactory;
import price.Price;
import tradable.Quote;
import tradable.QuoteSide;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.fail;
import static org.junit.Assert.assertEquals;

import customExceptions.InvalidVolumeException;
import enums.BookSide;

public class Quote_UnitTest {
	
	String username;
	String product;
	Price buyPrice;
	int buyVolume;
	Price sellPrice;
	int sellVolume;
	
	Quote q;
	
	@Before
	public void before()
	{
		username = "REX";
		product = "AMZN";
		buyPrice = PriceFactory.makeLimitPrice(1000);
		buyVolume = 20;
		sellPrice = PriceFactory.makeLimitPrice(1010);
		sellVolume = 10;
		try
		{
			q = new Quote(username, product, buyPrice, buyVolume, sellPrice, sellVolume);
		}
		catch (InvalidVolumeException e)
		{
			fail("setup failed");
		}
	}

	@Test
	public void getBuySide()
	{
		QuoteSide buySide = q.getQuoteSide(BookSide.BUY.toString());
		assertEquals("Buy side of Quoute is not a buy SideQuote", buySide.getSide(), BookSide.BUY.toString());
	}
	
	@Test
	public void getSellSide()
	{
		QuoteSide sellSide = q.getQuoteSide(BookSide.SELL.toString());
		assertEquals("Sell side of Quoute is not a sell SideQuote", sellSide.getSide(), BookSide.SELL.toString());
	}
}
