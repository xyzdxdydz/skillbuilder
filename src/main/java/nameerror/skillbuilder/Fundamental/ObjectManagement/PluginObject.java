//package nameerror.skillbuilder.Fundamental.ObjectManagement;
//
//import nameerror.skillbuilder.Fundamental.SBComponent;
//import nameerror.skillbuilder.Fundamental.SkillManagement.SkillManager;
//import nameerror.skillbuilder.Fundamental.SkillManagement.TargetSelector.Position;
//import org.bukkit.Location;
//import org.bukkit.configuration.ConfigurationSection;
//import org.bukkit.entity.Entity;
//import org.bukkit.entity.LivingEntity;
//import org.bukkit.util.Vector;
//
//import java.util.HashSet;
//import java.util.Set;
//
//public class PluginObject {
//
//    private final SBComponent owner;
//    private final Set<SBComponent>  summonedUnit = new HashSet<>();
//    private final String name;
//    private Position initialPosition;
//
//    private final ConfigurationSection objectSection;
//    // TODO: 3/7/2566 multiple sequence machine
//    private SequenceRunnerMachine sequenceRunnerMachine;
//
//    public PluginObject(SBComponent owner, Position initPosition, ConfigurationSection objectSection) {
//        this.owner = owner;
//        this.name = objectSection.getName();
//        this.initPosition = initPosition;
//        this.objectSection = objectSection;
//    }
//    public SBComponent getOwner() {
//        return owner;
//    }
//    public Set<SBComponent> getSummonedUnit() {
//        return summonedUnit;
//    }
//    public String getName() {
//        return name;
//    }
//    public Position getInitPosition() {
//        return initPosition;
//    }
//    public ConfigurationSection getObjectSection() {
//        return objectSection;
//    }
//    public SequenceRunnerMachine getSequenceRunnerMachine() {
//        return sequenceRunnerMachine;
//    }
//    public SequenceRunnerMachine getSequenceRunner() {
//        return sequenceRunnerMachine;
//    }
//
//    public void setSequenceRunner(SequenceRunnerMachine sequenceRunnerMachine) {
//        this.sequenceRunnerMachine = sequenceRunnerMachine;
//    }
//
//    public void addChild(SBComponent sbComponent) {
//        summonedUnit.add(sbComponent);
//    }
//
//}
