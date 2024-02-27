package nameerror.skillbuilder.Testing.TestFeature;

import nameerror.skillbuilder.Fundamental.ObjectManagement.AoEProjectile;
import nameerror.skillbuilder.Math.Shape.Sphere;
import nameerror.skillbuilder.SkillBuilder;
import nameerror.skillbuilder.Testing.TestModule;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitTask;

public class ProjectileTest extends TestModule {

    public ProjectileTest() {
        initTest();
    }

    private void initTest() {
        this.addTestCase("aoe_projectile", this::AoEProjectile);
    }

    private Integer AoEProjectile(Player player) {
        Sphere sphere = new Sphere(player.getLocation(), 5);
        AoEProjectile purpleBall = new AoEProjectile(sphere);
        purpleBall.setVelocity(player.getLocation().getDirection().multiply(20));

        player.sendMessage("Hollow Purple: Murasaki.");
        BukkitTask bukkitTask = Bukkit.getServer().getScheduler().runTaskTimer(
                SkillBuilder.getPlugin(),
                purpleBall::step,
                0, 1);

        Bukkit.getServer().getScheduler().runTaskLater(SkillBuilder.getPlugin(), () -> {
            player.sendMessage("Murasaki cancelled");
            bukkitTask.cancel();

        }, 20*10);

        return 0;
    }
}
