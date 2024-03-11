package nameerror.skillbuilder.Testing.TestFeature;

import nameerror.skillbuilder.Animations.CustomParticle.ParticleMaker;
import nameerror.skillbuilder.Animations.CustomParticle.ParticleModule.FireworkParticle;
import nameerror.skillbuilder.Fundamental.ObjectManagement.Explosion.Explosion;
import nameerror.skillbuilder.Fundamental.ObjectManagement.Field.Field;
import nameerror.skillbuilder.Fundamental.ObjectManagement.Projectile.AoEProjectile;
import nameerror.skillbuilder.Fundamental.ObjectManagement.Projectile.ProjectileHitInfo;
import nameerror.skillbuilder.Fundamental.ObjectManagement.Projectile.ProjectileManager;
import nameerror.skillbuilder.Fundamental.ObjectManagement.Projectile.RegularProjectile;
import nameerror.skillbuilder.Fundamental.StatusEffect.StatusEffect;
import nameerror.skillbuilder.Fundamental.StatusEffect.StatusEffectManager;
import nameerror.skillbuilder.Math.Shape.Sphere;
import nameerror.skillbuilder.SkillBuilder;
import nameerror.skillbuilder.Testing.TestModule;
import org.bukkit.*;
import org.bukkit.block.Block;
import org.bukkit.entity.*;
import org.bukkit.event.entity.ProjectileHitEvent;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.scheduler.BukkitTask;
import org.bukkit.util.Vector;

import java.util.ArrayList;

public class ProjectileTest extends TestModule {

    public ProjectileTest() {
        initTest();
    }

    private void initTest() {
        this.addTestCase("standard_projectile", this::standardProjectile);
        this.addTestCase("aoe_projectile", this::AoEProjectile);
    }

    private Integer standardProjectile(Player player) {
        Arrow a = player.getWorld().spawn(player.getEyeLocation(), Arrow.class);

        a.setShooter(player);
        a.setRotation(player.getLocation().getYaw(), player.getLocation().getPitch());
        a.setCritical(true);
        a.setPierceLevel(10);
        a.setPickupStatus(AbstractArrow.PickupStatus.ALLOWED);
        a.setVelocity(player.getLocation().getDirection().multiply(5));

        RegularProjectile regularProjectile = new RegularProjectile(a) {
            @Override
            public void onLaunch() {
               player.addPotionEffect(new PotionEffect(PotionEffectType.SPEED, 20*10,10));
            }

            @Override
            public void onHit(ProjectileHitInfo hitInfo) {
                // velocity immune to explosion
                Vector correctVelocity = this.getVelocity().clone();
                Explosion explosion = new Explosion(player, this.getLocation(), 5, false, false);
                explosion.explode();
                // TODO; make this 'tick zeroth technique' to explosive piercing arrow.
                Bukkit.getScheduler().runTaskLater(
                    SkillBuilder.getPlugin(), () -> {
                        this.setVelocity(correctVelocity);
                        Bukkit.broadcastMessage("Tick zero");
                    }, 0);
            }

            @Override
            public void onFly() {
                FireworkParticle fireworkParticle = new FireworkParticle(
                        FireworkEffect.Type.BURST,
                        Color.fromARGB(127,127,127,255),
                        false, false);
                fireworkParticle.spawn(getLocation());
                Field field = new Field(getLocation(), new Sphere(getLocation(), 5)) {
                    @Override
                    public void applyToEntity(Entity entity) {
                        if (StatusEffectManager.findStatusEffect(entity, "Sub zero").size() == 0) {
                            StatusEffect subZero = StatusEffectTest.subZero(player, entity);
                            StatusEffectManager.applyEffect(entity, subZero);
                        }
                    }
                };
                field.setOwner(player);
                field.setIgnoreOwner(true);
                field.setApplyToEntities(true);
                field.step();
            }

            @Override
            public void onDead() {

            }
        };

        ProjectileManager.register(regularProjectile);
        return 0;
    }

    private Integer AoEProjectile(Player player) {
        ParticleMaker pp = new ParticleMaker(
                Particle.DUST_COLOR_TRANSITION,
                1,
                0,0,0,
                1,
                new Particle.DustTransition(
                        Color.fromRGB(255,0,255),
                        Color.fromRGB(255,255,255),
                        1),
                true
        );

        Sphere sphere = new Sphere(player.getLocation(), 5);
        // Hollow Purple
        AoEProjectile purpleBall = new AoEProjectile(sphere) {
            @Override
            public void onLaunch() { }

            @Override
            public void onHit(ProjectileHitInfo hitInfo) { }

            @Override
            public void onFly() {
                ArrayList<Block> blocks = this.getSetSpace().findBlocks(false);
                ArrayList<Entity> entities = this.getSetSpace().findEntities(false);

                blocks.forEach(block -> block.setType(Material.AIR));
                entities.forEach(entity -> {
                    if (entity instanceof LivingEntity) {
                        ((LivingEntity) entity).damage(10);
                        ((LivingEntity) entity).setNoDamageTicks(0);
                    }
                });
            }

            @Override
            public void onDead() { }
        };
        purpleBall.setShooter(player);
        purpleBall.setVelocity(player.getLocation().getDirection().multiply(20));

        player.sendMessage("Hollow Purple: Murasaki.");
        BukkitTask bukkitTask = Bukkit.getServer().getScheduler().runTaskTimer(
                SkillBuilder.getPlugin(), purpleBall::step, 0, 1);

        BukkitTask bukkitTask2 = Bukkit.getServer().getScheduler().runTaskTimer(
                SkillBuilder.getPlugin(), () -> {
                    ArrayList<Block> surface = sphere.getBlockOnSurface();
                    surface.forEach(block -> pp.spawn(block.getLocation().add(0.5, 0.5, 0.5)));
                },
                0, 1);

        Bukkit.getServer().getScheduler().runTaskLater(SkillBuilder.getPlugin(), () -> {
            player.sendMessage("Murasaki cancelled");
            bukkitTask.cancel();
            bukkitTask2.cancel();

        }, 20*10);

        return 0;
    }
}
