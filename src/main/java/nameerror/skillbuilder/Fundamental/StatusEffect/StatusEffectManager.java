package nameerror.skillbuilder.Fundamental.StatusEffect;

import org.bukkit.entity.Entity;

import java.util.HashMap;
import java.util.HashSet;

public class StatusEffectManager {
    private static final HashMap<Entity, HashSet<StatusEffect>> statusMap = new HashMap<>();

    public static void applyEffect(Entity entity, StatusEffect statusEffect) {
        if (!statusMap.containsKey(entity)) {
            statusMap.put(entity, new HashSet<>());
        }
        statusMap.get(entity).add(statusEffect);

    }

    public static HashSet<StatusEffect> getEffectStatus(Entity entity) {
        return statusMap.get(entity);
    }

    public static void remove(Entity entity, StatusEffect statusEffect) {
        statusMap.get(entity).remove(statusEffect);
    }

    public static Runnable update() {
        for (HashMap.Entry<Entity, HashSet<StatusEffect>> entry : statusMap.entrySet()) {
            Entity entity = entry.getKey();
            for (StatusEffect statusEffect : entry.getValue()) {
                statusEffect.step();
                if (statusEffect.isExpired()) {
                    remove(entity, statusEffect);
                }
            }
        }
        return null;
    }
}
