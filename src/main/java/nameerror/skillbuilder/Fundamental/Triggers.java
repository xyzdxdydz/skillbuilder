package nameerror.skillbuilder.Fundamental;

import org.bukkit.event.entity.EntityDamageByBlockEvent;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;

public interface Triggers {
    default void onVictimDamage(EntityDamageByBlockEvent event) { }

    default void onVictimDamage(EntityDamageByEntityEvent event) { }

    default void onVictimDamage(EntityDamageEvent event) { }
}
