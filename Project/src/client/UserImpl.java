package client;

import enums.BookSide;
import gui.UserDisplayManager;

import java.sql.Timestamp;
import java.util.ArrayList;

import messages.CancelMessage;
import messages.FillMessage;
import price.Price;
import price.PriceFactory;
import tradable.TradableDTO;
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

public class UserImpl implements User {

	private String userName;
	private long connId;
	private ArrayList<String> stocksAvailable;
	private ArrayList<TradableUserData> ordersSubmitted;
	private Position values;
	private UserDisplayManager display;
	
	public UserImpl(String userNameIn) //throws DataValidationException
	{
		try {
			setUserName(userNameIn);
		} catch ( Exception e )
		{
			
		}
		values = new Position();
		
		stocksAvailable = new ArrayList<String>();
		ordersSubmitted = new ArrayList<TradableUserData>();
	}
	private void setUserName(String userNameIn) throws DataValidationException
	{
		if ( userNameIn == null || userNameIn.isEmpty() )
		{
			throw new DataValidationException("User's name cannot be null or empty");
		}
		userName = userNameIn;
	}
	
	@Override
	public String getUserName()
	{
		return userName;
	}
	
	@Override
	public void acceptLastSale(String product, Price price, int volume)
	{
		try
		{
			if ( stocksAvailable == null )
			{
				throw new UserNotConnectedException("User is not connected");
			}
		} 
		catch (Exception e)
		{
			System.out.println( e.getMessage() );
		}
		if ( display == null )
		{
			display = new UserDisplayManager(this);
		}
		display.updateLastSale(product, price, volume);
		values.updateLastSale(product, price);
	}
	
	@Override
	public void acceptMessage(FillMessage fm)
	{
		Timestamp tStamp = new Timestamp(System.currentTimeMillis());
		String summary = "{" + tStamp + "} Fill Message: " + fm.getSide().toString() + " side " + fm.getVolume() + " at " + fm.getPrice().toString() + " " + fm.getDetails() + " [Tradable Id: " + fm.getId() + "]\n" ;
		//String summary = fm.toString();
		try
		{
			if ( stocksAvailable == null )
			{
				throw new UserNotConnectedException("User is not connected");
			}
		} 
		catch (Exception e)
		{
			System.out.println( e.getMessage() );
		}
		if ( display == null )
		{
			display = new UserDisplayManager(this);
		}
		display.updateMarketActivity(summary);
		try
		{
			values.updatePosition(fm.getProduct(), fm.getPrice(), fm.getSide(), fm.getVolume());
		}
		catch ( Exception e )
		{
			System.out.println(e.getMessage());
		}
	}
	
	@Override
	public void acceptMessage(CancelMessage cm) {
		Timestamp tStamp = new Timestamp(System.currentTimeMillis());
		String summary = "{" + tStamp + "} Fill Message: " + cm.getSide().toString() + " side " + cm.getVolume() + " at " + cm.getPrice().toString() + " " + cm.getDetails() + " [Tradable Id: " + cm.getId() + "]\n" ;
		//String summary = cm.toString();
		try
		{
			if ( stocksAvailable == null )
			{
				throw new UserNotConnectedException("User is not connected");
			}
		} 
		catch (Exception e)
		{
			System.out.println( e.getMessage() );
		}
		if ( display == null )
		{
			display = new UserDisplayManager(this);
		}
		display.updateMarketActivity(summary);
	}
	
	@Override
	public void acceptMarketMessage(String message) {
		try
		{
			if ( stocksAvailable == null )
			{
				throw new UserNotConnectedException("User is not connected");
			}
		} 
		catch (Exception e)
		{
			System.out.println( e.getMessage() );
		}
		if ( display == null )
		{
			display = new UserDisplayManager(this);
		}
		display.updateMarketState(message);
	}
	
	@Override
	public void acceptTicker(String product, Price p, char direction) {
		try
		{
			if ( stocksAvailable == null )
			{
				throw new UserNotConnectedException("User is not connected");
			}
		} 
		catch (Exception e)
		{
			System.out.println( e.getMessage() );
		}
		if ( display == null )
		{
			display = new UserDisplayManager(this);
		}
		display.updateTicker(product, p, direction);
	}
	
	@Override
	public void acceptCurrentMarket(String product, Price bp, int bv, Price sp, int sv) {
		try
		{
			if ( stocksAvailable == null )
			{
				throw new UserNotConnectedException("User is not connected");
			}
		} 
		catch (Exception e)
		{
			System.out.println( e.getMessage() );
		}
		if ( display == null )
		{
			display = new UserDisplayManager(this);
		}
		display.updateMarketData(product, (bp == null ? PriceFactory.makeLimitPrice(0) : bp), bv, (sp == null ? PriceFactory.makeLimitPrice(0) : sp), sv);
	}
	
