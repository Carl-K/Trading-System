package PriceTest;

import price.Price;
import price.MarketPrice;
import price.PriceFactory;

import org.junit.Test;

import org.junit.Before;

import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertEquals;

public class PriceFactory_UnitTest {
	
	Price p;
	
	@Before
	public void before()
	{
		p = null;
	}
	
	@Test
	public void priceFactoryMakesLimitPriceFromLong()
	{
		long amountLong = 1250;
		
		String limitPriceNullErrorString;
		String limitPriceWrongValueErrorString;
		
		p = PriceFactory.makeLimitPrice(amountLong);
		
		limitPriceNullErrorString = String.format("Price object of long %d is nil", amountLong);
		assertNotEquals(limitPriceNullErrorString, null, p);
		
		limitPriceWrongValueErrorString = String.format("Price object of long %d method getValue() -> long is not %d", amountLong, amountLong);
		assertEquals(limitPriceWrongValueErrorString, amountLong, p.getValue());
	}
	
	@Test
	public void priceFactoryMakesLimitPriceFromString()
	{
		long amountLong = 1500;
		String amountString = "$15.00";
		
		String limitPriceNullErrorString;
		String limitPriceWrongValueErrorString;
		
		p = PriceFactory.makeLimitPrice(amountString);
		
		limitPriceNullErrorString = String.format("Price object of string %s is nil", amountString);
		assertNotEquals(limitPriceNullErrorString, null, p);
		
		limitPriceWrongValueErrorString = String.format("Price object of string %s method getValue() -> long is not %d", amountString, amountLong);
		assertEquals(limitPriceWrongValueErrorString, amountLong, p.getValue());
	}
	
	@Test(expected = NumberFormatException.class)
	public void priceFactoryCannotParseEmptyStringToLong()
	{
		String amountString;
		
		amountString = "";
		p = PriceFactory.makeLimitPrice(amountString);
	}
	
	@Test(expected = NumberFormatException.class)
	public void priceFactoryCannotParseInvalidStringToLong()
	{
		String amountString;
		
		amountString = "$OneHundredDollars.AndFiftyCents";
		p = PriceFactory.makeLimitPrice(amountString);
	}
	
	@Test
	public void priceFactoryManagesOnePricePerValue()
	{
		long amountLong = 1750;
		p = PriceFactory.makeLimitPrice(amountLong);
		
		Price anotherP = PriceFactory.makeLimitPrice(amountLong);
		
		String priceFactoryDoesNotManageOnePricePerValueErrorString = String.format("Price object of %d is not the same as another of %d", p.getValue(), anotherP.getValue());
		assertEquals(priceFactoryDoesNotManageOnePricePerValueErrorString, p, anotherP);
	}
	
	@Test
	public void priceFactoryMakesMarketPrice()
	{
		p = PriceFactory.makeMarketPrice();
		
		String marketPriceNullErrorString = "Market price is null";
		assertNotEquals(marketPriceNullErrorString, null, p);
		
		String marketPriceIsNotMarketPriceErrorString = "Market Price is not market price";
		assertEquals(marketPriceIsNotMarketPriceErrorString, true, p.isMarket());
		
		String priceCannotCastToMarketPriceErrorString = "Price cannot cast to market price";
		assertEquals(priceCannotCastToMarketPriceErrorString, (MarketPrice)p, p);
	}
	
	@Test
	public void priceFactoryManagesOneMarketPrice()
	{
		p = PriceFactory.makeMarketPrice();
		
		Price anotherP = PriceFactory.makeMarketPrice();
		
		String priceFactoryDoesNotManageOnePricePerValueErrorString = String.format("Market price p is not the same as market price anotherP");
		assertEquals(priceFactoryDoesNotManageOnePricePerValueErrorString, p, anotherP);
	}
}
