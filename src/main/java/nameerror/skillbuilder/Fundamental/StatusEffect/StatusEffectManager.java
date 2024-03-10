package nameerror.skillbuilder.Fundamental.StatusEffect;

import jdk.internal.org.jline.utils.Status;
import nameerror.skillbuilder.Fundamental.StatusEffect.CrowdControl.CrowdControlManager;
import org.bukkit.entity.Entity;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.function.Consumer;

public class StatusEffectManager {
    private static final HashMap<Entity, HashSet<StatusEffect>> statusMap = new HashMap<>();

    private static final HashMap<Entity, HashSet<StatusEffect>> addLater = new HashMap<>();
    private static final HashMap<Entity, HashSet<StatusEffect>> removeLater = new HashMap<>();

    private static boolean mapInUse = false;

    private static HashSet<StatusEffect> getStatusEffectContainer(HashMap<Entity, HashSet<StatusEffect>> map, Entity key) {
        if (!map.containsKey(key)) {
            map.put(key, new HashSet<>());
        }
        return map.get(key);
    }

    public static void applyEffect(Entity entity, StatusEffect statusEffect) {
        if (!mapInUse) {
            getStatusEffectContainer(statusMap, entity).add(statusEffect);

        } else {
            getStatusEffectContainer(addLater, entity).add(statusEffect);
        }
    }

    public static void applyEffect(Entity entity, HashSet<StatusEffect> statusEffects) {
        if (!mapInUse) {
            getStatusEffectContainer(statusMap, entity).addAll(statusEffects);

        } else {
            getStatusEffectContainer(addLater, entity).addAll(statusEffects);
        }
    }


    public static void removeEffect(Entity entity, StatusEffect statusEffect) {
        if (!mapInUse) {
            getStatusEffectContainer(statusMap, entity).remove(statusEffect);

        } else { // add to recycle-bin
            getStatusEffectContainer(removeLater, entity).add(statusEffect);
        }
    }


    public static void removeEffect(Entity entity, HashSet<StatusEffect> statusEffects) {
        if (!mapInUse) {
            getStatusEffectContainer(statusMap, entity).removeAll(statusEffects);

        } else { // add to recycle-bin
            getStatusEffectContainer(removeLater, entity).addAll(statusEffects);
        }
    }


    private static void setMapInUse(boolean b) {
        if (mapInUse & !b) {
            mapInUse = b;

            for (HashMap.Entry<Entity, HashSet<StatusEffect>> entry : removeLater.entrySet()) {
                removeEffect(entry.getKey(), entry.getValue());
            }
            removeLater.clear();

            for (HashMap.Entry<Entity, HashSet<StatusEffect>> entry : addLater.entrySet()) {
                applyEffect(entry.getKey(), entry.getValue());
            }
            addLater.clear();

        } else {
            mapInUse = b;
        }
    }

    public static void executeAllEntity(Consumer<StatusEffect> func) {
        setMapInUse(true);
        for (HashMap.Entry<Entity, HashSet<StatusEffect>> entry : statusMap.entrySet()) {
            for (StatusEffect statusEffect : entry.getValue()) {
                func.accept(statusEffect);
            }
        }
        setMapInUse(false);
    }

    public static void executeOneEntity(Entity victim, Consumer<StatusEffect> func) {
        setMapInUse(true);
        HashSet<StatusEffect> statusEffects = getStatusEffectContainer(statusMap, victim);
        for (StatusEffect statusEffect : statusEffects) {
            func.accept(statusEffect);
        }
        setMapInUse(false);
    }

    public static HashSet<StatusEffect> getStatusEffect(Entity entity) {
        return statusMap.getOrDefault(entity, new HashSet<>());
    }

    public static ArrayList<StatusEffect> findStatusEffect(Entity entity, String queryName) {
        setMapInUse(true);
        HashSet<StatusEffect> statusEffects = getStatusEffectContainer(statusMap, entity);
        ArrayList<StatusEffect> queryResult = new ArrayList<>();

        for (StatusEffect statusEffect : statusEffects) {
            if (statusEffect.getName().equals(queryName)) {
                queryResult.add((statusEffect));
            }
        }

        setMapInUse(false);

        return queryResult;
    }

    public static Runnable update() {
        setMapInUse(true);
        for (HashMap.Entry<Entity, HashSet<StatusEffect>> entry : statusMap.entrySet()) {
            Entity entity = entry.getKey();
            HashSet<StatusEffect> expired = new HashSet<>();

            CrowdControlManager.cleanse(entity); // cleanse expired CC

            for (StatusEffect statusEffect : entry.getValue()) {
                statusEffect.step();
                if (statusEffect.isExpired()) {
                    // remove after all entities processed, not per-entity clear
                    expired.add(statusEffect);
                }
            }

            // remove after all entities processed, not per-entity clear
            removeEffect(entity, expired);
        }
        setMapInUse(false);

        return null;
    }

    public static void clear() {
        statusMap.clear();
        CrowdControlManager.clear();
    }

}
