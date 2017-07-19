package PriceTest;

import price.Price;
import price.PriceFactory;

import org.junit.Test;
import org.junit.Before;

import customExceptions.InvalidPriceOperation;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

public class Price_UnitTest {
	
	Price p;
	
	@Before
	public void before()
	{
		p = null;
	}

	@Test
	public void priceSetToCorrectValue()
	{
		long amount = 500;
		
		p = PriceFactory.makeLimitPrice(amount);
		
		String priceIsNotCorrectValueErrorString = String.format("Price object created with value %d is not set with value %d", amount, amount);
		assertEquals(priceIsNotCorrectValueErrorString, amount, p.getValue());
	}
	
	//------------
	
	@Test
	public void invalidAdditionOfNullPrice()
	{
		p = PriceFactory.makeLimitPrice(500);
		Price anotherP = null;
		
		try
		{
			p.add(anotherP);
			fail("Limit price plus null did not throw InvalidPriceOperation exception");
		}
		catch (InvalidPriceOperation e)
		{
			
		}
	}
	
	@Test
	public void invalidAdditionOfMarketPrice()
	{
		p = PriceFactory.makeLimitPrice(500);
		Price anotherP = PriceFactory.makeMarketPrice();
		
		try
		{
			p.add(anotherP);
			fail("Limit price plus market price did not throw InvalidPriceOperation exception");
		}
		catch (InvalidPriceOperation e)
		{
			
		}
	}
	
	@Test
	public void pricePlusAnotherPriceEqualsCorrectValue()
	{
		long amount1 = 1000;
		long amount2 = 1550;
		long equals = amount1 + amount2;
		
		p = PriceFactory.makeLimitPrice(amount1);
		
		Price anotherP = PriceFactory.makeLimitPrice(amount2);
		
		String pricePlusAnotherPriceIsNotCorrectValueErrorString = String.format("Price value of %d plus price value of %d is not price %d", amount1, amount2, equals);
		
		try
		{
			Price newPrice = p.add(anotherP);
			assertEquals(pricePlusAnotherPriceIsNotCorrectValueErrorString, equals, newPrice.getValue());
		}
		catch(Exception e)
		{
			fail(e.getMessage());
		}
	}
	
	//----------------------
	
	@Test
	public void invalidSubtractionOfNullPrice()
	{
		p = PriceFactory.makeLimitPrice(500);
		Price anotherP = null;
		
		try
		{
			p.subtract(anotherP);
			fail("Limit price minus null did not throw InvalidPriceOperation exception");
		}
		catch (InvalidPriceOperation e)
		{
			
		}
	}
	
	@Test
	public void invalidSubtractionOfMarketPrice()
	{
		p = PriceFactory.makeLimitPrice(500);
		Price anotherP = PriceFactory.makeMarketPrice();
		
		try
		{
			p.subtract(anotherP);
			fail("Limit price minus market price did not throw InvalidPriceOperation exception");
		}
		catch (InvalidPriceOperation e)
		{
			
		}
	}
	
	@Test
	public void priceMinusAnotherPriceEqualsCorrectValue()
	{
		long amount1 = 1550;
		long amount2 = 1000;
		long equals = amount1 - amount2;
		
		p = PriceFactory.makeLimitPrice(amount1);
		
		Price anotherP = PriceFactory.makeLimitPrice(amount2);
		
		String priceMinusAnotherPriceIsNotCorrectValueErrorString = String.format("Price value of %d minus price value of %d is not price %d", amount1, amount2, equals);
		
		try
		{
			Price newPrice = p.subtract(anotherP);
			assertEquals(priceMinusAnotherPriceIsNotCorrectValueErrorString, equals, newPrice.getValue());
		}
		catch(Exception e)
		{
			fail(e.getMessage());
		}
	}
	
	//--------------------------------
	
