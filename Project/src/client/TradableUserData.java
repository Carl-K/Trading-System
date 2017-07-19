package client;

import customExceptions.DataValidationException;
import enums.BookSide;

public class TradableUserData {

	private String userName;
	private String symbol;
	private BookSide side;
	private String id;
	
	public TradableUserData(String userNameIn, String symbolIn, BookSide sideIn, String idIn) throws DataValidationException
	{
		setUserName(userNameIn);
		setSymbol(symbolIn);
		setSide(sideIn);
		setId(idIn);
	}
	
	public String getUserName()
	{
		return userName;
	}
	private void setUserName(String userNameIn) throws DataValidationException
	{
		if (userNameIn == null || userNameIn.isEmpty())
		{
			throw new DataValidationException("Invalid user name");
		}
		userName = userNameIn;
	}
	
	public String getProduct()
	{
		return symbol;
	}
	private void setSymbol(String symbolIn) throws DataValidationException
	{
		if (symbolIn == null || symbolIn.isEmpty())
		{
			throw new DataValidationException("Invalid stock symbol");
		}
		symbol = symbolIn;
	}
	
	public BookSide getSide()
	{
		return side;
	}
	private void setSide(BookSide sideIn)
	{
		side = sideIn;
	}
	
	public String getId()
	{
		return id;
	}
	private void setId(String idIn) throws DataValidationException
	{
		if (idIn == null || idIn.isEmpty())
		{
			throw new DataValidationException("Invalid id");
		}
		id = idIn;
	}
	
}
