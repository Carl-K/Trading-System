package book;

import java.util.ArrayList;
import java.util.HashMap;

import customExceptions.InvalidEnumException;
import customExceptions.InvalidVolumeException;
import enums.BookSide;
import price.Price;
import messages.FillMessage;
import tradable.Tradable;

public class TradeProcessorPriceTimeImpl implements TradeProcessor {
	
	private HashMap<String, FillMessage> fillMessages = new HashMap<String, FillMessage>();
	private ProductBookSide book;
	
	public TradeProcessorPriceTimeImpl( ProductBookSide bookIn )
	{
		book = bookIn;
	}
	
	private String makeFillKey(FillMessage fm)
	{
		return fm.getUser() + fm.getId() + fm.getPrice().toString();
	}
	
	private boolean isNewFill(FillMessage fm)
	{
		String key = makeFillKey(fm);
		if ( !fillMessages.containsKey(key) )
		{
			return true;
		}
		
		FillMessage oldFill = fillMessages.get(key);
		if ( !oldFill.getSide().equals( fm.getSide() ) )
		{
			return true;
		}
		
		if ( !oldFill.getId().equals( fm.getId() ) )
		{
			return true;
		}
		
		return false;
	}
	
	private void addFillMessage(FillMessage fm)
	{
		if ( isNewFill( fm ) )
		{
			String key = makeFillKey( fm );
			fillMessages.put( key, fm );
		}
		else
		{
			String key = makeFillKey( fm );
			fillMessages.get(key).setVolume( fillMessages.get(key).getVolume() + fm.getVolume() );
			fillMessages.get(key).setDetails( fm.getDetails() );
		}
	}
	
	//-------------------------------------------------------------------------------------------------

	public HashMap<String, FillMessage> doTrade(Tradable trd) throws InvalidVolumeException, InvalidEnumException 
	{
		fillMessages = new HashMap<String, FillMessage>();
		
		ArrayList<Tradable> tradedOut = new ArrayList<Tradable>();
		
		ArrayList<Tradable> entriesAtPrice  = book.getEntriesAtTopOfBook();
		
		for ( Tradable t : entriesAtPrice )
		{
			//System.out.println( "START: " + trd.toString() );
			//System.out.println( "T: " + t.toString() );
			if ( trd.getRemainingVolume() == 0 )
			{
				//System.out.println( "END: " + trd.toString() );
				for ( Tradable t2 : tradedOut )
				{
					entriesAtPrice.remove( t2 );
				}
				if ( entriesAtPrice.isEmpty() )
				{
					book.clearIfEmpty( book.topOfBookPrice() );
				}
				return fillMessages;
			}
			
			if ( trd.getRemainingVolume() >= t.getRemainingVolume() )
			{
				tradedOut.add( t );
				
				Price tPrice;
				if ( t.getPrice().isMarket() )
				{
					tPrice = trd.getPrice();
				}
				else
				{
					tPrice = t.getPrice();
				}
				
				FillMessage tFM = new FillMessage( t.getUser(), t.getProduct(), tPrice, t.getRemainingVolume(), "leaving 0", BookSide.valueOf( t.getSide() ), t.getId() );
				addFillMessage( tFM );
				
				FillMessage trdFM = new FillMessage( trd.getUser(), trd.getProduct(), tPrice, t.getRemainingVolume(), "leaving " + ( trd.getRemainingVolume() - t.getRemainingVolume() ), BookSide.valueOf( trd.getSide() ), trd.getId() );
				addFillMessage( trdFM );
				
				trd.setRemainingVolume( trd.getRemainingVolume() - t.getRemainingVolume() );
				t.setRemainingVolume( 0 );
				
				book.addOldEntry(t);
				//System.out.println( ">+ " + trd.toString() );
			}
			else
			{
				int remainder = t.getRemainingVolume() - trd.getRemainingVolume();
			
				Price tPrice;
				if ( t.getPrice().isMarket() )
				{
					tPrice = trd.getPrice();
				}
				else
				{
					tPrice = t.getPrice();
				}
			
				FillMessage tFM = new FillMessage( t.getUser(), t.getProduct(), tPrice, trd.getRemainingVolume(), "leaving " + remainder, BookSide.valueOf( t.getSide() ), t.getId() );
				addFillMessage( tFM );
			
				FillMessage trdFM = new FillMessage( trd.getUser(), trd.getProduct(), tPrice, trd.getRemainingVolume(), "leaving 0", BookSide.valueOf( trd.getSide() ), trd.getId() );
				addFillMessage( trdFM );
			
				trd.setRemainingVolume( 0 );
				//System.out.println( remainder );
				t.setRemainingVolume( remainder );
			
				book.addOldEntry(trd);
			
				for ( Tradable t2 : tradedOut )
				{
					entriesAtPrice.remove( t2 );
				}
				if ( entriesAtPrice.isEmpty() )
				{
					book.clearIfEmpty( book.topOfBookPrice() );
				}
				//System.out.println( "END1: " + trd.toString() );
				return fillMessages;
			}
		}
		
		for ( Tradable t2 : tradedOut )
		{
			entriesAtPrice.remove( t2 );
		}
		if ( entriesAtPrice.isEmpty() )
		{
			book.clearIfEmpty( book.topOfBookPrice() );
		}
		//System.out.println( "END2: " + trd.toString() );
		return fillMessages;
	}

}
