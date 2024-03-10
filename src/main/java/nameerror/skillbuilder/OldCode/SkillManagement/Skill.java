package nameerror.skillbuilder.OldCode.SkillManagement;

import nameerror.skillbuilder.Configuration.PluginConfiguration;
import nameerror.skillbuilder.CustomEvents.SkillLaunchEvent;
import nameerror.skillbuilder.OldCode.SequenceRunnerCallback;
import nameerror.skillbuilder.OldCode.PluginEntityType;
import nameerror.skillbuilder.OldCode.SequenceRunnerMachine;
import nameerror.skillbuilder.OldCode.TargetMarkable;
import nameerror.skillbuilder.OldCode.SBComponent;
import nameerror.skillbuilder.SkillBuilder;
import nameerror.skillbuilder.Verbose;
import org.bukkit.Bukkit;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.Entity;

import java.util.HashSet;
import java.util.Set;

public class Skill implements TargetMarkable, SequenceRunnerCallback {

    private final SBComponent owner;
    private final String name;
    private final Long startTime;
    // TODO: 25/6/2566 upgrade to multiple sequence runner
    private SequenceRunnerMachine sequenceRunnerMachine;
    private final ConfigurationSection actionSequence;
    private Set<Entity> targets;
    private SequenceRunnerMachine targetSelector;
    private final Set<SBComponent> summonedUnit = new HashSet<>();
    // TODO: 27/6/2566 if skill was cancelled -> cancel all sequence machines 
    private final boolean isCancel = false;

    public Skill(SBComponent owner, String skillName) {
        this.owner = owner;
        this.name = skillName;
        this.startTime = System.currentTimeMillis();
        this.actionSequence = getActionSequence();
    }

    private ConfigurationSection getActionSequence() {

        PluginConfiguration skillList = SkillBuilder.getPlugin().getConfigDatabase("SkillConfig");
        String path = this.name;
        // only used for showing an error
        String pathString = skillList.getDirPath().replace("\\", "/") + "/" + skillList.getFileName();

//        Snowball sn = (Snowball) owner.getGameEntity().getWorld().spawnEntity(owner.getGameEntity().getLocation(), EntityType.SNOWBALL);

        if (!skillList.isConfigurationSection(path)) {
            if (owner.getCasterType().equals(PluginEntityType.PLAYER)) {
                owner.getGameEntity().sendMessage(Verbose.skillSectionNotFound(pathString));
            }

            return null;
        }

        if (owner.getCasterType().equals(PluginEntityType.PLAYER)) {
            owner.getGameEntity().sendMessage(Verbose.skillLaunch(name));
        }


        path = name + ".Sequence";
        if (!skillList.isConfigurationSection(path)) {
            if (owner.getCasterType().equals(PluginEntityType.PLAYER)) {
                owner.getGameEntity().sendMessage(Verbose.skillSequenceError(name, pathString));
            }
            return null;
        }

        return skillList.getConfigurationSection(path);
    }

    public String getName() {
        return this.name;
    }

    public SBComponent getOwner() {
        return this.owner;
    }

    public Long getStartTime() {
        return this.startTime;
    }

    public SequenceRunnerMachine getSequenceRunner() {
        return this.sequenceRunnerMachine;
    }

    @Override
    public Set<Entity> getAllTargets() {
        return this.targets;
    }

    @Override
    public void setTarget(Set<Entity> entities) {
        this.targets = entities;
    }

    @Override
    public void addTarget(Set<Entity> entities) {
        this.targets.addAll(entities);
    }

    @Override
    public void removeTarget(Set<Entity> entities) {
        for (Entity e : entities) {
            this.targets.remove(e);
        }
    }

    @Override
    public void clearTarget() {
        this.targets.clear();
    }

    @Override
    public SBComponent getSource() {
        return this.getOwner();
    }

    public void launch() {
        Bukkit.getServer().getPluginManager().callEvent(new SkillLaunchEvent(this.owner, this));

        SkillManager.register(this.getOwner(), this);
        SequenceRunnerMachine sequenceMachine = new SequenceRunnerMachine(this.owner, this);

        sequenceMachine.load(actionSequence);
        if (!sequenceMachine.isOutOfSequence()) {
            sequenceMachine.start();

        } else {
            SkillManager.unregister(this.getOwner(), this);
        }
    }

    @Override
    public void onOutOfSequence() {
        SkillManager.unregister(this.getOwner(), this);
    }

    @Override
    public void onCancel() {
        SkillManager.unregister(this.getOwner(), this);
    }
}