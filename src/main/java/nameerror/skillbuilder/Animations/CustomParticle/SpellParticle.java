package nameerror.skillbuilder.Animations.CustomParticle;

import org.bukkit.Color;
import org.bukkit.Location;
import org.bukkit.Particle;

public class SpellParticle implements ColorizedParticle {
    private Color color;
    private final Particle particle;
    private final boolean force;

    public SpellParticle(Color color, boolean transparent, boolean forceDisplay) {
        this.color = color;
        this.force = forceDisplay;
        this.particle = transparent ? Particle.SPELL_MOB_AMBIENT : Particle.SPELL_MOB;
    }

    @Override
    public Color getColor() {
        return this.color;
    }

    @Override
    public void setColor(Color color) {
        this.color = color;
    }

    // module: colored spell
    // this is exactly 0 offsets
    // count: Must be set to 0, this enabled colorized particles, otherwise it will random color.
    // extra: This controls the brightness of the particle's color, generally you just want to set this to 1.
    // offsetX: Pass a value from 0 to 1, R channel
    // offsetY: Pass a value from 0 to 1, G channel
    // offsetZ: Pass a value from 0 to 1, B channel
    // ambient == transparent in terms of brightness
    @Override
    public void spawn(Location location) {
        double red = color.getRed() / 255.0;
        double green = color.getGreen() / 255.0;
        double blue = color.getBlue() / 255.0;

        location.getWorld().spawnParticle(particle, location, 0, red, green, blue, 1, null, force);
    }
}
