package nameerror.skillbuilder.Fundamental.ObjectManagement.Projectile;

import org.bukkit.entity.Projectile;
import org.bukkit.event.entity.ProjectileHitEvent;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;
import java.util.function.Consumer;

public class ProjectileManager {
    private static final Set<AbstractProjectile> projectileMap = new HashSet<>();
    private static final HashMap<Projectile, AbstractProjectile> regularProjectileMap = new HashMap<>();

    public static void register(AbstractProjectile projectile) {
        projectileMap.add(projectile);
        if (projectile instanceof RegularProjectile) {
            regularProjectileMap.put(((RegularProjectile) projectile).getProjectile(), projectile);
        }
        // TODO; create and event and call;
        projectile.onLaunch();
    }

    public static void remove(AbstractProjectile projectile, boolean triggerEvent) {
        projectile.onDead();

        if (projectile instanceof RegularProjectile) {
            regularProjectileMap.remove(((RegularProjectile) projectile).getProjectile());
        }
        projectileMap.remove(projectile);
    }

    // remove without trigger event
    public static void removeAll(HashSet<AbstractProjectile> projectilesToRemove) {
        for (AbstractProjectile proj : projectilesToRemove) {
            proj.onDead();
            if (proj instanceof RegularProjectile) {
                regularProjectileMap.remove(((RegularProjectile) proj).getProjectile());
            }
        }

        projectileMap.removeAll(projectilesToRemove);
    }

    public static Runnable update() {
        HashSet<AbstractProjectile> deadProjectile = new HashSet<>();
        for (AbstractProjectile proj : projectileMap) {
            if (!proj.isDead()) {
                proj.step();
            } else {
                deadProjectile.add(proj);
            }
        }

        removeAll(deadProjectile);
        return null;
    }

    public static void executeAll(Consumer<AbstractProjectile> func) {
        for (AbstractProjectile projectile : projectileMap) {
            func.accept(projectile);
        }
    }

    public static void dispatchBukkitEvent(ProjectileHitEvent event) {
        Projectile projectile = event.getEntity();
        if (regularProjectileMap.containsKey(projectile)) {
            regularProjectileMap.get(projectile).onHit(new ProjectileHitInfo(event));
        }
    }

    public static AbstractProjectile getAbstractProjectile(Projectile projectile) {
        return regularProjectileMap.getOrDefault(projectile, null);
    }

    public static void clear() {
        projectileMap.clear();
    }
}
