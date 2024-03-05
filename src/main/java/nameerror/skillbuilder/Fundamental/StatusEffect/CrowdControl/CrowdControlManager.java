package nameerror.skillbuilder.Fundamental.StatusEffect.CrowdControl;

import org.bukkit.entity.Entity;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;

public class CrowdControlManager {
    private static final HashMap<Entity, HashSet<ControlType>> controlStatus = new HashMap<>();

    public static HashSet<ControlType> getControlProperties(Entity victim) {
        return controlStatus.getOrDefault(victim, new HashSet<>());
    }

    public static boolean removeControlProperty(Entity victim, ControlType property) {
        return controlStatus.get(victim).remove(property);
    }

    public static void handleCrowdControl(Entity victim, ArrayList<ControlType> controlProperties) {
        if (!controlStatus.containsKey(victim)) {
            controlStatus.put(victim, new HashSet<>());
        }
        controlStatus.get(victim).addAll(controlProperties);
    }

    public static boolean isInflictCC(Entity victim) {
        return controlStatus.containsKey(victim) && !controlStatus.get(victim).isEmpty();
    }

    public static boolean entityContainsCC(Entity victim, ControlType controlProperty) {
        return controlStatus.containsKey(victim) && controlStatus.get(victim).contains(controlProperty);
    }

    public static void cleanse(Entity victim) {
        if (controlStatus.containsKey(victim)) {
            controlStatus.get(victim).clear();
        }
    }

    public static void clear() {
        controlStatus.clear();
    }
}
