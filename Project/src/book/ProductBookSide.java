package book;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;

import customExceptions.InvalidEnumException;
import customExceptions.InvalidVolumeException;
import customExceptions.OrderNotFoundException;
import price.Price;
import publishers.MessagePublisher;
import tradable.Tradable;
import tradable.TradableDTO;
import enums.BookSide;
import messages.CancelMessage;
import messages.FillMessage;
import book.ProductBook;

public class ProductBookSide {
	
	private BookSide side;
	private HashMap< Price, ArrayList<Tradable> > bookEntries = new HashMap< Price, ArrayList<Tradable> >();
	private TradeProcessor tradeProcessor;
	private ProductBook productBook;
	
	public ProductBookSide( ProductBook productBookIn, BookSide sideIn )
	{
		setSide( sideIn );
		setProductBook( productBookIn );
		setTradeProcessor( new TradeProcessorPriceTimeImpl( this ) );
	}
	
	private void setSide( BookSide sideIn )
	{
		side = sideIn;
	}
	
	private void setProductBook( ProductBook productBookIn )
	{
		productBook = productBookIn;
	}
	
	private void setTradeProcessor( TradeProcessor tradeProcessorIn )
	{
		tradeProcessor = tradeProcessorIn;
	}
	
	//-----------------------------------------------------------------------------------------------------------------------
	
	public synchronized ArrayList<TradableDTO> getOrdersWithRemainingQty(String userName)
	{
		ArrayList< TradableDTO > temp = new ArrayList< TradableDTO >();
		//temp.clear();
		for ( ArrayList<Tradable> list : bookEntries.values() )
		{
			for ( Tradable t : list )
			{
				if ( t.getUser().equals( userName ) )
				{
					if ( !t.isQuote() )
					{
						if ( t.getRemainingVolume() > 0 )
						{
							temp.add( new TradableDTO( t.getProduct(), t.getPrice(), t.getOriginalVolume(), t.getRemainingVolume(), t.getCancelledVolume(), t.getUser(), t.getSide(), t.isQuote(), t.getId() ) );
						}
					}
				}
			}
		}
		
		return temp;
	}
	
	synchronized ArrayList<Tradable> getEntriesAtTopOfBook()
	{
		if ( bookEntries.isEmpty() )
		{
			return null;
		}
		ArrayList<Price> sorted = new ArrayList<Price>(bookEntries.keySet()); // Get prices
		Collections.sort(sorted); // Sort them
		if ( side == BookSide.BUY ) {
			Collections.reverse(sorted); // Reverse them
		}
		return bookEntries.get(sorted.get(0));
	}
	
	public synchronized String[] getBookDepth()
	{
		if ( /*bookEntries != null &&*/  bookEntries.isEmpty() )
		{
			return new String[]{"<Empty>"};
		}
		else
		{
			String[] st = new String[ bookEntries.size() ];
			
			ArrayList<Price> sorted = new ArrayList<Price>(bookEntries.keySet()); // Get prices
			Collections.sort(sorted); // Sort them
			if ( side == BookSide.BUY ) {
				Collections.reverse(sorted); // Reverse them
			}
			
			int index = 0;
			for ( Price p : sorted )
			{
				ArrayList<Tradable> tList = bookEntries.get( p );
				int count = 0;
				for ( Tradable t : tList )
				{
					count = count + t.getRemainingVolume() ;
				}
				String s = p.toString() + " x " + count ;
				st[index] = s;
				index = index + 1 ;
			}
			return st;
		}
	}
	
	synchronized ArrayList<Tradable> getEntriesAtPrice(Price price)
	{
		return ( bookEntries.containsKey( price ) ) ? bookEntries.get(price) : null ;
	}
	
	public synchronized boolean hasMarketPrice()
	{
		for ( Price p : bookEntries.keySet() )
		{
			if ( p.isMarket() )
			{
				return true;
			}
		}
		return false;
	}
	
	public synchronized boolean hasOnlyMarketPrice()
	{
		for ( Price p : bookEntries.keySet() )
		{
			if ( !p.isMarket() )
			{
				return false;
			}
		}
		return true;
	}
	