	@Test
	public void priceTimesAScalarEqualsCorrectValue()
	{
		long amount = 1000;
		int scalar = 5;
		long equals = amount * scalar;
		
		p = PriceFactory.makeLimitPrice(amount);

		String priceTimesAScalarIsNotCorrectValueErrorString = String.format("Price value of %d times scalar %d is not price %d", amount, scalar, equals);
		
		try
		{
			Price newPrice = p.multiply(scalar);
			assertEquals(priceTimesAScalarIsNotCorrectValueErrorString, equals, newPrice.getValue());
		}
		catch(Exception e)
		{
			fail(e.getMessage());
		}
	}
	
	//--------------------------------
	
	@Test
	public void priceComparisonShouldBeNegativeOneForLessThan()
	{
		int result;
		String comparisonGivesWrongValueErrorString;
		
		long amount1 = 999;
		long amount2 = 1000;
		
		p = PriceFactory.makeLimitPrice(amount1);
		Price anotherP = PriceFactory.makeLimitPrice(amount2);
		
		result = p.compareTo(anotherP);
		comparisonGivesWrongValueErrorString = String.format("%d compared to %d should be less than", amount1, amount2);
		assertEquals(comparisonGivesWrongValueErrorString, -1, result);
	}
	
	@Test
	public void priceComparisonShouldBePositiveOneForGreaterThan()
	{
		int result;
		String comparisonGivesWrongValueErrorString;
		
		long amount1 = 501;
		long amount2 = 500;
		
		p = PriceFactory.makeLimitPrice(amount1);
		Price anotherP = PriceFactory.makeLimitPrice(amount2);
		
		result = p.compareTo(anotherP);
		comparisonGivesWrongValueErrorString = String.format("%d compared to %d should be greater than", amount1, amount2);
		assertEquals(comparisonGivesWrongValueErrorString, 1, result);
	}
	
	@Test
	public void priceComparisonShouldBeZeroForEqualsTo()
	{
		int result;
		String comparisonGivesWrongValueErrorString;
		
		long amount1 = 500;
		long amount2 = 500;
		
		p = PriceFactory.makeLimitPrice(amount1);
		Price anotherP = PriceFactory.makeLimitPrice(amount2);
		
		result = p.compareTo(anotherP);
		comparisonGivesWrongValueErrorString = String.format("%d compared to %d should be equals to", amount1, amount2);
		assertEquals(comparisonGivesWrongValueErrorString, 0, result);
	}
	
	//--------------------
	
	@Test
	public void priceShouldBeGreaterThanForGreaterThanOrEqualToAnotherPrice()
	{
		boolean result;
		String priceIsNotGreaterThanErrorString;
		
		long amount1 = 501;
		long amount2 = 500;
		
		p = PriceFactory.makeLimitPrice(amount1);
		Price anotherP = PriceFactory.makeLimitPrice(amount2);
		
		result = p.greaterOrEqual(anotherP);
		priceIsNotGreaterThanErrorString = String.format("%d >= %d should be true", amount1, amount2);
		assertTrue(priceIsNotGreaterThanErrorString, result);
	}
	
	@Test
	public void priceShouldBeEqualToForGreaterThanOrEqualToAnotherPrice()
	{
		boolean result;
		String priceIsNotEqualToErrorString;
		
		long amount1 = 500;
		long amount2 = 500;
		
		p = PriceFactory.makeLimitPrice(amount1);
		Price anotherP = PriceFactory.makeLimitPrice(amount2);
		
		result = p.greaterOrEqual(anotherP);
		priceIsNotEqualToErrorString = String.format("%d >= %d should be true", amount1, amount2);
		assertTrue(priceIsNotEqualToErrorString, result);
	}
	
	@Test
	public void priceShouldBeLessThanForGreaterThanOrEqualToAnotherPrice()
	{
		boolean result;
		String priceIsNotLessThanErrorString;
		
		long amount1 = 499;
		long amount2 = 500;
		
		p = PriceFactory.makeLimitPrice(amount1);
		Price anotherP = PriceFactory.makeLimitPrice(amount2);
		
		result = p.greaterOrEqual(anotherP);
		priceIsNotLessThanErrorString = String.format("%d >= %d should be false", amount1, amount2);
		assertFalse(priceIsNotLessThanErrorString, result);
	}
	
