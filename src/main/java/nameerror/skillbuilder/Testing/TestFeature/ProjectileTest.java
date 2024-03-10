package nameerror.skillbuilder.Testing.TestFeature;

import nameerror.skillbuilder.Animations.CustomParticle.ParticleMaker;
import nameerror.skillbuilder.Fundamental.ObjectManagement.Projectile.AoEProjectile;
import nameerror.skillbuilder.Math.Shape.Sphere;
import nameerror.skillbuilder.SkillBuilder;
import nameerror.skillbuilder.Testing.TestModule;
import org.bukkit.Bukkit;
import org.bukkit.Color;
import org.bukkit.Material;
import org.bukkit.Particle;
import org.bukkit.block.Block;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitTask;

import java.util.ArrayList;

public class ProjectileTest extends TestModule {

    public ProjectileTest() {
        initTest();
    }

    private void initTest() {
        this.addTestCase("aoe_projectile", this::AoEProjectile);
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
            public void onHit() { }

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
        };
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
