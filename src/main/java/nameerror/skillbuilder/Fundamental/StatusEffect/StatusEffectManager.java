package nameerror.skillbuilder.Fundamental.StatusEffect;

import nameerror.skillbuilder.Fundamental.StatusEffect.CrowdControl.CrowdControlManager;
import org.bukkit.entity.Entity;

import java.util.ArrayList;
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

    public static void removeEffect(Entity entity, StatusEffect statusEffect) {
        statusMap.get(entity).remove(statusEffect);
    }

    public static HashSet<StatusEffect> getStatusEffect(Entity entity) {
        return statusMap.getOrDefault(entity, new HashSet<>());
    }

    public static Runnable update() {

        for (HashMap.Entry<Entity, HashSet<StatusEffect>> entry : statusMap.entrySet()) {
            Entity entity = entry.getKey();
            ArrayList<StatusEffect> expired = new ArrayList<>();

            CrowdControlManager.cleanse(entity); // cleanse expired CC

            for (StatusEffect statusEffect : entry.getValue()) {
                statusEffect.step();
                if (statusEffect.isExpired()) {
                    expired.add(statusEffect);
                }
            }

            for (StatusEffect statusEffect : expired) {
                removeEffect(entity, statusEffect);
            }
        }

        return null;
    }

    public static void clear() {
        statusMap.clear();
        CrowdControlManager.clear();
    }

    // Crowd control handler

}
