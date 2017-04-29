package events;

import cardgamelibrary.Event;
import cardgamelibrary.EventType;
import cards.templates.TargetsPlayer;
import game.Player;

public class PlayerTargetedEvent implements Event {

	private TargetsPlayer	targetter;
	private Player				target;

	public PlayerTargetedEvent(TargetsPlayer targetter, Player target) {
		this.targetter = targetter;
		this.target = target;
	}

	public TargetsPlayer getTargetter() {
		return targetter;
	}

	public Player getTarget() {
		return target;
	}

	@Override
	public EventType getType() {
		// TODO Auto-generated method stub
		return EventType.PLAYER_TARGETED;
	}

}