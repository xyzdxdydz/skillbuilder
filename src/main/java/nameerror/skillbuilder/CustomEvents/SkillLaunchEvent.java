package nameerror.skillbuilder.CustomEvents;

import nameerror.skillbuilder.Fundamental.SBComponent;
import nameerror.skillbuilder.Fundamental.SkillManagement.Skill;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

public class SkillLaunchEvent extends Event {

    private static final HandlerList handlers = new HandlerList();

    private final SBComponent owner;
    private final Skill skill;

    public SkillLaunchEvent(SBComponent owner, Skill skill) {
        this.owner = owner;
        this.skill = skill;
    }

    public SBComponent getOwner() {
        return owner;
    }

    public String getSkillName() {
        return skill.getName();
    }

    public HandlerList getHandlers() {
        return handlers;
    }

    public static HandlerList getHandlerList() {
        return handlers;
    }
}