	public synchronized Price topOfBookPrice()
	{
		if ( bookEntries.isEmpty() )
		{
			return null;
		}
		
		ArrayList<Price> sorted = new ArrayList<Price>(bookEntries.keySet()); // Get prices
		//System.out.println( sorted.size() + " " + side.toString() );
		Collections.sort(sorted); // Sort them
		//System.out.println( sorted.size() + " " + side.toString() );
		if ( side.equals( BookSide.BUY ) ) {
			Collections.reverse(sorted); // Reverse them
		}
		return (sorted.get(0));
	}
	
	public synchronized int topOfBookVolume()
	{
		if ( bookEntries.isEmpty() )
		{
			return 0;
		}
		ArrayList<Price> sorted = new ArrayList<Price>(bookEntries.keySet()); // Get prices
		Collections.sort(sorted); // Sort them
		if ( side == BookSide.BUY ) {
			Collections.reverse(sorted); // Reverse them
		}
		ArrayList< Tradable > temp = bookEntries.get( sorted.get(0) );
		int count = 0;
		for ( Tradable t : temp )
		{
			count += t.getRemainingVolume();
		}
		return count;
	}
	
	public synchronized boolean isEmpty()
	{
		return bookEntries.isEmpty();
	}
	
	//-----------------------------------------------------------------------------------------------------------------------
	//ArrayList<Tradable> temp = new ArrayList<>(tList);
	public synchronized void cancelAll() throws OrderNotFoundException, InvalidVolumeException
	{
		/*
		System.out.println();
		for ( ArrayList<Tradable> tList : bookEntries.values() )
		{
			for ( Tradable t : tList )
			{
				System.out.println( t.toString() );
			}
		}
		System.out.println();
		*/
		HashMap< Price, ArrayList<Tradable> > temp1 = new HashMap<>(bookEntries);
		for ( ArrayList<Tradable> tList : temp1.values() )
		{
			ArrayList<Tradable> temp = new ArrayList<>(tList);
			for ( Tradable t : temp )
			{
				if ( t.isQuote() )
				{
					submitQuoteCancel( t.getUser() );
				}
				else
				{
					submitOrderCancel( t.getId() );
				}
			}
		}

	}
	
	public synchronized TradableDTO removeQuote(String user)
	{
		for ( ArrayList<Tradable> tList : bookEntries.values() )
		{
			for ( int i = 0; i < tList.size(); i++ )
			{
				if ( tList.get(i).getUser().equals( user ) )
				{
					if ( tList.get(i).isQuote() )
					{
						Tradable temp = tList.remove(i);
						if ( tList.isEmpty() )
						{
							bookEntries.remove( temp.getPrice() );
						}
						return new TradableDTO( temp.getProduct(), temp.getPrice(), temp.getOriginalVolume(), temp.getRemainingVolume(), temp.getCancelledVolume(), temp.getUser(), temp.getSide(), temp.isQuote(), temp.getId() );
					}
				}
			}
		}
		return null;

	}

	public synchronized void submitOrderCancel(String orderId) throws OrderNotFoundException, InvalidVolumeException
	{
		{
			for ( ArrayList<Tradable> tList : bookEntries.values() )
			{
				for ( int i = 0; i < tList.size(); i++ )
				{
					if ( tList.get(i).getId().equals(orderId) )
					{
						Tradable t = tList.remove(i);
						
						try
						{
							CancelMessage cM = new CancelMessage( t.getUser(), t.getProduct(), t.getPrice(), t.getRemainingVolume(), "Order cancelled by system", BookSide.valueOf( t.getSide() ), t.getId() );
							MessagePublisher.getInstance().publishCancel(cM);
						}
						catch ( Exception e )
						{
						
						}
					
						addOldEntry(t);
					
						if ( tList.isEmpty() )
						{
							bookEntries.remove( t.getPrice() );
						}
						return;
					}
				}
			}
			productBook.checkTooLateToCancel(orderId);
		}
	}
	
	public synchronized void submitQuoteCancel(String userName)
	{
		TradableDTO temp;
		if ( (temp = removeQuote(userName)) != null )
		{
			try
			{
				CancelMessage cM = new CancelMessage( temp.user, temp.product, temp.price, temp.remainingVolume, "Quote cancelled by system", temp.side, temp.id );

				MessagePublisher.getInstance().publishCancel(cM);
			}
			catch ( Exception e )
			{
				
			}
		}
		return;
	}
	
