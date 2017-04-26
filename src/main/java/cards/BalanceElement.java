package cards;

import cardgamelibrary.Board;
import cardgamelibrary.Card;
import cardgamelibrary.CardType;
import cardgamelibrary.Effect;
import cardgamelibrary.Element;
import cardgamelibrary.ElementType;
import cardgamelibrary.ManaPool;
import cardgamelibrary.Zone;
import game.Player;

public class BalanceElement extends Element {

	private static final ManaPool	defaultCost		= ManaPool.emptyPool();
	private static final String		defaultImage	= "images/waterChoices/balanceBig.jpg";
	private static final String		defaultName		= "balance";
	private static final String		defaultText		= "";
	private static final CardType	defaultType		= CardType.ELEMENT;

	public BalanceElement(Player owner) {
		super(defaultCost, defaultImage, owner, defaultName, defaultText, defaultType);
	}

	@Override
	public Effect onThisPlayed(Card c, Zone z) {
		assert (this.equals(c));
		return (Board board) -> {
			board.givePlayerElement(getOwner(), ElementType.BALANCE, this.DEFAULT_ELEMENT_GAIN);
		};
	}

}
