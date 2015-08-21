package book;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;

import customExceptions.DataValidationException;
import customExceptions.InvalidEnumException;
import customExceptions.InvalidVolumeException;
import customExceptions.OrderNotFoundException;
import messages.CancelMessage;
import messages.FillMessage;
import enums.BookSide;
import enums.MarketState;
import tradable.Order;
import tradable.Quote;
import tradable.Tradable;
import tradable.TradableDTO;
import price.Price;
import price.PriceFactory;
import publishers.CurrentMarketPublisher;
import publishers.LastSalePublisher;
import publishers.MarketDataDTO;
import publishers.MessagePublisher;

public class ProductBook {
	
	private String symbol;
	private ProductBookSide buySide;
	private ProductBookSide sellSide;
	private String latestValue = "";
	private HashSet<String> userQuotes = new HashSet<>();
	private HashMap<Price, ArrayList<Tradable>> oldEntries = new HashMap< Price, ArrayList<Tradable>>();

	public ProductBook( String symbolIn )
	{
		symbol = symbolIn;
		buySide = new ProductBookSide( this, BookSide.BUY );
		sellSide = new ProductBookSide( this, BookSide.SELL );
	}
	
	public synchronized ArrayList<TradableDTO> getOrdersWithRemainingQty(String userName)
	{
		/*
		ArrayList< TradableDTO > temp = new ArrayList< TradableDTO >();
		ArrayList< TradableDTO > buyTemp = buySide.getOrdersWithRemainingQty( userName );
		for ( int i = 0; i < buyTemp.size(); i++ )
		{
			temp.add( buyTemp.get(i) );
		}
		ArrayList< TradableDTO > sellTemp = sellSide.getOrdersWithRemainingQty( userName );
		for ( int i = 0; i < sellTemp.size(); i++ )
		{
			temp.add( sellTemp.get(i) );
		}
		return temp;
		*/
		ArrayList< TradableDTO > temp = new ArrayList< TradableDTO >();
		temp.addAll( buySide.getOrdersWithRemainingQty( userName ) );
		temp.addAll( sellSide.getOrdersWithRemainingQty( userName ) );
		return temp;
	}
	
	public synchronized void checkTooLateToCancel(String orderId) throws OrderNotFoundException
	{
		for ( ArrayList<Tradable> tList : oldEntries.values() )
		{
			for ( Tradable t : tList )
			{
				if ( t.getId().equals( orderId ) )
				{
					try
					{
						CancelMessage cM = new CancelMessage( t.getUser(), t.getProduct(), t.getPrice(), t.getCancelledVolume(),(t.isQuote() ? "Quote " : "Order ") + "too Late to Cancel", BookSide.valueOf( t.getSide() ), t.getId() );
						MessagePublisher.getInstance().publishCancel(cM);
						return;
					}
					catch ( InvalidVolumeException | InvalidEnumException e )
					{
						
					}
				}
			}
		}
		throw new OrderNotFoundException( "Requested order could not be found" );
	}
	
	public synchronized String[ ][ ] getBookDepth()
	{
		String[][] bd = new String[2][];
		bd[0] = buySide.getBookDepth();
		bd[1] = sellSide.getBookDepth();
		return bd;
	}
	
	public synchronized MarketDataDTO getMarketData()
	{
		Price bestBuyPrice = buySide.topOfBookPrice();
		if ( bestBuyPrice == null )
		{
			bestBuyPrice = PriceFactory.makeLimitPrice( "0.00" );
		}
		Price bestSellPrice = sellSide.topOfBookPrice();
		if ( bestSellPrice == null )
		{
			bestSellPrice = PriceFactory.makeLimitPrice( "0.00" );
		}
		
		int bestBuyVolume = buySide.topOfBookVolume();
		int bestSellVolume = sellSide.topOfBookVolume();
		
		return new MarketDataDTO( symbol, bestBuyPrice, bestBuyVolume, bestSellPrice, bestSellVolume );
	}
	
	//-------------------------------------------------------------------------------------------
	
	public synchronized void addOldEntry(Tradable t) throws InvalidVolumeException
	{
		if ( !oldEntries.containsKey( t.getPrice() ) )
		{
			oldEntries.put( t.getPrice() , new ArrayList<Tradable>() );
		}
		
		t.setCancelledVolume( t.getRemainingVolume() );
		t.setRemainingVolume( 0 );
		
		oldEntries.get( t.getPrice() ).add( t );
	}
	
	public synchronized void openMarket() throws InvalidVolumeException, InvalidEnumException
	{
		Price buyPrice = buySide.topOfBookPrice();
		Price sellPrice = sellSide.topOfBookPrice();
		if ( buyPrice == null || sellPrice == null )
		{
			return;
		}
		
		while( buyPrice.greaterOrEqual( sellPrice ) || buyPrice.isMarket() || sellPrice.isMarket() )
		{
			ArrayList< Tradable > topOfBuySide = buySide.getEntriesAtPrice( buyPrice );
			HashMap<String, FillMessage> allFills = null;
			ArrayList< Tradable > toRemove = new ArrayList< Tradable >();
			
			for ( Tradable t : topOfBuySide )
			{
				allFills = sellSide.tryTrade( t );
				if ( t.getRemainingVolume() == 0 )
				{
					toRemove.add( t );
				}
			}
			
			for ( Tradable t : toRemove )
			{
				buySide.removeTradable( t );
			}
			
			updateCurrentMarket();
			
			Price lastSalePrice = determineLastSalePrice( allFills );
			int lastSaleVolume = determineLastSaleQuantity( allFills );

			LastSalePublisher lSP = LastSalePublisher.getInstance();
			lSP.publishLastSale( symbol, lastSalePrice, lastSaleVolume);
			
			buyPrice = buySide.topOfBookPrice();
			sellPrice = sellSide.topOfBookPrice();
			
			if ( buyPrice == null || sellPrice == null )
			{
				break;
			}
		}
	}
	