	public void addOldEntry(Tradable t) throws InvalidVolumeException
	{
		productBook.addOldEntry( t );
	}
	
	public synchronized void addToBook(Tradable trd)
	{
		if ( !bookEntries.containsKey( trd.getPrice() ) )
		{
			bookEntries.put( trd.getPrice(), new ArrayList<Tradable>() );
		}
		bookEntries.get( trd.getPrice() ).add( trd );
		/*
		for ( ArrayList<Tradable> tList : bookEntries.values() )
		{
			for ( Tradable t : tList )
			{
				System.out.println( t.toString() );
			}
		}
		System.out.println();
		*/
	}
	
	public HashMap<String, FillMessage> tryTrade(Tradable trd) throws InvalidVolumeException, InvalidEnumException
	{
		HashMap<String, FillMessage> allFills;
		
		if ( side.equals( BookSide.BUY ) )
		{
			allFills = trySellAgainstBuySideTrade( trd );
		}
		else
		{
			allFills = tryBuyAgainstSellSideTrade( trd );
		}
		
		for ( FillMessage fM : allFills.values() )
		{
			MessagePublisher.getInstance().publishFill(fM);
		}
		
		return allFills;
	}
	
	public synchronized HashMap<String, FillMessage> trySellAgainstBuySideTrade(Tradable trd) throws InvalidVolumeException, InvalidEnumException
	{
		HashMap<String, FillMessage> allFills = new HashMap<String, FillMessage>();
		HashMap<String, FillMessage> fillMsgs = new HashMap<String, FillMessage>();
		
		while ( (trd.getRemainingVolume() > 0 && !isEmpty() && trd.getPrice().lessOrEqual( topOfBookPrice() )) || (trd.getRemainingVolume() > 0 && !isEmpty() && trd.getPrice().isMarket()) )
		{
			HashMap<String, FillMessage> someMsgs;
			someMsgs = tradeProcessor.doTrade(trd);
			
			fillMsgs = mergeFills(fillMsgs, someMsgs);
		}
		
		allFills.putAll(fillMsgs);
		return allFills;
	}
	
	private HashMap<String, FillMessage> mergeFills(HashMap<String, FillMessage> existing, HashMap<String, FillMessage> newOnes)
	{
		if ( existing.isEmpty() )
		{
			return new HashMap<String, FillMessage>(newOnes);
		}
		
		HashMap<String, FillMessage> results = new HashMap<>(existing);
		for (String key : newOnes.keySet()) 
		{
			if (!existing.containsKey(key)) 
			{
				results.put(key, newOnes.get(key));
			} 
			else 
			{
				FillMessage fm = results.get(key);
				fm.setVolume(newOnes.get(key).getVolume());
				fm.setDetails(newOnes.get(key).getDetails());
			}
		}
		return results;
	}
	
	public synchronized HashMap<String, FillMessage> tryBuyAgainstSellSideTrade(Tradable trd) throws InvalidVolumeException, InvalidEnumException
	{
		HashMap<String, FillMessage> allFills = new HashMap<String, FillMessage>();
		HashMap<String, FillMessage> fillMsgs = new HashMap<String, FillMessage>();
		
		while ( (trd.getRemainingVolume() > 0 && !isEmpty() && trd.getPrice().greaterOrEqual( topOfBookPrice() )) || (trd.getRemainingVolume() > 0 && !isEmpty() && trd.getPrice().isMarket()) )
		{
			HashMap<String, FillMessage> someMsgs;
			someMsgs = tradeProcessor.doTrade(trd);
			
			fillMsgs = mergeFills(fillMsgs, someMsgs);
		}
		
		allFills.putAll( fillMsgs );
		return allFills;
	}
	
	public synchronized void clearIfEmpty(Price p)
	{
		if ( bookEntries.get( p ).isEmpty() )
		{
			bookEntries.remove( p );
		}
	}
	
	public synchronized void removeTradable(Tradable t)
	{
		ArrayList<Tradable> temp = bookEntries.get(t.getPrice());
		if ( temp == null )
		{
			return;
		}
		
		boolean result = temp.remove( t );
		if ( !result )
		{
			return;
		}
		
		if ( temp.isEmpty( ) )
		{
			clearIfEmpty( t.getPrice() );
		}
	}		
	
}
