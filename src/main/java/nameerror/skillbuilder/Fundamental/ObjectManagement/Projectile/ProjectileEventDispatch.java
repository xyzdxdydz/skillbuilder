package nameerror.skillbuilder.Fundamental.ObjectManagement.Projectile;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.ProjectileHitEvent;

public class ProjectileEventDispatch implements Listener {

    @EventHandler
    public void dispatchProjectileHit(ProjectileHitEvent event) {
        if (!event.isCancelled()) {
            ProjectileManager.dispatchBukkitEvent(event);
        }
    }
}
