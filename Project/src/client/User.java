package client;

import java.util.ArrayList;

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
import price.Price;
import tradable.TradableDTO;
import messages.CancelMessage;
import messages.FillMessage;

public interface User {

	public String getUserName();
	public void acceptLastSale( String product, Price p, int v);
	public void acceptMessage( FillMessage fm );
	void acceptMessage( CancelMessage cm );
	void acceptMarketMessage(String message);
	void acceptTicker(String product, Price p, char direction);
	void acceptCurrentMarket(String product, Price bp, int bv, Price sp, int sv);
	
	void connect() throws AlreadyConnectedException, UserNotConnectedException, InvalidConnectionIdException;
	void disConnect() throws UserNotConnectedException, InvalidConnectionIdException;
	void showMarketDisplay() throws Exception;
	String submitOrder(String product, Price price, int volume, BookSide side) throws UserNotConnectedException, InvalidConnectionIdException, InvalidVolumeException, InvalidMarketStateException, NoSuchProductException, InvalidEnumException, DataValidationException;
	void submitOrderCancel(String product, BookSide side, String orderId) throws UserNotConnectedException, InvalidConnectionIdException, InvalidMarketStateException, NoSuchProductException, OrderNotFoundException, InvalidVolumeException;
	void submitQuote(String product, Price buyPrice, int buyVolume, Price sellPrice, int sellVolume) throws UserNotConnectedException, InvalidConnectionIdException, InvalidVolumeException, InvalidMarketStateException, NoSuchProductException, DataValidationException, InvalidEnumException;
	void submitQuoteCancel(String product) throws UserNotConnectedException, InvalidConnectionIdException, InvalidMarketStateException, NoSuchProductException;
	void subscribeCurrentMarket(String product) throws UserNotConnectedException, InvalidConnectionIdException, SubscriberException;
	void subscribeLastSale(String product) throws UserNotConnectedException, InvalidConnectionIdException, SubscriberException;
	void subscribeMessages(String product) throws UserNotConnectedException, InvalidConnectionIdException, SubscriberException;
	void subscribeTicker(String product) throws UserNotConnectedException, InvalidConnectionIdException, SubscriberException;
	Price getAllStockValue();
	Price getAccountCosts();
	Price getNetAccountValue();
	String[][] getBookDepth(String product) throws UserNotConnectedException, InvalidConnectionIdException, NoSuchProductException;
	String getMarketState() throws UserNotConnectedException, InvalidConnectionIdException;
	ArrayList<TradableUserData> getOrderIds();
	ArrayList<String> getProductList();
	Price getStockPositionValue(String sym);
	int getStockPositionVolume(String product);
	ArrayList<String> getHoldings();
	ArrayList<TradableDTO> getOrdersWithRemainingQty(String product);
	
}
