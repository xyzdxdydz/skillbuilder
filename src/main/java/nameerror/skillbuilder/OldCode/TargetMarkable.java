package nameerror.skillbuilder.OldCode;

import org.bukkit.entity.Entity;

import java.util.Set;

public interface TargetMarkable {
    Set<Entity> getAllTargets();

    void setTarget(Set<Entity> entities);

    void addTarget(Set<Entity> entities);

    void removeTarget(Set<Entity> entities);

    void clearTarget();

    SBComponent getSource();
}
