package price;

import customExceptions.InvalidPriceOperation;

//I'm doing the inheritance method discussed in class to build a market price class
public class MarketPrice extends Price {
	
	MarketPrice() //c'tor w/ Market price
	{
		
	}
	
	//------------------------------------------------------
	//overrided methods to avoid unneccessary "if" statements
	
	public Price add( Price p ) throws InvalidPriceOperation
	{
		throw new InvalidPriceOperation("Invalid price");
	}
	
	public Price subtract( Price p ) throws InvalidPriceOperation
	{
		throw new InvalidPriceOperation("Invalid price");
	}
	
	public Price multiply( int p ) throws InvalidPriceOperation
	{
		throw new InvalidPriceOperation("Invalid price");
	}
	
	//-------------------------------------------------------
	
	public int compareTo( Price p ) //throws InvalidPriceOperation //because I can't return false
	{
		//throw new InvalidPriceOperation( "Invalid price" );
		return 0;
	}
	
	public boolean greaterOrEqual( Price p )
	{
		return false;
	}
	
	public boolean greaterThan( Price p )
	{
		return false;
	}
	
	public boolean lessOrEqual( Price p )
	{
		return false;
	}
	
	public boolean lessThan( Price p )
	{
		return false;
	}
	
	public boolean equals( Price p )
	{
		return false;
	}
	
	public boolean isMarket()
	{
		return true;
	}
	
	public boolean isNegative()
	{
		return false;
	}
	
	public String toString()
	{
		return "MKT";
	}
	
}
