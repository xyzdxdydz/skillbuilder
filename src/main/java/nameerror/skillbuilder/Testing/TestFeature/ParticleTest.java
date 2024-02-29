package nameerror.skillbuilder.Testing.TestFeature;

import nameerror.skillbuilder.Animations.CustomParticle.FireworkParticle;
import nameerror.skillbuilder.Animations.CustomParticle.ParticleDrawer;
import nameerror.skillbuilder.Animations.CustomParticle.ParticleMaker;
import nameerror.skillbuilder.Testing.TestModule;
import org.bukkit.Color;
import org.bukkit.FireworkEffect;
import org.bukkit.Particle;
import org.bukkit.Particle.DustTransition;
import org.bukkit.entity.Player;

public class ParticleTest extends TestModule {
    public ParticleTest() {
        initTest();
    }

    private void initTest() {
        this.addTestCase("display_dust_color_trans", this::displayDust);
        this.addTestCase("straight_line", this::straightLine);
        this.addTestCase("circle", this::circle);
        this.addTestCase("firework", this::firework);
    }

    private Integer displayDust(Player player) {
        DustTransition dustTransition = new DustTransition(
                Color.fromRGB(255,0,255),
                Color.fromRGB(255,255,255),
                5);

        player.getWorld().spawnParticle(Particle.DUST_COLOR_TRANSITION,
                player.getLocation(), 2, 0,0,0, 10, dustTransition, true);
        return 0;
    }

    private Integer straightLine(Player player) {
        DustTransition dustTransition = new DustTransition(
                Color.fromRGB(255,0,255),
                Color.fromRGB(255,255,255),
                5);

        ParticleMaker pp = new ParticleMaker(
                Particle.DUST_COLOR_TRANSITION,
                1,
                0,0,0,
                1,
                dustTransition,
                false);

        ParticleDrawer.createLine(player.getEyeLocation(), 30, pp, 1);

        return 0;
    }

    private Integer circle(Player player) {
        DustTransition dustTransition = new DustTransition(
                Color.fromRGB(255,0,255),
                Color.fromRGB(255,255,255),
                5);

        ParticleMaker pp = new ParticleMaker(
                Particle.DUST_COLOR_TRANSITION,
                1,
                0,0,0,
                1,
                dustTransition,
                false);

        ParticleDrawer.createCircle(player.getEyeLocation(), 10, pp, 1);
        return 0;
    }

    private Integer firework(Player player) {
        FireworkParticle fireworkParticle = new FireworkParticle(
                FireworkEffect.Type.BURST,
                Color.fromARGB(128,255,166,255),
                false, false);

        fireworkParticle.spawn(player.getEyeLocation().add(player.getLocation().getDirection().multiply(4)));
        return 0;
    }
}
