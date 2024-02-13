package nameerror.skillbuilder.Fundamental;

import nameerror.skillbuilder.Fundamental.SkillManagement.Skill;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.Entity;

public class SBComponent {
    // TODO: 2/7/2566 - Make this class to generic (i.e., <T>)
    private final PluginEntityType casterType;
    private Entity gameEntity;
    private Skill casterSkill;

    public SBComponent(PluginEntityType casterType) {
        this.casterType = casterType;
    }

    public PluginEntityType getCasterType() {
        return this.casterType;
    }

    public Entity getGameEntity() {
        return  this.gameEntity;
    }
    public void setGameEntity(Entity gameEntity) {
        this.gameEntity = gameEntity;
    }

    public Skill getCasterSkill() {
        return this.casterSkill;
    }
    public void setCasterSkill(Skill skill) {
        this.casterSkill = skill;
    }

    public Location getLocation() {
        if (!casterType.equals(PluginEntityType.SKILL)) {
            return gameEntity.getLocation();
        }
    // TODO: 23/6/2566 - locate skill location and replace this dummy code
        return new Location(Bukkit.getWorlds().get(0), 0, 0, 0);
    }
}