	public synchronized void closeMarket() throws OrderNotFoundException, InvalidVolumeException
	{
		buySide.cancelAll();
		sellSide.cancelAll();
		updateCurrentMarket();
	}
	
	public synchronized void cancelOrder(BookSide side, String orderId) throws OrderNotFoundException, InvalidVolumeException
	{
		if ( side.equals( BookSide.BUY ) )
		{
			buySide.submitOrderCancel(orderId);
		}
		else if ( side.equals( BookSide.SELL ) )
		{
			sellSide.submitOrderCancel(orderId);
		}
		updateCurrentMarket();
	}
	
	public synchronized void cancelQuote(String userName)
	{
		buySide.submitQuoteCancel(userName);
		sellSide.submitQuoteCancel(userName);
		updateCurrentMarket();
	}
	
	public synchronized void addToBook(Quote q) throws DataValidationException, InvalidVolumeException, InvalidEnumException
	{
		if ( q.getQuoteSide( "SELL" ).getPrice().lessOrEqual( q.getQuoteSide( "BUY" ).getPrice() ) )
		{
			throw new DataValidationException("Sell price is less than or equal to buy price");
		}
		if ( q.getQuoteSide( "BUY" ).getPrice().lessOrEqual( PriceFactory.makeLimitPrice( "0.00" ) ) || q.getQuoteSide( "SELL" ).getPrice().lessOrEqual( PriceFactory.makeLimitPrice( "0.00" ) ) )
		{
			throw new DataValidationException("Buy or sell price is less than or equal to 0");
		}
		if ( q.getQuoteSide( "BUY" ).getOriginalVolume() <= 0 || q.getQuoteSide( "SELL" ).getOriginalVolume() <= 0 )
		{
			throw new DataValidationException("Buy or sell volume is less than or equal to 0");
		}
		
		if (userQuotes.contains(q.getUserName()))
		{
			buySide.removeQuote( q.getUserName() );
			sellSide.removeQuote( q.getUserName() );
			updateCurrentMarket();
		}
		
		addToBook( BookSide.valueOf( "BUY" ), q.getQuoteSide( "BUY" ) );
		addToBook( BookSide.valueOf( "SELL" ), q.getQuoteSide( "SELL" ) );
		
		userQuotes.add(q.getUserName());
		
		updateCurrentMarket();
		
	}
	
	public synchronized void addToBook(Order o) throws InvalidVolumeException, InvalidEnumException
	{
		addToBook( BookSide.valueOf( o.getSide() ), o);
		updateCurrentMarket();
	}
	
	public synchronized void updateCurrentMarket()
	{
		String st = ( buySide.topOfBookPrice() == null ? "NO_PRICE" : buySide.topOfBookPrice().toString() ) + buySide.topOfBookVolume() + ( sellSide.topOfBookPrice() == null ? "NO_PRICE" : sellSide.topOfBookPrice().toString() ) + sellSide.topOfBookVolume() ;
		if ( !latestValue.equals(st) )
		{
			MarketDataDTO mdDTO = new MarketDataDTO( symbol, buySide.topOfBookPrice(), buySide.topOfBookVolume(), sellSide.topOfBookPrice(), sellSide.topOfBookVolume() );
			CurrentMarketPublisher.getInstance().publishCurrentMarket(mdDTO);
			latestValue = st;
		}
	}
	
	private synchronized Price determineLastSalePrice(HashMap<String, FillMessage> fills)
	{
		ArrayList<FillMessage> msgs = new ArrayList<>(fills.values());
		Collections.sort(msgs);
		return msgs.get(0).getPrice();
	}
	
	private synchronized int determineLastSaleQuantity(HashMap<String, FillMessage> fills)
	{
		ArrayList<FillMessage> msgs = new ArrayList<>(fills.values());
		Collections.sort(msgs);
		return msgs.get(0).getVolume();
	}
	
	private synchronized void addToBook(BookSide side, Tradable trd) throws InvalidVolumeException, InvalidEnumException
	{
		ProductService.getInstance();
		if ( ProductService.marketState.equals( MarketState.valueOf( "PREOPEN" ) ) )
		{
			if ( side.equals( BookSide.valueOf( "BUY" ) ) )
			{
				buySide.addToBook(trd);
			}
			else
			{
				sellSide.addToBook(trd);
			}
			return;
		}
		
		HashMap<String, FillMessage> allFills = null;
		
		if ( side.equals( BookSide.valueOf( "BUY" ) ) )
		{
			allFills = sellSide.tryTrade(trd);
		}
		else
		{
			allFills = buySide.tryTrade(trd);
		}
		if ( allFills != null && !allFills.isEmpty() )
		{
			updateCurrentMarket();
			
			int diff = trd.getOriginalVolume() - trd.getRemainingVolume();
			
			Price lastSalePrice = determineLastSalePrice( allFills );
			
			LastSalePublisher.getInstance().publishLastSale( symbol, lastSalePrice, diff );
		}
		
		if ( trd.getRemainingVolume() > 0 )
		{
			if ( trd.getPrice().isMarket() )
			{
				CancelMessage cM = new CancelMessage( trd.getUser(), trd.getProduct(), trd.getPrice(), trd.getRemainingVolume(), "Cancelled", BookSide.valueOf( trd.getSide() ), trd.getId() );

				MessagePublisher.getInstance().publishCancel(cM);
			}
			else
			{
				if ( side.equals( BookSide.valueOf( "BUY" ) ) )
				{
					buySide.addToBook(trd);
				}
				else
				{
					sellSide.addToBook(trd);
				}
			}
		}
	}
	
	
	
}
