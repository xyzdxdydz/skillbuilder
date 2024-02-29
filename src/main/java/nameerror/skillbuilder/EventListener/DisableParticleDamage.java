package nameerror.skillbuilder.EventListener;

import org.bukkit.entity.Firework;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;

public class DisableParticleDamage implements Listener {

    @EventHandler
    public void onEntityDamageByFireWork(EntityDamageByEntityEvent event) {
        if (event.getDamager() instanceof Firework) {
            Firework fw = (Firework) event.getDamager();

            if (fw.hasMetadata("particle")) {
                event.setCancelled(true);
            }
        }
    }
}
