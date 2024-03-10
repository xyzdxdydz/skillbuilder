package nameerror.skillbuilder.OldCode.SkillManagement.TargetSelector;

import nameerror.skillbuilder.OldCode.SBComponent;
import nameerror.skillbuilder.OldCode.SkillManagement.TargetSelector.Enum.TargetType;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.util.Vector;

import javax.swing.text.html.parser.Entity;

/**
 * Store metadata of that target
 * @param <T>
 */
public class Target<T> {
    TargetType targetType;
    T object;
    boolean error = false;

    public Target(T target) {
        if (target instanceof Vector) {
            targetType = TargetType.DIRECTION;

        } else if (target instanceof Location) {
            targetType = TargetType.LOCATION;

        } else if (target instanceof SBComponent) {
            targetType = TargetType.SB_COMPONENT;

        } else if (target instanceof Entity) {
            targetType = TargetType.NORMAL_ENTITY;

        } else {
            Bukkit.broadcastMessage("INVALID TARGET TYPE");
            this.error = true;
        }

        if (!error) {
            this.object = target;

        }
    }

    public T getTarget() {
        return object;
    }
}
