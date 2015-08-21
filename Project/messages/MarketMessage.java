package messages;

import enums.MarketState;

public class MarketMessage {

	private MarketState state;
	
	public MarketMessage(MarketState stateIn) //throws InvalidEnumException
	{
		setMarketState( stateIn );
	}
	
	public MarketState getMarketState()
	{
		return state;
	}
	private void setMarketState( MarketState stateIn ) //throws InvalidEnumException
	{
		/*
		for ( MarketState m : MarketState.values() )
		{
			if ( m.name().equals( stateIn ) )
			{
				state = MarketState.valueOf( stateIn );
				return;
			}
		}
		throw new InvalidEnumException("Invalid market state");
		*/
		state = stateIn;
	}
	
	public String toString()
	{
		return state.toString();
	}
	
}
