//package nameerror.skillbuilder.Fundamental.ObjectManagement;
//
//import nameerror.skillbuilder.SkillBuilder;
//import nameerror.skillbuilder.Fundamental.SkillManagement.SkillManager;
//import org.bukkit.Location;
//import org.bukkit.Material;
//import org.bukkit.NamespacedKey;
//import org.bukkit.configuration.ConfigurationSection;
//import org.bukkit.craftbukkit.v1_20_R3.entity.CraftSnowball;
//import org.bukkit.craftbukkit.v1_20_R3.inventory.CraftItemStack;
//import org.bukkit.enchantments.Enchantment;
//import org.bukkit.entity.*;
//import org.bukkit.inventory.ItemStack;
//import org.bukkit.persistence.PersistentDataContainer;
//import org.bukkit.persistence.PersistentDataType;
//
//import java.util.HashMap;
//import java.util.HashSet;
//import java.util.Set;
//
//public class PluginObjectEntity extends PluginObject implements TargetMarkable, Destroyable {
//
//    private final String propertiesSubtype = "EntitySubtype";
//
//    EntityType entityType;
//    Entity entity;
//    Set<Entity> targets = new HashSet<>();
//    Location spawnLocation;
//
//    private final long startTime = System.currentTimeMillis();
//    // name , lastCall
//    private final HashMap<String, MessageBlock> messageTable = new HashMap<>();
//
//    public PluginObjectEntity(Entity caller, SkillManager rootSkill, ConfigurationSection objectSection, PluginObject parentInfo) {
//        super(caller, rootSkill, objectSection, parentInfo);
//        setupMessage();
//        this.spawnLocation = ObjectCompiler.compileLocation(getObjectSection().getString("Location"));
//    }
//
//    public void spawnEntity() {
//        EntityType entityType = EntityType.valueOf(getObjectSection().getString("EntityType"));
//        entity = getRoot().getWorld().spawnEntity(spawnLocation, entityType);
//        entity.setPersistent(true);
//
//        PersistentDataContainer data = entity.getPersistentDataContainer();
//        data.set(new NamespacedKey(SkillBuilder.getPlugin(), "SkillBuilder"), PersistentDataType.STRING,
//                getObjectSection().getName() + ":" + getRoot().getUniqueId() + ":");
//
//        entity.setFireTicks(getObjectSection().getInt("Fire"));
//        entity.setVisualFire((getObjectSection().getBoolean("VisualFire")));
//        entity.setGravity(!getObjectSection().getBoolean("NoGravity"));
//        entity.setInvulnerable(getObjectSection().getBoolean("Invulnerable"));
//        entity.setGlowing(getObjectSection().getBoolean("Glowing"));
//
//        if (entity instanceof LivingEntity) {
//            ((LivingEntity) entity).setRemainingAir((getObjectSection().getInt("Air")));
//            ((LivingEntity) entity).setAI(!getObjectSection().getBoolean("NoAI"));
//        }
//
//        switch (entity.getType()) {
//            case SLIME:
//                ((Slime) entity).setSize(getObjectSection().getInt(propertiesSubtype + ".Size"));
//                break;
//            case SNOWBALL:
//                Material material = Material.valueOf(getObjectSection().getString(propertiesSubtype + ".Material"));
//                ItemStack itemStack = new ItemStack(material);
//                if (getObjectSection().getBoolean(propertiesSubtype + ".EnchantEffect")) {
//                    itemStack.addUnsafeEnchantment(Enchantment.BINDING_CURSE, 1);
//                }
//                ((CraftSnowball) entity).getHandle().setItem(CraftItemStack.asNMSCopy(itemStack));
//                break;
//            default:
//                break;
//        }
//
//        ObjectManager.registerEntityObject(entity, this);
//    }
//
//    public void activate() {
//
//        setSequenceRunner(new SequenceRunnerMachine(getRoot(), getRootSkill(),this, new SequenceRunnerCallback() {
//
//            @Override
//            public void onOutOfSequence() {}
//
//            @Override
//            public void onCancel() {
//                getSequenceRunner().clear();
//            }
//        }));
//
//        ConfigurationSection section = getObjectSection().getConfigurationSection("EntityInitSequence");
//        if (section != null) {
//            getSequenceRunner().load(section);
//            getSequenceRunner().start();
//        }
//    }
//
//    public void setSpawnLocation(Location spawnLocation) {
//        this.spawnLocation = spawnLocation;
//    }
//
//    public Entity getOwner() {
//        return entity;
//    }
//
//
//    public void addTarget(Entity entity) {
//        targets.add(entity);
//    }
//
//    public void removeTarget(Entity entity) {
//        targets.remove(entity);
//    }
//
//    @Override
//    public Set<Entity> getAllTargets() {
//        return targets;
//    }
//
//    @Override
//    public void setTarget(Set<Entity> entities) {
//        this.targets = entities;
//    }
//
//    @Override
//    public void addTarget(Set<Entity> entities) {
//        this.targets.addAll(entities);
//    }
//
//    @Override
//    public void removeTarget(Set<Entity> entities) {
//        for (Entity e : entities) {
//            this.targets.remove(e);
//        }
//    }
//
//    @Override
//    public void clearTarget() {
//        this.targets.clear();
//    }
//
//    private void setupMessage() {
//        String path = "Message";
//        if (!getObjectSection().isConfigurationSection(path)) {
//            return;
//        }
//
//        ConfigurationSection messageSection = getObjectSection().getConfigurationSection(path);
//        assert messageSection != null;
//        for (String m : messageSection.getKeys(false)) {
//            long initCooldown = getObjectSection().getLong(path + "." + m + ".InitCooldown");
//            long cooldown = getObjectSection().getLong(path + "." + m + ".Cooldown");
//            ConfigurationSection sequenceSection = getObjectSection().getConfigurationSection(path + "." + m + ".Sequence");
//            MessageBlock block = new MessageBlock(initCooldown, cooldown, sequenceSection);
//            messageTable.put(m, block);
//        }
//    }
//
//    public void receiveMessage(String messageName) {
////        Bukkit.broadcastMessage(String.valueOf(messageTable.keySet()));
//        if (messageTable.containsKey(messageName)) {
//            long time = System.currentTimeMillis();
//            MessageBlock block = messageTable.get(messageName);
//            if ((time - startTime)/50 >= block.getInitCooldown() && (time - block.getLastCall())/50 >= block.getCooldown()) {
//                getSequenceRunner().loadSequence(getSequenceRunner().preCompileActionSequence(getOwner(), block.response(), 0));
//                if (!getSequenceRunner().isRunning()) {
//                    getSequenceRunner().start();
//                }
//            }
//        }
//    }
//
//    @Override
//    public void destroy() {
//        ObjectManager.removeEntityObject(entity);
//        if (getSequenceRunner().isRunning()) {
//            getSequenceRunner().stop();
//        }
//        SkillManager.cancelAll(entity);
//        entity.remove();
//    }
//}
