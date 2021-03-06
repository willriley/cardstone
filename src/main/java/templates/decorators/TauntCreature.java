package templates.decorators;

import cardgamelibrary.Creature;
import cardgamelibrary.CreatureInterface;
import cardgamelibrary.Event;
import cardgamelibrary.EventType;
import cardgamelibrary.Zone;
import events.CreatureAttackEvent;
import events.PlayerAttackEvent;

public class TauntCreature extends CreatureWrapper {

	public TauntCreature(CreatureInterface internal) {
		super(internal);
	}

	@Override
	public boolean onProposedLegalityEvent(Event e, Zone z) {
		if(active){
			if (e.getType() == EventType.CREATURE_ATTACKED) {
				CreatureAttackEvent eve = (CreatureAttackEvent) e;
				return getAllowedAttack(eve.getAttacker(), eve.getTarget()) || internal.onProposedLegalityEvent(e, z);
			} else if (e.getType() == EventType.PLAYER_ATTACKED) {
				PlayerAttackEvent eve = (PlayerAttackEvent) e;
				return getAllowedAttack(eve.getAttacker()) || internal.onProposedLegalityEvent(e, z);
			}
		}
		return internal.onProposedLegalityEvent(e, z);
	}

	public boolean getAllowedAttack(CreatureInterface attacker) {
		if (attacker.getOwner() == this.getOwner()) {
			return false;
		} else {
			return true;
		}
	}

	public boolean getAllowedAttack(CreatureInterface attacker, CreatureInterface target) {
		
		if (attacker.getOwner() == this.getOwner()) {
			return false;
		} else {
			if (target.isA(TauntCreature.class)) {
				return false;
			}
			return true;
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