	@Override
	public void connect() throws AlreadyConnectedException, UserNotConnectedException, InvalidConnectionIdException {
		connId = UserCommandService.getInstance().connect(this);
		stocksAvailable = UserCommandService.getInstance().getProducts(userName, connId);
	}
	
	@Override
	public void disConnect() throws UserNotConnectedException, InvalidConnectionIdException {
		UserCommandService.getInstance().disConnect(userName, connId);
	}
	
	@Override
	public void showMarketDisplay() throws Exception {
		if ( stocksAvailable == null )
		{
			throw new UserNotConnectedException("User is not connected");
		}
		if ( display == null )
		{
			display = new UserDisplayManager(this);
		}
		display.showMarketDisplay();
	}
	
	@Override
	public String submitOrder(String product, Price price, int volume, BookSide side) throws UserNotConnectedException, InvalidConnectionIdException, InvalidVolumeException, InvalidMarketStateException, NoSuchProductException, InvalidEnumException, DataValidationException {
		String id = UserCommandService.getInstance().submitOrder(userName, connId, product, price, volume, side);
		TradableUserData tUD = new TradableUserData(userName, product, side, id);
		ordersSubmitted.add(tUD);
		return id;
	}
	
	@Override
	public void submitOrderCancel(String product, BookSide side, String orderId) throws UserNotConnectedException, InvalidConnectionIdException, InvalidMarketStateException, NoSuchProductException, OrderNotFoundException, InvalidVolumeException {
		UserCommandService.getInstance().submitOrderCancel(userName, connId, product, side, orderId);
	}
	
	@Override
	public void submitQuote(String product, Price buyPrice, int buyVolume, Price sellPrice, int sellVolume) throws UserNotConnectedException, InvalidConnectionIdException, InvalidVolumeException, InvalidMarketStateException, NoSuchProductException, DataValidationException, InvalidEnumException {
		UserCommandService.getInstance().submitQuote(userName, connId, product, buyPrice, buyVolume, sellPrice, sellVolume);
	}
	
	@Override
	public void submitQuoteCancel(String product) throws UserNotConnectedException, InvalidConnectionIdException, InvalidMarketStateException, NoSuchProductException {
		UserCommandService.getInstance().submitQuoteCancel(userName, connId, product);
	}
	
	@Override
	public void subscribeCurrentMarket(String product) throws UserNotConnectedException, InvalidConnectionIdException, SubscriberException {
		UserCommandService.getInstance().subscribeCurrentMarket(userName, connId, product);
	}
	
	@Override
	public void subscribeLastSale(String product) throws UserNotConnectedException, InvalidConnectionIdException, SubscriberException {
		UserCommandService.getInstance().subscribeLastSale(userName, connId, product);
	}
	
	@Override
	public void subscribeMessages(String product) throws UserNotConnectedException, InvalidConnectionIdException, SubscriberException {
		UserCommandService.getInstance().subscribeMessages(userName, connId, product);
	}
	
	@Override
	public void subscribeTicker(String product) throws UserNotConnectedException, InvalidConnectionIdException, SubscriberException {
		UserCommandService.getInstance().subscribeTicker(userName, connId, product);
	}
	
	@Override
	public Price getAllStockValue() {
		try {
			return values.getAllStockValue();
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
		return null;
	}
	
	@Override
	public Price getAccountCosts() {
		return values.getAccountCosts();
	}
	
	@Override
	public Price getNetAccountValue(){
		try {
			return values.getNetAccountValue();
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
		return null;
	}
	
	@Override
	public String[][] getBookDepth(String product) throws UserNotConnectedException, InvalidConnectionIdException, NoSuchProductException {
		return UserCommandService.getInstance().getBookDepth(userName, connId, product);
	}
	
	@Override
	public String getMarketState() throws UserNotConnectedException, InvalidConnectionIdException {
		return UserCommandService.getInstance().getMarketState(userName, connId);
	}
	
	@Override
	public ArrayList<TradableUserData> getOrderIds() {
		return ordersSubmitted;
	}
	
	@Override
	public ArrayList<String> getProductList() {
		return stocksAvailable;
	}
	
	@Override
	public Price getStockPositionValue(String sym) {
		return values.getStockPositionValue(sym);
	}
	
	@Override
	public int getStockPositionVolume(String product) {
		return values.getStockPositionVolume(product);
	}
	
	@Override
	public ArrayList<String> getHoldings() {
		return values.getHoldings();
	}
	
	@Override
	public ArrayList<TradableDTO> getOrdersWithRemainingQty(String product) {
		try {
			return UserCommandService.getInstance().getOrdersWithRemainingQty(userName, connId, product);
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
		return null;
	}
	
	
}
