package client;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;

import price.Price;
import publishers.CurrentMarketPublisher;
import publishers.LastSalePublisher;
import publishers.MessagePublisher;
import publishers.TickerPublisher;
import tradable.Order;
import tradable.Quote;
import tradable.TradableDTO;
import book.ProductService;
import customExceptions.AlreadyConnectedException;
import customExceptions.DataValidationException;
import customExceptions.InvalidConnectionIdException;
import customExceptions.InvalidEnumException;
import customExceptions.InvalidMarketStateException;
import customExceptions.InvalidVolumeException;
import customExceptions.NoSuchProductException;
import customExceptions.OrderNotFoundException;
import customExceptions.SubscriberException;
import customExceptions.UserNotConnectedException;
import enums.BookSide;

public class UserCommandService {
	
	private static UserCommandService instance;

	private HashMap<String, Long> nameConnectionPairs;
	private HashMap<String, User> nameObjectPairs;
	private HashMap<String, Long> nameTimePairs;
	
	private UserCommandService()
	{
		nameConnectionPairs = new HashMap<String, Long>();
		nameObjectPairs = new HashMap<String, User>();
		nameTimePairs = new HashMap<String, Long>();
	}
	
	public static UserCommandService getInstance()
	{
		if ( instance == null )
		{
			instance = new UserCommandService();
		}
		return instance;
	}
	
	private void verifyUser(String userName, long connId) throws UserNotConnectedException, InvalidConnectionIdException
	{
		if ( !nameConnectionPairs.containsKey(userName) )
		{
			throw new UserNotConnectedException("User not connected");
		}
		if ( !nameConnectionPairs.get(userName).equals(connId) )
		{
			throw new InvalidConnectionIdException("Connection id does not match user's name");
		}
	}
	
	public synchronized long connect(User user) throws AlreadyConnectedException
	{
		if ( nameObjectPairs.containsValue(user))
		{
			throw new AlreadyConnectedException("User is already connected");
		}
		long time = System.nanoTime();
		nameConnectionPairs.put(user.getUserName(), time);
		nameObjectPairs.put(user.getUserName(), user);
		nameTimePairs.put(user.getUserName(), System.currentTimeMillis());
		
		return time;
	}
	
	public synchronized void disConnect(String userName, long connId) throws UserNotConnectedException, InvalidConnectionIdException
	{
		verifyUser(userName, connId);
		
		nameConnectionPairs.remove(userName);
		nameObjectPairs.remove(userName);
		nameTimePairs.remove(userName);
	}
	
	public String[][] getBookDepth(String userName, long connId, String product) throws UserNotConnectedException, InvalidConnectionIdException, NoSuchProductException
	{
		verifyUser(userName, connId);
		return ProductService.getInstance().getBookDepth(product);
	}
	
	public String getMarketState(String userName, long connId) throws UserNotConnectedException, InvalidConnectionIdException
	{
		verifyUser(userName, connId);
		return ProductService.getInstance().getMarketState().toString();
	}
	
	public synchronized ArrayList<TradableDTO> getOrdersWithRemainingQty(String userName, long connId, String product) throws UserNotConnectedException, InvalidConnectionIdException
	{
		verifyUser(userName, connId);
		return ProductService.getInstance().getOrdersWithRemainingQty(userName, product);
	}
	
	public ArrayList<String> getProducts(String userName, long connId) throws UserNotConnectedException, InvalidConnectionIdException
	{
		verifyUser(userName, connId);
		ArrayList<String> products = ProductService.getInstance().getProductList();
		Collections.sort(products);
		return products;
	}
	
	public String submitOrder(String userName, long connId, String product, Price price, int volume, BookSide side) throws UserNotConnectedException, InvalidConnectionIdException, InvalidVolumeException, InvalidMarketStateException, NoSuchProductException, InvalidEnumException
	{
		verifyUser(userName, connId);
		return ProductService.getInstance().submitOrder( new Order(userName, product, price, volume, side ) );
	}
	
	public void submitOrderCancel(String userName, long connId, String product, BookSide side, String orderId) throws UserNotConnectedException, InvalidConnectionIdException, InvalidMarketStateException, NoSuchProductException, OrderNotFoundException, InvalidVolumeException
	{
		verifyUser(userName, connId);
		ProductService.getInstance().submitOrderCancel(product, side, orderId);
	}
	
	public void submitQuote(String userName, long connId, String product, Price bPrice, int bVolume, Price sPrice, int sVolume) throws UserNotConnectedException, InvalidConnectionIdException, InvalidVolumeException, InvalidMarketStateException, NoSuchProductException, DataValidationException, InvalidEnumException
	{
		verifyUser(userName, connId);
		ProductService.getInstance().submitQuote( new Quote(userName, product, bPrice, bVolume, sPrice, sVolume) );
	}
	
	public void submitQuoteCancel(String userName, long connId, String product) throws UserNotConnectedException, InvalidConnectionIdException, InvalidMarketStateException, NoSuchProductException
	{
		verifyUser(userName, connId);
		ProductService.getInstance().submitQuoteCancel(userName, product);
	}
	
	public void subscribeCurrentMarket(String userName, long connId, String product) throws UserNotConnectedException, InvalidConnectionIdException, SubscriberException
	{
		verifyUser(userName, connId);
		CurrentMarketPublisher.getInstance().subscribe( nameObjectPairs.get(userName), product);
	}
	
	public void subscribeLastSale(String userName, long connId, String product) throws UserNotConnectedException, InvalidConnectionIdException, SubscriberException
	{
		verifyUser(userName, connId);
		LastSalePublisher.getInstance().subscribe( nameObjectPairs.get(userName), product);
	}
	
	public void subscribeMessages(String userName, long connId, String product) throws UserNotConnectedException, InvalidConnectionIdException, SubscriberException
	{
		verifyUser(userName, connId);
		MessagePublisher.getInstance().subscribe( nameObjectPairs.get(userName), product);
	}
	
	public void subscribeTicker(String userName, long connId, String product) throws UserNotConnectedException, InvalidConnectionIdException, SubscriberException
	{
		verifyUser(userName, connId);
		TickerPublisher.getInstance().subscribe( nameObjectPairs.get(userName), product);
	}
	
	public void unSubscribeCurrentMarket(String userName, long connId, String product) throws UserNotConnectedException, InvalidConnectionIdException, SubscriberException
	{
		verifyUser(userName, connId);
		CurrentMarketPublisher.getInstance().unSubscribe( nameObjectPairs.get(userName), product);
	}
	
	public void unSubscribeLastSaleMarket(String userName, long connId, String product) throws UserNotConnectedException, InvalidConnectionIdException, SubscriberException
	{
		verifyUser(userName, connId);
		LastSalePublisher.getInstance().unSubscribe( nameObjectPairs.get(userName), product);
	}
	
	public void unSubscribeTickerMarket(String userName, long connId, String product) throws UserNotConnectedException, InvalidConnectionIdException, SubscriberException
	{
		verifyUser(userName, connId);
		TickerPublisher.getInstance().unSubscribe( nameObjectPairs.get(userName), product);
	}
	
	public void unSubscribeMessages(String userName, long connId, String product) throws UserNotConnectedException, InvalidConnectionIdException, SubscriberException
	{
		verifyUser(userName, connId);
		MessagePublisher.getInstance().unSubscribe( nameObjectPairs.get(userName), product);
	}
}
