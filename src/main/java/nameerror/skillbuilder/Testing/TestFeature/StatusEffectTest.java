package nameerror.skillbuilder.Testing.TestFeature;

import nameerror.skillbuilder.Animations.CustomParticle.FireworkParticle;
import nameerror.skillbuilder.Animations.CustomParticle.ParticleDrawer;
import nameerror.skillbuilder.Animations.CustomParticle.ParticleMaker;
import nameerror.skillbuilder.Fundamental.ObjectManagement.Field;
import nameerror.skillbuilder.Fundamental.ObjectManagement.FieldManager;
import nameerror.skillbuilder.Fundamental.ObjectManagement.LegacyEntity;
import nameerror.skillbuilder.Fundamental.StatusEffect.CrowdControl.ControlType;
import nameerror.skillbuilder.Fundamental.StatusEffect.CrowdControl.CrowdControl;
import nameerror.skillbuilder.Fundamental.StatusEffect.Debuff;
import nameerror.skillbuilder.Fundamental.StatusEffect.DoT;
import nameerror.skillbuilder.Fundamental.StatusEffect.StatusEffectManager;
import nameerror.skillbuilder.Math.SetSpace;
import nameerror.skillbuilder.Math.Shape.Sphere;
import nameerror.skillbuilder.Testing.TestModule;
import org.bukkit.*;
import org.bukkit.entity.Creeper;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.util.Vector;

import java.util.ArrayList;
import java.util.Arrays;

public class StatusEffectTest extends TestModule {

    public StatusEffectTest() { initTest(); }

    private void initTest() {
        this.addTestCase("cc_snare", this::CCSnarePlayerTest);
        this.addTestCase("cc_stun", this::CCStunPlayerTest);
        this.addTestCase("DoT_basic", this::basicDoT);
        this.addTestCase("stack_to_trigger", this::stackTriggerTest);
    }

    private void drawCircle(Location location, double radius, Color color1, Color color2) {
        Particle.DustTransition dustTransition = new Particle.DustTransition(
                color1,
                color2,
                1);
        ParticleMaker pp = new ParticleMaker(
                Particle.DUST_COLOR_TRANSITION,
                1,
                0,0,0,
                1,
                dustTransition,
                false);
        location.setDirection(new Vector(0,1,0));
        ParticleDrawer.createCircle(location, radius, pp, 8);
    }

    private Player getOnePlayer(Entity owner, SetSpace setSpace) {
        ArrayList<Entity> entities = setSpace.findEntities(false);
        Player playerVictim = null;
        for (Entity e: entities) {
            if (e instanceof Player && !e.equals(owner)) {
                playerVictim = (Player) e;
                break;
            }
        }
        return playerVictim;
    }

    private Integer CCSnarePlayerTest(Player player) {
        Location location = player.getLocation();
        double radius = 5;
        Field field = new Field(location, new Sphere(location, radius)) {
            @Override
            public void applyToEntity(Entity entity) {
                drawCircle(this.getLocation(), radius, Color.YELLOW, Color.GRAY);
                Player victim = getOnePlayer(player, setSpace);

                if (victim != null) {
                    player.sendMessage("Snared:", victim.getDisplayName());

                    CrowdControl cc = new CrowdControl(LegacyEntity.get(player), LegacyEntity.get(
                            victim), "Snare", 20*10, 1);
                    ArrayList<ControlType> controlTypes = new ArrayList<>(Arrays.asList(ControlType.values()));
                    controlTypes.remove(ControlType.DISABLE_LOOK);
                    cc.setControlProperties(controlTypes);

                    victim.sendMessage("A 'snared' effect will be removed in next 10 seconds.");

                    StatusEffectManager.applyEffect(victim, cc);
                }
            }
        };

        field.setOwner(player);
        field.setApplyToEntities(true);
        FieldManager.register(field);

        return 0;
    }

    private Integer CCStunPlayerTest(Player player) {
        Location location = player.getLocation();
        double radius = 5;
        Field field = new Field(location, new Sphere(location, radius)) {
            @Override
            public void applyToEntity(Entity entity) {
                drawCircle(this.getLocation(), radius, Color.PURPLE, Color.GRAY);
                Player victim = getOnePlayer(player, setSpace);

                if (victim != null) {
                    player.sendMessage("Stunned:", victim.getDisplayName());

                    CrowdControl cc = new CrowdControl(LegacyEntity.get(player), LegacyEntity.get(
                            victim), "Snare", 20*10, 1);
                    ArrayList<ControlType> controlTypes = new ArrayList<>(Arrays.asList(ControlType.values()));
                    cc.setControlProperties(controlTypes);

                    victim.sendMessage("A 'stun' effect will be removed in next 10 seconds.");

                    StatusEffectManager.applyEffect(victim, cc);
                }
            }
        };

        field.setOwner(player);
        field.setApplyToEntities(true);
        FieldManager.register(field);

        return 0;
    }

    private Integer stackTriggerTest(Player player) {
        Sphere sphere = new Sphere(player.getLocation(), 5);
        Player victim = getOnePlayer(player, sphere);

        if (victim != null) {
            Debuff subZero = new Debuff(LegacyEntity.get(player), LegacyEntity.get(victim),
                    "Sub zero", 20 * 10, 1, 1, 1) {
                @Override
                public void trigger() { }

                @Override
                public void onVictimDamage(EntityDamageByEntityEvent event) {
                    if (event.getEntity().equals(getVictim().getEntity())) {
                        victim.sendMessage("More frozen");
                        addStack(1, true);
                    }
                }

                @Override
                public void onStackChange() {
                    getVictim().getEntity().setFreezeTicks(35 * getStack());
                    victim.sendMessage("More frozen, Total: " + getStack());

                    if (getStack() % 5 == 0) {
                        Bukkit.broadcastMessage(ChatColor.AQUA + "Freeze for 5 secs (no sync).");
//                        setStack(1, false);

                        CrowdControl cc = new CrowdControl(getApplier(), getVictim(), "Frozen", 20 * 5, 1);
                        ArrayList<ControlType> controlTypes = new ArrayList<>(Arrays.asList(ControlType.values()));
                        cc.setControlProperties(controlTypes);

                        FireworkParticle fireworkParticle = new FireworkParticle(
                                FireworkEffect.Type.BURST,
                                Color.AQUA,
                                false, false);
                        fireworkParticle.spawn(getVictim().getLocation());

                        StatusEffectManager.applyEffect(getVictim().getEntity(), cc);
                    }
                }
            };

            StatusEffectManager.applyEffect(victim, subZero);
        }
        return 0;
    }

    private Integer basicDoT(Player player) {
        Location spawnLoc = player.getEyeLocation().add(player.getLocation().getDirection().multiply(5));
        Creeper creeper = player.getWorld().spawn(spawnLoc, Creeper.class);
        DoT dot = new DoT(LegacyEntity.get(player), LegacyEntity.get(creeper), "Electric",
                20*8,20,1,1, 2);

        StatusEffectManager.applyEffect(creeper, dot);
        return 0;
    }
}