	//-----------------------
	
	@Test
	public void priceShouldBeGreaterThanForGreaterThanAnotherPrice()
	{
		boolean result;
		String priceIsNotGreaterThanErrorString;
		
		long amount1 = 501;
		long amount2 = 500;
		
		p = PriceFactory.makeLimitPrice(amount1);
		Price anotherP = PriceFactory.makeLimitPrice(amount2);
		
		result = p.greaterThan(anotherP);
		priceIsNotGreaterThanErrorString = String.format("%d > %d should be true", amount1, amount2);
		assertTrue(priceIsNotGreaterThanErrorString, result);
	}
	
	@Test
	public void priceShouldBeNotGreaterThanForGreaterThanToAnotherPrice()
	{
		boolean result;
		String priceIsNotEqualToErrorString;
		
		long amount1 = 500;
		long amount2 = 500;
		
		p = PriceFactory.makeLimitPrice(amount1);
		Price anotherP = PriceFactory.makeLimitPrice(amount2);
		
		result = p.greaterThan(anotherP);
		priceIsNotEqualToErrorString = String.format("%d > %d should be false", amount1, amount2);
		assertFalse(priceIsNotEqualToErrorString, result);
	}
	
	//---------------------------
	
	@Test
	public void priceShouldBeLessThanForLessThanOrEqualToAnotherPrice()
	{
		boolean result;
		String priceIsNotLessThanErrorString;
		
		long amount1 = 499;
		long amount2 = 500;
		
		p = PriceFactory.makeLimitPrice(amount1);
		Price anotherP = PriceFactory.makeLimitPrice(amount2);
		
		result = p.lessOrEqual(anotherP);
		priceIsNotLessThanErrorString = String.format("%d <= %d should be true", amount1, amount2);
		assertTrue(priceIsNotLessThanErrorString, result);
	}
	
	@Test
	public void priceShouldBeEqualToForLessThanOrEqualToAnotherPrice()
	{
		boolean result;
		String priceIsNotEqualToErrorString;
		
		long amount1 = 500;
		long amount2 = 500;
		
		p = PriceFactory.makeLimitPrice(amount1);
		Price anotherP = PriceFactory.makeLimitPrice(amount2);
		
		result = p.lessOrEqual(anotherP);
		priceIsNotEqualToErrorString = String.format("%d <= %d should be true", amount1, amount2);
		assertTrue(priceIsNotEqualToErrorString, result);
	}
	
	@Test
	public void priceShouldBeGreaterThanForLessThanOrEqualToAnotherPrice()
	{
		boolean result;
		String priceIsNotGreaterThanErrorString;
		
		long amount1 = 501;
		long amount2 = 500;
		
		p = PriceFactory.makeLimitPrice(amount1);
		Price anotherP = PriceFactory.makeLimitPrice(amount2);
		
		result = p.lessOrEqual(anotherP);
		priceIsNotGreaterThanErrorString = String.format("%d <= %d should be false", amount1, amount2);
		assertFalse(priceIsNotGreaterThanErrorString, result);
	}
	
	//-----------------------
	
	@Test
	public void priceShouldBeLessThanForLessThanAnotherPrice()
	{
		boolean result;
		String priceIsNotLessThanErrorString;
		
		long amount1 = 499;
		long amount2 = 500;
		
		p = PriceFactory.makeLimitPrice(amount1);
		Price anotherP = PriceFactory.makeLimitPrice(amount2);
		
		result = p.lessThan(anotherP);
		priceIsNotLessThanErrorString = String.format("%d < %d should be true", amount1, amount2);
		assertTrue(priceIsNotLessThanErrorString, result);
	}
	
