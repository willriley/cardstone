package templates.decorators;

import cardgamelibrary.Creature;
import cardgamelibrary.Event;
import cardgamelibrary.EventType;
import cardgamelibrary.Zone;
import events.CreatureAttackEvent;
import events.PlayerAttackEvent;

public class TauntCreature extends CreatureWrapper {

	public TauntCreature(Creature internal) {
		super(internal);
	}

	@Override
	public boolean onProposedLegalityEvent(Event e, Zone z) {
		if (e.getType() == EventType.CREATURE_ATTACKED) {
			CreatureAttackEvent eve = (CreatureAttackEvent) e;
			return getAllowedAttack(eve.getAttacker(), eve.getTarget());
		} else if (e.getType() == EventType.PLAYER_ATTACKED) {
			PlayerAttackEvent eve = (PlayerAttackEvent) e;
			return getAllowedAttack(eve.getAttacker());
		}
		return false;
	}

	public boolean getAllowedAttack(Creature attacker) {
		if (attacker.getOwner() == this.getOwner()) {
			return false;
		} else {
			return false;
		}
	}

	public boolean getAllowedAttack(Creature attacker, Creature target) {
		if (attacker.getOwner() == this.getOwner()) {
			return false;
		} else {
			if (target.isA(TauntCreature.class)) {
				return true;
			}
			return false;
		}
	}

	@Override
	public String getComplaint(Event e, Zone z) {
		if (onProposedLegalityEvent(e, z)) {
			return "Minions with taunt must be attacked first!";
		}
		return "Invalid Event";
	}
}