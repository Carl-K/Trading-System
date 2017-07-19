package PriceTest;

import price.MarketPrice;
import price.Price;
import price.PriceFactory;

import org.junit.Test;
import org.junit.Before;

import static org.junit.Assert.fail;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import customExceptions.InvalidPriceOperation;

public class MarketPrice_UnitTest {
	
	MarketPrice p;
	
	@Before
	public void before()
	{
		p = null;
	}

	@Test
	public void marketPricePlusAnythingThrowsException()
	{
		p = (MarketPrice)PriceFactory.makeMarketPrice();
		
		Price anotherP = PriceFactory.makeLimitPrice(0);
		
		try
		{
			p.add(anotherP);
			fail("Market price plus anything did not throw exception");
		}
		catch(InvalidPriceOperation e)
		{
			
		}
	}
	
	@Test
	public void marketPriceMinusAnythingThrowsException()
	{
		p = (MarketPrice)PriceFactory.makeMarketPrice();
		
		Price anotherP = PriceFactory.makeLimitPrice(0);
		
		try
		{
			p.subtract(anotherP);
			fail("Market price minus anything did not throw exception");
		}
		catch(InvalidPriceOperation e)
		{
			
		}
	}
	
	@Test
	public void marketPriceTimesAnythingThrowsException()
	{
		p = (MarketPrice)PriceFactory.makeMarketPrice();
		
		int scalar = 0;
		
		try
		{
			p.multiply(scalar);
			fail("Market price times anything did not throw exception");
		}
		catch(InvalidPriceOperation e)
		{
			
		}
	}
	
	//------------------------
	
	@Test
	public void marketPriceComparedToAnythingShouldReturnZero()
	{
		p = (MarketPrice)PriceFactory.makeMarketPrice();
		
		Price anotherP = PriceFactory.makeLimitPrice(0);
		
		int result = p.compareTo(anotherP);
		assertEquals("Market price comparison did not return 0", 0, result);
	}
	
	@Test
	public void marketPriceGreaterThanOrEqualToAnythingShouldReturnFalse()
	{
		p = (MarketPrice)PriceFactory.makeMarketPrice();
		
		Price anotherP = PriceFactory.makeLimitPrice(0);
		
		boolean result = p.greaterOrEqual(anotherP);
		assertFalse("Market price >= anything did not return false", result);
	}
	
	@Test
	public void marketPriceGreaterThanToAnythingShouldReturnFalse()
	{
		p = (MarketPrice)PriceFactory.makeMarketPrice();
		
		Price anotherP = PriceFactory.makeLimitPrice(0);
		
		boolean result = p.greaterThan(anotherP);
		assertFalse("Market price > anything did not return false", result);
	}
	
	@Test
	public void marketPriceLessThanOrEqualToAnythingShouldReturnFalse()
	{
		p = (MarketPrice)PriceFactory.makeMarketPrice();
		
		Price anotherP = PriceFactory.makeLimitPrice(0);
		
		boolean result = p.lessOrEqual(anotherP);
		assertFalse("Market price <= anything did not return false", result);
	}
	
	@Test
	public void marketPriceLessThanToAnythingShouldReturnFalse()
	{
		p = (MarketPrice)PriceFactory.makeMarketPrice();
		
		Price anotherP = PriceFactory.makeLimitPrice(0);
		
		boolean result = p.lessThan(anotherP);
		assertFalse("Market price < anything did not return false", result);
	}
	
	@Test
	public void marketPriceEqualToAnythingShouldReturnFalse()
	{
		p = (MarketPrice)PriceFactory.makeMarketPrice();
		
		Price anotherP = PriceFactory.makeLimitPrice(0);
		
		boolean result = p.equals(anotherP);
		assertFalse("Market price == anything did not return false", result);
	}
	
	@Test
	public void marketPriceIsMarketPriceShouldReturnTrue()
	{
		p = (MarketPrice)PriceFactory.makeMarketPrice();
		
		assertTrue("Market price is not market price", p.isMarket());
	}
	
	@Test
	public void marketPriceIsNegativeShouldReturnFalse()
	{
		p = (MarketPrice)PriceFactory.makeMarketPrice();
		
		assertFalse("Market price is not not negative", p.isNegative());
	}
	
	@Test
	public void marketPriceIsStringFormattedCorrectly()
	{
		String format = "MKT";
		p = (MarketPrice)PriceFactory.makeMarketPrice();
		String wrongFormatErrorString = String.format("Object string format ( %s ) is not correctly formatted to: %s", p.toString(), format);
		
		assertEquals(wrongFormatErrorString, format, p.toString());
	}
}