	@Test
	public void priceShouldBeNotLessThanForLessThanToAnotherPrice()
	{
		boolean result;
		String priceIsLessThanErrorString;
		
		long amount1 = 501;
		long amount2 = 500;
		
		p = PriceFactory.makeLimitPrice(amount1);
		Price anotherP = PriceFactory.makeLimitPrice(amount2);
		
		result = p.lessThan(anotherP);
		priceIsLessThanErrorString = String.format("%d < %d should be false", amount1, amount2);
		assertFalse(priceIsLessThanErrorString, result);
	}
	
	//---------------------------------
	
	@Test
	public void priceShouldBeEqualToForEqualsAnotherPrice()
	{
		boolean result;
		String priceIsNotEqualToErrorString;
		
		long amount1 = 500;
		long amount2 = 500;
		
		p = PriceFactory.makeLimitPrice(amount1);
		Price anotherP = PriceFactory.makeLimitPrice(amount2);
		
		result = p.equals(anotherP);
		priceIsNotEqualToErrorString = String.format("%d == %d should be true", amount1, amount2);
		assertTrue(priceIsNotEqualToErrorString, result);
	}
	
	@Test
	public void priceShouldBeNotEqualToForEqualsAnotherPrice()
	{
		boolean result;
		String priceIsNotEqualToErrorString;
		
		long amount1 = 501;
		long amount2 = 500;
		
		p = PriceFactory.makeLimitPrice(amount1);
		Price anotherP = PriceFactory.makeLimitPrice(amount2);
		
		result = p.equals(anotherP);
		priceIsNotEqualToErrorString = String.format("%d == %d should be false", amount1, amount2);
		assertFalse(priceIsNotEqualToErrorString, result);
	}
	
	@Test
	public void priceShouldNotBeMarket()
	{
		p = PriceFactory.makeLimitPrice(500);
		String priceIsMarketErrorString = "Price is incorrectly market price";
		assertFalse(priceIsMarketErrorString, p.isMarket());
	}
	
	@Test
	public void priceShouldBeNegative()
	{
		p = PriceFactory.makeLimitPrice(-1);
		String priceIsNotNegativeErrorString = String.format("Price %d is incorrectly not negative", p.getValue());
		assertTrue(priceIsNotNegativeErrorString, p.isNegative());
	}
	
	public void priceShouldBeNotNegative()
	{
		String priceIsNotNegativeErrorString;
		
		p = PriceFactory.makeLimitPrice(0);
		priceIsNotNegativeErrorString = String.format("Price %d is incorrectly negative", p.getValue());
		assertFalse(priceIsNotNegativeErrorString, p.isNegative());
		
		p = PriceFactory.makeLimitPrice(1);
		priceIsNotNegativeErrorString = String.format("Price %d is incorrectly negative", p.getValue());
		assertFalse(priceIsNotNegativeErrorString, p.isNegative());
	}
	
	@Test
	public void priceFormatsCorrectly()
	{
		long amount;
		String priceNotFormattedCorrectlyErrorString;
		
		amount = -1;
		p = PriceFactory.makeLimitPrice(amount);
		priceNotFormattedCorrectlyErrorString = String.format("Price %d formatted incorrectly", amount);
		assertEquals(priceNotFormattedCorrectlyErrorString, p.toString(), "$-0.01");
		
		amount = 0;
		p = PriceFactory.makeLimitPrice(amount);
		priceNotFormattedCorrectlyErrorString = String.format("Price %d formatted incorrectly", amount);
		assertEquals(priceNotFormattedCorrectlyErrorString, p.toString(), "$0.00");
		
		amount = 1;
		p = PriceFactory.makeLimitPrice(amount);
		priceNotFormattedCorrectlyErrorString = String.format("Price %d formatted incorrectly", amount);
		assertEquals(priceNotFormattedCorrectlyErrorString, p.toString(), "$0.01");
		
		amount = 100000;
		p = PriceFactory.makeLimitPrice(amount);
		priceNotFormattedCorrectlyErrorString = String.format("Price %d formatted incorrectly", amount);
		assertEquals(priceNotFormattedCorrectlyErrorString, p.toString(), "$1,000.00");
	}
}
