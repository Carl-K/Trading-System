package book;

import java.util.HashMap;

import customExceptions.InvalidEnumException;
import customExceptions.InvalidVolumeException;
import tradable.Tradable;
import messages.FillMessage;

public interface TradeProcessor {
	
	public HashMap<String, FillMessage> doTrade(Tradable trd) throws InvalidVolumeException, InvalidEnumException;
	
}
