package book;

import java.util.ArrayList;
import java.util.HashMap;

import messages.MarketMessage;
import customExceptions.DataValidationException;
import customExceptions.InvalidEnumException;
import customExceptions.InvalidMarketStateException;
import customExceptions.InvalidMarketStateTransitionException;
import customExceptions.InvalidVolumeException;
import customExceptions.NoSuchProductException;
import customExceptions.OrderNotFoundException;
import customExceptions.ProductAlreadyExistsException;
import publishers.MarketDataDTO;
import publishers.MessagePublisher;
import tradable.Order;
import tradable.Quote;
import tradable.TradableDTO;
import enums.BookSide;
import enums.MarketState;

public class ProductService {

	private HashMap<String, ProductBook> allBooks = new HashMap<String, ProductBook>();
	static public MarketState marketState = MarketState.CLOSED;
	
	private static ProductService instance;
	
	public static ProductService getInstance()
	{
		if (instance == null)
		{
			instance = new ProductService();
		}
		return instance;
	}
	
	public synchronized ArrayList<TradableDTO> getOrdersWithRemainingQty(String userName, String product)
	{
		ProductBook temp = allBooks.get( product );
		ArrayList< TradableDTO > temp2 = temp.getOrdersWithRemainingQty( userName );
		return temp2;
	}
	
	public synchronized MarketDataDTO getMarketData(String product)
	{
		ProductBook temp = allBooks.get( product );
		MarketDataDTO temp2 = temp.getMarketData();
		return temp2;
	}
	
	public synchronized MarketState getMarketState()
	{
		return marketState;
	}
	
	public synchronized String[][] getBookDepth(String product) throws NoSuchProductException
	{
		if ( !allBooks.containsKey( product ) )
		{
			throw new NoSuchProductException( product + " not found" );
		}

		return allBooks.get( product ).getBookDepth();
	}
	
	public synchronized ArrayList<String> getProductList()
	{
		return new ArrayList<String>(allBooks.keySet());
	}
	
	//-------------------------------------------------------------------------------------------------------------------------------------
	
	public synchronized void setMarketState(MarketState ms) throws InvalidMarketStateTransitionException, InvalidVolumeException, InvalidEnumException, OrderNotFoundException
	{
		if ( ms.ordinal() != (marketState.ordinal() + 1) % MarketState.values().length )
		{
			throw new InvalidMarketStateTransitionException("Cannot transition market from " + marketState + " to " + ms );
		}
		
		marketState = ms;
		
		MarketMessage mM = new MarketMessage(marketState);
		
		MessagePublisher mP = MessagePublisher.getInstance();
		mP.publishMarketMessage(mM);
		
		if ( marketState.equals( MarketState.OPEN ) )
		{
			for ( ProductBook pB : allBooks.values() )
			{
				pB.openMarket();
			}
		}
		else if ( marketState.equals( MarketState.CLOSED ) )
		{
			for ( ProductBook pB : allBooks.values() )
			{
				pB.closeMarket();
			}
		}
	}
	
	public synchronized void createProduct(String product) throws DataValidationException, ProductAlreadyExistsException
	{
		if ( product == null || product.isEmpty() )
		{
			throw new DataValidationException( "Invalid product passed in" );
		}
		
		if ( allBooks.containsKey( product ) )
		{
			throw new ProductAlreadyExistsException( "Book for product already exists" );
		}
		
		allBooks.put( product, new ProductBook( product ) );
	}
	
	public synchronized void submitQuote(Quote q) throws InvalidMarketStateException, NoSuchProductException, DataValidationException, InvalidVolumeException, InvalidEnumException
	{
		if ( marketState.equals( MarketState.CLOSED ) )
		{
			throw new InvalidMarketStateException("Market is closed");
		}
		if ( !allBooks.containsKey( q.getProduct() ) )
		{
			throw new NoSuchProductException("Product does not exist");
		}
		
		allBooks.get( q.getProduct() ).addToBook( q );
	}
	
	public synchronized String submitOrder(Order o) throws InvalidMarketStateException, NoSuchProductException, InvalidVolumeException, InvalidEnumException
	{
		if ( marketState.equals( MarketState.CLOSED ) )
		{
			throw new InvalidMarketStateException( "Market is closed" );
		}
		
		if ( marketState.equals( MarketState.PREOPEN ) && o.getPrice().isMarket() )
		{
			throw new InvalidMarketStateException( "Cannot submit market price order when market is preopened" );
		}
		
		if ( !allBooks.containsKey( o.getProduct() ) )
		{
			throw new NoSuchProductException( "Order's product does not exist" );
		}
		
		allBooks.get( o.getProduct() ).addToBook( o );
		return o.getId();
	}
	
	public synchronized void submitOrderCancel(String product, BookSide side, String orderId) throws InvalidMarketStateException, NoSuchProductException, OrderNotFoundException, InvalidVolumeException
	{
		if ( marketState.equals( MarketState.CLOSED ) )
		{
			throw new InvalidMarketStateException( "Market is closed" );
		}
		
		if ( !allBooks.containsKey( product ) )
		{
			throw new NoSuchProductException( product + " not found" );
		}
		
		allBooks.get( product ).cancelOrder( side, orderId );
	}
	
	public synchronized void submitQuoteCancel(String userName, String product) throws InvalidMarketStateException, NoSuchProductException
	{
		if ( marketState.equals( MarketState.CLOSED ) )
		{
			throw new InvalidMarketStateException( "Market is closed" );
		}
		
		if ( !allBooks.containsKey( product ) )
		{
			throw new NoSuchProductException( product + " not found" );
		}
		
		allBooks.get( product ).cancelQuote( userName );
	}
}
