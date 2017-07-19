package price;
import java.util.HashMap;

public class PriceFactory {
	
	private static HashMap<Long, Price> map;
	private static MarketPrice mktP;
	
	private static long formatToLong( String s )
	{
		return ( (long)Math.round( ( Double.valueOf( s.replaceAll( "[$,]", "" ) ) ) * 100.0 ) );
	}
	
	public static Price makeLimitPrice( String value )
	{
		return makeLimitPrice( formatToLong( value ) );
	}
	
	public static Price makeLimitPrice( long value )
	{
		if ( map == null )
		{
			map = new HashMap<Long, Price>();
		}
		if ( map.containsKey( value ) )
		{
			return map.get( value );
		}
		Price p = new Price( value );
		map.put( value, p );
		return p;
	}
	
	public static Price makeMarketPrice()
	{
		if ( mktP == null )
		{
			mktP = new MarketPrice();
		}
		return mktP;
	}
}
