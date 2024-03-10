package nameerror.skillbuilder.Fundamental;

import nameerror.skillbuilder.Fundamental.StatusEffect.StatusEffectManager;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;


public class EventDispatch implements Listener {

    // TODO; complete this and integrate to other i.e. field, etc.
    @EventHandler
    public void dispatchEntityDamageByEntityEvent(EntityDamageByEntityEvent event) {
        StatusEffectManager.executeOneEntity(event.getEntity(), se -> se.onVictimDamage(event));
    }

}
