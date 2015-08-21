package price;

import customExceptions.InvalidPriceOperation;

public class Price implements Comparable<Price> {
	
	private long value;
	
	Price( long valueIn ) //c'tor
	{
		value = valueIn;
	}
	
	Price() //default c'tor needed for MarketPrice
	{
		
	}
	
	//------------------------------------------------------
	
	public long getValue()
	{
		return value;
	}
	
	public Price add( Price p ) throws InvalidPriceOperation
	{
		if ( /*this.isMarket() || */p.isMarket() || p == null )
		{
			throw new InvalidPriceOperation("Invalid price");
		}
		//return new Price( p.value + this.value );
		return PriceFactory.makeLimitPrice( p.getValue() + getValue() );
	}
	
	public Price subtract( Price p ) throws InvalidPriceOperation
	{
		if ( /*this.isMarket() || */p.isMarket() || p == null )
		{
			throw new InvalidPriceOperation("Invalid price");
		}
		//return new Price( p.value - this.value );
		return PriceFactory.makeLimitPrice( getValue() - p.getValue() );
	}
	
	public Price multiply( int p ) throws InvalidPriceOperation //<-- used for MarketPrice subclass
	{
		/*
		if ( this.isMarket() )
		{
			throw new InvalidPriceOperation("Invalid price");
		}
		*/
		//return new Price( this.value * p );
		return PriceFactory.makeLimitPrice( getValue() * p );
	}
	
	//-------------------------------------------------------
	
	/*
	public int compareTo( Price p ) throws InvalidPriceOperation //because I can't return false
	{
		if ( p.isMarket() )
		{
			throw new InvalidPriceOperation( "Invalid price" );
		}
		if ( getValue() < p.getValue() )
		{
			return -1;
		}
		else if ( getValue() > p.getValue() )
		{
			return 1;
		}
		else
		{
			return 0;
		}
	}
	*/
	public int compareTo( Price p )
	{
		if ( getValue() < p.getValue() )
		{
			return -1;
		}
		else if ( getValue() > p.getValue() )
		{
			return 1;
		}
		else
		{
			return 0;
		}
	}
	
	public boolean greaterOrEqual( Price p )
	{
		if ( /*this.isMarket() || */p.isMarket() )
		{
			return false;
		}
		return ( value >= p.value );
	}
	
	public boolean greaterThan( Price p )
	{
		if ( /*this.isMarket() || */p.isMarket() )
		{
			return false;
		}
		return ( value > p.value );
	}
	
	public boolean lessOrEqual( Price p )
	{
		if ( /*this.isMarket() || */p.isMarket() )
		{
			return false;
		}
		return ( value <= p.value );
	}
	
	public boolean lessThan( Price p )
	{
		if ( /*this.isMarket() || */p.isMarket() )
		{
			return false;
		}
		return ( value < p.value );
	}
	
	public boolean equals( Price p )
	{
		
		if ( /*this.isMarket() || */p.isMarket() )
		{
			return false;
		}
		return ( p.value ==value );
	}
	
	public boolean isMarket()
	{
		return false;
	}
	
	public boolean isNegative()
	{
		return ( /*!this.isMarket() && */value < 0 );
	}
	
	public String toString()
	{
		String s = String.valueOf( value );
		boolean b = s.startsWith("-");
		if ( b )
		{
			s = s.substring(1);
		}
		
		for ( int i = s.length() - 5; i > 0; i -= 3 )
		{
			s = s.substring(0, i) + "," + s.substring(i, s.length());
		}
		
		s = 	"$" + //add dollar sign
				( b ? "-" : "" ) + //check if requires '-'
				( s.length() <= 2 ? "0" : s.substring( 0, s.length() - 2) ) +  //check if less than $1.00
				"." + //add decimal place
				( s.length() <= 1 ? "0" + s  : s.substring(s.length() - 2, s.length() ) ); //check if less than 0.10 cents
		return s;
		
	}
/*
	public int compare(Object arg0, Object arg1) {
		Price p1 = (Price)arg0;
		Price p2 = (Price)arg1;
		try
		{
			return p1.compareTo(p2);
		}
		catch ( InvalidPriceOperation e )
		{
			
		}
		return 0;
	}
*/
	
}
