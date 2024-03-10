package nameerror.skillbuilder.Testing.TestFeature;

import nameerror.skillbuilder.Animations.CustomParticle.ParticleModule.FireworkParticle;
import nameerror.skillbuilder.Fundamental.ObjectManagement.Explosion.Explosion;
import nameerror.skillbuilder.SkillBuilder;
import nameerror.skillbuilder.Testing.Test;
import nameerror.skillbuilder.Testing.TestModule;
import org.bukkit.*;
import org.bukkit.entity.Player;

public class ExplosionTest extends TestModule {

    public ExplosionTest() {
        initTest();
    }

    private void initTest() {
        this.addTestCase("basic", this::basic);
        this.addTestCase("basic_break_block", this::basicWithBreakBlock);
    }

    private Integer basic(Player player) {
        redSkill(player, 50,false, false);
        return 0;
    }

    // Cursed Technique Reversal: Red
    private Integer basicWithBreakBlock(Player player) {
        redSkill(player, 100,true, true);
        return 0;
    }

    private void redSkill(Player player, float power, boolean setFire, boolean breakBlock) {
        World world = player.getWorld();

        Location explodeLoc = player.getEyeLocation().add(player.getLocation().getDirection().multiply(2));

        world.spawnParticle(Particle.SPELL_MOB, explodeLoc,10, 1, 1, 0, 1, null, true);

        FireworkParticle fireworkParticle = new FireworkParticle(
                FireworkEffect.Type.BURST,
                Color.fromARGB(128,255,0,0),
                false, false);
        fireworkParticle.spawn(explodeLoc);

        Explosion explosion = new Explosion(player, explodeLoc,power, setFire, breakBlock);

        Bukkit.getScheduler().runTaskLater(SkillBuilder.getPlugin(), () -> {
            explosion.explode();
            Test.get("field").test("divergent", player);
        }, 2);
    }
}
