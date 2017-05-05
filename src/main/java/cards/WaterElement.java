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

public class WaterElement extends Element {

	private static final ManaPool defaultCost = ManaPool.emptyPool();
	private static final String defaultImage = "images/waterChoices/waterBig.jpg";
	private static final String defaultName = "water";
	private static final String defaultText = "";
	private static final CardType defaultType = CardType.ELEMENT;

	public WaterElement(Player owner) {
		super(defaultCost, defaultImage, owner, defaultName, defaultText, defaultType);
	}

	@Override
	public Effect onThisPlayed(Card c, Zone z) {
		assert (this.equals(c));
		return (Board board) -> {
			// give player element.
			board.givePlayerElement(getOwner(), ElementType.WATER, this.DEFAULT_ELEMENT_GAIN);
			// send element to grave from hand.
			board.addCardToOcc(c, board.getOcc(getOwner(), Zone.GRAVE), board.getOcc(getOwner(), Zone.HAND));
		};
	}

}
